package com.model.parser;


public class JTable extends JElement{
	public JTable() {}
	String id;
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "\n\tJTable {id:" + id + ","+super.toString();
	}

	
	public String getId() {
		return id;
	}
}
