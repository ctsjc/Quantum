package com.db.modal;

public class RagnarRw {
	D d;
	String id;
	String table;
	String row;
	String val;
	String refTable;
	String senteceId;
	
	public RagnarRw(String sentenceId) {
		this.senteceId=sentenceId;
	}
	public D getD() {
		return d;
	}
	public void setD(D d) {
		this.d = d;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public String getRefTable() {
		return refTable;
	}
	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}
	@Override
	public String toString() {
		return "RagnarRw [" + (d != null ? "d=" + d + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (table != null ? "table=" + table + ", " : "")
				+ (row != null ? "row=" + row + ", " : "")
				+ (val != null ? "val=" + val + ", " : "")
				+ (refTable != null ? "refTable=" + refTable + ", " : "")
				+ (senteceId != null ? "senteceId=" + senteceId : "") + "]";
	}
	public String getSenteceId() {
		return senteceId;
	}
	public void setSenteceId(String senteceId) {
		this.senteceId = senteceId;
	}
	
	
}
