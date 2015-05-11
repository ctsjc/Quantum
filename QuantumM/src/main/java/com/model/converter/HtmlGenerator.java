package com.model.converter;

import com.model.conv.HColumn;
import com.model.conv.HRow;
import com.model.conv.HTable;
import com.model.parser.JCOL;
import com.model.parser.JElement;
import com.model.parser.JTable;

public class HtmlGenerator {

	HTable hTable=new HTable();
	public HTable toHtml(JElement elements){
		for(JElement jElement:elements.getChild()){
			if(jElement instanceof JTable){
			}
			else if (jElement instanceof JCOL){
				JCOL jcol=(JCOL)jElement;
				
				// its a table
				String parentRow = jcol.getParentRow();
				String parentTbl = jcol.getParentTbl();
				if(jcol.getTable() != null){
					JElement element=jcol.getTable();
					String tblId = ((JTable)jcol.getTable()).getId();
					//Add a table
					HTable hTable=new HTable();
					hTable.setId(tblId);
					HColumn column=new HColumn();
					column.setChildTable(hTable);
					findRow(parentTbl,parentRow).getColumns().add(column);
					toHtml(element);
					
				}
				//its a column
				else{
					HColumn column=new HColumn();
					String value = jcol.getValue().getValue();
					column.setValue(value);
					//addToRow(column);
					// find the row in table
					if(hTable.getId() == 0){
						hTable.setId(parentTbl);
					}
					findRow(parentTbl,parentRow).getColumns().add(column);
				}
			}//end of else
		}//end of for
		return hTable;
	}//end of method
	private HRow findRow(String parentTbl, String parentRow) {
		HRow newhRow=null;
		for(HRow hRow: hTable.getRows()){
			if(hRow.getId() == Integer.parseInt(parentRow))
				return hRow;
			
			for(HColumn hColumn:hRow.getColumns()){				
				if(hColumn.getChildTable()!=null && hColumn.getChildTable().getId() == Integer.parseInt(parentTbl)){
					// Table found. now search row in this table
					// if found ok.
					//else create one and return
					for(HRow hRow2: hColumn.getChildTable().getRows()){
						if(hRow2.getId() == Integer.parseInt(parentRow))
							return hRow2;
					}//end of hRow2
					newhRow=new HRow();
					newhRow.setId(parentRow);
					hColumn.getChildTable().getRows().add(newhRow);
					return newhRow;					
				}//end of if
			}//end of hRow
		}//end of for
		newhRow=new HRow();
		newhRow.setId(parentRow);
		hTable.getRows().add(newhRow);
		return newhRow;					
	}//end of findRow
}//end of class
