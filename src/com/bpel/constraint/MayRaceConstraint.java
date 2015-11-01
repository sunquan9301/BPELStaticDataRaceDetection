package com.bpel.constraint;
import java.util.ArrayList;
import java.util.HashMap;
import com.bpel.hbconstruct.HBCreat;
import com.bpel.hbconstruct.InterEdgeHB;
import com.bpel.hbconstruct.InterNodeHB;
import com.bpel.hbconstruct.NodeHB;
import com.bpel.hbconstruct.NodeReadAndWrite;
import com.bpel.hbconstruct.ProcessSharedVariable;
public class MayRaceConstraint {
	public static HashMap<String,String> GIDToTID=new HashMap<String, String>();
	public static HashMap<String,ArrayList<String>> mayRaceActivityOrder=new HashMap<String, ArrayList<String>>();
	//public static HashMap<String, String> mayRaceActivityOrder=new HashMap<String, String>();
	public void startProcess()
	{
		getGIDMapToGID();
		processMayRace();
	}
	//获取GID映射到TID
	private void getGIDMapToGID() {
		// TODO Auto-generated method stub
		if(HBCreat.intraHBGraphs!=null)
		{
			for (int i = 0; i < HBCreat.intraHBGraphs.size(); i++) {
				NodeHB hbGraph = HBCreat.intraHBGraphs.get(i);
				processIntraHBGraph(hbGraph);
			}
		}
		if(HBCreat.interHBGraphs!=null)
		{
			for (int i = 0; i < HBCreat.interHBGraphs.size(); i++) {
				InterNodeHB hbGraph = HBCreat.interHBGraphs.get(i);
				processInterHBGraph(hbGraph);
			}
		}
	}
	public void processInterHBGraph(InterNodeHB interHBGraph)
	{
		GIDToTID.put(interHBGraph.getgId(), interHBGraph.gettId());
		for (int i = 0; i < interHBGraph.getInterEdgeList().size(); i++) {
			InterEdgeHB interEdge=interHBGraph.getInterEdgeList().get(i);
			processInterHBGraph(interEdge.getTargetNode());
		}
	}
	private void processIntraHBGraph(NodeHB hbGraph)
	{
		for (int i = 0; i < hbGraph.getChildrenHBNode().size(); i++) {
			NodeHB child=hbGraph.getChildrenHBNode().get(i);
			GIDToTID.put(child.getgId(), child.gettId());
			processIntraHBGraph(child);
		}		
	}
	//处理可能存在race的活动
	private void processMayRace()
	{
		Object[] keys=ProcessSharedVariable.varReadAndWriteList.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			//System.out.println(keys[i].toString());
			ArrayList<NodeReadAndWrite> readList = ProcessSharedVariable.varReadAndWriteList.get(keys[i].toString()).getReadList();
			ArrayList<NodeReadAndWrite> writeList = ProcessSharedVariable.varReadAndWriteList.get(keys[i].toString()).getWriteList();
			if(readList!=null&&writeList!=null)
			{
				//System.out.println("i:"+i);
				processMayRaceReadToWrite(readList,writeList);
			}
			if(writeList!=null)
			{
				//System.out.println("i:"+i);
				processMayRaceWriteToWrite(writeList);
			}
		}
	}
	private void processMayRaceWriteToWrite(
			ArrayList<NodeReadAndWrite> writeList) {
		// TODO Auto-generated method stub
		for (int i = 0; i < writeList.size(); i++) {
			NodeReadAndWrite first=writeList.get(i);
			for (int j = 0; j < writeList.size(); j++) {
				NodeReadAndWrite second=writeList.get(j);
				if(first!=second)
				{	
					//System.out.println("first:"+first.getActivityGID()+"TID"+GIDToTID.get(first.getActivityGID())+"second:"+second.getActivityGID()+"TID"+GIDToTID.get(second.getActivityGID()));
					if(GIDToTID.get(first.getActivityGID())!=GIDToTID.get(second.getActivityGID()))
					{
						if(OrderConstraint.actHBOrder.get(first.getActivityGID())!=null)
						{
							if(OrderConstraint.actHBOrder.get(first.getActivityGID()).contains(second.getActivityGID()))
							{
								continue;
							}
						}
						if(OrderConstraint.actHBOrder.get(second.getActivityGID())!=null)
						{
							if(OrderConstraint.actHBOrder.get(second.getActivityGID()).contains(first.getActivityGID()))
							{
								continue;
							}
						}
						//System.out.println("AAAAAA first:"+first.getActivityGID()+"TID"+GIDToTID.get(first.getActivityGID())+"second:"+second.getActivityGID()+"TID"+GIDToTID.get(second.getActivityGID()));
						if(mayRaceActivityOrder.get(first.getActivityGID())==null)
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(second.getActivityGID());
							mayRaceActivityOrder.put(first.getActivityGID(), temp);
						}else{
							mayRaceActivityOrder.get(first.getActivityGID()).add(second.getActivityGID());
						}
					}
				}
			}
		}
	}
	private void processMayRaceReadToWrite(
			ArrayList<NodeReadAndWrite> readList,
			ArrayList<NodeReadAndWrite> writeList) {
		// TODO Auto-generated method stub
		for (int i = 0; i < readList.size(); i++) {
			NodeReadAndWrite read=readList.get(i);
			for (int j = 0; j < writeList.size(); j++) {
				NodeReadAndWrite write=writeList.get(j);
				if(read.getActivityGID()!=write.getActivityGID())
				{
					//System.out.println("read:"+read.getActivityGID()+"tid:"+GIDToTID.get(read.getActivityGID())+"write:"+write.getActivityGID()+"TID:"+GIDToTID.get(write.getActivityGID()));

					if(GIDToTID.get(read.getActivityGID())!=GIDToTID.get(write.getActivityGID()))
					{
						if(OrderConstraint.actHBOrder.get(read.getActivityGID())!=null)
						{
							if(OrderConstraint.actHBOrder.get(read.getActivityGID()).contains(write.getActivityGID()))
							{
								continue;
							}
						}
						if(OrderConstraint.actHBOrder.get(write.getActivityGID())!=null)
						{
							if(OrderConstraint.actHBOrder.get(write.getActivityGID()).contains(read.getActivityGID()))
							{
								continue;
							}
						}
						//System.out.println("AAAAAAread:"+read.getActivityGID()+"tid:"+GIDToTID.get(read.getActivityGID())+"write:"+write.getActivityGID()+"TID:"+GIDToTID.get(write.getActivityGID()));

						if(mayRaceActivityOrder.get(read.getActivityGID())==null)
						{
							ArrayList<String> temp=new ArrayList<String>();
							temp.add(write.getActivityGID());
							mayRaceActivityOrder.put(read.getActivityGID(), temp);
						}else{
							mayRaceActivityOrder.get(read.getActivityGID()).add(write.getActivityGID());
						}
					}
				}
			}
		}
	}
}
