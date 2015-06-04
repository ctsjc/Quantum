package com.statement.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.model.conv.HColumn;
import com.model.conv.HRow;
import com.model.conv.HTable;
import com.model.converter.HtmlGenerator;
import com.model.parser.JCOL;
import com.model.parser.JElement;
import com.model.parser.JTable;

public class Parser {
	List<String> columnTags;

	String input;

	public Parser(String input) {
		this.input=input;
		TagFinder finder=new TagFinder();
		columnTags=finder.find(input);
	}

	public static void main(String[] args) {
		String jsn="{table:1000,a:{tbl:1000,row:1001,val:hindu},b:{tbl:1000,row:1004,val:greek },c:{tbl:1000,row:1004,val:{table:1014,a:{tbl:1014,row:1015,val:egypt },b:{tbl:1014,row:1018,val:norse },c:{tbl:1014,row:1018,val:cicily }}},r1_1009:{tbl:1000,row:1009,val:vikings },r2_1009:{tbl:1000,row:1009,val:roman }}";
		jsn="{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val: b},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val:q},b:{tbl:1009,row:1013,val: w},c:{tbl:1009,row:1013,val:e},r1_1018:{tbl:1009,row:1018,val:{table:1023,a:{tbl:1023,row:1024,val: c},b:{tbl:1023,row:1027,val:e },c:{tbl:1023,row:1027,val:de}}},r2_1018:{tbl:1009,row:1018,val: dc}}}}";
		jsn="{map:{table:1000,a:{tbl:1000,row:1001,val: r},b:{tbl:1000,row:1004,val: r},c:{tbl:1000,row:1004,val: r}}}";
		jsn="{map:{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val:b },c:{tbl:1000,row:1004,val:c }}}";
		jsn="{map:{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val: b},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val:{table:1027,a:{tbl:1027,row:1028,val:c },b:{tbl:1027,row:1031,val:d },c:{tbl:1027,row:1031,val:{table:1036,a:{tbl:1036,row:1037,val:e },b:{tbl:1036,row:1040,val:f },c:{tbl:1036,row:1040,val:{table:1045,a:{tbl:1045,row:1046,val:g },b:{tbl:1045,row:1049,val:h },c:{tbl:1045,row:1049,val:i }}},r1_1059:{tbl:1036,row:1059,val: g},r2_1059:{tbl:1036,row:1059,val: h}}}}},b:{tbl:1009,row:1013,val:j },c:{tbl:1009,row:1013,val:{table:1018,a:{tbl:1018,row:1019,val:k },b:{tbl:1018,row:1022,val:l },c:{tbl:1018,row:1022,val:m },r1_1054:{tbl:1018,row:1054,val: n},r2_1054:{tbl:1018,row:1054,val: p}}}}}}}";

		System.out.println(jsn);
		//find id {a,b,c,r1_10XX

		Parser p =new Parser(jsn);
		JTable jTable = (JTable) p.getTable();
		System.out.println(jTable);
		HtmlGenerator htmlGenerator=new HtmlGenerator();

		HTable hTable=htmlGenerator.toHtml(jTable);
		System.out.println(hTable.toHtml());
	}
	public JElement getTable(){
		return getTable(input);
	}
	/***
	 * Populate the rows/columns in the table 
	 * @param jsn statement
	 * @return JTable
	 */
	private JElement getTable(String jsn){
		ArrayList<ElementInfo> elements = populateElements(jsn);	
		JElement parent=null;
		JElement current=null;

		for(int i=0; i<elements.size();i++){
			// table will watch for { a b c }
			// { a b c } will watch for prev table

			ElementInfo element = elements.get(i);
			// if table... then assign the id
			if(element.elementName.equals("{table:")){
				current=new JTable();				
				((JTable)current).setId(element.elementValue.val);
				if(i == 0){
					parent=current;	
				}				
			}
			// if column, then search into column for value or table
			else{
				current=new JCOL(element.elementValue.tbl,element.elementValue.row);
				// if column contain table, then make a recursive call to populate its rows
				if(StringUtils.contains(element.elementValue.val, "{table:")){
					JElement childTable = getTable(element.elementValue.val);
					((JCOL)current).setTable(childTable);
				}
				else{
					((JCOL)current).setValue( element.elementValue.val);
				}
				parent.getChild().add(current);				
			}	
		}//end of for		
		return parent;
	}

	private ArrayList<ElementInfo> populateElements(String jsn) {
		ElementInfo element=retriveElement(jsn);
		ArrayList<ElementInfo> chain=new ArrayList<ElementInfo>();
		String nextPartOfStatement="";
		while( element !=null){
			chain.add(element);			
			if(element.statement.contains(element.valueStatment)){
				nextPartOfStatement=StringUtils.removeStart(element.statement, element.valueStatment);
			}
			element=retriveElement(nextPartOfStatement);
		}//end of while
		return chain;
	}

	/***
	 * Keeps on retrieving single element from the statement...
	 * based on the tags
	 * @param statement
	 * @return
	 */
	private ElementInfo retriveElement(String statement) {
		ArrayList<String> tags=new ArrayList<String>(columnTags);
		tags.add("{table:");

		int endIndex=Integer.MAX_VALUE;
		String token="";
		String tokenStatement="";
		String tokenValue="";
		for(String tag : tags){
			if(statement.contains(tag)){
				if(endIndex > statement.indexOf(tag)){
					endIndex=statement.indexOf(tag);
					token=tag;
				}
			}
		}

		if(token.length() == 0)
			return null;
		tokenStatement=statement.substring(endIndex+token.length());
		// if token is table
		if(token.equals("{table:")){
			tokenValue=tokenStatement.substring(0, tokenStatement.indexOf(","));

		}
		//		else token is row
		else
		{
			tokenValue = extractTokenValue(tokenStatement, tokenValue);
		}			
		//find actual Token
		ElementInfo element=new ElementInfo();

		element.elementName=token;		
		element.statement=tokenStatement;
		element.elementValue=getColumnValue(token, tokenValue);
		element.valueStatment=tokenValue;
		return element;
	}

	private ElementValue getColumnValue(String token,String tokenValue) {
		ElementValue element =new ElementValue();
		element.val=tokenValue;
		for(String tag : columnTags){
			if(token.equals(tag) && tokenValue.contains("val:")){				
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

	/***
	 * Extract token value from statement, logic based on the parenthesis
	 * @param tokenStment
	 * @param tokenValue
	 * @return
	 */
	private String extractTokenValue(String tokenStment, String tokenValue) {
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