package com.bpel.constraint;

import java.util.HashMap;

import com.bpel.preprocess.BPELFiles;
import com.bpel.preprocess.BPELKeyWords;
import com.bpel.preprocess.SaxBPELProcess;

public class BasicActDefine {
	public static HashMap<String,String> ActGIDMapZ3Order=new HashMap<String, String>(); 
	public void DefineBasicActivityInfo()
	{
		processBaseActivity();
		processCaseActivity();
		if(SaxBPELProcess.nodeLinkList.size()!=0)
		{
			processAssignActivity();
		}
	}
	private void processAssignActivity() {
		// TODO Auto-generated method stub
		for (int i = 0; i < SaxBPELProcess.nodeAssignActivityList.size(); i++) {
			ActGIDMapZ3Order.put(SaxBPELProcess.nodeAssignActivityList.get(i).getaId(), "O"+SaxBPELProcess.nodeAssignActivityList.get(i).getaId());
		}
	}
	private  void processCaseActivity() {
		// TODO Auto-generated method stub
		for (int i = 0; i < SaxBPELProcess.nodeCaseActivityList.size(); i++) {
			ActGIDMapZ3Order.put(SaxBPELProcess.nodeCaseActivityList.get(i).getcId(), "O"+SaxBPELProcess.nodeCaseActivityList.get(i).getcId());
		}
	}
	private  void processBaseActivity() {
		// TODO Auto-generated method stub
		for (int i = 0; i < SaxBPELProcess.nodeBaseActivityList.size(); i++) {
			if(SaxBPELProcess.nodeLinkList.size()!=0)
			{
				if(SaxBPELProcess.nodeBaseActivityList.get(i).getActivityType().equals(BPELKeyWords.COPY))
				{
					continue;
				}
			}
			ActGIDMapZ3Order.put(SaxBPELProcess.nodeBaseActivityList.get(i).getgId(), "O"+SaxBPELProcess.nodeBaseActivityList.get(i).getgId());
		}
	}
}
