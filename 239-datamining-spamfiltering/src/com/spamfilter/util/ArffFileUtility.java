/**
 * 
 */
package com.spamfilter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.spamfilter.domain.Word;

/**
 * @author renis
 * 
 */
public class ArffFileUtility {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// new ArffFileUtility().writeToArffFile("renish_test","fdd");

	}

	// private String filePath =
	// "C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\";

	public static boolean createContentForTrainingFile(String filePath,
			String fileName) {
		StringBuilder content1 = new StringBuilder();
		HashMap<String, List<HashMap<String, Word>>> globalMapNext = SVMEngine.globalMapNextTraining;
		HashMap<String, String> globalHeader = SVMEngine.globalHeaderTraining;
		// globalMap.

		content1.append("@relation svm_training");
		content1.append("\n\n");
		String[] attributeValues = globalHeader.values().toArray(
				new String[globalHeader.size()]);

		for (int i = 0; i < attributeValues.length; i++) {
			content1.append("\n@attribute word_freq_" + attributeValues[i]
					+ " numeric");
		}

		content1.append("\n@attribute is_spam {0,1}");
		content1.append("\n\n@data");
		content1.append("\n");

		// writeToArffFile(filePath, fileName, content1);
		// content1 = null;
		// content1 = new StringBuilder();

		System.out.println("Done");

		System.out.println("\n=======Content=========");
		// System.out.println("\n" + content1.toString());

		// for (int i = 0; i < globalMapNext.size(); i++) {
		String[] emailsKey = globalMapNext.keySet().toArray(
				new String[globalMapNext.size()]);
		for (int emailCount = 0; emailCount < emailsKey.length; emailCount++) {
			System.out.println("|||" + emailsKey[emailCount] + "|||");

			List<HashMap<String, Word>> list = globalMapNext
					.get(emailsKey[emailCount]);
			for (HashMap<String, Word> hashmap : list) {
				for (int attValCount = 0; attValCount < attributeValues.length; attValCount++) {
					Word word = hashmap.get(attributeValues[attValCount]);
					if (word != null) {

						// System.out.print(word.count+",");
						content1.append(word.count).append(",");

					} else {
						// System.out.print("0,");
						content1.append("0");
						content1.append(",");
					}

				}

			}
			// System.out.print("0\n");
			content1.append("0");
			content1.append("\n");
		}

		// }
		// 0 for SPAM, 1 for HAM
		return writeToArffFile(filePath, fileName, content1);

	}

	public static boolean createContentForTestingFile(String filePath,
			String fileName) {
		StringBuilder content1 = new StringBuilder();
		HashMap<String, List<HashMap<String, Word>>> globalMapNextTesting = SVMEngine.globalMapNextTesting;
		HashMap<String, String> globalHeaderTraining = SVMEngine.globalHeaderTraining;
		HashMap<String, String> globalHeaderTesting = SVMEngine.globalHeaderTesting;
		// globalMap.

		content1.append("@relation svm_testing");
		content1.append("\n\n");
		String[] attributeValues = globalHeaderTraining.values().toArray(
				new String[globalHeaderTraining.size()]);

		for (int i = 0; i < attributeValues.length; i++) {
			content1.append("\n@attribute word_freq_" + attributeValues[i]
					+ " numeric");
		}

		content1.append("\n@attribute is_spam {0,1}");
		content1.append("\n\n@data");
		content1.append("\n");

//		writeToArffFile(filePath, fileName, content1);
//		content1 = null;
//		content1 = new StringBuilder();

		System.out.println("Done");

		System.out.println("\n=======Content=========");
		// System.out.println("\n" + content1.toString());

		// for (int i = 0; i < globalMapNext.size(); i++) {
		String[] emailsKey = globalMapNextTesting.keySet().toArray(
				new String[globalMapNextTesting.size()]);
		for (int emailCount = 0; emailCount < emailsKey.length; emailCount++) {
			System.out.println("|||" + emailsKey[emailCount] + "|||");

			List<HashMap<String, Word>> list = globalMapNextTesting
					.get(emailsKey[emailCount]);
			for (HashMap<String, Word> hashmap : list) {
				for (int attValCount = 0; attValCount < attributeValues.length; attValCount++) {
					Word word = hashmap.get(attributeValues[attValCount]);
					if (word != null) {

						// System.out.print(word.count+",");
						content1.append(word.count).append(",");

					} else {
						// System.out.print("0,");
						content1.append("0");
						content1.append(",");
					}

				}

			}
			// System.out.print("0\n");
			content1.append("0");
			content1.append("\n");
		}

		// }
		// 0 for SPAM, 1 for HAM
		return writeToArffFile(filePath, fileName, content1);

	}

	public static boolean writeToArffFile(String filePath, String fileName,
			StringBuilder content) {

		try {
			// StringBuffer content = new StringBuffer();
			// content.append("@relation svm_training");
			// content.append("\n");
			// content.append("\n@attribute word_freq_make numeric");
			// content.append("\n@attribute word_freq_all numeric");
			// content.append("\n@attribute word_freq_renish numeric");
			// content.append("\n@attribute is_spam {0,1}");
			// content.append("\n\n@data");
			// // @data
			// content.append("\n0,1,1,0");

			File file = new File(filePath + fileName + ".arff");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content.toString());
			bw.close();
			fw.close();

			System.out.println("Done");
			return true;

		} catch (Exception e) {

			System.out.println("Exception :" + e);
			return false;
		}

	}

}
