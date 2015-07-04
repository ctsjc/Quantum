package com.first;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.db.facade.SentenceFacade;
import com.model.conv.HTable;
import com.model.converter.HtmlGenerator;
import com.model.parser.JTable;
import com.statement.logic.Parser;

public class StatementHandler {
	public static ModelAndView create(String map){
		ModelAndView model = new ModelAndView();
		HTable hTable=null;
		if(map!= null && !map.isEmpty()){
			map=StringUtils.replace(map, "\"", "");
			System.out.println(map);
			Parser parser=new Parser(map);
			JTable jTable = (JTable) parser.getTable();
			
			SentenceFacade sentenceFacade=new SentenceFacade();
			String sentenceText="this is my life";
			String structure="nun";
			List<Map<String, List<String>>> term_ques = sentenceFacade.saveSentenceData(sentenceText, structure, jTable);
			
			System.out.println("Term Questions :: "+term_ques);
			HtmlGenerator htmlGenerator=new HtmlGenerator();
			hTable=htmlGenerator.toHtml(jTable);
			
			model.addObject("name",hTable.toHtml());
			model.addObject("ques",term_ques);
			System.out.println(hTable.getHtmlFormat().isEmpty()?hTable.getHtmlFormat():hTable.getHtmlFormat());
		}//end of if
		model.setViewName("test");
		return model;
	}
}
