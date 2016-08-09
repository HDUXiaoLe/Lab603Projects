package com.horstmann.violet.application.gui.util.wqq.other;

public class State {
	private String name;//名称
	private DBM_element[][] invariantDBM ;//状态中的时钟不变式集合
	private DBM_element[][] addClockRelationDBM ;//加入了时钟关系的不变式矩阵
	private String position;//判断两个状态位置是否相同的标志位
	//private String type;//后期可能添加的状态类型属性（是不是初始状态）
	boolean finalState;//标志状态是否为终止状态
	
	
	
	public DBM_element[][] getAddClockRelationDBM() {
		return addClockRelationDBM;
	}
	public void setAddClockRelationDBM(DBM_element[][] addClockRelationDBM) {
		this.addClockRelationDBM = addClockRelationDBM;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DBM_element[][] getInvariantDBM() {
		return invariantDBM;
	}
	public void setInvariantDBM(DBM_element[][] invariantDBM) {
		this.invariantDBM = invariantDBM;
	}
	public boolean isFinalState() {
		return finalState;
	}
	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}
	
	
	
}
