package com.bpel.constraint;

public class NodeActivityOrder {
	private String frontActGID="";
	private String laterActGID="";
	public NodeActivityOrder(String frontActGID, String laterActGID) {
		super();
		this.frontActGID = frontActGID;
		this.laterActGID = laterActGID;
	}
	public String getFrontActGID() {
		return frontActGID;
	}
	public void setFrontActGID(String frontActGID) {
		this.frontActGID = frontActGID;
	}
	public String getLaterActGID() {
		return laterActGID;
	}
	public void setLaterActGID(String laterActGID) {
		this.laterActGID = laterActGID;
	}
}
