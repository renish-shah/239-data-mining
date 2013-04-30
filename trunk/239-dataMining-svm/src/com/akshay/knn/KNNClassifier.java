package com.akshay.knn;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
public class KNNClassifier {

	public static void main(String[] args){
		DataSource source;
		DataSource test;
		try {
			source = new DataSource("C:\\Users\\Ax\\Desktop\\KNNDatasets\\spambase_training_1_1-KNN.arff");
			test = new DataSource("C:\\Users\\Ax\\Desktop\\KNNDatasets\\spambase_testing_1_1-KNN.arff");
			Instances data = source.getDataSet();
			Instances testing = test.getDataSet();
			 // setting class attribute if the data format does not provide this information
			 // For example, the XRFF format saves the class attribute information as well
			 if (data.classIndex() == -1)
			   data.setClassIndex(data.numAttributes() - 1);
			 
			 if (testing.classIndex() == -1)
				   testing.setClassIndex(testing.numAttributes() - 1);
			 
			 String[] options = weka.core.Utils.splitOptions("-K 7");
			 IBk knn = new IBk();
			 knn.setOptions(options);
			 knn.buildClassifier(data);
			 
			 Evaluation knntest = new Evaluation(testing);
			 knntest.evaluateModel(knn,testing);
			 
			 System.out.println(knntest.toSummaryString());
			 System.out.println(knntest.toMatrixString());
			 
			 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

	}

}