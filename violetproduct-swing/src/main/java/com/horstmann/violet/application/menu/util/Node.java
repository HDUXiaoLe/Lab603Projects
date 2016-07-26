package com.horstmann.violet.application.menu.util;

import java.awt.Point;

public class Node  {

	private String type;
	private String id;
	private String name;
	private String attribute;
	private String method;
	private Point leftLocation;//节点左上角的坐标
	private Point rightLocation;//节点右下角的坐标
	
	
	
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Point getLeftLocation() {
		return leftLocation;
	}

	public void setLeftLocation(Point leftLocation) {
		this.leftLocation = leftLocation;
	}

	public Point getRightLocation() {
		return rightLocation;
	}

	public void setRightLocation(Point rightLocation) {
		this.rightLocation = rightLocation;
	}

	public Node(){}

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

	

	public Point getLocation() {
		return leftLocation;
	}

	public void setLocation(Point location) {
		this.leftLocation = location;
	}

	@Override
	public String toString() {
		return "Node [type=" + type + ", id=" + id + ", name=" + name
				+ ", attribute=" + attribute + ", method=" + method
				+ ", leftLocation=" + leftLocation + ", rightLocation="
				+ rightLocation + "]";
	}
	
	
}
