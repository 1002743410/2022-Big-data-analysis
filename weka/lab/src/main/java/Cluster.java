import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

/** 聚类 */
public class Cluster {
    /** SimpleKMeans */
    public static String cluster_kMeans(Instances instances,int num) throws Exception {

        instances=DimensionReduce.datapreprocessing(instances);

        SimpleKMeans kMeans = new SimpleKMeans();
        kMeans.setNumClusters(num);
        kMeans.buildClusterer(instances);
        return kMeans.toString();
    }

    /** EM */
    public static String cluster_EM(Instances instances,int num) throws Exception {

        instances=DimensionReduce.datapreprocessing(instances);

        EM em=new EM();
        em.setNumClusters(num);
        em.buildClusterer(instances);
        return em.toString();
    }
}
