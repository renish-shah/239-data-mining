package com.spamfilter.util;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.spamfilter.domain.Word;
import com.spamfilter.test.SVMTest;

public class SVMEngine {

	public static final String TRAIN_SPAM_DATASET = "TRAIN_SPAM_RENISH";
	public static final String TEST_SPAM_DATASET = "TEST_SPAM_RENISH";
	public static final String PROJECT_SVM_DATA_DIRECTORY = "C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\";
	public static final String SPAM_TRAIN_DIRECTORY = "E:\\personal_dropbox\\Dropbox\\239\\239 Datasets\\ex6DataEmails\\spam-train";
	public static final String SPAM_TEST_DIRECTORY = "E:\\personal_dropbox\\Dropbox\\239\\239 Datasets\\ex6DataEmails\\spam-test";
	public static int fileCounter = 0;
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

	public boolean generateTrainingArffFile() {
		fileCounter++;
		File folder = new File(SPAM_TRAIN_DIRECTORY);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			List<String> listOfWords = FileUtility.readFile("", i, "txt",
					listOfFiles[i]);
			getCountForNextEmail(listOfWords, "spmsga" + i);
			listOfWords = null;
		}
		return ArffFileUtility.createContentForTrainingFile(PROJECT_SVM_DATA_DIRECTORY,
				TRAIN_SPAM_DATASET + "_" + fileCounter);

	}

	public boolean generateTestingArffFile() {
		// fileCounter++;
		// Testing Phase
		File folder = new File(SPAM_TEST_DIRECTORY);
		File[] listOfFiles = null;
		listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			List<String> listOfWords = FileUtility.readFile("", i, "txt",
					listOfFiles[i]);
			getCountForTestingEmail(listOfWords, listOfFiles[i].getName());
			listOfWords = null;
		}

		return ArffFileUtility.createContentForTestingFile(PROJECT_SVM_DATA_DIRECTORY,
				TEST_SPAM_DATASET + "_" + fileCounter);

	}

	public List<String> runSVMEngine() {

//		fileCounter++;

		// Apply Machine Learning
		SVMTest svmTest = new SVMTest();
		return svmTest.testDataSet(
				PROJECT_SVM_DATA_DIRECTORY + TEST_SPAM_DATASET + "_" + fileCounter
						+ ".arff",
				svmTest.trainDataSet(PROJECT_SVM_DATA_DIRECTORY + TRAIN_SPAM_DATASET
						+ "_" + fileCounter + ".arff"));

		// System.out.println("Done");

	}
}