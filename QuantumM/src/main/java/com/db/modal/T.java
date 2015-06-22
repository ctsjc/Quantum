package com.db.modal;

import java.util.UUID;

public class T {
	String id;
	String val;
	public T(String val) {
		super();
		id=UUID.randomUUID().toString();
		this.val = val;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	@Override
	public String toString() {
		return "T [" + (id != null ? "id=" + id + ", " : "")
				+ (val != null ? "val=" + val : "") + "]";
	}
	
	
}
