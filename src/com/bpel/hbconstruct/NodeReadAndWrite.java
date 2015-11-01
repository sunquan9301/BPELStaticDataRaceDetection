package com.bpel.hbconstruct;

public class NodeReadAndWrite {

	private String activityGID=null;
	private String activityType=null;
	private String activityName=null;
	public NodeReadAndWrite(String activityGID, String activityType,
			String activityName) {
		super();
		this.activityGID = activityGID;
		this.activityType = activityType;
		this.activityName = activityName;
	}
	public String getActivityGID() {
		return activityGID;
	}
	public void setActivityGID(String activityGID) {
		this.activityGID = activityGID;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[activityGID:  ]"+activityGID+"[   activityType:   ]"+activityType
				+"[    activityName:   ]"+activityName;
	}
}
