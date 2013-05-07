package com.spamfilter.svm;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.spamfilter.domain.Word;
import com.spamfilter.test.SVMTest;
import com.spamfilter.util.ArffFileUtility;
import com.spamfilter.util.FileUtility;

/**
 * @author RENISH
 * 
 */

public class SVMEngine {

	public static final String TRAIN_SPAM_DATASET = "TRAIN_SPAM_DATASET";
	public static final String TEST_SPAM_DATASET = "TEST_SPAM_DATASET";
	public static final String TRAIN_NONSPAM_DATASET = "TRAIN_NONSPAM_DATASET";
	public static final String TEST_NONSPAM_DATASET = "TEST_NONSPAM_DATASET";

	public static final String PROJECT_SVM_DATA_DIRECTORY = "C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\";
	public static final String SPAM_TRAIN_DIRECTORY = "E:\\personal_dropbox\\Dropbox\\239\\239 Datasets\\ex6DataEmails\\spam-train";
	public static final String SPAM_TEST_DIRECTORY = "E:\\personal_dropbox\\Dropbox\\239\\239 Datasets\\ex6DataEmails\\spam-test";
	public static final String NONSPAM_TRAIN_DIRECTORY = "E:\\personal_dropbox\\Dropbox\\239\\239 Datasets\\ex6DataEmails\\nonspam-train";
	public static final String NONSPAM_TEST_DIRECTORY = "E:\\personal_dropbox\\Dropbox\\239\\239 Datasets\\ex6DataEmails\\nonspam-test";
	public static int fileCounter = 0;

	public static boolean isTestYahoo = false;
	public static final String SPAM_YAHOO_TRAIN_DIRECTORY = "E:/SpamEmails";
	public static final String SPAM_YAHOO_TEST_DIRECTORY = "E:/SpamEmails-Test";

	// HashMap<String, HashMap<String, List<Double>>> globalMap = new
	// HashMap<String, HashMap<String, List<Double>>>();
	// public static HashMap<String, HashMap<String, Word>> globalMapTraining =
	// new HashMap<String, HashMap<String, Word>>();
	public static HashMap<String, List<HashMap<String, Word>>> globalMapNextTraining = new HashMap<String, List<HashMap<String, Word>>>();
	public static HashMap<String, String> globalHeaderTraining = new HashMap<String, String>();

	public static HashMap<String, List<HashMap<String, Word>>> globalMapNextTesting = new HashMap<String, List<HashMap<String, Word>>>();
	public static HashMap<String, String> globalHeaderTesting = new HashMap<String, String>();

	private void getCountForNextEmail(List<String> words, String emailKey) {

		HashMap<String, Word> mapOfLocalWords = new HashMap<String, Word>();
		List<HashMap<String, Word>> listOfWordsWithCount = new ArrayList<HashMap<String, Word>>();
		int totalNoOfWordsInEmail = words.size();
		// globalHeaderTraining = new HashMap<String, String>();
		// globalMapNextTraining = new HashMap<String, List<HashMap<String,
		// Word>>>();

		for (String stringKey : words) {

			// mapOfLocalWords = globalMap.get(stringKey);

			String headerValue = globalHeaderTraining.get(stringKey);
			if (headerValue == null) {
				globalHeaderTraining.put(stringKey, stringKey);
			}

			if (mapOfLocalWords != null) {

				Word word = mapOfLocalWords.get(stringKey);
				if (word == null) {
					word = new Word(stringKey, 1.0);
				} else {
					word.count++;
				}
				mapOfLocalWords.put(stringKey, word);
			} else {
				mapOfLocalWords = new HashMap<String, Word>();
				Word word = new Word(stringKey, 1.0);
				mapOfLocalWords.put(stringKey, word);
			}
		}
		String keyArray[] = mapOfLocalWords.keySet().toArray(
				new String[mapOfLocalWords.size()]);

		// System.out.println("===Map Size===:" + mapOfLocalWords.size());
		// System.out.println("\nKey=Value\n");
		for (int i = 0; i < keyArray.length; i++) {

			// System.out.println("" + keyArray[i] + "=" +
			// mapOfLocalWords.get(keyArray[i]));
			Word w = mapOfLocalWords.get(keyArray[i]);
			w.count = (w.count * 100) / totalNoOfWordsInEmail;
			mapOfLocalWords.put(keyArray[i], w);
		}

		listOfWordsWithCount.add(mapOfLocalWords);

		globalMapNextTraining.put(emailKey, listOfWordsWithCount);

		System.out.println("Global Map Next Size :"
				+ globalMapNextTraining.size());
	}

