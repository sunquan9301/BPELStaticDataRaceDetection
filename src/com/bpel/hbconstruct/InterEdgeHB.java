package com.bpel.hbconstruct;

public class InterEdgeHB {
	private InterNodeHB sourceNode=null;
	private String linkName=null;
	private InterNodeHB targetNode=null;
	private String transitionCondition=null;
	public InterEdgeHB(InterNodeHB sourceNode, String linkName,
			InterNodeHB targetNode, String transitionCondition) {
		super();
		this.sourceNode = sourceNode;
		this.linkName = linkName;
		this.targetNode = targetNode;
		this.transitionCondition = transitionCondition;
	}
	public InterNodeHB getSourceNode() {
		return sourceNode;
	}
	public void setSourceNode(InterNodeHB sourceNode) {
		this.sourceNode = sourceNode;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public InterNodeHB getTargetNode() {
		return targetNode;
	}
	public void setTargetNode(InterNodeHB targetNode) {
		this.targetNode = targetNode;
	}
	public String getTransitionCondition() {
		return transitionCondition;
	}
	public void setTransitionCondition(String transitionCondition) {
		this.transitionCondition = transitionCondition;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[sourceNode:]"+sourceNode.getgId()+"  :  "+sourceNode.getType()+"\n\r"
				+"[LinkName:]"+linkName+"\n\r"
				+"[targetNode:]"+targetNode.getgId()+"  :  "+targetNode.getType()+"\n\r"
				+"[transitionCondition:]"+transitionCondition;
	}
}
