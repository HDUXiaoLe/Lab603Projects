package com.horstmann.violet.application.menu.util;

import java.awt.Point;

public class Edge {
	private String type;
	private String id;
	private String name;
	private String starNodeid;//消息�?��节点的id
	private String starNodeType;
	private String endNodeid;//消息结束端节点的id
	private String endNodeType;
	public Edge() {}
	
	
	public String getStarNodeType() {
		return starNodeType;
	}


	public void setStarNodeType(String starNodeType) {
		this.starNodeType = starNodeType;
	}


	public String getEndNodeType() {
		return endNodeType;
	}


	public void setEndNodeType(String endNodeType) {
		this.endNodeType = endNodeType;
	}


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStarNodeid() {
		return starNodeid;
	}
	public void setStarNodeid(String starNodeid) {
		this.starNodeid = starNodeid;
	}

	public String getEndNodeid() {
		return endNodeid;
	}
	public void setEndNodeid(String endNodeid) {
		this.endNodeid = endNodeid;
	}


	@Override
	public String toString() {
		return "Edge [type=" + type + ", id=" + id + ", name=" + name
				+ ", starNodeid=" + starNodeid + ", starNodeType="
				+ starNodeType + ", endNodeid=" + endNodeid + ", endNodeType="
				+ endNodeType + "]";
	}

	


	
	
}
