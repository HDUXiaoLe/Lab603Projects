package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class UppaalTransition {
	
	private ArrayList<String> Events;//事件
	
	private String target;//转换发生的源状态名称
	private String source;//转换到达的目的状态名称
	private ArrayList<String> ResetClocks;//迁移中复位的时间变量集合
	private ArrayList<String> constraint;//迁移上的时钟约束集合
	//private ArrayList<String> com_constraint;//时钟约束的补集
	//private ArrayList<String> SetClock;//迁移上重置的时钟
	private ArrayList<String> types;
	private ArrayList<String> typeIds;

	public ArrayList<String> getTypes() {
		return types;
	}
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	public ArrayList<String> getTypeIds() {
		return typeIds;
	}
	public void setTypeIds(ArrayList<String> typeIds) {
		this.typeIds = typeIds;
	}
	/*public ArrayList<String> getCom_constraint() {
		return com_constraint;
	}
	public void setCom_constraint(ArrayList<String> com_constraint) {
		this.com_constraint = com_constraint;
	}
	public ArrayList<String> getSetClock() {
		return SetClock;
	}
	public void setSetClock(ArrayList<String> setClock) {
		SetClock = setClock;
	}*/
	public ArrayList<String> getEvents() {
		return Events;
	}
	public void setEvents(ArrayList<String> events) {
		Events = events;
	}
	public ArrayList<String> getResetClocks() {
		return ResetClocks;
	}
	public void setResetClocks(ArrayList<String> resetClocks) {
		ResetClocks = resetClocks;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public ArrayList<String> getConstraint() {
		return constraint;
	}
	public void setConstraint(ArrayList<String> constraint) {
		this.constraint = constraint;
	}	 
}
