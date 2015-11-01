package com.bpel.preprocess;

import java.util.ArrayList;

public class NodeAssignActivity {
	private String aId = null;
	private String activityType = null;
	private String activityName = null;
	private int startLogicClock = -1;
	private int endLogicClock = -1;
	private String inputVariable="";
	private String outputVariable="";
	private ArrayList<String> sourceLinkName = null;
	private ArrayList<String> targetLinkName = null;
	private ArrayList<String> correlation=null;
	public NodeAssignActivity(String aId, String activityType,
			String activityName, String inputVariable,String outputVariable,int startLogicClock, int endLogicClock,ArrayList<String> sourceLinkName,ArrayList<String> targetLinkName) {
		super();
		this.aId = aId;
		this.activityType = activityType;
		this.activityName = activityName;
		this.startLogicClock = startLogicClock;
		this.endLogicClock = endLogicClock;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
		this.sourceLinkName = sourceLinkName;
		this.targetLinkName = targetLinkName;
	}
	public ArrayList<String> getCorrelation() {
		return correlation;
	}
	public void setCorrelation(ArrayList<String> correlation) {
		this.correlation = correlation;
	}

	public String getaId() {
		return aId;
	}
	public void setaId(String aId) {
		this.aId = aId;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public int getStartLogicClock() {
		return startLogicClock;
	}
	public void setStartLogicClock(int startLogicClock) {
		this.startLogicClock = startLogicClock;
	}
	public int getEndLogicClock() {
		return endLogicClock;
	}
	public void setEndLogicClock(int endLogicClock) {
		this.endLogicClock = endLogicClock;
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
		return "[GID:]"+aId
				+"[activityType:]"+activityType
				+"[activityName:]"+activityName
				+"[inputVariable:]"+inputVariable
				+"[outputVariable:]"+outputVariable
				+"[startLogicClock:]"+startLogicClock
				+"[endLogicClock:]"+endLogicClock;
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
}
