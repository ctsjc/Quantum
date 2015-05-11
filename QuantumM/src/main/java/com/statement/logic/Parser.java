package com.statement.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.model.conv.HTable;
import com.model.converter.HtmlGenerator;
import com.model.parser.JCOL;
import com.model.parser.JElement;
import com.model.parser.JTable;

public class Parser {
	List<String> predefinedColumns;
	public Parser(String input) {
		TagFinder finder=new TagFinder();
		predefinedColumns=finder.find(input);
	}

	public static void main(String[] args) {
		String jsn="{table:1000,a:{tbl:1000,row:1001,val:hindu},b:{tbl:1000,row:1004,val:greek },c:{tbl:1000,row:1004,val:{table:1014,a:{tbl:1014,row:1015,val:egypt },b:{tbl:1014,row:1018,val:norse },c:{tbl:1014,row:1018,val:cicily }}},r1_1009:{tbl:1000,row:1009,val:vikings },r2_1009:{tbl:1000,row:1009,val:roman }}";
		jsn="{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val: b},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val:q},b:{tbl:1009,row:1013,val: w},c:{tbl:1009,row:1013,val:e},r1_1018:{tbl:1009,row:1018,val:{table:1023,a:{tbl:1023,row:1024,val: c},b:{tbl:1023,row:1027,val:e },c:{tbl:1023,row:1027,val:de}}},r2_1018:{tbl:1009,row:1018,val: dc}}}}";
		System.out.println(jsn);
		//find id {a,b,c,r1_10XX
		Parser p =new Parser(jsn);
		JTable jTable = (JTable) p.getTable(jsn);
		HtmlGenerator htmlGenerator=new HtmlGenerator();
		
		HTable hTable=htmlGenerator.toHtml(jTable);
		System.out.println(hTable.toHtml());
		
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
				current=new JCOL(chainOfElements.get(i).elementValue.tbl,chainOfElements.get(i).elementValue.row);
				//if token value is simple then add it as it is
				//else if token value is table, then parse it
				if(StringUtils.contains(chainOfElements.get(i).elementValue.val, "{table:")){
					JElement childTable = getTable(chainOfElements.get(i).elementValue.val);
					((JCOL)current).setTable(childTable);
				}
				else{
					((JCOL)current).setValue( chainOfElements.get(i).elementValue.val);
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
				nextStat=StringUtils.removeStart(statement, valStatment);

			}
			map=findElements(nextStat);
		}
		return chain;
	}


	private ElementValue getColumnValue(String token,String tokenValue) {
		/*String[] tokenSet={
				",a:",
				",b:",
				",c:",
				",r2_1018:",
				",r2_1041:",

				",r1_1009:",
				",r2_1009:",
				
				",r1_1014",
				",r2_1014",
		",d:"};			
*/		
		
 		ElementValue element =new ElementValue();
		element.val=tokenValue;
		for(String t : predefinedColumns){
			if(token.equals(t) && tokenValue.contains("val:")){				
				// Now search for val							
				int indexOf = tokenValue.indexOf("val:");	
				element.val=tokenValue.substring(indexOf+"val:".length(),tokenValue.length()-1).trim();
				// search for row
				element.row=tokenValue.substring(tokenValue.indexOf("row:")+"row:".length(),indexOf-1).trim();
				element.tbl=tokenValue.substring(tokenValue.indexOf("tbl:")+"tbl:".length(),tokenValue.indexOf("row:")-1).trim();
				return element;
			}//end of if
		}//end of for
		return element;
	}//end of getColVal

	private ElementInfo findElements(String statmnt) {
/*		String[] predefined_Elements={"{table:",
				",a:",
				",b:",
				",c:",
				",r2_1018:",
				",r2_1041:",
				",r1_1009:",
				",r2_1009:",
				",r1_1014",
				",r2_1014",
				
		",d:"};*/
		ArrayList<String> predefined_Elements=new ArrayList<String>(predefinedColumns);
		predefined_Elements.add("{table:");
		
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
		String tbl;
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