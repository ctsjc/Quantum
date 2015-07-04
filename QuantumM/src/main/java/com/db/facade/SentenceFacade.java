package com.db.facade;

import java.util.List;
import java.util.Map;

import com.db.logic.RowGenerator;
import com.db.logic.SentenceDTO;
import com.db.modal.Sentence;
import com.model.parser.JElement;

public class SentenceFacade {
	String statment;
	String ragnarStart;
	String ragnarEnd;
	public SentenceFacade() {
		// get the ragnar start point for this sentence;
		
	}
	public static void main(String[] args) {
		SentenceFacade facade=new  SentenceFacade();
		Sentence sentence=new Sentence();
		sentence.setSentence("Hello World");
		sentence.setStructure("NV");
		facade.insertStatement(sentence);
		
	}
	
	public List<Map<String, List<String>>> saveSentenceData(String sentenceText, String structure,JElement jTable){
		
		Sentence sentence=new Sentence();
		sentence.setSentence(sentenceText);
		sentence.setStructure(structure);
		insertStatement(sentence);
		
		
		RowGenerator gen=new RowGenerator();
		gen.insert(jTable,sentence.getSentenceId());
		List<Map<String, List<String>>> term_ques=gen.getQuesListFromDb(jTable,sentence.getSentenceId());
		return term_ques;
	}
	public void insertStatement(Sentence sentenceRagnar){
		SentenceDTO dto=new SentenceDTO();
		dto.insert(sentenceRagnar);
	}
}
