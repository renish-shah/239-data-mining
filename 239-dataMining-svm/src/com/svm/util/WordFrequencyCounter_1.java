package com.svm.util;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.svm.domain.Word;

public class WordFrequencyCounter_1 {

	private static final String SPAM_TRAIN = "E:\\personal_dropbox\\Dropbox\\239\\239 Datasets\\ex6DataEmails\\spam-train";
	private static final String SPAM_TEST = "E:\\personal_dropbox\\Dropbox\\239\\239 Datasets\\ex6DataEmails\\spam-test";
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

	public static void main(String[] args) {

		String words[] = { "hello", "world", "java", "code", "example", "hello" };
		String words1[] = { "hello", "world", "renish", "binit", "renish" };
		WordFrequencyCounter_1 counter_1 = new WordFrequencyCounter_1();

		// File folder = new File(SPAM_TEST);

		// Training Phase
		File folder = new File(SPAM_TRAIN);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			// List<String> listOfWords =
			// FileUtility.readFile("C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\spmsga",i,
			// "txt");
			List<String> listOfWords = FileUtility
					.readFile(
							"C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\spmsga",
							i, "txt", listOfFiles[i]);
			counter_1.getCountForNextEmail(listOfWords, "spmsga" + i);
			listOfWords = null;
		}
		ArffFileUtility.createContentForTrainingFile("C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\","TRAIN_SPAM_RENISH");

		// Testing Phase
		folder = new File(SPAM_TEST);
		listOfFiles=null;
		listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {

			// List<String> listOfWords =
			// FileUtility.readFile("C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\spmsga",i,
			// "txt");
			List<String> listOfWords = FileUtility.readFile("C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\spmsga",i, "txt", listOfFiles[i]);
			counter_1.getCountForTestingEmail(listOfWords, listOfFiles[i].getName());
			listOfWords = null;
		}

		

		ArffFileUtility.createContentForTestingFile("C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\","TEST_SPAM_RENISH");
		
		System.out.println("Done");

	}
}