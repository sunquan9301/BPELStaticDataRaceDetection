package com.bpel.hbconstruct;

import java.util.ArrayList;

import org.xml.sax.SAXParseException;

import com.bpel.preprocess.BPELKeyWords;
import com.bpel.preprocess.IsSynchronization;
import com.bpel.preprocess.NodeAssignActivity;
import com.bpel.preprocess.NodeBaseActivity;
import com.bpel.preprocess.NodeCaseActivity;
import com.bpel.preprocess.NodeFlowActivity;
import com.bpel.preprocess.NodeScopeActivity;
import com.bpel.preprocess.NodeSequenceActivity;
import com.bpel.preprocess.NodeSwitchActivity;
import com.bpel.preprocess.SaxBPELProcess;

/**
 * 构造happenbefore图，分为2中情况，
 * 一:link不为空的情况,由link链接的情况   Inter
 * 二：由组合活动构造的happenbefore图， Intra
 * 
 * 未来需要工作：
 * 1.这里没考虑锁的情况
 * 2.没考虑到while组合情况
 * 3.没考虑到switch,case组合情况，case里面有otherwise关键字。
 * 4.没考虑到if条件情况。
 * 
 * 思路
 * link为空，组合活动为空
 * 1.每一个活动都是一个线程。 Intra
 * link为空的情况，组合活动不全为空的情况 Inter
 * 1.flow 链接组合活动图 flow start [sequence,scope,while,switch] flow end
 * 2.组合活动递归内部链接构建基本后动
 * link不为空   Intra
 * 1.TODO
 */
