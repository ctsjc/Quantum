package com.model.parser;

public class JCOL extends JElement {
		Value value;
		JTable table;
		
		public class Value{
			String tbl;
			String rowid;
			String value;
			public Value(String rowid, String value) {
				super();
				this.rowid = rowid;
				this.value = value;
			}
			@Override
			public String toString() {
				
				return rowid+" , "+value;
			}
			public String getRowid() {
				return rowid;
			}
			public String getValue() {
				return value;
			}
			
		}
		
		public void setValue(String rowid, String value) {
			this.value = new Value(rowid, value);
		}

		@Override
		public String toString() {
			if(value !=null && table != null){
				return "{" + value + "," + table + "}";		
			}
			else if(value !=null )
				return "{" + value + "}";
			else
				return "{" + table + "}";
		}
		
		public void setTable(JElement table) {
			this.table = (JTable) table;
		}
		
		public JTable getTable() {
			return table;
		}
		
		public Value getValue() {
			return value;
		}
		
}
