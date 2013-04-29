package wekaexamples.core.converters;

import weka.classifiers.functions.LibSVM;
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
public class SVMTest {

	private static final String TESTING_DATASET = "spambase_testing_1_1.arff";
	private static final String TRAINING_DATASET = "spambase_training_1_1.arff";
	private static final String DATA_DIR_PATH = "C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\";
	private LibSVM svm;
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
		SVMTest svmTest=new SVMTest();
		svmTest.testDataSet(TESTING_DATASET, svmTest.trainDataSet(TRAINING_DATASET));
	}

	public void testDataSet(String fileName, LibSVM svm) {

		try {
			String sourceSpamBaseArff = DATA_DIR_PATH + fileName;
			arffLoader = new ArffLoader();
			arffLoader.setSource(new File(sourceSpamBaseArff));

			Instances instances = arffLoader.getDataSet();
			instances.setClassIndex(instances.numAttributes() - 1);

			System.out.println("\n\n" + instances);

			Instance firstInstance = instances.firstInstance();

			double d1 = svm.classifyInstance(firstInstance);
			System.out.println("Result:" + d1);

			Instance lastInstance = instances.lastInstance();
			d1 = svm.classifyInstance(lastInstance);
			System.out.println("Result:" + d1);

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}

	}

	public LibSVM trainDataSet(String fileName) {
		try {
			String sourceSpamBaseArff = DATA_DIR_PATH + fileName;
			ArffLoader arffLoader = new ArffLoader();
			arffLoader.setSource(new File(sourceSpamBaseArff));

			Instances instances = arffLoader.getDataSet();
			instances.setClassIndex(instances.numAttributes() - 1);

			//System.out.println("\n\n" + instances);

			svm = new LibSVM();
			svm.buildClassifier(instances);
			
			System.out.println("\n===Classifier is built===");

		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
		return svm;
	}

}