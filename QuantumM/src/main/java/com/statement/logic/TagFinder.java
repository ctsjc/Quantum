package com.statement.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagFinder {

	public static void main(String[] args) {
		TagFinder t=new TagFinder();
		t.find("{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val: b},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val:q},b:{tbl:1009,row:1013,val: w},c:{tbl:1009,row:1013,val:e},r1_1018:{tbl:1009,row:1018,val:{table:1023,a:{tbl:1023,row:1024,val: c},b:{tbl:1023,row:1027,val:e },c:{tbl:1023,row:1027,val:de}}},r2_1018:{tbl:1009,row:1018,val: dc}}}}");
	}

	List<String> find(String input){
		List<String> tags=new ArrayList<String>();	
		String regx=",r[12]_\\d{4}:";	
		Pattern p = Pattern.compile(regx);
		Matcher matcher = p.matcher(input);
		while (matcher.find())
			tags.add(matcher.group());
		
		tags.add(",a:");
		tags.add(",b:");
		tags.add(",c:");
		return tags;
	}//end of find
}//end of class
