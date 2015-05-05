package com.model.converter;

import com.model.parser.JCOL;
import com.model.parser.JElement;
import com.model.parser.JTable;

public class HtmlGenerator {
	public void toHtml(JElement elements){
		for(JElement jElement:elements.getChild()){
//			System.out.println(jElement);
			if(jElement instanceof JTable){
				System.out.println("Table");
			}
			else if (jElement instanceof JCOL){
				System.out.println("Column");
				JCOL jcol=(JCOL)jElement;
				if(jcol.getTable() != null){
					System.out.println("Table");

					JElement element=jcol.getTable();
					System.out.println(jcol.getTable().getId());
					
					toHtml(element);
				}else{
					System.out.println(jcol.getValue().getRowid()+"\t"+jcol.getValue().getValue());
				}
					
			}//end of else
			
			System.out.println("------------------------");
			
		}//end of for
	}//end of method
}//end of class
