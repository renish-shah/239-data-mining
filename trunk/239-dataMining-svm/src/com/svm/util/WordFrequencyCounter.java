package com.svm.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WordFrequencyCounter {

	private class Word /* implements Comparable<Word> */{
		String word;
		Double count;

		public Word(String word, Double count) {
			this.word = word;
			this.count = count;
		}

		// public int compareTo(Word otherWord) {
		// if (this.count == otherWord.count) {
		// return this.word.compareTo(otherWord.word);
		// }
		// return otherWord.count - this.count;
		// }
	}

	private void getFrequentWords(List<String> words) {
		HashMap<String, Double> map = new HashMap<String, Double>();

		int totalNoOfWords=words.size();
		for (String s : words) {

			Double wordCount = 0.0;
			if (map.get(s) != null) {
				wordCount = map.get(s);
				map.put(s, ++wordCount);
			} else
				map.put(s, 1.0);
		}

		String keyArray[] = map.keySet().toArray(new String[map.size()]);
		System.out.println("\nKey=Value\n");
		for (int i = 0; i < map.size(); i++) {
			
			System.out.println(""+keyArray[i]+"="+map.get(keyArray[i]));
			map.put(keyArray[i], (map.get(keyArray[i])/totalNoOfWords)*100);
		}

		System.out.println("\nKey=Value with Percentage\n");
		String keyArray1[] = map.keySet().toArray(new String[map.size()]);
		for (int i = 0; i < map.size(); i++) {
			System.out.println(""+keyArray1[i]+"="+map.get(keyArray1[i]));
			
			//map.put(keyArray[i], map.get(keyArray[i])/totalNoOfWords);
		}
		
//		Word[] list = map.values().toArray(new Word[] {});
//		Arrays.sort(list);
//		return list;
	}

	public List<String> readFile(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String sCurrentLine;
			List<String> listOfWords = new ArrayList<String>();

			while ((sCurrentLine = br.readLine()) != null) {
				String[] arrayOfWords = sCurrentLine.split("\\s");
				for (int i = 0; i < arrayOfWords.length; i++) {
					listOfWords.add(arrayOfWords[i]);
				}
				System.out.println(sCurrentLine);
			}

			System.out.println("===Completed===");
			System.out.println("Abc Size:" + listOfWords.size());

			return listOfWords;

		} catch (Exception e) {
			System.out.println("Excpetion :" + e);
		}
		return null;

	}

	public static void main(String[] args) {

		WordFrequencyCounter frequencyCounter = new WordFrequencyCounter();
		List<String> listOfWords = frequencyCounter
				.readFile("C:\\Users\\renis\\Desktop\\SJSU\\239\\Project_SVM\\data\\spmsga11.txt");

		// String words[] = { "hello", "world", "java", "code", "example",
		// "hello" };
		new WordFrequencyCounter().getFrequentWords(listOfWords);
//		for (Word w : frequency) {
//			System.out.println(w.word + "=" + w.count);
//		}
	}

}