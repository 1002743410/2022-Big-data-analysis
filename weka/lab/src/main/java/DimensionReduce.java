import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.PrincipalComponents;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.Standardize;

/** 降维 */
public class DimensionReduce {
    public static Instances datapreprocessing(Instances instances) throws Exception {

        /** 应用过滤器进行数据预处理 */
        //缺失值处理
        Filter replaceMissingValues = new ReplaceMissingValues();
        replaceMissingValues.setInputFormat(instances);
        instances = Filter.useFilter(instances, replaceMissingValues);
        //标准化
        Filter standardize = new Standardize();
        standardize.setInputFormat(instances);
        //规范化
        instances = Filter.useFilter(instances, standardize);
        Filter normalize = new Normalize();
        normalize.setInputFormat(instances);
        instances = Filter.useFilter(instances, normalize);

        return instances;
    }

    /** PCA */
    public static String PCA(Instances instances,int num) throws Exception {
        Ranker ranker = new Ranker();
        ranker.setNumToSelect(num);
        PrincipalComponents pca=new PrincipalComponents();
        pca.setMaximumAttributeNames(ranker.getNumToSelect());
        pca.buildEvaluator(instances);

        return pca.toString();
    }
}
