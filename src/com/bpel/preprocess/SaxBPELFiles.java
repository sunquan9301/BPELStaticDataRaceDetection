package com.bpel.preprocess;
import java.util.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
public class SaxBPELFiles extends DefaultHandler{
	private static boolean isInFlowConstruct = false;     //�ж��Ƿ���flow�ṹ����
	private static int logicClock = -1;                   //����е��߼�ʱ�ӣ�
	private String copyActivityInputValue="";
	private String copyActivityOutputValue="";
	
	//����node�ṹ�����
	private String var = null;	
	private NodeLink nodeLink = null;
	private NodePartLink nodePartLink = null;
	private NodeBaseActivity nodeBaseActivity = null;    //����������������� invoke receive copy
	private NodeFlowActivity nodeFlowActivity = null;   //flow�����������Ŀ�ʼ�ڵ�
	private NodeScopeActivity nodeScopeActivity = null;
	private NodeSequenceActivity nodeSequenceActivity = null;
	private NodeAssignActivity nodeAssignActivity = null;
	private NodeSwitchActivity nodeSwitchActivity = null;
	private NodeCaseActivity nodeCaseActivity = null;
	//---------------------���ܰ������flow�������ж��ͼ����Ҫ����------------------------
	//����list���
	private ArrayList<String> VarList;
	private ArrayList<NodeLink> nodeLinkList;
	private ArrayList<NodePartLink> nodePartLinkList;
	private ArrayList<NodeBaseActivity> nodeBaseActivityList;
	private ArrayList<NodeFlowActivity> nodeFlowActivityList;
	private ArrayList<NodeScopeActivity> nodeScopeActivityList;
	private ArrayList<NodeSequenceActivity> nodeSequenceActivityList;
	private ArrayList<NodeAssignActivity> nodeAssignActivityList;
	private ArrayList<NodeSwitchActivity> nodeSwitchActivityList;
	private ArrayList<NodeCaseActivity> nodeCaseActivityList;
	
	private ArrayList<String> sourceLinkList;
	private ArrayList<String> targetLinkList;
	private ArrayList<String> correlationList;
	//�������ļ��� �����Ȳ�������
	
	//���������get����
	public ArrayList<NodeCaseActivity> getNodeCaseActivityList() {
		return nodeCaseActivityList;
	}
	public ArrayList<NodeSwitchActivity> getNodeSwitchActivityList() {
		return nodeSwitchActivityList;
	}
	public ArrayList<NodeAssignActivity> getNodeAssignActivityList() {
		return nodeAssignActivityList;
	}
	public ArrayList<NodeSequenceActivity> getNodeSequenceActivityList() {
		return nodeSequenceActivityList;
	}
	public ArrayList<NodeScopeActivity> getNodeScopeActivityList() {
		return nodeScopeActivityList;
	}
	public ArrayList<NodeFlowActivity> getNodeFlowActivityList() {
		return nodeFlowActivityList;
	}
	public ArrayList<NodeBaseActivity> getNodeBaseActivitieList() {
		return nodeBaseActivityList;
	}
	public ArrayList<NodeLink> getNodeLinkList() {
		return nodeLinkList;
	}
	public ArrayList<NodePartLink> getNodePartLinkList() {
		return nodePartLinkList;
	}
	public ArrayList<String> getVariable()
	{
		return VarList;
	}
	//��дSAX�������̶�����
	@Override
	public void startDocument() throws SAXException
	{
		VarList = new ArrayList<String>();
		nodeLinkList = new ArrayList<NodeLink>();
		nodePartLinkList = new ArrayList<NodePartLink>();
		nodeBaseActivityList = new ArrayList<NodeBaseActivity>();
		nodeFlowActivityList = new ArrayList<NodeFlowActivity>();
		nodeScopeActivityList = new ArrayList<NodeScopeActivity>();
		nodeSequenceActivityList = new ArrayList<NodeSequenceActivity>();
		nodeAssignActivityList = new ArrayList<NodeAssignActivity>();
		nodeSwitchActivityList = new ArrayList<NodeSwitchActivity>();
		nodeCaseActivityList = new ArrayList<NodeCaseActivity>();
		
		sourceLinkList = new ArrayList<String>();
		targetLinkList = new ArrayList<String>();
		correlationList = new ArrayList<String>();
	}
	
