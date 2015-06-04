package com.statement.logic.db;

import java.util.ArrayList;
import java.util.List;

import com.model.conv.HColumn;
import com.model.conv.HRow;
import com.model.conv.HTable;
import com.model.converter.HtmlGenerator;
import com.model.parser.JTable;
import com.statement.logic.Parser;

public class DBTransformer {
	List<DbRow> dbRows=new ArrayList<DbRow>();
	public static void main(String[] args) {
		String jsn=null;
		jsn="{map:{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val: b},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val: c},b:{tbl:1009,row:1013,val: d},c:{tbl:1009,row:1013,val: e}}}}}";
		jsn="{map:{table:1000,a:{tbl:1000,row:1001,val: a},b:{tbl:1000,row:1004,val:b },c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val:c },b:{tbl:1009,row:1013,val:d },c:{tbl:1009,row:1013,val:e },r1_1018:{tbl:1009,row:1018,val:f },r2_1018:{tbl:1009,row:1018,val:{table:1023,a:{tbl:1023,row:1024,val:g },b:{tbl:1023,row:1027,val:h },c:{tbl:1023,row:1027,val:{table:1032,a:{tbl:1032,row:1033,val:i },b:{tbl:1032,row:1036,val:j },c:{tbl:1032,row:1036,val:k }}}}}}}}}";
		System.out.println(jsn);
		//find id {a,b,c,r1_10XX

		Parser p =new Parser(jsn);
		JTable jTable = (JTable) p.getTable();
		HtmlGenerator htmlGenerator=new HtmlGenerator();

		HTable hTable=htmlGenerator.toHtml(jTable);
		System.out.println(hTable.toHtml());
		DBTransformer transformer=new DBTransformer();
		transformer.tableReader(hTable);
		for(DbRow dbRow:transformer.dbRows){
			System.out.println(dbRow);
		}
	}//end of method

	private void tableReader(HTable hTable) {
		String term = null;
		for(int row_index=0;row_index<hTable.getRows().size();row_index++){
			HRow row = hTable.getRows().get(row_index);
			String ques = null;

			for(int column_index=0;column_index < row.getColumns().size();column_index++){
				HColumn column = row.getColumns().get(column_index);
//				System.out.println(hTable.getId()+"\t"+column_index+"\t"+column.getValue());
				
				//-----------
				if(row_index == 0){
					term=column.getValue();
				}else{
					if(column_index == 0 ){
						ques=column.getValue();
					}else{
						DbRow dbRow=new DbRow();

						dbRow.setId(String.valueOf(hTable.getId()));
						dbRow.setTerm(term);
						dbRow.setQues(ques);
						
						if(column.getValue()!= null){
							dbRow.setInfo(column.getValue());
						}
						else{
							dbRow.setRefId(String.valueOf(column.getChildTable().getId()));
							tableReader(column.getChildTable());
						}
						dbRows.add(dbRow);
					}//else
				}//else
				//-----------
				
			}//end of for
		}//end of for
	}//end of method
}//end of class
