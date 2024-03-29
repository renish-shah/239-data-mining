package com.spamfilter.knn;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import com.spamfilter.svm.SVMEngine;

public class KNNClassifier {

	public List<String> runKnn(String dataDirectory, String fileName,
			boolean isSpam) {
		DataSource source;
		DataSource test;
		try {
			double startTime = System.currentTimeMillis();

			if (isSpam) {
				source = new DataSource(SVMEngine.PROJECT_SVM_DATA_DIRECTORY
						+ SVMEngine.TRAIN_SPAM_DATASET + "_"
						+ SVMEngine.fileCounter + ".arff");
				test = new DataSource(SVMEngine.PROJECT_SVM_DATA_DIRECTORY
						+ SVMEngine.TEST_SPAM_DATASET + "_"
						+ SVMEngine.fileCounter + ".arff");

			} else {
				source = new DataSource(SVMEngine.PROJECT_SVM_DATA_DIRECTORY
						+ SVMEngine.TRAIN_NONSPAM_DATASET + "_"
						+ SVMEngine.fileCounter + ".arff");
				test = new DataSource(SVMEngine.PROJECT_SVM_DATA_DIRECTORY
						+ SVMEngine.TEST_NONSPAM_DATASET + "_"
						+ SVMEngine.fileCounter + ".arff");

			}
			Instances sourceInstances = source.getDataSet();
			Instances testInstances = test.getDataSet();
			// setting class attribute if the data format does not provide this
			// information
			// For example, the XRFF format saves the class attribute
			// information as well
			if (sourceInstances.classIndex() == -1)
				sourceInstances
						.setClassIndex(sourceInstances.numAttributes() - 1);

			if (testInstances.classIndex() == -1)
				testInstances.setClassIndex(testInstances.numAttributes() - 1);

			String[] options = weka.core.Utils.splitOptions("-K 7");
			IBk knn = new IBk();
			knn.setOptions(options);
			knn.buildClassifier(sourceInstances);

			Evaluation evaluation = new Evaluation(testInstances);
			evaluation.evaluateModel(knn, testInstances);

			System.out.println(evaluation.toSummaryString());
			// System.out.println(evaluation.toMatrixString());

			List<String> values = new ArrayList<String>();
			values.add("=== RESULTS FOR KNN ===");
			values.add("Correctly Classified Instances:" + evaluation.correct());
			values.add("Correctly Classified Instances(%):"
					+ evaluation.pctCorrect());
			values.add("Incorrectly Classified Instances:"
					+ evaluation.incorrect());
			values.add("Incorrectly Classified Instances(%):"
					+ evaluation.pctIncorrect());
			values.add("Total Time taken to classify:"
					+ (System.currentTimeMillis() - startTime) + " ms");

			return values;

		} catch (Exception e) {
			System.out.println("Exception in runKnn :"+e);
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
		new KNNClassifier().runKnn(null, null, true);
	}

}