package com.bpel.hbconstruct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.bpel.preprocess.BPELKeyWords;
import com.bpel.preprocess.NodeAssignActivity;
import com.bpel.preprocess.NodeBaseActivity;
import com.bpel.preprocess.NodeCaseActivity;
import com.bpel.preprocess.SaxBPELProcess;

public class ProcessSharedVariable {

	//����������ϡ�
	//NodeShareVariable�ǹ��������� ���а������� ��Ϊ�ڲ��ĺ��ⲿ��
	public static ArrayList<NodeShareVariable> sharedVariableList=new ArrayList<NodeShareVariable>();
	//�����Ķ�д����
	public static HashMap<String,ReadAndWriteList> varReadAndWriteList=new HashMap<String, ReadAndWriteList>();

	//��ȡ�����Ķ�д����
	public void getSharedVarListAndVarReadAndWriteList()
	{
		getSharedVariableList();
		getVarReadAndWriteList();
	}
	private void getVarReadAndWriteList()
	{
		for (int i = 0; i < SaxBPELProcess.nodeBaseActivityList.size(); i++) {
			NodeBaseActivity base=SaxBPELProcess.nodeBaseActivityList.get(i);
			if(SaxBPELProcess.nodeLinkList.size()!=0)
			{
				if(base.getActivityType().equals(BPELKeyWords.COPY))
				{
					continue;
				}
			}
			if(base.getInputVariable()!=null)
			{
				String var=getInnerShareVar(base.getInputVariable());
				if(var!=null)
				{
					if(varReadAndWriteList.get(var)==null)
					{
						ReadAndWriteList readAndWriteList=new ReadAndWriteList();
						readAndWriteList.getReadList().add(new NodeReadAndWrite(base.getgId(), base.getActivityType(), base.getActivityName()));
						varReadAndWriteList.put(var, readAndWriteList);
					}
					else
					{
						varReadAndWriteList.get(var).getReadList().add(new NodeReadAndWrite(base.getgId(), base.getActivityType(), base.getActivityName()));
					}
				}

			}
			if(base.getOutputVariable()!=null)
			{
				String var=getInnerShareVar(base.getOutputVariable());
				if(varReadAndWriteList.get(var)==null)
				{
					ReadAndWriteList readAndWriteList=new ReadAndWriteList();
					readAndWriteList.getWriteList().add(new NodeReadAndWrite(base.getgId(), base.getActivityType(), base.getActivityName()));
					varReadAndWriteList.put(var, readAndWriteList);
				}
				else
				{
					varReadAndWriteList.get(var).getWriteList().add(new NodeReadAndWrite(base.getgId(), base.getActivityType(), base.getActivityName()));
				}
			}

		}
		for (int i = 0; i < SaxBPELProcess.nodeCaseActivityList.size(); i++) {
			NodeCaseActivity caseActivity=SaxBPELProcess.nodeCaseActivityList.get(i);
			String condition=caseActivity.getCondition();
			String var=getInnerShareVar(condition);
			if(var!=null)
			{
				//TODO---------------------------**************************-------------------------------
				if(varReadAndWriteList.get(var)==null)
				{
					ReadAndWriteList readAndWriteList=new ReadAndWriteList();
					readAndWriteList.getReadList().add(new NodeReadAndWrite(caseActivity.getcId(), caseActivity.getActivityType(), caseActivity.getActivityName()));
					varReadAndWriteList.put(var, readAndWriteList);
				}
				else
				{
					varReadAndWriteList.get(var).getReadList().add(new NodeReadAndWrite(caseActivity.getcId(), caseActivity.getActivityType(), caseActivity.getActivityName()));
				}
			}
		}
		if(SaxBPELProcess.nodeLinkList.size()!=0)
		{
			for (int i = 0; i < SaxBPELProcess.nodeAssignActivityList.size(); i++) {
				NodeAssignActivity assign=SaxBPELProcess.nodeAssignActivityList.get(i);
				if(assign.getInputVariable()!=null)
				{
					if(varReadAndWriteList.get(assign.getInputVariable())==null)
					{
						ReadAndWriteList readAndWriteList=new ReadAndWriteList();
						readAndWriteList.getReadList().add(new NodeReadAndWrite(assign.getaId(), assign.getActivityType(), assign.getActivityName()));
						varReadAndWriteList.put(assign.getInputVariable(), readAndWriteList);
					}
					else
					{
						varReadAndWriteList.get(assign.getInputVariable()).getReadList().add(new NodeReadAndWrite(assign.getaId(), assign.getActivityType(), assign.getActivityName()));
					}
				}
				if(assign.getOutputVariable()!=null)
				{
					if(varReadAndWriteList.get(assign.getOutputVariable())==null)
					{
						ReadAndWriteList readAndWriteList=new ReadAndWriteList();
						readAndWriteList.getWriteList().add(new NodeReadAndWrite(assign.getaId(), assign.getActivityType(), assign.getActivityName()));
						varReadAndWriteList.put(assign.getOutputVariable(), readAndWriteList);
					}
					else
					{
						varReadAndWriteList.get(assign.getOutputVariable()).getWriteList().add(new NodeReadAndWrite(assign.getaId(), assign.getActivityType(), assign.getActivityName()));
					}
				}
			}
		}

	}
	//��ȡ���������List
	private void getSharedVariableList()
	{
		//TODO------------------------û����catch���������-------------------------
		HashSet<String> variableSet = getVariableSet();
		Object[] objVar=variableSet.toArray();
		for (int i = 0; i < objVar.length; i++) {
			String var=objVar[i].toString();
			NodeShareVariable shareVar=new NodeShareVariable(var, null);
			if(SaxBPELProcess.varSets.contains(var))
			{
				shareVar.setType(BPELKeyWords.IN_VARIABLE);
			}
			else
			{
				shareVar.setType(BPELKeyWords.OUT_VARIABLE);
			}
			sharedVariableList.add(shareVar);
		}
	}
	//����HashSetʹ��������ظ�
	private HashSet<String> getVariableSet()
	{
		HashSet<String> variableSet=new HashSet<String>();
		for (int i = 0; i < SaxBPELProcess.nodeBaseActivityList.size(); i++) {
			NodeBaseActivity base=SaxBPELProcess.nodeBaseActivityList.get(i);
			if(base.getInputVariable()!=null)
			{
				String var=getInnerShareVar(base.getInputVariable());
				//System.out.println("aaaaa"+base.getActivityType()+","+var);
				if(var!=null)
				{
					variableSet.add(var);
				}
			}
			if(base.getOutputVariable()!=null)
			{
				String var=getInnerShareVar(base.getOutputVariable());
				//System.out.println("aaaaa"+base.getActivityType()+","+var);

				if(var!=null)
				{
					variableSet.add(var);
				}
			}
		}
		//����case����Ķ�����
		//û����� ���ܻ�������
		for (int i = 0; i < SaxBPELProcess.nodeCaseActivityList.size(); i++) {
			String condition=SaxBPELProcess.nodeCaseActivityList.get(i).getCondition();
			String var=getInnerShareVar(condition);
			if(var!=null)
			{
				variableSet.add(var);
			}
		}
		return variableSet;
	}
	private String getInnerShareVar(String condition) {
		// TODO Auto-generated method stub
		String result="";
		String temp="";
		for (int i = 0; i < SaxBPELProcess.varSets.size(); i++) {
			temp=SaxBPELProcess.varSets.get(i);
			if(condition.contains(temp))
			{
				if(temp.length()>result.length())
				{
					result=temp;
				}
			}
		}
		//��Щcopy��ȡ����partLink����Ķ���
		if(result=="")
		{
			for (int i = 0; i < SaxBPELProcess.nodePartLinkList.size(); i++) {
				temp=SaxBPELProcess.nodePartLinkList.get(i).getName();
				if(condition.contains(temp))
				{
					if(temp.length()>result.length())
					{
						result=temp;
					}
				}
			}
		}
		return result;
	}

}