	private void getCountForTestingEmail(List<String> words, String emailKey) {

		HashMap<String, Word> mapOfLocalWords = new HashMap<String, Word>();
		List<HashMap<String, Word>> listOfWordsWithCount = new ArrayList<HashMap<String, Word>>();
		int totalNoOfWordsInEmail = words.size();
		// globalHeaderTesting = new HashMap<String, String>();
		// globalMapNextTesting=new HashMap<String,
		// List<HashMap<String,Word>>>();
		for (String stringKey : words) {

			// mapOfLocalWords = globalMap.get(stringKey);

			String headerValue = globalHeaderTesting.get(stringKey);
			if (headerValue == null) {
				globalHeaderTesting.put(stringKey, stringKey);
			}

			if (mapOfLocalWords != null) {

				Word word = mapOfLocalWords.get(stringKey);
				if (word == null) {
					word = new Word(stringKey, 1.0);
				} else {
					word.count++;
				}
				mapOfLocalWords.put(stringKey, word);
			} else {
				mapOfLocalWords = new HashMap<String, Word>();
				Word word = new Word(stringKey, 1.0);
				mapOfLocalWords.put(stringKey, word);
			}
		}
		String keyArray[] = mapOfLocalWords.keySet().toArray(
				new String[mapOfLocalWords.size()]);

		for (int i = 0; i < keyArray.length; i++) {

			Word w = mapOfLocalWords.get(keyArray[i]);
			w.count = (w.count * 100) / totalNoOfWordsInEmail;
			mapOfLocalWords.put(keyArray[i], w);
		}

		listOfWordsWithCount.add(mapOfLocalWords);

		globalMapNextTesting.put(emailKey, listOfWordsWithCount);

		System.out.println("Global Map Next Testing Size :"
				+ globalMapNextTesting.size());
	}

	public boolean generateYahooTrainingArffFile(boolean isSpam) {
		fileCounter++;
		File folder;

		if (isSpam)
			folder = new File(SPAM_YAHOO_TRAIN_DIRECTORY);
		else
			folder = new File(NONSPAM_TRAIN_DIRECTORY);

		File[] listOfFiles = folder.listFiles();
		globalHeaderTesting = null;
		globalHeaderTraining = new HashMap<String, String>();
		globalMapNextTraining = null;
		globalMapNextTraining = new HashMap<String, List<HashMap<String, Word>>>();

		for (int i = 0; i < listOfFiles.length; i++) {

			List<String> listOfWords = FileUtility.readFilePreProcess("", i,
					"txt", listOfFiles[i]);
			getCountForNextEmail(listOfWords, "spmsga" + i);
			listOfWords = null;
		}
		if (isSpam) {
			return ArffFileUtility.createContentForTrainingFile(
					PROJECT_SVM_DATA_DIRECTORY, TRAIN_SPAM_DATASET + "_YAHOO_"
							+ fileCounter, true);
		} else {
			return ArffFileUtility.createContentForTrainingFile(
					PROJECT_SVM_DATA_DIRECTORY, TRAIN_NONSPAM_DATASET
							+ "_YAHOO_" + fileCounter, false);
		}

	}

	public boolean generateTrainingArffFile(boolean isSpam) {
		fileCounter++;
		File folder;

		if (isSpam)
			folder = new File(SPAM_TRAIN_DIRECTORY);
		else
			folder = new File(NONSPAM_TRAIN_DIRECTORY);

		File[] listOfFiles = folder.listFiles();
		globalHeaderTesting = null;
		globalHeaderTraining = new HashMap<String, String>();
		globalMapNextTraining = null;
		globalMapNextTraining = new HashMap<String, List<HashMap<String, Word>>>();

		for (int i = 0; i < listOfFiles.length; i++) {

			List<String> listOfWords = FileUtility.readFile("", i, "txt",
					listOfFiles[i]);
			getCountForNextEmail(listOfWords, "spmsga" + i);
			listOfWords = null;
		}
		if (isSpam) {
			return ArffFileUtility.createContentForTrainingFile(
					PROJECT_SVM_DATA_DIRECTORY, TRAIN_SPAM_DATASET + "_"
							+ fileCounter, true);
		} else {
			return ArffFileUtility.createContentForTrainingFile(
					PROJECT_SVM_DATA_DIRECTORY, TRAIN_NONSPAM_DATASET + "_"
							+ fileCounter, false);
		}

	}

