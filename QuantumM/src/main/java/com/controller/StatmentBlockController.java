package com.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.model.conv.HTable;
import com.model.converter.HtmlGenerator;
import com.model.parser.JTable;
import com.statement.logic.Parser;


/**
 * this code will accept the statement block ( tabular representation of sentence )
 *
 */
@Controller
public class StatmentBlockController {

	private static final Logger logger = LoggerFactory.getLogger(StatmentBlockController.class);

	@RequestMapping(value = "/sendstatement/", method = RequestMethod.POST)	
	public ModelAndView welcome(@RequestBody String map) {
		System.out.println("I am Called");
		ModelAndView model = new ModelAndView();
		HTable hTable=null;
		if(map!= null && !map.isEmpty()){
			map=StringUtils.replace(map, "\"", "");
			System.out.println(map);
			Parser parser=new Parser(map);
			JTable jTable = (JTable) parser.getTable(map);
			HtmlGenerator htmlGenerator=new HtmlGenerator();
			hTable=htmlGenerator.toHtml(jTable);
			model.addObject("name",hTable.toHtml());
		}//end of if
		model.setViewName("test");	
		return model;
	}
}
