package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class UppaalTemPlate {

	private String name;//名称
	private UppaalLocation InitState;//初始状态
	private ArrayList<UppaalTransition> transitions;//状态集合
	private ArrayList<UppaalLocation> locations;//转换集合
	private ArrayList<String> Clocks;//时钟集合
	
	public UppaalLocation getInitState() {
		return InitState;
	}
	public void setInitState(UppaalLocation initState) {
		InitState = initState;
	}
	public ArrayList<String> getClocks() {
		return Clocks;
	}
	public void setClockSet(ArrayList<String> clocks) {
		Clocks = clocks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<UppaalTransition> getTransitions() {
		return transitions;
	}
	public void setTransitions(ArrayList<UppaalTransition> transitions) {
		this.transitions = transitions;
	}
	public ArrayList<UppaalLocation> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<UppaalLocation> locations) {
		this.locations = locations;
	}
	
}
