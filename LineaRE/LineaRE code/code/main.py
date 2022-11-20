import data
import traintest
import utils
from configure import config

if __name__ == '__main__':
	utils.set_logger()

	#测试FB15k数据集
	config.setting('config/config_FB15k.json')
	#测试FB15k-237数据集
	# config.set_log("./config/config_FB15k-237.json")
	#测试WN18数据集
	# config.set_log("config/config_WN18.json")
	#测试WN18RR数据集
	# config.set_log("config/config_WN18RR.json")

	tt = traintest.TrainTest(data.KG())
	# train用于判定对称关系、非对称关系、反转关系、组合关系
	tt.train()
	tt.test()
