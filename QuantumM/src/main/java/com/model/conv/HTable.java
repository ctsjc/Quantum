package com.model.conv;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HTable {

	List<HRow> rows;
	public HTable() {
		rows=new CopyOnWriteArrayList<HRow>();
	}
	int id;
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}
	public int getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HRow other = (HRow) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public List<HRow> getRows() {
		return rows;
	}
	public void setRows(List<HRow> rows) {
		this.rows = rows;
	}
	public String toHtml() {
		return "<table  border=1>" + rowsHtml() + "</table>";
	}
	private String rowsHtml() {
		String htmlFormat="";
		for(HRow hRow:rows){
			htmlFormat+=hRow.toHtml();
		}
		return htmlFormat;
	}
	@Override
	public String toString() {
		return "HTable [id=" + id + ", " + (rows != null ? "rows=" + rows : "")
				+ "]";
	}

	
}
