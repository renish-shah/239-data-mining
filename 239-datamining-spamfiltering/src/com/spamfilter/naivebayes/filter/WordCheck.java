package com.spamfilter.naivebayes.filter;

public class WordCheck {
	private String word;    
	private int spamOccurence;   
	private int authenticOccurence;  
	private float spamPercentage;     
	private float goodPercentage;    
	private float spamProbabilty;    
	
	//Initialize all variables
	public WordCheck(String s) {
		word = s;
		spamOccurence = 0;
		authenticOccurence = 0;
		spamPercentage = 0;
		goodPercentage = 0;
		spamProbabilty = 0;
	}
	
	public void spamOccurence() {
		spamOccurence++;
	}

	public void authenticOccurence() {
		authenticOccurence++;
	}

	// Probability of bad word
	public void calSpamProb(int total) {
		if (total > 0) spamPercentage = spamOccurence / (float) total;
	}
	
	// Probability of good word
	public void calcGoodProb(int total) {
		if (total > 0) goodPercentage = 2*authenticOccurence / (float) total;
	}

	// Naive Bayes probabilty algorithm for calculating the likeliness of the word being a spam
	public void probabilityCalc() {
		if (goodPercentage + spamPercentage > 0) spamProbabilty = spamPercentage / (spamPercentage + goodPercentage);
		if (spamProbabilty < 0.01) spamProbabilty = (float) 0.01;
		else if (spamProbabilty > 0.99) spamProbabilty = (float) 0.99;
	}
	
	// Variance of the word from 0.5 probability
	public float mainwords() {
		return (float) Math.abs(0.5 - spamProbabilty);
	}
	
	// Getters and Setters	
	public float getProGood() {
		return goodPercentage;
	}

	public float getProBad() {
		return spamPercentage;
	}
	
	public float getSpamPro() {
		return spamProbabilty;
	}

	public void setSpamPro(float f) {
		spamProbabilty = f;
	}

	public String getWord() {
		return word;
	}
}
