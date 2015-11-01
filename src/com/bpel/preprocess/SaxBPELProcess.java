package com.bpel.preprocess;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
public class SaxBPELProcess {
	private String bpelFilePath="";
	public SaxBPELProcess(String bpelFilePath) {
		this.bpelFilePath=bpelFilePath;
	}
	public static ArrayList<String> varSets = null;
	public static ArrayList<NodeLink> nodeLinkList;
	public static ArrayList<NodePartLink> nodePartLinkList;
	public static ArrayList<NodeBaseActivity> nodeBaseActivityList;
	public static ArrayList<NodeFlowActivity> nodeFlowActivityList;
	public static ArrayList<NodeScopeActivity> nodeScopeActivityList;
	public static ArrayList<NodeSequenceActivity> nodeSequenceActivityList;
	public static ArrayList<NodeAssignActivity> nodeAssignActivityList;
	public static ArrayList<NodeSwitchActivity> nodeSwitchActivityList;
	public static ArrayList<NodeCaseActivity> nodeCaseActivityList;
	/**
	 * 开始解析bpel文件
	 */
	public void startParseBPEL()
	{
		parseXMLFile();
	}
	private void parseXMLFile()
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser=null;
		try {
			saxParser = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is = SaxBPELProcess.class.getClassLoader().getResourceAsStream(bpelFilePath);

		SaxBPELFiles handle = new SaxBPELFiles();
		try {
			saxParser.parse(is, handle);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		varSets = handle.getVariable();
		//System.out.println(varSets.size());
		nodeLinkList = handle.getNodeLinkList();
		nodePartLinkList = handle.getNodePartLinkList();
		nodeBaseActivityList = handle.getNodeBaseActivitieList();
		nodeFlowActivityList = handle.getNodeFlowActivityList();
		nodeScopeActivityList = handle.getNodeScopeActivityList();
		nodeSequenceActivityList = handle.getNodeSequenceActivityList();
		nodeAssignActivityList = handle.getNodeAssignActivityList();
		nodeSwitchActivityList = handle.getNodeSwitchActivityList();
		nodeCaseActivityList = handle.getNodeCaseActivityList();
	}
	/**
	 * 处理copy活动合并到assign中。
	 */
	public void processCopyToAssignActivitys()
	{
		//System.out.println("size:"+nodeBaseActivityList.size());

		for (int i = 0; i < nodeAssignActivityList.size(); i++) {
			NodeAssignActivity nodeAssignActivity=nodeAssignActivityList.get(i);
			processCopyToAssignActivity(nodeAssignActivity);
		}
		//把copy活动从baseActivityList中移除
		//System.out.println("size:"+nodeBaseActivityList.size());
//		for (int i = 0; i < nodeBaseActivityList.size(); i++) {
//			
//			//System.out.println(i+":"+nodeBaseActivityList.get(i).getgId()+":    "+nodeBaseActivityList.get(i).getActivityType());
//			if(nodeBaseActivityList.get(i).getActivityType().equals(BPELKeyWords.COPY))
//			{
//				System.out.println("delete:"+nodeBaseActivityList.get(i).getgId());
//				nodeBaseActivityList.remove(i);
//				i=0;
//			}
//		}
	}
	public void processCopyToAssignActivity(NodeAssignActivity assignActivity)
	{
		ArrayList<NodeBaseActivity> copyActivityList = new ArrayList<NodeBaseActivity>();
		int startLogicClock=assignActivity.getStartLogicClock();
		int endLogicClock=assignActivity.getEndLogicClock();
		for (int i = 0; i < nodeBaseActivityList.size(); i++) {
			NodeBaseActivity nba=nodeBaseActivityList.get(i);
			if(nba.getActivityType().equals(BPELKeyWords.COPY)&&nba.getLogicClockOrder()>startLogicClock&&nba.getLogicClockOrder()<endLogicClock)
			{
				copyActivityList.add(nba);
			}
		}
		assignActivity.setInputVariable(copyActivityList.get(0).getInputVariable());
		assignActivity.setOutputVariable(copyActivityList.get(copyActivityList.size()-1).getOutputVariable());
	}
	/**
	 * 打印所有活动信息
	 */
	public void printAllActivity()
	{
		System.out.println("***********************[var:]************************");
		for (int i = 0; i < varSets.size(); i++) {
			System.out.println(varSets.get(i).toString());
		}
		System.out.println("***********************[nodeLinkList:]************************");

		for (int i = 0; i < nodeLinkList.size(); i++) {
			System.out.println(nodeLinkList.get(i).toString());

		}
		System.out.println("***********************[nodePartLinkList:]************************");
		for (int i = 0; i < nodePartLinkList.size(); i++) {
			System.out.println(nodePartLinkList.get(i).toString());
		}
		System.out.println("***********************[nodeFlowActivityList:]************************");
		for (int i = 0; i < nodeFlowActivityList.size(); i++) {
			System.out.println(nodeFlowActivityList.get(i).toString());
		}
		System.out.println("***********************[nodeScopeActivityList:]************************");
		for (int i = 0; i < nodeScopeActivityList.size(); i++) {
			System.out.println(nodeScopeActivityList.get(i).toString());
		}
		System.out.println("***********************[nodeSequenceActivityList:]************************");
		for (int i = 0; i < nodeSequenceActivityList.size(); i++) {
			System.out.println(nodeSequenceActivityList.get(i).toString());
		}
		System.out.println("***********************[nodeAssignActivityList:]************************");
		for (int i = 0; i < nodeAssignActivityList.size(); i++) {
			System.out.println(nodeAssignActivityList.get(i).toString());
		}
		System.out.println("***********************[nodeSwitchActivityList:]************************");
		for (int i = 0; i < nodeSwitchActivityList.size(); i++) {
			System.out.println(nodeSwitchActivityList.get(i).toString());
		}
		System.out.println("***********************[nodeCaseActivityList:]************************");
		for (int i = 0; i < nodeCaseActivityList.size(); i++) {
			System.out.println(nodeCaseActivityList.get(i).toString());
		}
		System.out.println("***********************[nodeBaseActivityList:]************************");
		for (int i = 0; i < nodeBaseActivityList.size(); i++) {
			System.out.println(nodeBaseActivityList.get(i).toString());
		}
	}

}
