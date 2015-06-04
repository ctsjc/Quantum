package com.model.parser;

public class JCOL extends JElement {
		Value value;
		JElement table;
		String parentTbl;
		String parentRow;
		public class Value{
			/*String tbl;
			String rowid;*/
			String value;
			public Value(String value) {
				super();
				this.value = value;
			}
			public String getValue() {
				return value;
			}
			@Override
			public String toString() {
				return "Value [value=" + value + "]";
			}
			
		}
		
		public void setValue(String value) {
			this.value = new Value(value);
		}
		
		public JCOL(String tbl, String rowid) {
			super();
			this.parentTbl = tbl;
			this.parentRow = rowid;
		}

		public Value getValue() {
			return value;
		}

		public void setValue(Value value) {
			this.value = value;
		}


		public JElement getTable() {
			return table;
		}

		public void setTable(JElement table) {
			this.table = table;
		}

		public String getParentTbl() {
			return parentTbl;
		}

		public void setParentTbl(String parentTbl) {
			this.parentTbl = parentTbl;
		}

		public String getParentRow() {
			return parentRow;
		}

		public void setParentRow(String parentRow) {
			this.parentRow = parentRow;
		}

		@Override
		public String toString() {
			return "JCOL ["
					+ (parentTbl != null ? "pTbl=" + parentTbl + ", " : "")
					+ (parentRow != null ? "pRow=" + parentRow + ", " : "")
					+ (value != null ? "value=" + value + ", " : "")
					+ (table != null ? "table=" + table : "") + "]";
		}

		
}
