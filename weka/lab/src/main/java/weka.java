import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;
import weka.filters.unsupervised.attribute.Standardize;

public class weka {
    public static void main(String[] args) throws Exception {
        /** 加载数据 */
        Instances data_dimensionReduce= ConverterUtils.DataSource.read("F:/Java/weka/lab1/src/dataset/dimension_reduce/diabetes.arff");//降维数据
        Instances data_classTrain= ConverterUtils.DataSource.read("F:/Java/weka/lab1/src/dataset/classification/spambase.train.arff");//分类数据训练集
        Instances data_classTest= ConverterUtils.DataSource.read("F:/Java/weka/lab1/src/dataset/classification/spambase.test.arff");//分类数据测试集
        Instances data_clustering= ConverterUtils.DataSource.read("F:/Java/weka/lab1/src/dataset/cluster/bank_data.arff");//聚类数据

        /** 数据预处理 */
        data_dimensionReduce=DimensionReduce.datapreprocessing(data_dimensionReduce);
        data_clustering=DimensionReduce.datapreprocessing(data_clustering);

        /** 降维 */
        //System.out.println(DimensionReduce.datapreprocessing(data_dimensionReduce,5));//PCA

        /** 分类 */
        //System.out.println(Classification.classification_J48(data_classTrain,data_classTest));//J48
        //System.out.println(Classification.classification_NativeByes(data_classTrain,data_classTest));//NativeByes

        /** 聚类 */
    System.out.println(Cluster.cluster_kMeans(data_clustering,6));//kMeans
        //System.out.println(Cluster.cluster_EM(data_clustering,6));//EM
    }
}
