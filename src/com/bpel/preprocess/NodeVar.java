package com.bpel.preprocess;
/**
 * 存储变量类，用于表示变量的信息
 * @name 表示变量的名字
 * @isSharedValue 表示是否为并发活动所访问的共享变量，初始默认为UNSHARE_VAR
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
