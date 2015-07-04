package com.db.modal;

import java.util.UUID;

public class Sentence {
	String sentence;
	String sentenceId;
	String structure;

	public Sentence() {
		sentenceId=UUID.randomUUID().toString();
	}

	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getSentenceId() {
		return sentenceId;
	}
	public void setSentenceId(String sentenceId) {
		this.sentenceId = sentenceId;
	}
	
}
