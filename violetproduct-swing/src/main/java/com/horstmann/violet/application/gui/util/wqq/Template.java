package com.horstmann.violet.application.gui.util.wqq;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("template")
public class Template extends Entity{
	
	@XStreamAlias("name")
	private String name;
	@XStreamAlias("locationList")
	private ArrayList<Location> locationList = new ArrayList<Location>();
	@XStreamAlias("transitionList")
	private ArrayList<Transition> transitionList = new ArrayList<Transition>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(ArrayList<Location> locationList) {
		this.locationList = locationList;
	}
	public ArrayList<Transition> getTransitionList() {
		return transitionList;
	}
	public void setTransitionList(ArrayList<Transition> transitionList) {
		this.transitionList = transitionList;
	}
	
}
