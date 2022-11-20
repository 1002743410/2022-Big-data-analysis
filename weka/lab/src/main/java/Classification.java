import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Debug;
import weka.core.Instances;

import java.util.Arrays;
import java.util.Random;

/** 分类 */
public class Classification {
    /** J48 */
   /* public static String classification_J48(Instances trainInstances,Instances testInstances) throws Exception {
        J48 j48 = new J48();
        trainInstances.setClassIndex(trainInstances.numAttributes() - 1);
        testInstances.setClassIndex(testInstances.numAttributes() - 1);
        j48.buildClassifier(trainInstances);

        Evaluation evaluation = new Evaluation(trainInstances);
        evaluation.evaluateModel(j48, testInstances);

        return evaluation.toSummaryString("",true);
    }*/

    /** NativeByes */
    public static String classification_NativeByes(Instances trainInstances, Instances testInstances) throws Exception {
        Classifier nativeByes=new NaiveBayes();
        trainInstances.setClassIndex(trainInstances.numAttributes() - 1);
        testInstances.setClassIndex(testInstances.numAttributes() - 1);
        nativeByes.buildClassifier(trainInstances);

        Evaluation evaluation=new Evaluation(trainInstances);
        evaluation.evaluateModel(nativeByes,testInstances);

        return evaluation.toSummaryString("",true);
    }
}