public class HBCreat {
	/**
	 * 根据flow活动的数量，可能存在多个HB图
	 * @return HBGraph 集合
	 */
	public static ArrayList<Integer[]> sequenceStartAndEndLogicClockSet;
	public static ArrayList<Integer[]> scopeStartAndEndLogicClockSet;
	//两个HB图集合
	public static ArrayList<NodeHB> intraHBGraphs=null;
	public static ArrayList<InterNodeHB> interHBGraphs=null;
	public static Integer InterTIdCount=-1;
	public void startCreatHB()
	{
		if(SaxBPELProcess.nodeLinkList.size()==0)
		{
			intraHBGraphs=creatIntraHBList();
		}
		else
		{
			interHBGraphs=creatInterHBList();
		}
	}
	public ArrayList<InterNodeHB> creatInterHBList()
	{
		ArrayList<InterNodeHB> interHBGraphs=new ArrayList<InterNodeHB>();
		if(SaxBPELProcess.nodeFlowActivityList.size()==0)
		{
			return null;
		}
		for (int j = 0; j < SaxBPELProcess.nodeFlowActivityList.size(); j++) {
			NodeFlowActivity nodeFlowActivity=SaxBPELProcess.nodeFlowActivityList.get(j);
			//对于每一个flow 结构构建一个HB图
			InterNodeHB interHbGraph=creatSingleInterHBGraph(nodeFlowActivity);
			interHBGraphs.add(interHbGraph);
		}
		//到上一层再判断是否为空，来打印输出对应的信息。
		//System.out.println("HBGraphs.size()"+interHBGraphs.size());

		return interHBGraphs;
	}
	public InterNodeHB creatSingleInterHBGraph(NodeFlowActivity nodeFlowActivity)
	{
		String graphySign=BPELKeyWords.INTER_LINK;
		//----------------------订一个标识-------------------------------
		//创建根节点
		//InterNodeHB interRootNode =new InterNodeHB(nodeFlowActivity.getfId(), getTId(InterTIdCount++), BPELKeyWords.FLOW, nodeFlowActivity.getActivityName(), null, null, null, null, null, null);
		//创建终止结点
		//InterNodeHB interEndNode=new InterNodeHB(nodeFlowActivity.getfId(), getTId(InterTIdCount),BPELKeyWords.END_FLOW, nodeFlowActivity.getActivityName(), null, null, null, null, null, null);
		//开始序
		int flowStartGlobalOrder=nodeFlowActivity.getStartLogicClock();
		//结束序
		int flowEndGlobalOrder=nodeFlowActivity.getEndLogicClock();
		//取最小的基本活动
		NodeBaseActivity base=NodeGetMinObject.getMinBaseNode(SaxBPELProcess.nodeBaseActivityList, flowStartGlobalOrder, flowEndGlobalOrder);
		InterNodeHB interRootHB=new InterNodeHB(base.getgId(), getTId(InterTIdCount++), base.getActivityType(), base.getActivityName(), base.getPartnerLink(), base.getInputVariable(), base.getOutputVariable(), base.getCorrelation(), base.getSourceLinkName(), base.getTargetLinkName());
		InterNodeHB interGraph=interRootHB;
		for (int i = 0; i < base.getSourceLinkName().size(); i++) {
			String sourceLinkName=base.getSourceLinkName().get(i);
			InterNodeConnEdge(sourceLinkName,interRootHB);
		}
		return interGraph;
	}
	public void InterNodeConnEdge(String linkValue,InterNodeHB interNodeHB)
	{
		//System.out.println("[interNodeHB:]"+interNodeHB);
		//System.out.println("[linkValue:]"+linkValue);
		
		InterNodeHB targetNode=null;
		InterEdgeHB edge=new InterEdgeHB(interNodeHB, linkValue, null, null);
//		InterNodeEdgeRelation interNodeEdgeRelation=new InterNodeEdgeRelation(edge, BPELKeyWords.SOURCE_LINK);
		//原结点连边
		interNodeHB.getInterEdgeList().add(edge);
		//边连target结点
		for (int i = 0; i < SaxBPELProcess.nodeBaseActivityList.size(); i++) {
			NodeBaseActivity baseNode=SaxBPELProcess.nodeBaseActivityList.get(i);
			//System.out.println(baseNode.getActivityType()+baseNode.getTargetLinkName());
//			System.out.println(linkValue);
//			System.out.println(baseNode.getTargetLinkName().contains(linkValue));
			if(baseNode.getTargetLinkName()!=null&&baseNode.getTargetLinkName().contains(linkValue))
			{
				targetNode=new InterNodeHB(baseNode.getgId(), getTId(InterTIdCount++), baseNode.getActivityType(), baseNode.getActivityName(), baseNode.getPartnerLink(), baseNode.getInputVariable(), baseNode.getOutputVariable(), baseNode.getCorrelation(), baseNode.getSourceLinkName(), baseNode.getTargetLinkName());
				edge.setTargetNode(targetNode);
				break;
			}
		}
		if(targetNode==null)
		{
			for (int i = 0; i < SaxBPELProcess.nodeAssignActivityList.size(); i++) {
				NodeAssignActivity assignNode=SaxBPELProcess.nodeAssignActivityList.get(i);
				if(assignNode.getTargetLinkName()!=null&&assignNode.getTargetLinkName().contains(linkValue))
				{
					targetNode=new InterNodeHB(assignNode.getaId(), getTId(InterTIdCount++), assignNode.getActivityType(), assignNode.getActivityName(), null, assignNode.getInputVariable(), assignNode.getOutputVariable(), assignNode.getCorrelation(), assignNode.getSourceLinkName(), assignNode.getTargetLinkName());
					edge.setTargetNode(targetNode);
					break;
				}
			}
		}
		//System.out.println("[targetNode:]"+targetNode);
		InterProcessInterNode(targetNode);
		
	}
	public void InterProcessInterNode(InterNodeHB interNodeHB)
	{
//		System.out.println(interNodeHB);
//		System.out.println(interNodeHB.getType()+interNodeHB.getSourceLinkName());
//		System.out.println("size:   "+interNodeHB.getSourceLinkName());
		
		if(interNodeHB.getSourceLinkName()!=null&&interNodeHB.getSourceLinkName().size()!=0)
		{
			for (int i = 0; i < interNodeHB.getSourceLinkName().size(); i++) {
				String sourceLinkName=interNodeHB.getSourceLinkName().get(i);
				//System.out.println("[sourceLinkName:]"+sourceLinkName);

				InterNodeConnEdge(sourceLinkName, interNodeHB);
			}
		}
	}
	public ArrayList<NodeHB> creatIntraHBList()
	{
		ArrayList<NodeHB> HBGraphs= new ArrayList<NodeHB>(); 
		//System.out.println("SaxBPELProcess.nodeFlowActivityList"+SaxBPELProcess.nodeFlowActivityList.size());
		//如果最基本的基础活动都没有则返回null
		if(SaxBPELProcess.nodeFlowActivityList.size()==0)
		{
			return null;
		}

		for (int j = 0; j < SaxBPELProcess.nodeFlowActivityList.size(); j++) {
			NodeFlowActivity nodeFlowActivity=SaxBPELProcess.nodeFlowActivityList.get(j);
			//对于每一个flow 结构构建一个HB图
			NodeHB hbGraph=creatSingleHBGraph(nodeFlowActivity);
			HBGraphs.add(hbGraph);
		}
		//到上一层再判断是否为空，来打印输出对应的信息。
		//System.out.println("HBGraphs.size()"+HBGraphs.size());

		return HBGraphs;
	}
	
