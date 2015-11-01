package com.bpel.hbconstruct;

import java.util.ArrayList;

import com.bpel.preprocess.BPELKeyWords;
import com.bpel.preprocess.NodeAssignActivity;
import com.bpel.preprocess.NodeBaseActivity;
import com.bpel.preprocess.NodeScopeActivity;
import com.bpel.preprocess.NodeSequenceActivity;
import com.bpel.preprocess.NodeSwitchActivity;

public class NodeGetMinObject {

	public static NodeSequenceActivity getMinSequenceNode(ArrayList<NodeSequenceActivity> nodeSequenceActivityList,int startGO,int endGO)
	{
		NodeSequenceActivity nodeSequenceActivity=null;
		int i;
		for (i = 0; i < nodeSequenceActivityList.size(); i++) {
			nodeSequenceActivity=nodeSequenceActivityList.get(i);
			if(nodeSequenceActivity.getStartLogicClock()>startGO&&nodeSequenceActivity.getStartLogicClock()<endGO)
			{
				break;
			}
		}
		if(i==nodeSequenceActivityList.size())
		{
			return null;
		}
		//nodeSequenceActivityList.remove(i);
		return nodeSequenceActivity;
	}
	public static NodeScopeActivity getMinScopeNode(ArrayList<NodeScopeActivity> nodeScopeActivityList,int startGO,int endGO)
	{
		NodeScopeActivity nodeScopeActivity=null;
		int i;
		for (i = 0; i < nodeScopeActivityList.size(); i++) {
			nodeScopeActivity=nodeScopeActivityList.get(i);
			if(nodeScopeActivity.getStartLogicClock()>startGO&&nodeScopeActivity.getStartLogicClock()<endGO)
			{
				break;
			}
		}
		if(i==nodeScopeActivityList.size())
		{
			return null;
		}
		//nodeScopeActivityList.remove(i);
		return nodeScopeActivity;
	}
	public static NodeBaseActivity getMinBaseNode(ArrayList<NodeBaseActivity> nodeBaseActivityList,int startGO,int endGO)
	{
		NodeBaseActivity nodebaseActivity=null;
		int i;
		for (i = 0; i < nodeBaseActivityList.size(); i++) {
			nodebaseActivity=nodeBaseActivityList.get(i);
//			if(nodebaseActivity.getActivityType().equals(BPELKeyWords.COPY))
//			{
//				continue;
//			}
			if(nodebaseActivity.getLogicClockOrder()>startGO&&nodebaseActivity.getLogicClockOrder()<endGO)
			{
				break;
			}
		}
		if(i==nodeBaseActivityList.size())
		{
			return null;
		}
		//nodeScopeActivityList.remove(i);
		return nodebaseActivity;
	}
	public static NodeAssignActivity getMinAssignNode(ArrayList<NodeAssignActivity> nodeAssignActivityList,int startGO,int endGO)
	{
		NodeAssignActivity nodeAssignActivity=null;
		int i;
		for (i = 0; i < nodeAssignActivityList.size(); i++) {
			nodeAssignActivity=nodeAssignActivityList.get(i);
			if(nodeAssignActivity.getStartLogicClock()>startGO&&nodeAssignActivity.getEndLogicClock()<endGO)
			{
				break;
			}
		}
		if(i==nodeAssignActivityList.size())
		{
			return null;
		}
		//nodeScopeActivityList.remove(i);
		return nodeAssignActivity;
	}
	public static NodeSwitchActivity getMinSwitchNode(ArrayList<NodeSwitchActivity> nodeSwitchActivityList,int startGO,int endGO)
	{
		NodeSwitchActivity nodeSwitchActivity=null;
		int i;
		for (i = 0; i < nodeSwitchActivityList.size(); i++) {
			nodeSwitchActivity=nodeSwitchActivityList.get(i);
			if(nodeSwitchActivity.getStartLogicClock()>startGO&&nodeSwitchActivity.getEndLogicClock()<endGO)
			{
				break;
			}
		}
		if(i==nodeSwitchActivityList.size())
		{
			return null;
		}
		//nodeScopeActivityList.remove(i);
		return nodeSwitchActivity;
	}
}
