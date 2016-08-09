package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class UppaalLocation {
	private String name;//名称
	private ArrayList<String> invariant ;//状态中的时钟不变式集合
	//private ArrayList<String> com_invariant ;//时钟不变式的补集
	boolean finalState;//标志状态是否为终止状态
	
	/*public ArrayList<String> getCom_invariant() {
		return com_invariant;
	}
	public void setCom_invariant(ArrayList<String> com_invariant) {
		this.com_invariant = com_invariant;
	}*/
	public boolean isFinalState() {
		return finalState;
	}
	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}
	public ArrayList<String> getInvariant() {
		return invariant;
	}
	public void setInvariant(ArrayList<String> invariant) {
		this.invariant = invariant;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
