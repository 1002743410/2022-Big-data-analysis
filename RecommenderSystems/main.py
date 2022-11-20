import math
import pandas as pd
import csv

"""
读取文件数据并将userId、rating、movieId整合成一个新的文件存储到data中
"""


def data_load():
    # 读取movies文件，设置列名为’movieId', 'title', 'genres'
    movies = pd.read_csv("F:/Python/RecommenderSystems/inputs/movies.csv", names=['movieId', 'title', 'genres'])
    # 读取ratings文件，设置列名为'userId', 'movieId', 'rating', 'timestamp'
    ratings = pd.read_csv("F:/Python/RecommenderSystems/inputs/ratings.csv",names=['userId', 'movieId', 'rating', 'timestamp'])
    # 通过两数据集之间的 movieId 连接
    data = pd.merge(movies, ratings, on='movieId')
    # 保存'userId', 'rating', 'movieId', 'title'为data数据集
    data[['userId', 'movieId', 'rating', ]].to_csv("F:/Python/RecommenderSystems/outputs/data.csv", index=False)





"""
计算用户间相似度
"""


def user_similarity(data_dict):
    # 建立电影-用户的倒排表

    # 数据格式：key:电影 value:用户1，用户2
    movieId_userId = dict()
    for userId, movieId in data_dict.items():
        # 遍历用户对应的电影数据
        for movieid in movieId.keys():
            # 倒排表还没有该电影
            if movieid not in movieId_userId:
                # 倒排表中该物品项赋值为set()集合
                movieId_userId[movieid] = set()
            # 倒排表中该电影添加该用户
            movieId_userId[movieid].add(userId)

    # 计算用户-用户相关性矩阵

    uuMatrix = dict()  # 用户-用户共现矩阵
    umMatrix = dict()  # 用户对应的电影个数

    # 遍历电影-用户的倒排表，取得电影-用户数据
    for movieId, userId in movieId_userId.items():
        # 遍历电影movieId下的用户
        for userid1 in userId:
            # 初始化用户对应的电影个数0
            umMatrix.setdefault(userid1, 0)
            # 遍历到该用户加1
            umMatrix[userid1] += 1

            # 用户-用户共现矩阵初始化
            uuMatrix.setdefault(userid1, {})

            # 遍历该电影对应的所有用户
            for userid2 in userId:
                # 若该项为当前用户，跳过
                if userid1 == userid2:
                    continue
                # 遍历到其他不同用户则加1
                # 初始化为0
                uuMatrix[userid1].setdefault(userid2, 0)
                # 矩阵值加1
                uuMatrix[userid1][userid2] += 1

    # 计算用户之间的相似度（余弦相似度）

    similarityMatrix = dict() # 用户相似度矩阵

    # 遍历用户-用户共现矩阵的所有项
    for userid1, other_users in uuMatrix.items():
        # 存放用户间相似度
        similarityMatrix.setdefault(userid1, {})
        # 遍历其他每一个用户及对应的共现矩阵的值，即分子部分
        for userid2, cuv in other_users.items():
            # 计算用户之间的余弦相似度
            similarityMatrix[userid1][userid2] = cuv / math.sqrt(umMatrix[userid1] * umMatrix[userid2])

    similarityMatrix_sort = dict() #排序后的用户相似度矩阵

    # 对用户user按相似度从大到小进行排列
    for user_similar in similarityMatrix.items():
        similarityMatrix_sort[user_similar[0]]=dict(sorted(user_similar[1].items(),key=lambda x:x[1],reverse=True))

    # 返回用户相似度矩阵
    return similarityMatrix_sort


"""
根据与当前用户相似度最高的前userNum位用户评分记录，按降序排列，向用户推荐其还未观看的评分最高的前reultNum部电影
"""


def recommend(data_dict,similarityMatrix,userId,userNum,resultNum):
    movieRank = dict() # 用户user对电影的评分值排序
    seenmovie = data_dict[userId].keys() # 用户user已经观看过的电影

    i=0
    # 取与用户user相似度最大的前userNum个用户
    for otheruserid,similarity in similarityMatrix[userId].items():
        # 遍历每一步电影、用户对该电影的评分值
        for movieid, rating in data_dict[otheruserid].items():
            # 若用户user对电影movieid已有评价，则跳过
            if movieid in seenmovie:
                continue
            # 计算用户user对电影movieId的评分值
            # 初始化该值为0
            movieRank.setdefault(movieid, 0)
            # 通过与其相似用户对电影movieid的评分相乘
            movieRank[movieid] =similarity*rating

        i+=1
        if i>=userNum:
            break

    # 按评分值大小，为用户user推荐前resultNum部电影
    movieRank=dict(sorted(movieRank.items(), key=lambda x: x[1], reverse=True)[0:resultNum])

    return movieRank


if __name__ == '__main__':

    #读取数据，获得data.csv文件
    data_load()
    #movie.csv表头
    header=["USERID","MOVIEID"]
    # 新建一个data字典存放每位用户评论的电影和评分, 如果字典中没有某位用户，则使用用户ID来创建这位用户,否则直接添加以该用户ID为key字典中
    data = dict()

    # 读取data.csv文件，并获取data数据
    with open("F:/Python/RecommenderSystems/outputs/data.csv", 'r', encoding='UTF-8') as file1:
        for line in file1.readlines():
            userId, movieId, rating = line.strip().split(",")
            # 用户-电影评分矩阵
            data.setdefault(userId, {})
            # 分数赋值
            data[userId][movieId] = eval(rating)

    #计算用户相似度，得到相似度矩阵
    similarityMatrix = user_similarity(data)

    # 未排序的movie.csv文件
    with open("F:/Python/RecommenderSystems/outputs/movie_temp.csv", 'w', newline="", encoding='UTF-8') as file2:
        writer = csv.writer(file2)
        # 写表头
        writer.writerow(header)
        # 电影推荐
        for userId in data.keys():
            movieRank = recommend(data, similarityMatrix, userId, 5, 10)
            for movieid in movieRank:
                writer.writerow([int(userId), movieid])

    # 按USERId对movie.csv进行排序
    movie=pd.read_csv("F:/Python/RecommenderSystems/outputs/movie_temp.csv")
    movie.sort_values('USERID',axis=0).to_csv("F:/Python/RecommenderSystems/outputs/movie.csv", index=False)

