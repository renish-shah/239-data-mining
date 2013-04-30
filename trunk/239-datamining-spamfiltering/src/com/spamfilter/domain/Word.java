package com.spamfilter.domain;

/**
 * @author RENISH
 * 
 */

public class Word implements Comparable<Word> {
	public String word;
	public Double count;

	public Word(String word, Double count) {
		this.word = word;
		this.count = count;
	}

	public int compareTo(Word otherWord) {
		if (this.count == otherWord.count) {
			return (int) this.word.compareTo(otherWord.word);
		}
//		return (double)(otherWord.count - this.count);
		return 0;
	}
}