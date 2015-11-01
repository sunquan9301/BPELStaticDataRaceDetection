package com.bpel.hbconstruct;

import java.util.ArrayList;

public class NodeHB {

	private ArrayList<NodeHB> childrenHBNode=new ArrayList<NodeHB>();
	private String gId=null;
	private String tId=null;
	private String lId=null;
	private String type=null;
	private String name=null;
	private String partnerLink=null;  
	private String inputVariable=null;
	private String outputVariable=null;
	private ArrayList<String> correlation;           //后期需要处理
//	private String isolated;           //两种锁需要处理
	private int isSynchronization=-1;   //只针对invork活动来标识是否同步，其他活动不考虑
	private ArrayList<String> sourceLinkName = null;
	private ArrayList<String> targetLinkName = null;
	private String condition=null;
	private boolean isolataScope=false;
	public NodeHB(String gId, String tId, String lId, String type, String name,
			String partnerLink, String inputVariable, String outputVariable,
			ArrayList<String> correlation, int isSynchronization,
			ArrayList<String> sourceLinkName, ArrayList<String> targetLinkName) {
		super();
		this.gId = gId;
		this.tId = tId;
		this.lId = lId;
		this.type = type;
		this.name = name;
		this.partnerLink = partnerLink;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
		this.correlation = correlation;
		this.isSynchronization = isSynchronization;
		this.sourceLinkName = sourceLinkName;
		this.targetLinkName = targetLinkName;
	}
	
	public NodeHB(String gId, String tId,
			String lId, String type, String name, String partnerLink,
			String inputVariable, String outputVariable, ArrayList<String> correlation,
			int isSynchronization, ArrayList<String> sourceLinkName,
			ArrayList<String> targetLinkName, String condition,
			boolean isolataScope) {
		super();
		this.gId = gId;
		this.tId = tId;
		this.lId = lId;
		this.type = type;
		this.name = name;
		this.partnerLink = partnerLink;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
		this.correlation = correlation;
		this.isSynchronization = isSynchronization;
		this.sourceLinkName = sourceLinkName;
		this.targetLinkName = targetLinkName;
		this.condition = condition;
		this.isolataScope = isolataScope;
	}

	public boolean isIsolataScope() {
		return isolataScope;
	}

	public void setIsolataScope(boolean isolataScope) {
		this.isolataScope = isolataScope;
	}

	public ArrayList<NodeHB> getChildrenHBNode() {
		return childrenHBNode;
	}

	public void setChildrenHBNode(ArrayList<NodeHB> childrenHBNode) {
		this.childrenHBNode = childrenHBNode;
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
	public String getlId() {
		return lId;
	}
	public void setlId(String lId) {
		this.lId = lId;
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
	public ArrayList<String> getCorrelation() {
		return correlation;
	}
	public void setCorrelation(ArrayList<String> correlation) {
		this.correlation = correlation;
	}
	public int getIsSynchronization() {
		return isSynchronization;
	}
	public void setIsSynchronization(int isSynchronization) {
		this.isSynchronization = isSynchronization;
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
	
	public NodeHB(String gId, String tId,
			String lId, String type, String name, String partnerLink,
			String inputVariable, String outputVariable, ArrayList<String> correlation,
			int isSynchronization, ArrayList<String> sourceLinkName,
			ArrayList<String> targetLinkName, String condition) {
		super();
		this.gId = gId;
		this.tId = tId;
		this.lId = lId;
		this.type = type;
		this.name = name;
		this.partnerLink = partnerLink;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
		this.correlation = correlation;
		this.isSynchronization = isSynchronization;
		this.sourceLinkName = sourceLinkName;
		this.targetLinkName = targetLinkName;
		this.condition = condition;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[Type:]"+type+"\n\r"
				+"[GID:]"+gId+"[TID:]"+tId+"[LID:]"+lId+"\n\r"
				+"[inputVariable:]"+inputVariable
				+"[outputVariable:]"+outputVariable;
	}
}
