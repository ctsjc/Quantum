package com.model.conv;

public class HColumn {
	String value;
	HTable childTable;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String toHtml(int size) {
		
		if(value != null){
			return  size < 2? "<td  colspan=2>"+value.toString()+"</td>":"<td  colspan=1>"+value.toString()+"</td>";
		}
		else{
			return "<td>"+childTable.toHtml()+"</td>";
		}		
	}
	
	
	public HTable getChildTable() {
		return childTable;
	}
	public void setChildTable(HTable childTable) {
		this.childTable = childTable;
	}
	@Override
	public String toString() {
		return "HColumn [" + (value != null ? "val=" + value + ", " : "")
				+ (childTable != null ? "chldTbl=" + childTable : "") + "]";
	}
	
}
