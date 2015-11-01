package com.bpel.preprocess;

public class NodeLink {

	private String name="";
	public NodeLink(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[name:]"+name;
	}
}
