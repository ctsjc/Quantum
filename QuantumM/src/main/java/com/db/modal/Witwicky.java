package com.db.modal;

import java.util.UUID;

public class Witwicky {
	String id;
	String t;
	String c;
	public Witwicky(String t, String c) {
		super();
		id=UUID.randomUUID().toString();
		this.t = t;
		this.c = c;
	}
	@Override
	public String toString() {
		return "Witwicky [" + (id != null ? "id=" + id + ", " : "")
				+ (t != null ? "t=" + t + ", " : "")
				+ (c != null ? "c=" + c : "") + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	
}
