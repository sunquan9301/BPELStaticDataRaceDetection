package com.bpel.preprocess;
/**
 * �洢�����࣬���ڱ�ʾ��������Ϣ
 * @name ��ʾ����������
 * @isSharedValue ��ʾ�Ƿ�Ϊ����������ʵĹ����������ʼĬ��ΪUNSHARE_VAR
 *
 */
public class NodeVar {
	private String name="";
//	private int isSharedValue=IsShared.UNSHARE_VAR;
	public NodeVar(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[name:]"+name;
	}
}
