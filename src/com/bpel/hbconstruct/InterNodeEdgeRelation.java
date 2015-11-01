package com.bpel.hbconstruct;

public class InterNodeEdgeRelation {

	//node结点相对于边的关系
	private InterEdgeHB edge=null;
	private String nodeEdgeRelation=null;
	public InterNodeEdgeRelation(InterEdgeHB edge, String nodeEdgeRelation) {
		super();
		this.edge = edge;
		this.nodeEdgeRelation = nodeEdgeRelation;
	}
	public InterEdgeHB getEdge() {
		return edge;
	}
	public void setEdge(InterEdgeHB edge) {
		this.edge = edge;
	}
	public String getNodeEdgeRelation() {
		return nodeEdgeRelation;
	}
	public void setNodeEdgeRelation(String nodeEdgeRelation) {
		this.nodeEdgeRelation = nodeEdgeRelation;
	}
	
}
