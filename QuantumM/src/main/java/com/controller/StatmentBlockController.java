package com.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.model.parser.JElement;
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
		logger.debug("I A M C LAEED ");
		String m=StringUtils.replace(map, "\"", "");
		ModelAndView model = new ModelAndView();
		Parser parser=new Parser();
		JTable jTable = (JTable) parser.getTable(m);
		for(JElement jElement:jTable.getChild()){
			System.out.println(jElement);
		}
		
		model.setViewName("test");
		model.addObject("name",jTable);
		return model;
	}
}