	@Override
	public void startElement(String uri,String localName,String name,Attributes attrLocal) throws SAXException
	{
		if(BPELKeyWords.VARIABLE.equals(name))
		{
			//System.out.println("var:"+attrLocal.getValue("name"));
			var=attrLocal.getValue("name");
		}
		if(BPELKeyWords.PARTNER_LINK.equals(name))
		{
			nodePartLink=new NodePartLink(attrLocal.getValue("name"));
		}
		if(BPELKeyWords.LINK.equals(name))
		{
			nodeLink=new NodeLink(attrLocal.getValue("name"));
		}
		if(BPELKeyWords.FLOW.equals(name))
		{
			SaxBPELFiles.isInFlowConstruct=true;
			SaxBPELFiles.logicClock++;
			nodeFlowActivity = new NodeFlowActivity(getGID(logicClock), name, attrLocal.getValue("name"), logicClock, -1);
		}
		if(BPELKeyWords.SCOPE.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeScopeActivity = new NodeScopeActivity(getGID(logicClock), name, attrLocal.getValue("name"), logicClock, -1, attrLocal.getValue("isolated").equals("yes")?true:false);
			}
		}
		if(BPELKeyWords.SEQUENCE.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeSequenceActivity = new NodeSequenceActivity(getGID(logicClock), name, attrLocal.getValue("name"), logicClock, -1);
			}
		}
		if(BPELKeyWords.ASSIGN.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeAssignActivity = new NodeAssignActivity(getGID(logicClock), name, attrLocal.getValue("name"),null,null, logicClock, -1,null,null);
			}
		}
		if(BPELKeyWords.SWITCH.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeSwitchActivity = new NodeSwitchActivity(getGID(logicClock), name, attrLocal.getValue("name"), logicClock, -1);
			}
		}
		if(BPELKeyWords.CASE.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeCaseActivity = new NodeCaseActivity(getGID(logicClock), name, attrLocal.getValue("name"),attrLocal.getValue("condition"), logicClock, -1);
			}
		}
		if(BPELKeyWords.RECEIVE.equals(name))
		{
			//tid����Ϊ������ĳߴ��1
			//local ����ʱ������
			if(isInFlowConstruct)
			{
				//ֱ�Ӷ���ͬ����
				SaxBPELFiles.logicClock++;
				nodeBaseActivity = new NodeBaseActivity(getGID(logicClock), name, attrLocal.getValue("name"), attrLocal.getValue("partnerLink"), attrLocal.getValue("inputVariable"),attrLocal.getValue("variable"), null, logicClock,  IsSynchronization.SYNCHRONIZATION, null, null);
			}
		}
		if(BPELKeyWords.REPLY.equals(name))
		{
			if(isInFlowConstruct)
			{
				//ֱ�Ӷ���ͬ����
				SaxBPELFiles.logicClock++;
				nodeBaseActivity = new NodeBaseActivity(getGID(logicClock), name, attrLocal.getValue("name"), attrLocal.getValue("partnerLink"), attrLocal.getValue("inputVariable"), attrLocal.getValue("variable"), null, logicClock,  IsSynchronization.SYNCHRONIZATION, null, null);
			}
		}
		if(BPELKeyWords.COPY.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeBaseActivity = new NodeBaseActivity(getGID(logicClock), name, attrLocal.getValue("name"), attrLocal.getValue("partnerLink"), null, null, null, logicClock, IsSynchronization.SYNCHRONIZATION, null, null);
			}
		}
		if(BPELKeyWords.INVOKE.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeBaseActivity = new NodeBaseActivity(getGID(logicClock), name, attrLocal.getValue("name"), attrLocal.getValue("partnerLink"), attrLocal.getValue("inputVariable"), attrLocal.getValue("outputVariable"), null, logicClock,attrLocal.getValue("outputVariable")==null?IsSynchronization.ASYNCHRONIZATION:IsSynchronization.SYNCHRONIZATION, null, null);
			}
		}
		//from and to
