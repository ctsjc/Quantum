package com.statement.logic.db;

public class DbRow {

	String id;
	String refId;
	String term;
	String ques;
	String info;
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getQues() {
		return ques;
	}
	public void setQues(String ques) {
		this.ques = ques;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	@Override
	public String toString() {
		return "DbRow [id=" + id + ", refId=" + refId + ", term=" + term
				+ ", ques=" + ques + ", info=" + info + "]";
	}
}
