package com.model.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class JElement {
	List<JElement> child=new ArrayList<JElement>();
		
	public List<JElement> getChild() {
		return child;
	}
	public void setChild(List<JElement> child) {
		this.child = child;
	}
	@Override
	public String toString() {
		return "{" + child + "}";
	}
	
}
