/**
 * 
 */
package com.spamfilter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author renis
 * 
 */
public class FileUtility {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<String> readFile(String path, int fileNumber,
			String ext, File file) {
		try {

			List<String> listOfWords = new ArrayList<String>();
			BufferedReader br;

			// for (int i = 1; i <= noOfFiles; i++) {

			// br = new BufferedReader(new FileReader(path+fileNumber+"."+ext));
			br = new BufferedReader(new FileReader(file));
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				String[] arrayOfWords = sCurrentLine.split("\\s");
				for (int j = 0; j < arrayOfWords.length; j++) {
					listOfWords.add(arrayOfWords[j]);
				}
				// System.out.println(sCurrentLine);
			}

			System.out.println("===Completed===");
			System.out.println("Abc Size:" + listOfWords.size());
			// }
			return listOfWords;

		} catch (Exception e) {
			System.out.println("Excpetion :" + e);
		}
		return null;

	}

}
