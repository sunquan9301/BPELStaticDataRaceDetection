package com.bpel.constraint;

import java.util.ArrayList;
import java.util.HashMap;

import com.bpel.hbconstruct.HBCreat;
import com.bpel.hbconstruct.InterEdgeHB;
import com.bpel.hbconstruct.InterNodeHB;
import com.bpel.hbconstruct.NodeHB;
import com.bpel.preprocess.BPELKeyWords;
import com.bpel.preprocess.NodeAssignActivity;
import com.bpel.preprocess.NodeBaseActivity;
import com.bpel.preprocess.NodeCaseActivity;
import com.bpel.preprocess.SaxBPELProcess;

//a<b,b<c  要不要列a<c了。
public class OrderConstraint {
	public static HashMap<String,ArrayList<String>> actHBOrder=new HashMap<String, ArrayList<String>>();
	//public static HashMap<String, String> actHBOrder=new HashMap<String, String>();
	//public static ArrayList<NodeActivityOrder> actHBOrder=new ArrayList<NodeActivityOrder>();
	public void getActHBOrder()
	{
		for (int i = 0; i < SaxBPELProcess.nodeBaseActivityList.size(); i++) {
			if(SaxBPELProcess.nodeLinkList.size()!=0)
			{
				if(SaxBPELProcess.nodeBaseActivityList.get(i).getActivityType().equals(BPELKeyWords.COPY))
				{
					continue;
				}
			}

			NodeBaseActivity baseFront=SaxBPELProcess.nodeBaseActivityList.get(i);
			for (int j = 0; j < SaxBPELProcess.nodeBaseActivityList.size(); j++) {
				NodeBaseActivity baseLater=SaxBPELProcess.nodeBaseActivityList.get(j);
				if(baseFront!=baseLater)
				{
					if(checkHBOrder(baseFront.getgId(),baseLater.getgId()))
					{
						//NodeActivityOrder nodeActOrder=new NodeActivityOrder(baseFront.getgId(), baseLater.getgId());
						if(actHBOrder.get(baseFront.getgId())==null)
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(baseLater.getgId());
							actHBOrder.put(baseFront.getgId(), temp);
						}else{
							actHBOrder.get(baseFront.getgId()).add(baseLater.getgId());
						}
					}
				}
			}
			for (int j = 0; j < SaxBPELProcess.nodeCaseActivityList.size(); j++) {
				NodeCaseActivity caseLater=SaxBPELProcess.nodeCaseActivityList.get(j);
				if(checkHBOrder(baseFront.getgId(),caseLater.getcId()))
				{
					if(actHBOrder.get(baseFront.getgId())==null)
					{
						ArrayList<String> temp=new ArrayList<String>();
						temp.add(caseLater.getcId());
						actHBOrder.put(baseFront.getgId(), temp);
					}else{
						actHBOrder.get(baseFront.getgId()).add(caseLater.getcId());
					}

				}
			}
			if(SaxBPELProcess.nodeLinkList.size()!=0)
			{
				for (int j = 0; j < SaxBPELProcess.nodeAssignActivityList.size(); j++) {
					NodeAssignActivity AssignLater=SaxBPELProcess.nodeAssignActivityList.get(j);
					if(checkHBOrder(baseFront.getgId(),AssignLater.getaId()))
					{
						if(actHBOrder.get(baseFront.getgId())==null)
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(AssignLater.getaId());
							actHBOrder.put(baseFront.getgId(), temp);
						}else{
							actHBOrder.get(baseFront.getgId()).add(AssignLater.getaId());
						}

					}
				}
			}
		}


		for (int i = 0; i < SaxBPELProcess.nodeCaseActivityList.size(); i++) {
			NodeCaseActivity caseFront_1=SaxBPELProcess.nodeCaseActivityList.get(i);

			for (int j = 0; j < SaxBPELProcess.nodeCaseActivityList.size(); j++) {
				NodeCaseActivity caseLater_1=SaxBPELProcess.nodeCaseActivityList.get(j);
				if(caseFront_1!=caseLater_1)
				{
					if(checkHBOrder(caseFront_1.getcId(),caseLater_1.getcId()))
					{
						if(actHBOrder.get(caseFront_1.getcId())==null)
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(caseLater_1.getcId());
							actHBOrder.put(caseFront_1.getcId(), temp);
						}else{
							actHBOrder.get(caseFront_1.getcId()).add(caseLater_1.getcId());
						}

					}
				}
			}

			for (int j = 0; j < SaxBPELProcess.nodeBaseActivityList.size(); j++) {
				if(SaxBPELProcess.nodeLinkList.size()!=0)
				{
					if(SaxBPELProcess.nodeBaseActivityList.get(j).getActivityType().equals(BPELKeyWords.COPY))
					{
						continue;
					}
				}
				NodeBaseActivity baseLater_1=SaxBPELProcess.nodeBaseActivityList.get(j);
				if(checkHBOrder(caseFront_1.getcId(),baseLater_1.getgId()))
				{
					if(actHBOrder.get(caseFront_1.getcId())==null)
					{
						ArrayList<String> temp=new ArrayList<String>();
						temp.add(baseLater_1.getgId());
						actHBOrder.put(caseFront_1.getcId(), temp);
					}else{
						actHBOrder.get(caseFront_1.getcId()).add(baseLater_1.getgId());
					}

				}
			}

			if(SaxBPELProcess.nodeLinkList.size()!=0)
			{
				for (int j = 0; j < SaxBPELProcess.nodeAssignActivityList.size(); j++) {
					NodeAssignActivity AssignLater_1=SaxBPELProcess.nodeAssignActivityList.get(j);
					if(checkHBOrder(caseFront_1.getcId(),AssignLater_1.getaId()))
					{
						if(actHBOrder.get(caseFront_1.getcId())==null)
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(AssignLater_1.getaId());
							actHBOrder.put(caseFront_1.getcId(), temp);
						}else{
							actHBOrder.get(caseFront_1.getcId()).add(AssignLater_1.getaId());
						}

					}
				}
			}	
		}
		
		if(SaxBPELProcess.nodeLinkList.size()!=0)
		{
			for (int i = 0; i < SaxBPELProcess.nodeAssignActivityList.size(); i++) {
				NodeAssignActivity assignFront=SaxBPELProcess.nodeAssignActivityList.get(i);
				//mine
				for (int q = 0; q < SaxBPELProcess.nodeAssignActivityList.size(); q++) {
					NodeAssignActivity assignLater=SaxBPELProcess.nodeAssignActivityList.get(q);
					if(assignFront!=assignLater)
					{
						if(checkHBOrder(assignFront.getaId(),assignLater.getaId()))
						{
							if(actHBOrder.get(assignFront.getaId())==null)
							{
								ArrayList<String> temp=new ArrayList<String>();
								temp.add(assignLater.getaId());
								actHBOrder.put(assignFront.getaId(), temp);
							}else{
								actHBOrder.get(assignFront.getaId()).add(assignLater.getaId());
							}
						}
					}
				}
				//base
				for (int j = 0; j < SaxBPELProcess.nodeBaseActivityList.size(); j++) {
					if(SaxBPELProcess.nodeBaseActivityList.get(j).getActivityType().equals(BPELKeyWords.COPY))
					{
						continue;
					}
					NodeBaseActivity baseLater_1=SaxBPELProcess.nodeBaseActivityList.get(j);
					if(checkHBOrder(assignFront.getaId(),baseLater_1.getgId()))
					{
						if(actHBOrder.get(assignFront.getaId())==null)
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(baseLater_1.getgId());
							actHBOrder.put(assignFront.getaId(), temp);
						}else{
							actHBOrder.get(assignFront.getaId()).add(baseLater_1.getgId());
						}

					}
				}
				//case
				for (int j = 0; j < SaxBPELProcess.nodeCaseActivityList.size(); j++) {
					NodeCaseActivity caseLater_1=SaxBPELProcess.nodeCaseActivityList.get(j);
					if(checkHBOrder(assignFront.getaId(),caseLater_1.getcId()))
					{
						if(actHBOrder.get(assignFront.getaId())==null)
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(caseLater_1.getcId());
							actHBOrder.put(assignFront.getaId(), temp);
						}else{
							actHBOrder.get(assignFront.getaId()).add(caseLater_1.getcId());
						}
					}
				}
			}
		}
		
	}
	//检查HBOrder
	private boolean checkHBOrder(String frontGID, String laterGID) {
		// TODO Auto-generated method stub
		boolean result=false;
		//NodeHB frontNode=null;
		boolean intraCheck=false;
		if(HBCreat.intraHBGraphs!=null)
		{
			NodeHB intraFrontNode=null;
			for (int i = 0; i < HBCreat.intraHBGraphs.size(); i++) {
				NodeHB intraFlowStart=HBCreat.intraHBGraphs.get(i);
				intraFrontNode=searchNodeInIntra(intraFlowStart,frontGID);
				if(intraFrontNode!=null)
				{
					intraCheck=true;
					break;
				}
			}
			//找到前结点且不为空才开始查找是否有偏序关系
			if(intraFrontNode!=null)
			{
				if(checkHBOrderBetweenAct(intraFrontNode,laterGID))
				{
					return true;
				}
			}
		}
		//在intra中没有找到结点
		if(!intraCheck)
		{
			//在inter中寻找
			if(HBCreat.interHBGraphs!=null)
			{
				InterNodeHB interFrontNode=null;
				for (int i = 0; i < HBCreat.interHBGraphs.size(); i++) {
					InterNodeHB interFlowStart=HBCreat.interHBGraphs.get(i);
					interFrontNode=searchNodeInInter(interFlowStart,frontGID);
					if(interFlowStart!=null)
					{
						break;
					}
				}
				//是否找到
				if(interFrontNode!=null)
				{
					if(checkHBOrderBetweenAct(interFrontNode,laterGID))
					{
						return true;
					}
				}

			}
		}
		return result;
	}
	private boolean checkHBOrderBetweenAct(InterNodeHB interFrontNode,
			String laterGID) {
		// TODO Auto-generated method stub
		boolean result=false;
		if(interFrontNode.getgId().equals(laterGID))
		{
			return true;
		}
		for (int i = 0; i < interFrontNode.getInterEdgeList().size(); i++) {
			InterEdgeHB interEdge=interFrontNode.getInterEdgeList().get(i);
			result = checkHBOrderBetweenAct(interEdge.getTargetNode(),laterGID);
			if(result)
			{
				return result;
			}
		}
		return false;
	}
	private boolean checkHBOrderBetweenAct(NodeHB intraFrontNode,
			String laterGID) {
		// TODO Auto-generated method stub
		boolean result=false;
		for (int i = 0; i < intraFrontNode.getChildrenHBNode().size(); i++) {
			NodeHB child=intraFrontNode.getChildrenHBNode().get(i);
			if(child.getgId().equals(laterGID))
			{
				return true;
			}
			result = checkHBOrderBetweenAct(child, laterGID);
			if(result)
			{
				return result;
			}
		}
		return result;
	}
	//interNodeHB中搜索前面结点
	private InterNodeHB searchNodeInInter(InterNodeHB interFlowStart,
			String frontGID) {
		// TODO Auto-generated method stub
		InterNodeHB result=null;
		if(interFlowStart.getgId().equals(frontGID))
		{
			return interFlowStart;
		}
		for (int i = 0; i < interFlowStart.getInterEdgeList().size(); i++) {
			InterEdgeHB interEdge=interFlowStart.getInterEdgeList().get(i);
			result=searchNodeInInter(interEdge.getTargetNode(),frontGID);
			if(result!=null)
			{
				return result;
			}
		}
		return result;
	}
	//intra中搜索前面给定结点
	private NodeHB searchNodeInIntra(NodeHB intraFlowStart,
			String frontGID) {
		// TODO Auto-generated method stub
		NodeHB result=null;
		for (int i = 0; i < intraFlowStart.getChildrenHBNode().size(); i++) {
			NodeHB child=intraFlowStart.getChildrenHBNode().get(i);
			if(child.getgId().equals(frontGID))
			{
				return child;
			}
			result = searchNodeInIntra(child, frontGID);
			if(result!=null)
			{
				return result;
			}
		}
		return result;
	}
}