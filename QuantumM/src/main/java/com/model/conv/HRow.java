package com.model.conv;

import java.util.ArrayList;
import java.util.List;

public class HRow {
	List<HColumn> columns;
	
	public HRow() {
		// TODO Auto-generated constructor stub
		columns=new ArrayList<HColumn>();
	}
	int id;
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	
	@Override
	public String toString() {
		return "HRow [columns=" + columns + ", id=" + id + "]";
	}
	public String toHtml() {
		return "<tr>" + columnHtml() + "</tr>";
	}
	
	private String columnHtml() {
		String htmlFormat="";
		for(HColumn hColumn:columns){
			htmlFormat+=hColumn.toHtml(columns.size());
		}
		return htmlFormat;
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
	public List<HColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<HColumn> columns) {
		this.columns = columns;
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
	
	
}
