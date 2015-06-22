package com.db.logic;

import java.util.ArrayList;
import java.util.List;

import com.db.modal.D;
import com.db.modal.RagnarRw;
import com.model.conv.HTable;
import com.model.parser.JCOL;
import com.model.parser.JElement;
import com.model.parser.JTable;
import com.statement.logic.Parser;

public class RowGenerator {
	
	public static void main(String[] args) {
		String jsn=null;
		jsn="{map:{table:1000,a:{tbl:1000,row:1001,val:{table:1018,a:{tbl:1018,row:1019,val: AA},b:{tbl:1018,row:1022,val:AB },c:{tbl:1018,row:1022,val:AC }}},b:{tbl:1000,row:1004,val:B},c:{tbl:1000,row:1004,val:{table:1009,a:{tbl:1009,row:1010,val: CA},b:{tbl:1009,row:1013,val:CB},c:{tbl:1009,row:1013,val:{table:1027,a:{tbl:1027,row:1028,val:{table:1045,a:{tbl:1045,row:1046,val:{table:1054,a:{tbl:1054,row:1055,val: CCA},b:{tbl:1054,row:1058,val: CCB},c:{tbl:1054,row:1058,val: CCC}}},b:{tbl:1045,row:1049,val: CCD},c:{tbl:1045,row:1049,val:{table:1063,a:{tbl:1063,row:1064,val: CCEA},b:{tbl:1063,row:1067,val: CCEB},c:{tbl:1063,row:1067,val: CCEC},r1_1072:{tbl:1063,row:1072,val: CCED},r2_1072:{tbl:1063,row:1072,val: CCEE},r1_1077:{tbl:1063,row:1077,val: CCEF},r2_1077:{tbl:1063,row:1077,val: CCEG},r1_1082:{tbl:1063,row:1082,val: CCEH},r2_1082:{tbl:1063,row:1082,val: CCEI}}}}},b:{tbl:1027,row:1031,val: CCF},c:{tbl:1027,row:1031,val:{table:1036,a:{tbl:1036,row:1037,val: CCGA},b:{tbl:1036,row:1040,val: CCGB},c:{tbl:1036,row:1040,val: CCGC}}}}}}}}}";

		Parser p =new Parser(jsn);
		JTable jTable = (JTable) p.getTable();
		System.out.println(jTable);
		RowGenerator gen=new RowGenerator();
		gen.insert(jTable);
	}
	
	public void parse(JElement elements,List<RagnarRw> rows){
		RagnarRw row=null;
		for(int i=0;i<elements.getChild().size();i++){
			JElement jElement = elements.getChild().get(i);
			D d=null;
			if(i == 0)
				d=D.TERM;
			else if(i % 2 == 0)
				d=D.INFO;
			else
				d=D.QUES;
				
			row=new RagnarRw();
			row.setD(d);
			row.setId(String.valueOf(i));
			rows.add(row);
			if(jElement instanceof JTable){
			}
			else if (jElement instanceof JCOL){
				JCOL jcol=(JCOL)jElement;
				String parentRow = jcol.getParentRow();
				String parentTbl = jcol.getParentTbl();
				row.setTable(parentTbl);
				row.setRow(parentRow);

				// its a table
				if(jcol.getTable() != null){
					JElement element=jcol.getTable();
					//get table id
					String tblId = ((JTable)jcol.getTable()).getId();
					row.setRefTable(tblId);
					parse(element,rows);
				}
				//its a column
				else{
					row.setVal(jcol.getValue().getValue());
				}
			}//end of else
		}//end of for
	}//end of method
	
	public void insert(JElement elements){
		List<RagnarRw> rows=new ArrayList<RagnarRw>();
		parse(elements, rows);
		for(RagnarRw dRow:rows){
			System.out.println(dRow);
		}
		JDBCExample dbO=new JDBCExample();
		//dbO.insert(rows);
	}
}
