package com.spamfilter.naivebayes.filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.spamfilter.naivebayes.file.ReadFile;

public class NaiveBayes {

	private static final String DATA_NONSPAM_TEST = "C:/Users/renis/Desktop/SJSU/239/Project_SVM/data/nonspam-test";
	private static final String DATA_SPAM_TEST = "C:/Users/renis/Desktop/SJSU/239/Project_SVM/data/spam-test";
	private static final String DATA_SPAM_TXT = "C:/Users/renis/Desktop/SJSU/239/Project_SVM/data/Spam.txt";
	private static final String DATA_NON_SPAM_TXT = "C:/Users/renis/Desktop/SJSU/239/Project_SVM/data/nonspam.txt";

	int spamCount = 0;
	int nonSpamCount = 0;

	public static void main(String[] args) {
		new NaiveBayes().checkNaiveBayes("nonspam");
	}

	public List<String> checkNaiveBayes(String dataset) {

		try {
			double startTime = System.currentTimeMillis();

			EmailSpamFilter emailfilter = new EmailSpamFilter();

			// File containing spam emails for training
			emailfilter.spamEmailTraining(DATA_SPAM_TXT);

			// File containing non-spam emails for training
			emailfilter.authenticEmailTraining(DATA_NON_SPAM_TXT);
			// Method for completing training
			emailfilter.training();
			File folder = null;
			if(dataset.equals("spam"))
			{
				folder = new File(DATA_SPAM_TEST);
			}
			else if(dataset.equals("nonspam"))
			{
				folder = new File(DATA_NONSPAM_TEST);
			}
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

			List<String> results = new ArrayList<String>();

			results.add("=== Naive Bayes Result ===");
			results.add("Time taken:"
					+ (System.currentTimeMillis() - startTime) + " ms");
			results.add("Spam Count:" + spamCount);
			results.add("Non-Spam Count:" + nonSpamCount);
			if(dataset.equals("spam"))
			{
			results.add("Accuracy:"
					+ ((spamCount * 100) / (spamCount + nonSpamCount)) + "%");
			}
			else
			{
				results.add("Accuracy:"
						+ ((nonSpamCount * 100) / (spamCount + nonSpamCount)) + "%");
			}
			return results;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
