package com.spamfilter.naivebayes.filter;

import java.io.File;
import java.io.IOException;

import com.spamfilter.naivebayes.file.ReadFile;

public class NaiveBayes {

	private static final String DATA_NONSPAM_TEST = "C:/Users/renis/Desktop/SJSU/239/Project_SVM/data/nonspam-test";
	private static final String DATA_SPAM_TEST = "C:/Users/renis/Desktop/SJSU/239/Project_SVM/data/spam-test";
	private static final String DATA_SPAM_TXT = "C:/Users/renis/Desktop/SJSU/239/Project_SVM/data/Spam.txt";
	private static final String DATA_NON_SPAM_TXT = "C:/Users/renis/Desktop/SJSU/239/Project_SVM/data/nonspam.txt";

	int spamCount = 0;
	int nonSpamCount = 0;
	
	public static void main(String[] args)
	{
		new NaiveBayes().checkNaiveBayes();
	}

	public void checkNaiveBayes() {

		try {
			double startTime=System.currentTimeMillis();
			
			EmailSpamFilter emailfilter = new EmailSpamFilter();

			// File containing spam emails for training
			emailfilter.spamEmailTraining(DATA_SPAM_TXT);

			// File containing non-spam emails for training
			emailfilter.authenticEmailTraining(DATA_NON_SPAM_TXT);
			// Method for completing training
			emailfilter.training();

			File folder = new File(DATA_SPAM_TEST);
			File[] listOfFiles = null;
			listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {

				// Reading text file
				ReadFile readfile = new ReadFile();
				readfile.readDataFromFile(listOfFiles[i]);
				String data = readfile.getContent();

				// Analyze file content
				boolean spam = emailfilter.analyzewords(data);

				// Result
				if (spam) {
					++spamCount;
					System.out.println("Watch out, it's a spam!!!!!!");
					// spamCount++;
				}

				else {
					++nonSpamCount;
					System.out.println("Bingo!!! It's an authentic email.");
				}
			}
			System.out.println("=== Naive Bayes Result ===");
			System.out.println("Time taken:"+(System.currentTimeMillis()-startTime)+" ms");
			System.out.println("Spam Count:" + spamCount);
			System.out.println("Non-Spam Count:" + nonSpamCount);
			System.out.println("Accuracy:"+((spamCount*100)/(spamCount+nonSpamCount))+"%");
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
