package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.first.StatementHandler;


/**
 * this code will accept the statement block ( tabular representation of sentence )
 *
 */
@Controller
public class StatmentBlockController {

	private static final Logger logger = LoggerFactory.getLogger(StatmentBlockController.class);

	public static void main(String[] args) {
		StatmentBlockController controller=new StatmentBlockController();
		ModelAndView model=controller.welcome("{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val: b},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val:q},b:{tbl:1009,row:1013,val: w},c:{tbl:1009,row:1013,val:e},r1_1018:{tbl:1009,row:1018,val:{table:1023,a:{tbl:1023,row:1024,val: c},b:{tbl:1023,row:1027,val:e },c:{tbl:1023,row:1027,val:de}}},r2_1018:{tbl:1009,row:1018,val: dc}}}}");
		System.out.println(model.getModel().get("name"));
	}
	@RequestMapping(value = "/sendstatement/", method = RequestMethod.POST)	
	public ModelAndView welcome(@RequestBody String map) {
		ModelAndView model = StatementHandler.create(map);
		return model;
	}
}