	public boolean generateYahooTestingArffFile(boolean isSpam) {

		// Testing Phase
		File folder;
		if (isSpam)
			folder = new File(SPAM_YAHOO_TEST_DIRECTORY);
		else
			folder = new File(NONSPAM_TEST_DIRECTORY);

		File[] listOfFiles = null;
		listOfFiles = folder.listFiles();
		globalHeaderTesting = new HashMap<String, String>();
		globalMapNextTesting = new HashMap<String, List<HashMap<String, Word>>>();

		for (int i = 0; i < listOfFiles.length; i++) {

			List<String> listOfWords = FileUtility.readFilePreProcess("", i,
					"txt", listOfFiles[i]);
			getCountForTestingEmail(listOfWords, listOfFiles[i].getName());
			listOfWords = null;
		}
		if (isSpam) {

			return ArffFileUtility.createContentForTestingFile(
					PROJECT_SVM_DATA_DIRECTORY, TEST_SPAM_DATASET + "_YAHOO_"
							+ fileCounter, true);
		} else {
			return ArffFileUtility.createContentForTestingFile(
					PROJECT_SVM_DATA_DIRECTORY, TEST_NONSPAM_DATASET
							+ "_YAHOO_" + fileCounter, false);
		}

	}

	public boolean generateTestingArffFile(boolean isSpam) {
		// fileCounter++;
		// Testing Phase
		File folder;
		if (isSpam)
			folder = new File(SPAM_TEST_DIRECTORY);
		else
			folder = new File(NONSPAM_TEST_DIRECTORY);

		File[] listOfFiles = null;
		listOfFiles = folder.listFiles();
		globalHeaderTesting = new HashMap<String, String>();
		globalMapNextTesting = new HashMap<String, List<HashMap<String, Word>>>();

		for (int i = 0; i < listOfFiles.length; i++) {

			List<String> listOfWords = FileUtility.readFile("", i, "txt",
					listOfFiles[i]);
			getCountForTestingEmail(listOfWords, listOfFiles[i].getName());
			listOfWords = null;
		}
		if (isSpam) {

			return ArffFileUtility.createContentForTestingFile(
					PROJECT_SVM_DATA_DIRECTORY, TEST_SPAM_DATASET + "_"
							+ fileCounter, true);
		} else {
			return ArffFileUtility.createContentForTestingFile(
					PROJECT_SVM_DATA_DIRECTORY, TEST_NONSPAM_DATASET + "_"
							+ fileCounter, false);
		}

	}

	public List<String> runYahooSVMEngine(boolean trainSpam, boolean testSpam) {
		SVMTest svmTest = new SVMTest();
		return svmTest.testDataSet(
				PROJECT_SVM_DATA_DIRECTORY + TEST_SPAM_DATASET + "_YAHOO_"
						+ fileCounter + ".arff",
				svmTest.trainDataSet(PROJECT_SVM_DATA_DIRECTORY
						+ TRAIN_SPAM_DATASET + "_YAHOO_" + fileCounter
						+ ".arff"));
	}

	public List<String> runSVMEngine(boolean trainSpam, boolean testSpam) {

		// fileCounter++;

		// Apply Machine Learning
		SVMTest svmTest = new SVMTest();
		if (trainSpam && testSpam) {
			return svmTest
					.testDataSet(
							PROJECT_SVM_DATA_DIRECTORY + TEST_SPAM_DATASET
									+ "_" + fileCounter + ".arff",
							svmTest.trainDataSet(PROJECT_SVM_DATA_DIRECTORY
									+ TRAIN_SPAM_DATASET + "_" + fileCounter
									+ ".arff"));
		} else if (!trainSpam && !testSpam) {
			return svmTest.testDataSet(
					PROJECT_SVM_DATA_DIRECTORY + TEST_NONSPAM_DATASET + "_"
							+ fileCounter + ".arff",
					svmTest.trainDataSet(PROJECT_SVM_DATA_DIRECTORY
							+ TRAIN_NONSPAM_DATASET + "_" + fileCounter
							+ ".arff"));
		} else if (trainSpam && !testSpam) {
			return svmTest
					.testDataSet(
							PROJECT_SVM_DATA_DIRECTORY + TEST_NONSPAM_DATASET
									+ "_" + fileCounter + ".arff",
							svmTest.trainDataSet(PROJECT_SVM_DATA_DIRECTORY
									+ TRAIN_SPAM_DATASET + "_" + fileCounter
									+ ".arff"));
		} else if (!trainSpam && testSpam) {
			return svmTest.testDataSet(
					PROJECT_SVM_DATA_DIRECTORY + TEST_SPAM_DATASET + "_"
							+ fileCounter + ".arff",
					svmTest.trainDataSet(PROJECT_SVM_DATA_DIRECTORY
							+ TRAIN_NONSPAM_DATASET + "_" + fileCounter
							+ ".arff"));
		}
		return null;

		// System.out.println("Done");

	}
}