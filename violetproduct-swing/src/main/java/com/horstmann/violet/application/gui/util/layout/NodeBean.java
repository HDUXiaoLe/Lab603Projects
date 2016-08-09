package com.horstmann.violet.application.gui.util.layout;

public class NodeBean {
	
	private String name;
	
	public NodeBean(String name){
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
	
	

}
