package com.horstmann.violet.application.gui.util;

import java.util.ArrayList;

public class UppaalTemPlate 
{
	
	String declaration;
	String Name;//自动机名称
	ArrayList<UppaalLocation> locations=new ArrayList<UppaalLocation>();//状态集合
	ArrayList<UppaalTransition>transitions=new ArrayList<UppaalTransition>();//迁移集合
	
	public String getName() 
	{
		return Name;
	}
	public void setName(String name) 
	{
		Name = name;
	}
	public ArrayList<UppaalLocation> getLocations() 
	{
		return locations;
	}
	public void setLocations(ArrayList<UppaalLocation> locations) 
	{
		this.locations = locations;
	}
	public ArrayList<UppaalTransition> getTransitions() 
	{
		return transitions;
	}
	public void setTransitions(ArrayList<UppaalTransition> transitions) 
	{
		this.transitions = transitions;
	}
}
