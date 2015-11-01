package com.bpel.hbconstruct;

import java.util.ArrayList;

public class ReadAndWriteList {
	ArrayList<NodeReadAndWrite> readList=new ArrayList<NodeReadAndWrite>();
	ArrayList<NodeReadAndWrite> writeList=new ArrayList<NodeReadAndWrite>();
	public ArrayList<NodeReadAndWrite> getReadList() {
		return readList;
	}
	public void setReadList(ArrayList<NodeReadAndWrite> readList) {
		this.readList = readList;
	}
	public ArrayList<NodeReadAndWrite> getWriteList() {
		return writeList;
	}
	public void setWriteList(ArrayList<NodeReadAndWrite> writeList) {
		this.writeList = writeList;
	}
	
}
