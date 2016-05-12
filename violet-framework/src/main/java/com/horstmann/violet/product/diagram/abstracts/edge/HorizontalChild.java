
package com.horstmann.violet.product.diagram.abstracts.edge;


import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.thoughtworks.xstream.annotations.XStreamAlias;
//这里添加一个注解，因为在freamWork框架中无法实例化基类类型的horizontal,
//所以整体在上部框架中实现，故将原始节点标签改为horizontalchild,为了之后处理XML方便.
public class HorizontalChild implements IHorizontalChild{
	
	private Point2D startPoint;
	private Point2D endPoint;
	private String state;
	private String condition;
	private String continuetime;

	public HorizontalChild() {
		// TODO Auto-generated constructor stub
		
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getContinuetime() {
		return continuetime;
	}

	public void setContinuetime(String continuetime) {
		this.continuetime = continuetime;
	}

	@Override
	public void setStart(Point2D startpoint) {
		// TODO Auto-generated method stub
		this.startPoint=startpoint;
	}
	@Override
	public void setEnd(Point2D endpoint) {
		// TODO Auto-generated method stub
		this.endPoint=endpoint;
	}
	@Override
	public Point2D getStart() {
		// TODO Auto-generated method stub
		return startPoint;
	}
	@Override
	public Point2D getEnd() {
		// TODO Auto-generated method stub
		return endPoint;
	}

	

}
