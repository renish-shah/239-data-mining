package com.spamfilter.test;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SVMTest {

	private static final String TESTING_DATASET = "TRAIN_SPAM_RENISH.arff";
	private static final String TRAINING_DATASET = "TEST_SPAM_RENISH.arff";
	private static final String DATA_DIR_PATH = "C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\";
	private LibSVM svm;
	private ArffLoader arffLoader;

	public static void main(String[] args) throws Exception {

		// CSVLoader loader = new CSVLoader();
		// //loader.setSource(new File(args[0]));
		// loader.setSource(new File(source));
		SVMTest svmTest = new SVMTest();
		svmTest.testDataSet(TESTING_DATASET,
				svmTest.trainDataSet(TRAINING_DATASET));
	}

	public List<String> testDataSet(String fileName, LibSVM svm) {
        List<String> values=new ArrayList<String>();
		try {
			double startTime=System.currentTimeMillis();
			String sourceSpamBaseArff = fileName;
			arffLoader = new ArffLoader();
			arffLoader.setSource(new File(sourceSpamBaseArff));

			Instances instances = arffLoader.getDataSet();
			instances.setClassIndex(instances.numAttributes() - 1);
			
			Evaluation evaluation=new Evaluation(instances);
			evaluation.evaluateModel(svm, instances);
			System.out.println(evaluation.toSummaryString());

			values.add("=== RESULTS FOR SVM ===");
			values.add("Correctly Classified Instances:"+evaluation.correct());
			values.add("Correctly Classified Instances(%):"+evaluation.pctCorrect());
			values.add("Incorrectly Classified Instances:"+evaluation.incorrect());
			values.add("Incorrectly Classified Instances(%):"+evaluation.pctIncorrect());
            values.add("Total Time taken to classify:"+(System.currentTimeMillis()-startTime)+" ms");
			return values;

//			for (int i = 0; i < instances.numInstances(); i++) {
//
//				double classify = svm.classifyInstance(instances.instance(i));
//				double eval=evaluation.evaluateModelOnce(svm, instances.instance(i));
//				evaluation.
//				System.out.println("Result using classifyInstance:" + classify);
//				System.out.println("Result using evaluateModelOnce:" + eval);
//				// System.out.println("\n\n" + instances);
//
//			}
			// Instance firstInstance = instances.firstInstance();

			// Instance lastInstance = instances.lastInstance();
			// d1 = svm.classifyInstance(lastInstance);
			// System.out.println("Result:" + d1);

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
		return values;

	}

	public LibSVM trainDataSet(String fileName) {
		try {
			String sourceSpamBaseArff = fileName;
			ArffLoader arffLoader = new ArffLoader();
			arffLoader.setSource(new File(sourceSpamBaseArff));

			Instances instances = arffLoader.getDataSet();
			instances.setClassIndex(instances.numAttributes() - 1);

			// System.out.println("\n\n" + instances);

			svm = new LibSVM();
			svm.buildClassifier(instances);

			System.out.println("\n===Classifier is built===");

		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}
		return svm;
	}

}