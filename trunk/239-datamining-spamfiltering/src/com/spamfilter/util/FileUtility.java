/**
 * 
 */
package com.spamfilter.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author RENISH
 * 
 */

public class FileUtility {

	/**
	 * @param args
	 */
	static HashMap<String, String> listOfYahooWords=new HashMap<String, String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new FileUtility().removeStopWords(readFilePreProcess("", 1, "", null));

	}

	public static List<String> readFile(String path, int fileNumber,
			String ext, File file) {
		try {

			List<String> listOfWords = new ArrayList<String>();
			BufferedReader br;

			//file=new File("E:/SpamEmails/Spam_yahoo_2.txt");
			// for (int i = 1; i <= noOfFiles; i++) {

			// br = new BufferedReader(new FileReader(path+fileNumber+"."+ext));
			br = new BufferedReader(new FileReader(file));
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				String[] arrayOfWords = sCurrentLine.split("\\s");
				//String[] arrayOfWords = sCurrentLine.split("[\\.\\'\\-\\,\\?\\\"\\(\\)\\&\\\\;\\:\\s]");
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
			e.printStackTrace();
		}
		return null;

	}
	
	public static List<String> readFilePreProcess(String path, int fileNumber,
			String ext, File file) {
		try {

			//List<String> listOfWords = new ArrayList<String>();
			listOfYahooWords=new HashMap<String, String>();
			BufferedReader br;

			//file=new File("E:/SpamEmails/Spam_yahoo_2.txt");


			br = new BufferedReader(new FileReader(file));
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				//String[] arrayOfWords = sCurrentLine.split("\\s");
				String[] arrayOfWords = sCurrentLine.split("[\\.\\'\\-\\,\\!\\#\\%\\*\\|\\?\\\"\\(\\)\\&\\\\;\\:\\s]");
				for (int j = 0; j < arrayOfWords.length; j++) {
					if(arrayOfWords[j].length()>2 && !arrayOfWords[j].isEmpty())
						listOfYahooWords.put(arrayOfWords[j],arrayOfWords[j]);
				}
				// System.out.println(sCurrentLine);
			}

			System.out.println("===Completed===");
			System.out.println("List Of Words Size:" + listOfYahooWords.size());
			
			new FileUtility().removeStopWords();
			System.out.println("List Of Words Size:" + listOfYahooWords.size());
			List<String> list = new ArrayList<String>(listOfYahooWords.values());
			return list;

		} catch (Exception e) {
			System.out.println("Excpetion :" + e);
			e.printStackTrace();
		}
		return null;

	}
	
	public void removeStopWords()
	{
		try{
			
		//InputStreamReader reader=new InputStreamReader(("resources/stopwords-long.txt"));
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stopwords-long.txt");
			
		//FileInputStream fs = new FileInputStream("resources/stopwords-long.txt");
		DataInputStream ds = new DataInputStream(inputStream);
		BufferedReader in = new BufferedReader(new InputStreamReader(ds));
		String line="";
		while((line=in.readLine())!=null)
		{
			if(listOfYahooWords.containsKey(line))
				listOfYahooWords.remove(line);
		}
		}catch (Exception e) {
			System.out.println("Exception in removeStopWords:"+e);
			e.printStackTrace();
		}
		
	}


}
