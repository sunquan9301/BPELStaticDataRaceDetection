package com.bpel.preprocess;

public class NodeSwitchActivity {
	private String sId = null;
	private String activityType = null;
	private String activityName = null;
	private int startLogicClock = -1;
	private int endLogicClock = -1;
	public NodeSwitchActivity(String sId, String activityType,
			String activityName, int startLogicClock, int endLogicClock) {
		super();
		this.sId = sId;
		this.activityType = activityType;
		this.activityName = activityName;
		this.startLogicClock = startLogicClock;
		this.endLogicClock = endLogicClock;
	}
	public String getsId() {
		return sId;
	}
	public void setsId(String sId) {
		this.sId = sId;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[GID:]"+sId+"[activityType:]"+activityType+"[activityName:]"+activityName+"[startLogicClock:]"+startLogicClock
				+"[endLogicClock:]"+endLogicClock;
	}
}
