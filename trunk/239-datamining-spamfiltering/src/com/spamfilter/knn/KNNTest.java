package com.spamfilter.knn;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import java.io.File;

/**
 * Loads the data from the CSV file provided as first parameter.
 * 
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class KNNTest {

	private static final String TESTING_DATASET = "spambase_testing_1_1-KNN.arff";
	private static final String TRAINING_DATASET = "spambase_training_1_1-KNN.arff";
	private static final String DATA_DIR_PATH = "C:\\Users\\Ax\\Desktop\\KNNDatasets\\";
	private IBk knn;
	private ArffLoader arffLoader;

	public static void main(String[] args) throws Exception {
		// output usage
		// if (args.length != 1) {
		// System.err.println("\nUsage: java LoadDataFromCsvFile <file>\n");
		// System.exit(1);
		// }

		// System.out.println("\nReading file " + args[0] + "...");
//		String sourceSpamCharArff = "C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\spamchar.arff";
//		String sourceSpamBaseArff = "C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\spambase.arff";

		// CSVLoader loader = new CSVLoader();
		// //loader.setSource(new File(args[0]));
		// loader.setSource(new File(source));
		KNNTest svmTest=new KNNTest();
		svmTest.testDataSet(TESTING_DATASET, svmTest.trainDataSet(TRAINING_DATASET));
	}

	public void testDataSet(String fileName, IBk knn) {

		try {
			String sourceSpamBaseArff = DATA_DIR_PATH + fileName;
			arffLoader = new ArffLoader();
			arffLoader.setSource(new File(sourceSpamBaseArff));
			
			Classifier knnibk;

			Instances instances = arffLoader.getDataSet();
			instances.setClassIndex(instances.numAttributes() - 1);

			System.out.println("\n\n" + instances);

			Instance firstInstance = instances.firstInstance();
			knn.classifyInstance(firstInstance);
			double d1 = knn.classifyInstance(firstInstance);
			System.out.println("Result:" + d1);
			
			knnibk=new IBk(5);

			Instance lastInstance = instances.lastInstance();
			d1 = knn.classifyInstance(lastInstance);
			System.out.println("Result:" + d1);
			
			

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}

	}

	public IBk trainDataSet(String fileName) {
		try {
			String sourceSpamBaseArff = DATA_DIR_PATH + fileName;
			ArffLoader arffLoader = new ArffLoader();
			arffLoader.setSource(new File(sourceSpamBaseArff));

			Instances instances = arffLoader.getDataSet();
			instances.setClassIndex(instances.numAttributes() - 1);

			//System.out.println("\n\n" + instances);

			knn = new IBk();
			knn.buildClassifier(instances);
			
			System.out.println("\n===Classifier is built===");

		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
		return knn;
	}

}