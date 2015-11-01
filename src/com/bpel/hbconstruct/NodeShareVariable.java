package com.bpel.hbconstruct;

public class NodeShareVariable {
	private String name=null;
	private String type=null;
	public NodeShareVariable(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[------var-----:]"+"\n\r"
				+"[name: ]"+name
				+"[type:  ]"+type;
	}
}