	//创建单个的hb图
	public NodeHB creatSingleHBGraph(NodeFlowActivity nodeFlowActivity)
	{
		String graphySign="";
		//----------------------订一个标识-------------------------------
		//创建根节点
		NodeHB rootHBGraph = new NodeHB(nodeFlowActivity.getfId(), null, null, BPELKeyWords.FLOW, nodeFlowActivity.getActivityName(), null, null, null, null, -1, null, null);
		//创建终止结点
		NodeHB endHB=new NodeHB(nodeFlowActivity.getfId(), null, null, BPELKeyWords.END_FLOW, nodeFlowActivity.getActivityName(), null, null, null, null, -1, null, null);
		//开始序
		int flowStartGlobalOrder=nodeFlowActivity.getStartLogicClock();
		//结束序
		int flowEndGlobalOrder=nodeFlowActivity.getEndLogicClock();
//		if(SaxBPELProcess.nodeLinkList.size()==0)
//		{//判断linkList是否为空，如果linkList为空则做过程间分析
		NodeSequenceActivity nodeSequence=NodeGetMinObject.getMinSequenceNode(SaxBPELProcess.nodeSequenceActivityList, flowStartGlobalOrder, flowEndGlobalOrder);
		NodeScopeActivity nodeScope=NodeGetMinObject.getMinScopeNode(SaxBPELProcess.nodeScopeActivityList, flowStartGlobalOrder, flowEndGlobalOrder);
		if((nodeSequence==null)&&(nodeScope==null))
		{//所有的活动都是一个单独的线程
			graphySign=BPELKeyWords.INTRA_ONLY_BASE;
			for (int i = 0; i < SaxBPELProcess.nodeBaseActivityList.size(); i++) {
				int lId=0;
				NodeBaseActivity base=SaxBPELProcess.nodeBaseActivityList.get(i);
				if(base.getLogicClockOrder()>flowStartGlobalOrder&&base.getLogicClockOrder()<flowEndGlobalOrder&&!base.getActivityType().equals(BPELKeyWords.COPY))
				{
					NodeHB baseNode=new NodeHB(base.getgId(), getTId(rootHBGraph.getChildrenHBNode().size()), getLId(lId), base.getActivityType(), base.getActivityName(), base.getPartnerLink(), base.getInputVariable(), base.getOutputVariable(), base.getCorrelation(), base.getIsSynchronization(), null, null);	
					rootHBGraph.getChildrenHBNode().add(baseNode);
					baseNode.getChildrenHBNode().add(endHB);
				}
			}
			
		}
		else
		{
			if(nodeSequence==null)
			{
				graphySign=BPELKeyWords.INTRA_SCOPE;
				//scope不为空
				for (int i = 0; i < SaxBPELProcess.nodeScopeActivityList.size(); i++) {
					int lId=0;
					NodeScopeActivity scope=SaxBPELProcess.nodeScopeActivityList.get(i);
					if(scope.getStartLogicClock()>flowStartGlobalOrder&&scope.getEndLogicClock()<flowEndGlobalOrder)
					{
						NodeHB scopeStart=new NodeHB(scope.getsId(), getTId(rootHBGraph.getChildrenHBNode().size()), getLId(-1), BPELKeyWords.SCOPE_START, scope.getActivityName(), null, null, null, null, -1, null, null, null, scope.isIsolated());
						NodeHB scopeEnd=new NodeHB(scope.getsId(), getTId(rootHBGraph.getChildrenHBNode().size()), getLId(-1), BPELKeyWords.SCOPE_END, scope.getActivityName(), null, null, null, null, -1, null, null, null, scope.isIsolated());
						rootHBGraph.getChildrenHBNode().add(scopeStart);
						NodeHB startNextNode=creatBaseActivityGraph(scope.getStartLogicClock(), scope.getEndLogicClock(), getTId(rootHBGraph.getChildrenHBNode().size()), lId++, scopeEnd, nodeFlowActivity.getEndLogicClock()+1,scope.isIsolated());
						scopeStart.getChildrenHBNode().add(startNextNode);
						scopeEnd.getChildrenHBNode().add(endHB);
					}
				}
//				scopeStartAndEndLogicClockSet=getScopeStartAndEndLogicClockSet(flowStartGlobalOrder, flowEndGlobalOrder);
//				for (int i = 0; i < scopeStartAndEndLogicClockSet.size(); i++) {
//					Integer[] temp=scopeStartAndEndLogicClockSet.get(i);
//					int scopeStartClock=temp[0];
//					int scopeEndClock=temp[1];
//					String tId=getTId(rootHBGraph.getChildrenHBNode().size());
//					int lId=0;
//				}
			}
			if(nodeScope==null)
			{	//sequence不为空
				//获取满意的sequence活动范围
				graphySign=BPELKeyWords.INTRA_SEQUENCE;
				sequenceStartAndEndLogicClockSet=getSequenceStartAndEndLogicClockSet(flowStartGlobalOrder,flowEndGlobalOrder);
				for (int i = 0; i < sequenceStartAndEndLogicClockSet.size(); i++) {
					Integer[] temp=sequenceStartAndEndLogicClockSet.get(i);
					int startLogicClock=temp[0];
					int endLogicClock=temp[1];
					String tId=getTId(rootHBGraph.getChildrenHBNode().size());
					int lId = 0;
					//找不到在start和end之中的baseactivity时，直接连flow
					//考虑 NodeHB是否需要startLogicClock和endLogicClock
					//NodeHB sequenceNodeHB=new NodeHB(nodeSequenceActivity.getsId(), getTId(rootHBGraph.getChildrenHBNode().size()), null, nodeSequenceActivity.getActivityType(), nodeSequenceActivity.getActivityName(), null, null, null, null, -1, null, null);
					NodeHB childNodeHB=creatBaseActivityGraph(startLogicClock,endLogicClock,tId,lId,endHB,nodeFlowActivity.getEndLogicClock()+1,false);
					rootHBGraph.getChildrenHBNode().add(childNodeHB);
				}
			}
			graphySign=BPELKeyWords.INTRA_SEQUENCE_SCOPE;
			//scope和sequecne 都不为空
		
		}
			
//			if(SaxBPELProcess.nodeScopeActivityList.size()==0
//					&&SaxBPELProcess.nodeSequenceActivityList.size()==0)
//				//判断组合活动是否都为空
//			{
//				//说明每一个基本活动都是线程
//				//TODO
//			}else
//			{
//				System.out.println("rootHBGraph"+rootHBGraph.toString());
//
////				if(SaxBPELProcess.nodeSwitchActivityList.size()!=0)
////				{
////					NodeSwitchActivity nodeSwitchActivity=SaxBPELProcess.nodeSwitchActivityList.get(0);
////					NodeHB switchNode=new NodeHB(gId, tId, lId, type, name, partnerLink, inputVariable, outputVariable, lockName, isSynchronization, sourceLinkName, targetLinkName)
////				}
//				//先考虑sequence
//				if(SaxBPELProcess.nodeSequenceActivityList.size()!=0)
//				{
//
//					for (int i = 0; i < SaxBPELProcess.nodeSequenceActivityList.size(); i++) {
//						
//						NodeSequenceActivity nodeSequenceActivity=SaxBPELProcess.nodeSequenceActivityList.get(i);
//						int startLogicClock=nodeSequenceActivity.getStartLogicClock();
//						int endLogicClock=nodeSequenceActivity.getEndLogicClock();
//						String tId=getTId(rootHBGraph.getChildrenHBNode().size());
//						int lId = 0;
//						//找不到在start和end之中的baseactivity时，直接连flow
//						//考虑 NodeHB是否需要startLogicClock和endLogicClock
//						//NodeHB sequenceNodeHB=new NodeHB(nodeSequenceActivity.getsId(), getTId(rootHBGraph.getChildrenHBNode().size()), null, nodeSequenceActivity.getActivityType(), nodeSequenceActivity.getActivityName(), null, null, null, null, -1, null, null);
//						NodeHB childNodeHB=creatBaseActivityGraph(startLogicClock,endLogicClock,tId,lId);
//						rootHBGraph.getChildrenHBNode().add(childNodeHB);
//					}
//				}else{
//					//如果为空考虑scope
//					//TODO
//				}
//			}
			
//		}
			//else{
//			//用linkName来链接考虑到source和target的情况 做过程内分析。
//			//TODO
//			graphySign=BPELKeyWords.INTER_LINK;
//			
//		}
		
		if(rootHBGraph.getChildrenHBNode().size()==0)
		{
			return null;
		}
		
		return rootHBGraph;
	}
	private NodeHB creatBaseActivityGraph(int StartLogicClock,int endLogicClock,String tId,int lId,NodeHB endNode,int maxClock,boolean scopeIsolate)
	{//给定一个组合的开始和结束的逻辑时钟的范围，在这之间找基础活动加入进去，一旦返回值为null则指到最后
		//sequence里面包含receive,invoke,assign,switch,有了switch才会考虑case
		int startClock;
		int endOrder;
		NodeHB hb;
		NodeBaseActivity nodeBaseActivity=NodeGetMinObject.getMinBaseNode(SaxBPELProcess.nodeBaseActivityList, StartLogicClock, endLogicClock);
		//NodeAssignActivity nodeAssignActivity=NodeGetMinObject.getMinAssignNode(SaxBPELProcess.nodeAssignActivityList, StartLogicClock, endLogicClock);
		NodeSwitchActivity nodeSwitchActivity=NodeGetMinObject.getMinSwitchNode(SaxBPELProcess.nodeSwitchActivityList, StartLogicClock, endLogicClock);
		NodeHB childNodeHB=null;

		String minClockActivityType=getMinClockActivityType(nodeBaseActivity,nodeSwitchActivity,maxClock);
		//System.out.println("bbbb"+minClockActivityType);
		switch (minClockActivityType) {
		case (BPELKeyWords.NO_ACTIVITY):
			childNodeHB= endNode;
			break;
		case (BPELKeyWords.BASE):
			//SaxBPELProcess.nodeBaseActivityList.remove(baseIndex);
			childNodeHB=new NodeHB(nodeBaseActivity.getgId(), tId, getLId(lId), nodeBaseActivity.getActivityType(), nodeBaseActivity.getActivityName(), nodeBaseActivity.getPartnerLink(), nodeBaseActivity.getInputVariable(), nodeBaseActivity.getOutputVariable(), nodeBaseActivity.getCorrelation(), nodeBaseActivity.getIsSynchronization(), nodeBaseActivity.getSourceLinkName(), nodeBaseActivity.getTargetLinkName());
			childNodeHB.setIsolataScope(scopeIsolate);
			startClock = nodeBaseActivity.getLogicClockOrder();
			hb = creatBaseActivityGraph(startClock, endLogicClock, tId, lId++,endNode,maxClock,scopeIsolate);
//			if(hb==null)
//			{
//				//endNode = new NodeHB(null, null, null, BPELKeyWords.FLOW, null, null, null, null, null, -1, null, null);
//				childNodeHB.getChildrenHBNode().add(endNode);
//			}
//			else
//			{
				childNodeHB.getChildrenHBNode().add(hb);
//			}
			break;
//		case (BPELKeyWords.ASSIGN):
//			//TODOassign活动锁没有处理
//			//SaxBPELProcess.nodeAssignActivityList.remove(assignIndex);
//			childNodeHB=new NodeHB(nodeAssignActivity.getaId(), tId, getLId(lId), nodeAssignActivity.getActivityType(), nodeAssignActivity.getActivityName(), null, nodeAssignActivity.getInputVariable(), nodeAssignActivity.getOutputVariable(), null, IsSynchronization.SYNCHRONIZATION, nodeAssignActivity.getSourceLinkName(), nodeAssignActivity.getTargetLinkName());
//			childNodeHB.setIsolataScope(scopeIsolate);	
//			startClock = nodeAssignActivity.getStartLogicClock();
//			hb = creatBaseActivityGraph(startClock, endLogicClock, tId, lId++,endNode,maxClock,scopeIsolate);
////			if(hb==null)
////			{
////				//endNode = new NodeHB(null, null, null, BPELKeyWords.FLOW, null, null, null, null, null, -1, null, null);
////				childNodeHB.getChildrenHBNode().add(endNode);
////			}
////			else
////			{
//				childNodeHB.getChildrenHBNode().add(hb);
////			}
//			break;
			//TODO以后处理
		case BPELKeyWords.SWITCH:
			childNodeHB=new NodeHB(nodeSwitchActivity.getsId(), tId, getLId(lId), nodeSwitchActivity.getActivityType(), nodeSwitchActivity.getActivityName(), null, null, null, null, -1, null, null);
			childNodeHB.setIsolataScope(scopeIsolate);	
			NodeHB switchEnd=new NodeHB(nodeSwitchActivity.getsId(), tId, getLId(lId++), BPELKeyWords.END_SWITH, nodeSwitchActivity.getActivityName(), null, null, null, null, -1, null, null);
			switchEnd.setIsolataScope(scopeIsolate);	

			startClock = nodeSwitchActivity.getStartLogicClock();
			endOrder=nodeSwitchActivity.getEndLogicClock();
			for (int i = 0; i < SaxBPELProcess.nodeCaseActivityList.size(); i++) {
				NodeCaseActivity nodeCaseActivity=SaxBPELProcess.nodeCaseActivityList.get(i);
				if(nodeCaseActivity.getStartLogicClock()>startClock&&nodeCaseActivity.getEndLogicClock()<endOrder)
				{
					NodeHB caseNode=new NodeHB(nodeCaseActivity.getcId(), tId, getLId(lId++), nodeCaseActivity.getActivityType(), nodeCaseActivity.getActivityName(), null, null, null, null, -1, null, null, nodeCaseActivity.getCondition());
					caseNode.setIsolataScope(scopeIsolate);	

					childNodeHB.getChildrenHBNode().add(caseNode);
					NodeHB caseNextNode=creatBaseActivityGraph(nodeCaseActivity.getStartLogicClock(), endOrder, tId, lId++, switchEnd,maxClock,scopeIsolate);
					caseNode.getChildrenHBNode().add(caseNextNode);
				}
			}
			hb = creatBaseActivityGraph(endOrder, endLogicClock, tId, lId++,endNode,maxClock,scopeIsolate);
			switchEnd.getChildrenHBNode().add(hb);
			break;
//		case BPELKeyWords.SCOPE:
//			
//			break;
		}
		
		return childNodeHB;
	}
	private String getMinClockActivityType(NodeBaseActivity base,NodeSwitchActivity switchAct,int maxClock)
	{
		int baseClock,assignClock,switchClock;
		if(base==null)
		{
			baseClock=maxClock;
		}else{
			baseClock=base.getLogicClockOrder();
		}
		if(switchAct==null)
		{
			switchClock=maxClock;
		}else{
			switchClock=switchAct.getStartLogicClock();
		}
		if(baseClock==maxClock&&switchClock==maxClock)
		{
			return BPELKeyWords.NO_ACTIVITY;
		}
		
		if(baseClock<switchClock)
		{
			return BPELKeyWords.BASE;
		}
//		if(assignClock<baseClock&&assignClock<switchClock)
//		{
//			return BPELKeyWords.ASSIGN;
//		}
		if(switchClock<baseClock)
		{
			return BPELKeyWords.SWITCH;
		}
		return BPELKeyWords.SCOPE;
	}
	private String getLId(int lidCount)
	{
		return "l_"+Integer.toString(lidCount);
	}
	private String getTId(int childrenNodeSize)
	{
		return "t_"+Integer.toString(childrenNodeSize);
	}
	//获取所有sequence的开始和结束序列
	public ArrayList<Integer[]> getSequenceStartAndEndLogicClockSet(int start,int end)
	{
		ArrayList<Integer[]> result=new ArrayList<Integer[]>();
		for (int i = 0; i < SaxBPELProcess.nodeSequenceActivityList.size(); i++) {
			NodeSequenceActivity sequence=SaxBPELProcess.nodeSequenceActivityList.get(i);
			if(sequence.getStartLogicClock()>start&&sequence.getEndLogicClock()<end)
			{
				Integer[] temp=new Integer[]{sequence.getStartLogicClock(),sequence.getEndLogicClock()};
				result.add(temp);
			}
		}
		return result;
	}
	//获取所有scope的开始和结束序列
	public ArrayList<Integer[]> getScopeStartAndEndLogicClockSet(int start,int end)
	{
		ArrayList<Integer[]> result=new ArrayList<Integer[]>();
		for (int i = 0; i < SaxBPELProcess.nodeScopeActivityList.size(); i++) {
			NodeScopeActivity scope=SaxBPELProcess.nodeScopeActivityList.get(i);
			if(scope.getStartLogicClock()>start&&scope.getEndLogicClock()<end)
			{
				Integer[] temp=new Integer[]{scope.getStartLogicClock(),scope.getEndLogicClock()};
				result.add(temp);
			}
		}
		return result;
	}
}
