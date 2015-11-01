package com.bpel.preprocess;

import java.util.HashMap;

/**
 * 处理所有活动，形成hashMap映射 key-GID value-结点
 * @author Sun
 *
 */
public class HashMapAllActivity {
	public static HashMap<String, NodeBaseActivity> baseMap=new HashMap<String, NodeBaseActivity>();
	public static HashMap<String, NodeScopeActivity> scopeMap=new HashMap<String, NodeScopeActivity>();
	public static HashMap<String,NodeAssignActivity> assignMap=new HashMap<String, NodeAssignActivity>();
	public static HashMap<String,NodeCaseActivity> caseMap=new HashMap<String, NodeCaseActivity>();
	public static HashMap<String,NodeSequenceActivity> sequenceMap=new HashMap<String, NodeSequenceActivity>();
	public static HashMap<String,NodeSwitchActivity> switchMap=new HashMap<String, NodeSwitchActivity>();
	public static HashMap<String,NodeFlowActivity> flowMap=new HashMap<String, NodeFlowActivity>();
	public void getAllMap()
	{
		getBaseMap();
		getScopeMap();
		getAssignMap();
		getCaseMap();
		getSequenceMap();
		getSwitchMap();
		getFlowMap();
	}
	public void getBaseMap()
	{
		for (int i = 0; i < SaxBPELProcess.nodeBaseActivityList.size(); i++) {
			baseMap.put(SaxBPELProcess.nodeBaseActivityList.get(i).getgId(), SaxBPELProcess.nodeBaseActivityList.get(i));
		}
	}
	public void getScopeMap()
	{
		for (int i = 0; i < SaxBPELProcess.nodeScopeActivityList.size(); i++) {
			scopeMap.put(SaxBPELProcess.nodeScopeActivityList.get(i).getsId(), SaxBPELProcess.nodeScopeActivityList.get(i));
		}
	}
	public void getAssignMap()
	{
		for (int i = 0; i < SaxBPELProcess.nodeAssignActivityList.size(); i++) {
			assignMap.put(SaxBPELProcess.nodeAssignActivityList.get(i).getaId(), SaxBPELProcess.nodeAssignActivityList.get(i));
		}
	}
	public void getCaseMap()
	{
		for (int i = 0; i < SaxBPELProcess.nodeCaseActivityList.size(); i++) {
			caseMap.put(SaxBPELProcess.nodeCaseActivityList.get(i).getcId(), SaxBPELProcess.nodeCaseActivityList.get(i));
		}
	}
	public void getSequenceMap()
	{
		for (int i = 0; i < SaxBPELProcess.nodeSequenceActivityList.size(); i++) {
			sequenceMap.put(SaxBPELProcess.nodeSequenceActivityList.get(i).getsId(), SaxBPELProcess.nodeSequenceActivityList.get(i));
		}
	}
	public void getSwitchMap()
	{
		for (int i = 0; i < SaxBPELProcess.nodeSwitchActivityList.size(); i++) {
			switchMap.put(SaxBPELProcess.nodeSwitchActivityList.get(i).getsId(), SaxBPELProcess.nodeSwitchActivityList.get(i));
		}
	}
	public void getFlowMap()
	{
		for (int i = 0; i < SaxBPELProcess.nodeFlowActivityList.size(); i++) {
			flowMap.put(SaxBPELProcess.nodeFlowActivityList.get(i).getfId(), SaxBPELProcess.nodeFlowActivityList.get(i));
		}
	}
}
