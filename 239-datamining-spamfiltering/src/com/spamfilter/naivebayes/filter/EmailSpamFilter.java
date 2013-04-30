package com.spamfilter.naivebayes.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.spamfilter.naivebayes.file.ReadFile;


public class EmailSpamFilter {

	HashMap wordlist;
	String wordsplitregex;
	Pattern stringregex;

	public EmailSpamFilter() {
		wordlist = new HashMap();
		wordsplitregex = "\\W";
		stringregex = Pattern.compile("\\w+");
	}

	// Spam email training. The training file contains 350 spam emails.
	public void spamEmailTraining(String file) throws IOException {
		ReadFile readfile = new ReadFile(file);

		// Break data into wordlist
		String data = readfile.getContent();
		String[] tokens = data.split(wordsplitregex);
		int totalSpamEmails = 0;

		for (int i = 0; i < tokens.length; i++) {
			String word = tokens[i].toLowerCase();
			Matcher matcher = stringregex.matcher(word);
			if (matcher.matches()) {
				totalSpamEmails++;

				// Increment count of word if it exists in Hashmap
				if (wordlist.containsKey(word)) {
					WordCheck w = (WordCheck) wordlist.get(word);
					w.spamOccurence();
				} else {
					WordCheck w = new WordCheck(word);
					w.spamOccurence();
					wordlist.put(word,w);
				}
			}
		}

		Iterator iterator = wordlist.values().iterator();
		while (iterator.hasNext()) {
			WordCheck word = (WordCheck) iterator.next();
			word.calSpamProb(totalSpamEmails);
		}
	}


	//Authentic email training. The training file contains 350 genuine emails. 
	public void authenticEmailTraining(String file) throws IOException {
		ReadFile readfile = new ReadFile(file);

		// Break data into wordlist
		String data = readfile.getContent();
		String[] tokens = data.split(wordsplitregex);
		int totalauthenticemails = 0;

		for (int i = 0; i < tokens.length; i++) {
			String word = tokens[i].toLowerCase();
			Matcher matcher = stringregex.matcher(word);
			if (matcher.matches()) {
				totalauthenticemails++;

				if (wordlist.containsKey(word)) {
					WordCheck w = (WordCheck) wordlist.get(word);
					w.authenticOccurence();
				} else {
					WordCheck w = new WordCheck(word);
					w.authenticOccurence();
					wordlist.put(word,w);
				}
			}
		}

		Iterator iterator = wordlist.values().iterator();
		while (iterator.hasNext()) {
			WordCheck word = (WordCheck) iterator.next();
			word.calcGoodProb(totalauthenticemails);
		}
	}


	public boolean analyzewords(String emailwords) {

		ArrayList mainwords = new ArrayList();
		String[] tokens = emailwords.split(wordsplitregex);

		for (int i = 0; i < tokens.length; i++) {
			String a = tokens[i].toLowerCase();
			Matcher matcher = stringregex.matcher(a);
			if (matcher.matches()) {

				WordCheck wordcheck;
				// Get the word from hashmap if it exists
				if (wordlist.containsKey(a)) {
					wordcheck = (WordCheck) wordlist.get(a);
				} else {
					wordcheck = new WordCheck(a);
					wordcheck.setSpamPro(0.4f);
				}

				int wordlimit = 100;
				if (mainwords.isEmpty()) {
					mainwords.add(wordcheck);
				} else {
					for (int j = 0; j < mainwords.size(); j++) {
						WordCheck newword = (WordCheck) mainwords.get(j);
						if (wordcheck.getWord().equals(newword.getWord())) {
							break;
						} else if (wordcheck.mainwords() > newword.mainwords()) {
							mainwords.add(j,wordcheck);
							break;
						} else if (j == mainwords.size()-1) {
							mainwords.add(wordcheck);
						}
					}
				}

				// delete entries at the end
				while (mainwords.size() > wordlimit) mainwords.remove(mainwords.size()-1);
			}
		}

		// Applying Bayes' rule 
		float positiveprob = 1;
		float negativeprob = 1;

		for (int i = 0; i < mainwords.size(); i++) {
			WordCheck w = (WordCheck) mainwords.get(i);
			positiveprob *= w.getSpamPro();
			negativeprob *= (1.0f - w.getSpamPro());
		}

		float spamrating = positiveprob / (positiveprob + negativeprob);
		System.out.println("\nSpam Rating: " + spamrating);

		// If the computed value is greater than 0.9, its a Spam.
		if (spamrating > 0.9) 
			return true;
		else return false;
	}

	// Calculating spam probability for each word
	public void training() {
		Iterator iterator = wordlist.values().iterator();
		while (iterator.hasNext()) {
			WordCheck word = (WordCheck) iterator.next();
			word.probabilityCalc();
		}	
	}	

}
