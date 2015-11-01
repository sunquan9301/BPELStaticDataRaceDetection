package com.bpel.hbconstruct;

import java.util.ArrayList;

public class InterNodeHB {
	//每一个活动就是一个线程。
	private ArrayList<InterEdgeHB> interEdgeList=null;
	private String gId=null;
	private String tId=null;
	private String type=null;
	private String name=null;
	private String partnerLink=null;  
	private String inputVariable=null;
	private String outputVariable=null;
	private ArrayList<String> correlation;           //后期需要处理
//	private String isolated;           //两种锁需要处理
	private ArrayList<String> sourceLinkName = new ArrayList<String>();
	private ArrayList<String> targetLinkName = new ArrayList<String>();
	public InterNodeHB(
			String gId, String tId, String type, String name,
			String partnerLink, String inputVariable, String outputVariable,
			ArrayList<String> correlation, ArrayList<String> sourceLinkName,
			ArrayList<String> targetLinkName) {
		super();
		this.interEdgeList = new ArrayList<InterEdgeHB>();
		this.gId = gId;
		this.tId = tId;
		this.type = type;
		this.name = name;
		this.partnerLink = partnerLink;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
		this.correlation = correlation;
		this.sourceLinkName = sourceLinkName;
		this.targetLinkName = targetLinkName;
	}

	public ArrayList<InterEdgeHB> getInterEdgeList() {
		return interEdgeList;
	}

	public void setInterEdgeList(ArrayList<InterEdgeHB> interEdgeList) {
		this.interEdgeList = interEdgeList;
	}

	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
	}
	public String gettId() {
		return tId;
	}
	public void settId(String tId) {
		this.tId = tId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartnerLink() {
		return partnerLink;
	}
	public void setPartnerLink(String partnerLink) {
		this.partnerLink = partnerLink;
	}
	public String getInputVariable() {
		return inputVariable;
	}
	public void setInputVariable(String inputVariable) {
		this.inputVariable = inputVariable;
	}
	public String getOutputVariable() {
		return outputVariable;
	}
	public void setOutputVariable(String outputVariable) {
		this.outputVariable = outputVariable;
	}
	public ArrayList<String> getLockName() {
		return correlation;
	}
	public void setLockName(ArrayList<String> correlation) {
		this.correlation = correlation;
	}
	public ArrayList<String> getSourceLinkName() {
		return sourceLinkName;
	}
	public void setSourceLinkName(ArrayList<String> sourceLinkName) {
		this.sourceLinkName = sourceLinkName;
	}
	public ArrayList<String> getTargetLinkName() {
		return targetLinkName;
	}
	public void setTargetLinkName(ArrayList<String> targetLinkName) {
		this.targetLinkName = targetLinkName;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[Type:]"+type+"\n\r"
				+"[Name:]"+name+"\n\r"
				+"[GID:]"+gId+"[TID:]"+tId+"[LID:]"+"\n\r"
				+"[inputVariable:]"+inputVariable
				+"[outputVariable:]"+outputVariable;
	}
}
