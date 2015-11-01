package com.bpel.preprocess;

public class NodeCaseActivity {
	private String cId = null;
	private String activityType = null;
	private String activityName = null;
	private String condition = null;
	private int startLogicClock = -1;
	private int endLogicClock = -1;
	public NodeCaseActivity(String cId, String activityType,
			String activityName, String condition, int startLogicClock,
			int endLogicClock) {
		super();
		this.cId = cId;
		this.activityType = activityType;
		this.activityName = activityName;
		this.condition = condition;
		this.startLogicClock = startLogicClock;
		this.endLogicClock = endLogicClock;
	}
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
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
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
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
		return "[GID:]"+cId
				+"[activityType:]"+activityType
				+"[activityName:]"+activityName
				+"[condition:]"+condition
				+"[startLogicClock:]"+startLogicClock
				+"[endLogicClock:]"+endLogicClock;
	}
}
