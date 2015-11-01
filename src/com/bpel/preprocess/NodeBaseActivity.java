package com.bpel.preprocess;

import java.util.ArrayList;

/**
 * 存储基本活动信息
 */
public class NodeBaseActivity {
	private String gId=null; //全局Id
	private String activityType=null; //活动类型
	private String activityName=null; //活动名
	private String partnerLink=null;  
	private String inputVariable=null;
	private String outputVariable=null;
	private ArrayList<String> correlation;           //包含锁的名字
	private int LogicClockOrder=-1;       //基本逻辑时钟序列号
	private int isSynchronization=-1;   //只针对invork活动来标识是否同步，其他活动不考虑
	private ArrayList<String> sourceLinkName = null;
	private ArrayList<String> targetLinkName = null;
	
	public NodeBaseActivity(String gId,
			String activityType, String activityName, String partnerLink,
			String inputVariable, String outputVariable,ArrayList<String> lockName,
			int logicClockOrder, int isSynchronization,
			ArrayList<String> sourceLinkName, ArrayList<String> targetLinkName) {
		super();
		this.gId = gId;
		this.activityType = activityType;
		this.activityName = activityName;
		this.partnerLink = partnerLink;
		this.inputVariable = inputVariable;
		this.outputVariable = outputVariable;
		this.correlation = lockName;
		LogicClockOrder = logicClockOrder;
		this.isSynchronization = isSynchronization;
		this.sourceLinkName = sourceLinkName;
		this.targetLinkName = targetLinkName;
	}
	public String getgId() {
		return gId;
	}
	public void setgId(String gId) {
		this.gId = gId;
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
	public int getLogicClockOrder() {
		return LogicClockOrder;
	}
	public void setLogicClockOrder(int logicClockOrder) {
		LogicClockOrder = logicClockOrder;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[GID:]"+gId+"[activityType:]"+activityType+"[activityName:]"+activityName
				+"[inputVariable:]"+inputVariable
				+"[outputVariable:]"+outputVariable
				+"[lockName:]"+correlation
				+"[LogicClock:]"+LogicClockOrder
				+"[isSynchronization:]"+isSynchronization;
	}
}
