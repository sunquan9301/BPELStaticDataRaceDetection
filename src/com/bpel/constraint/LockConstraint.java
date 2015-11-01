package com.bpel.constraint;

import java.util.ArrayList;
import java.util.HashMap;

import com.bpel.hbconstruct.HBCreat;
import com.bpel.hbconstruct.NodeHB;
import com.bpel.preprocess.NodeBaseActivity;
import com.bpel.preprocess.SaxBPELProcess;
public class LockConstraint {
	public static ArrayList<String> isolateScope=new ArrayList<String>();
	public static HashMap<String, ArrayList<String>> correlationSet=new HashMap<String, ArrayList<String>>();
	public void processLock()
	{
		processIsolateLock();
		processCorrelateSet();
	}
	private void processCorrelateSet() {
		// TODO Auto-generated method stub
		for (int i = 0; i < SaxBPELProcess.nodeBaseActivityList.size(); i++) {
			NodeBaseActivity base=SaxBPELProcess.nodeBaseActivityList.get(i);
			if(base.getCorrelation()!=null)
			{
				for (int j = 0; j < base.getCorrelation().size(); j++) {
					String lock=base.getCorrelation().get(j);
					if(correlationSet.get(lock)==null)
					{
						ArrayList<String> actGID=new ArrayList<String>();
						actGID.add(base.getgId());
						correlationSet.put(lock, actGID);
					}else{
						correlationSet.get(lock).add(base.getgId());
					}
				}
			}
		}
		
	}
	private void processIsolateLock()
	{
		if(HBCreat.intraHBGraphs!=null)
		{
			for (int i = 0; i < HBCreat.intraHBGraphs.size(); i++) {
				NodeHB hbGraph = HBCreat.intraHBGraphs.get(i);
				processLockHBGraph(hbGraph);
			}
		}
	}
	private void processLockHBGraph(NodeHB hbGraph)
	{
		for (int i = 0; i < hbGraph.getChildrenHBNode().size(); i++) {
			NodeHB child=hbGraph.getChildrenHBNode().get(i);
			if(child.isIsolataScope())
			{
				isolateScope.add(child.getgId());
			}
			processLockHBGraph(child);
		}		
	}
}
