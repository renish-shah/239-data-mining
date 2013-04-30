package com.spamfilter.naivebayes.filter;

import java.io.IOException;

import com.spamfilter.naivebayes.file.ReadFile;


public class NaiveBayes {

	public static void main (String args[]) {
		try {
			EmailSpamFilter emailfilter = new EmailSpamFilter();   
			
			// File containing spam emails for training
			emailfilter.spamEmailTraining("C:/Users/Pranay/workspace/SpamEmailClassification/src/com/naivebayes/trainingset/Spam.txt");

			// File containing non-spam emails for training
			emailfilter.authenticEmailTraining("C:/Users/Pranay/workspace/SpamEmailClassification/src/com/naivebayes/trainingset/NonSpam.txt");
			// Method for completing training
			emailfilter.training();
			
			
			for (int i = 1; i < 11; i++) {
				// Reading text file
				ReadFile readfile = new ReadFile("C:/Users/Pranay/workspace/SpamEmailClassification/src/com/naivebayes/testingset/Email" + i + ".txt");
				String data = readfile.getContent();
				
				// Analyze file content
				boolean spam = emailfilter.analyzewords(data);
				
 				// Result
				if (spam) System.out.println("Watch out, it's a spam!!!!!!");
				else System.out.println("Bingo!!! It's an authentic email.");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
