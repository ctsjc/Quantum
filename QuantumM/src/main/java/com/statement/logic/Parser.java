package com.statement.logic;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.model.converter.HtmlGenerator;
import com.model.parser.JCOL;
import com.model.parser.JElement;
import com.model.parser.JTable;

public class Parser {
	public static void main(String[] args) {
		Parser p =new Parser();
	/*	String jsn=""
				+ "{table:1000,"
				+ "a:{row:1001,val: a},"
				+ "b:{row:1004,val: b},"
				+ "c:{row:1004,val:"
				+ "{table:1009,"
				+ "a:{row:1010,val: cc},"
				+ "b:{row:1013,val: bb},"
				+ "c:{row:1013,val: vv}}},"
				+ "d:{row:1004,val: d}};";
	*/
		String jsn="{table:1000,a:{row:1001,val: roman},b:{row:1004,val:greek },c:{row:1004,val:{table:1009,a:{row:1010,val:hindu },b:{row:1013,val:egyptian },c:{row:1013,val:{table:1023,a:{row:1024,val:chinease },b:{row:1027,val:japanese },c:{row:1027,val:americans }}},r1_1018:{row:1018,val:{table:1032,a:{row:1033,val:british },b:{row:1036,val:french },c:{row:1036,val:german }}},r2_1018:{row:1018,val:russian },r1_1041:{row:1041,val:mongol },r2_1041:{row:1041,val:Vikings }}}}";

		
		
		
		//String jsn="{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val: b},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val: r},b:{tbl:1009,row:1013,val: t},c:{tbl:1009,row:1013,val: y},r1_1018:{tbl:1009,row:1018,val:{table:1023,a:{tbl:1023,row:1024,val: o},b:{tbl:1023,row:1027,val: p},c:{tbl:1023,row:1027,val: o}}},r2_1018:{tbl:1009,row:1018,val: p}}}}";

				
		System.out.println(jsn);
		JTable jTable = (JTable) p.getTable(jsn);
		HtmlGenerator htmlGenerator=new HtmlGenerator();
		
		for(JElement jElement:jTable.getChild()){
			System.out.println(jElement);
		}
		System.out.println("=========================");
		htmlGenerator.toHtml(jTable);
	}

	public JElement getTable(String jsn){
		ArrayList<ElementInfo> chainOfElements = elementsFinder(jsn);

		
		JElement parent=null;
		JElement current=null;
		
		for(int i=0; i<chainOfElements.size();i++){
			// table will watch for { a b c }
			// { a b c } will watch for prev table
						
			if(chainOfElements.get(i).elementName.equals("{table:")){
				current=new JTable();				
				((JTable)current).setId(chainOfElements.get(i).elementValue.val);
				if(i == 0){
					parent=current;	
				}				
			}
			else{
				current=new JCOL();
				//if token value is simple then add it as it is
				//else if token value is table, then parse it
				if(StringUtils.contains(chainOfElements.get(i).elementValue.val, "{table:")){
					JElement childTable = getTable(chainOfElements.get(i).elementValue.val);
					((JCOL)current).setTable(childTable);
				}
				else{
					((JCOL)current).setValue(chainOfElements.get(i).elementValue.row, chainOfElements.get(i).elementValue.val);
				}
				parent.getChild().add(current);				
			}	
		}//end of for		
		return parent;
	}

	private ArrayList<ElementInfo> elementsFinder(String jsn) {
		ElementInfo map=findElements(jsn);
		ArrayList<ElementInfo> chain=new ArrayList<ElementInfo>();

		while( map !=null){
			chain.add(map);
			String statement=map.statement;
			String valStatment=map.valueStatment;
			String nextStat="";
			if(statement.contains(valStatment)){

				nextStat=StringUtils.remove(statement, valStatment);

			}
			map=findElements(nextStat);
		}
		return chain;
	}


	private ElementValue getColumnValue(String token,String tokenValue) {
		String[] tokenSet={
				",a:",
				",b:",
				",c:",
				",r2_1018:",
				",r2_1041:",

				",r1_1009:",
				",r2_1009:",
		",d:"};
		ElementValue element =new ElementValue();
		element.val=tokenValue;
		for(String t : tokenSet){
			if(token.equals(t) && tokenValue.contains("val:")){				
				// Now search for val							
				int indexOf = tokenValue.indexOf("val:");	
				element.val=tokenValue.substring(indexOf+"val:".length(),tokenValue.length()-1).trim();
				// search for row
				element.row=tokenValue.substring(tokenValue.indexOf("row:")+"row:".length(),indexOf-1).trim();

				return element;
			}//end of if
		}//end of for
		return element;
	}//end of getColVal

	private ElementInfo findElements(String statmnt) {
		String[] predefined_Elements={"{table:",
				",a:",
				",b:",
				",c:",
				",r2_1018:",
				",r2_1041:",
				",r1_1009:",
				",r2_1009:",
				
		",d:"};
		int endIndex=Integer.MAX_VALUE;
		String token="";
		String tokenStment="";
		String tokenValue="";
		for(String t : predefined_Elements){
			if(statmnt.contains(t)){
				if(endIndex > statmnt.indexOf(t)){
					endIndex=statmnt.indexOf(t);
					token=t;
				}
			}
		}

		if(token.length() == 0)
			return null;
		tokenStment=statmnt.substring(endIndex+token.length());
		// if token is table
		if(token.equals("{table:")){
			tokenValue=tokenStment.substring(0, tokenStment.indexOf(","));

		}
//		else token is either { a b c}
		else
		{
			// parse till  }
			tokenValue = searchElementValue(tokenStment, tokenValue);
		}			
		//find actual Token
		ElementInfo buc=new ElementInfo();
		
		buc.elementName=token;		
		buc.statement=tokenStment;
		buc.elementValue=getColumnValue(token, tokenValue);
		buc.valueStatment=tokenValue;
		return buc;
	}
	
	class ElementValue{
		String row;
		String val;		
	}
	
	class ElementInfo{
		String elementName;
		String statement;
		ElementValue elementValue;
		String valueStatment;		
	}
	
	private String searchElementValue(String tokenStment, String tokenValue) {
		int stack=0;
		for(int i=0;i<tokenStment.length(); i++){
			char ch= tokenStment.charAt(i); 
			if( ch == '{' ){
				stack++;
			}
			else if(ch == '}'){
				stack--;
				if(stack == 0 ){
					tokenValue=tokenStment.substring(0, i+1);
					break;
				}
			}
		}
		return tokenValue;
	}
}
/***
Pattern regex = Pattern.compile(",[abc]+");
 */