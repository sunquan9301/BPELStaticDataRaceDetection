package com.bpel.preprocess;

import java.util.ArrayList;
import java.util.HashMap;

import com.bpel.hbconstruct.InterEdgeHB;
import com.bpel.hbconstruct.InterNodeHB;
import com.bpel.hbconstruct.NodeHB;
import com.bpel.hbconstruct.NodeReadAndWrite;
import com.bpel.hbconstruct.NodeShareVariable;
import com.bpel.hbconstruct.ReadAndWriteList;

public class PrintInformation {
	//打印过程内
	public static void printHBGraph(NodeHB hbGraph)
	{
		System.out.println(hbGraph.toString());
		System.out.println("------------------");
		//System.out.println("aaaaa"+hbGraph.getChildrenHBNode().size());
		for (int i = 0; i < hbGraph.getChildrenHBNode().size(); i++) {
			NodeHB child=hbGraph.getChildrenHBNode().get(i);
			System.out.println(child);
			printHBGraph(child);
		}
		
	}
	//打印过程间
	public static void printHBInterGraph(InterNodeHB interHBGraph)
	{
		System.out.println("---node:   ");
		System.out.println(interHBGraph);
		
		for (int i = 0; i < interHBGraph.getInterEdgeList().size(); i++) {
			InterEdgeHB interEdge=interHBGraph.getInterEdgeList().get(i);
			System.out.println("---edge:   ");
			System.out.println(interEdge);
			printInterEdge(interEdge);
		}
	}
	//打印过程间Link边信息
	public static void printInterEdge(InterEdgeHB interEdge)
	{
		printHBInterGraph(interEdge.getTargetNode());
	}
	//打印共享变量信息
	public static void printShareVariableInformation(ArrayList<NodeShareVariable> shareVariable)
	{
		System.out.println("***********ShareVarInfo**************");
		for (int i = 0; i < shareVariable.size(); i++) {
			System.out.println(shareVariable.get(i));
		}
	}
	//打印全部共享变量和其读写List信息
	public static void printShareVariableReadAndWriteList(HashMap<String, ReadAndWriteList> varReadAndWriteList) {
		// TODO Auto-generated method stub
		System.out.println("************varReadAndWrite**************");
		Object[] keys=varReadAndWriteList.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			System.out.println("-------var"+i+"-----------");
			System.out.println("[variable: ]"+keys[i].toString());
			printReadAndWriteList(varReadAndWriteList.get(keys[i].toString()));
		}
	}
	//打印单个变量读写list信息
	private static void printReadAndWriteList(ReadAndWriteList readAndWriteList) {
		// TODO Auto-generated method stub
		printReadListInformation(readAndWriteList.getReadList());
		printWriteListInformation(readAndWriteList.getWriteList());
	}
	//打印读List信息
	private static void printReadListInformation(
			ArrayList<NodeReadAndWrite> readList) {
		// TODO Auto-generated method stub
		System.out.println("-readList-");
		for (int i = 0; i < readList.size(); i++) {
			System.out.println(readList.get(i));
		}
	}
	//打印写list信息
	private static void printWriteListInformation(
			ArrayList<NodeReadAndWrite> writeList) {
		// TODO Auto-generated method stub
		System.out.println("-writeList-");
		for (int i = 0; i < writeList.size(); i++) {
			System.out.println(writeList.get(i));
		}
	}
	public static void printActGIDZ3Order(HashMap<String, String> actGIDMapZ3Order) {
		// TODO Auto-generated method stub
		System.out.println("-----------actGIDZ3Order-------------");
		Object[] keys=actGIDMapZ3Order.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			System.out.println(keys[i].toString()+"   :   "+actGIDMapZ3Order.get(keys[i].toString()));
		}
	}
	public static void printActHBOrder(HashMap<String, ArrayList<String>> actHBOrder) {
		// TODO Auto-generated method stub
		System.out.println("-----------actHBOrder-------------");
		Object[] keys=actHBOrder.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			ArrayList<String> temp=actHBOrder.get(keys[i].toString());
			for (int j = 0; j < temp.size(); j++) {
				System.out.println(keys[i].toString()+"   <   "+temp.get(j));

			}
		}
	}
	public static void printMayRace(HashMap<String, ArrayList<String>> mayRaceActivityOrder) {
		// TODO Auto-generated method stub
		System.out.println("-----------mayRaceActivityOrder-------------");
		Object[] keys=mayRaceActivityOrder.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			ArrayList<String> temp=mayRaceActivityOrder.get(keys[i].toString());
			for (int j = 0; j < temp.size(); j++) {
				System.out.println(keys[i].toString()+"   =   "+temp.get(j));
			}
		}
	}
}