//		<copy> 
//		<from> 
//			<wsa:EndpointReference> 
//				<wsa:Address>xs:anyURI</wsa:Address> 
//				<wsa:ServiceName>ars:RegistrationService</wsa:ServiceName> 
//			</wsa:EndpointReference> 
//		</from> 
//		<to partnerLink="auctionRegistrationService"/> 
//	</copy> 
		if(BPELKeyWords.FROM.equals(name))
		{
			if(isInFlowConstruct)
			{
				if(attrLocal.getValue("variable")!=null)
				{
					copyActivityInputValue=attrLocal.getValue("variable");
				}
				if(attrLocal.getValue("expression")!=null)
				{
					copyActivityInputValue=attrLocal.getValue("expression");
				}
				if(attrLocal.getValue("partnerLink")!=null)
				{
					copyActivityInputValue=attrLocal.getValue("partnerLink");
				}
			}
		}
		if(BPELKeyWords.TO.equals(name))
		{
			if(isInFlowConstruct)
			{
				if(attrLocal.getValue("variable")!=null)
				{
					copyActivityOutputValue=attrLocal.getValue("variable");
				}
				if(attrLocal.getValue("partnerLink")!=null)
				{
					copyActivityOutputValue=attrLocal.getValue("partnerLink");
				}
			}
		}
		//source,target
		if(BPELKeyWords.SOURCE.equals(name))
		{
			if(isInFlowConstruct)
			{
				sourceLinkList.add(attrLocal.getValue("linkName"));
			}
		}
		if(BPELKeyWords.TARGET.equals(name))
		{
			if(isInFlowConstruct)
			{
				targetLinkList.add(attrLocal.getValue("linkName"));
			}
		}
		if(BPELKeyWords.CORRELATION.equals(name))
		{
			if(isInFlowConstruct)
			{
				correlationList.add(attrLocal.getValue("set"));
			}
		}
	}
	
	@Override
	public void endElement(String uri,String localName,String name)throws SAXException
	{
		if(BPELKeyWords.VARIABLE.equals(name))
		{
			VarList.add(var);
			var=null;
		}
		if(BPELKeyWords.PARTNER_LINK.equals(name))
		{
			nodePartLinkList.add(nodePartLink);
			nodePartLink=null;
		}
		if(BPELKeyWords.LINK.equals(name))
		{
			nodeLinkList.add(nodeLink);
			nodeLink=null;
		}
		if(BPELKeyWords.FLOW.equals(name))
		{
			SaxBPELFiles.isInFlowConstruct=false;
			SaxBPELFiles.logicClock++;
			nodeFlowActivity.setEndLogicClock(logicClock);
			nodeFlowActivityList.add(nodeFlowActivity);
			nodeFlowActivity=null;
		}
		if(BPELKeyWords.SCOPE.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeScopeActivity.setEndLogicClock(logicClock);
				nodeScopeActivityList.add(nodeScopeActivity);
				nodeScopeActivity=null;
			}
		}
		if(BPELKeyWords.SEQUENCE.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeSequenceActivity.setEndLogicClock(logicClock);
				nodeSequenceActivityList.add(nodeSequenceActivity);
				nodeSequenceActivity=null;
			}
		}
		if(BPELKeyWords.SWITCH.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeSwitchActivity.setEndLogicClock(logicClock);
				nodeSwitchActivityList.add(nodeSwitchActivity);
				nodeSwitchActivity=null;
			}
		}
		if(BPELKeyWords.CASE.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeCaseActivity.setEndLogicClock(logicClock);
				nodeCaseActivityList.add(nodeCaseActivity);
				nodeCaseActivity=null;
			}
		}
		if(BPELKeyWords.ASSIGN.equals(name))
		{
			if(isInFlowConstruct)
			{
				SaxBPELFiles.logicClock++;
				nodeAssignActivity.setEndLogicClock(logicClock);
				if(sourceLinkList.size()>0)
				{
					nodeAssignActivity.setSourceLinkName(sourceLinkList);
				}
				if(targetLinkList.size()>0)
				{
					nodeAssignActivity.setTargetLinkName(targetLinkList);
				}
				if(correlationList.size()>0)
				{
					nodeAssignActivity.setCorrelation(correlationList);;
				}
				nodeAssignActivityList.add(nodeAssignActivity);
				nodeAssignActivity=null;
				sourceLinkList=new ArrayList<String>();
				targetLinkList=new ArrayList<String>();
				correlationList=new ArrayList<String>();
			}
		}
		if(BPELKeyWords.RECEIVE.equals(name)||BPELKeyWords.INVOKE.equals(name)||BPELKeyWords.REPLY.equals(name))
		{
			if(isInFlowConstruct)
			{
				if(sourceLinkList.size()>0)
				{
					nodeBaseActivity.setSourceLinkName(sourceLinkList);
				}
				if(targetLinkList.size()>0)
				{
					nodeBaseActivity.setTargetLinkName(targetLinkList);
				}
				if(targetLinkList.size()>0)
				{
					nodeBaseActivity.setCorrelation(correlationList);
				}
				nodeBaseActivityList.add(nodeBaseActivity);
				nodeBaseActivity=null;
				sourceLinkList=new ArrayList<String>();
				targetLinkList=new ArrayList<String>();
				correlationList=new ArrayList<String>();

			}
		}
		if(BPELKeyWords.COPY.equals(name))
		{
			if(isInFlowConstruct)
			{
				nodeBaseActivity.setInputVariable(copyActivityInputValue);
				nodeBaseActivity.setOutputVariable(copyActivityOutputValue);
				nodeBaseActivityList.add(nodeBaseActivity);
				nodeBaseActivity=null;
//				sourceLinkList=new ArrayList<String>();
//				targetLinkList=new ArrayList<String>();
				copyActivityInputValue=null;
				copyActivityOutputValue=null;
			}
		}
	}
	/**
	 * @param logicClock ��Ŀ�ʼ���߼�ʱ��
	 * @return GID       ����global order
	 */
	public String getGID(int logicClock)
	{
		return "G"+Integer.toString(logicClock);
	}
	
	
}
