package com.model.converter;

import java.util.Iterator;

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
				System.out.println("1. Element "+jcol);
				String parentRow = jcol.getParentRow();
				String parentTbl = jcol.getParentTbl();
				// its a table
				if(jcol.getTable() != null){
					JElement element=jcol.getTable();
					//get table id
					String tblId = ((JTable)jcol.getTable()).getId();
					//Create a new table
					HTable nTbl=new HTable();
					nTbl.setId(tblId);
					//Create a new column
					HColumn column=new HColumn();

					// set table into column
					column.setChildTable(nTbl);

					//find the row in which new column to have added
					findRow(parentTbl,parentRow).getColumns().add(column);
					System.out.println("4. My Row  "+hTable+"\n");

					// make a recursive call for newly found table
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
					System.out.println("4. My Row  "+hTable+"\n");

				}
			}//end of else
		}//end of for

		return hTable;
	}//end of method
	private HRow test(String parentTbl, String parentRow, HTable tbl){
		HRow hRow=null;
		Iterator<HRow> it =tbl.getRows().iterator();
		while(it.hasNext()){
			HRow row = it.next();
			System.out.println("2 test :: "+"\t("+parentTbl+", "+parentRow+ ") Into ("+ tbl.getId()+","+row.getId()+")");
			if(Integer.parseInt(parentTbl) == tbl.getId() && Integer.parseInt(parentRow) == row.getId()){
				System.out.println("FOUND");
				hRow= row;
			}else{
				for(HColumn hColumn:row.getColumns()){	
					if(hColumn.getChildTable()!=null){
						System.out.println("...[]...");
						hRow = test(parentTbl,parentRow,hColumn.getChildTable());
					}
				}//end of for column
			}		
		}
		return hRow;
	}
	private HRow addRowToTable(String parentTbl, String parentRow, HTable tbl){
		HRow hRow=null;
		if(Integer.parseInt(parentTbl) == tbl.getId() ){
			System.out.println("3. Adding new Row "+parentRow+" to "+parentTbl+" into "+tbl);
			HRow newhRow = new HRow();
			newhRow.setId(parentRow);
			tbl.getRows().add(newhRow);
			return newhRow;
		}else{
			Iterator<HRow> it =tbl.getRows().iterator();
			while(it.hasNext()){
				HRow row = it.next();
				if(Integer.parseInt(parentTbl) == tbl.getId() ){
					System.out.println("Adding new Row"+parentRow+" to "+parentTbl+" into "+tbl);
					HRow newhRow = new HRow();
					newhRow.setId(parentRow);
					tbl.getRows().add(newhRow);
					return newhRow;
				}else{
					for(HColumn hColumn:row.getColumns()){	
						if(hColumn.getChildTable()!=null){
							return addRowToTable(parentTbl,parentRow,hColumn.getChildTable());
						}
					}//end of for column
				}	
			}
		}
		return hRow;
	}
	private HRow findRow(String parentTbl, String parentRow) {
		HRow newhRow=test(parentTbl,parentRow,hTable);
		if(newhRow == null){
			newhRow =  addRowToTable(parentTbl,parentRow,hTable);
		}
		return newhRow;
		/*for(HRow hRow: hTable.getRows()){
			if(hRow.getId() == Integer.parseInt(parentRow))
				return hRow;

			for(HColumn hColumn:hRow.getColumns()){			

				System.out.println("\nColumn ID :: "+hColumn+"\n\n");

				if(hColumn.getChildTable()!=null && hColumn.getChildTable().getId() == Integer.parseInt(parentTbl)){
					// Table found. now search row in this table
					// if found ok.
					//else create one and return
					for(HRow hRow2: hColumn.getChildTable().getRows()){
						//if hRow is table needs recursion over here
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
		return newhRow;	*/				
	}//end of findRow
}//end of class
