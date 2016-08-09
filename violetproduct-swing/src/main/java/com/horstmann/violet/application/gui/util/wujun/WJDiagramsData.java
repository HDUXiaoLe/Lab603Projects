package com.horstmann.violet.application.gui.util.wujun;

import java.util.ArrayList;

public class WJDiagramsData{
	
	
	
	ArrayList <String> ids = new ArrayList<String>();
	ArrayList <WJLifeline> lifelineArray = new ArrayList <WJLifeline>();
	ArrayList <WJFragment> fragmentArray = new ArrayList <WJFragment>();
	ArrayList <WJMessage> messageArray = new ArrayList <WJMessage>();
	ArrayList <REF> refArray = new ArrayList <REF>();
	String diagramID;
	String name;
	int displayCount = 0;
	public int getDisplayCount() {
		return displayCount;
	}
	public void setDisplayCount(int displayCount) {
		this.displayCount = displayCount;
	}

	int RefEndTo;
	
	public ArrayList<String> getIds() {
		return ids;
	}
	public void setIds(ArrayList<String> ids) {
		this.ids = ids;
	}
	public ArrayList<WJLifeline> getLifelineArray() {
		return lifelineArray;
	}
	public void setLifelineArray(ArrayList<WJLifeline> lifelineArray) {
		this.lifelineArray = lifelineArray;
	}
	public ArrayList<WJFragment> getFragmentArray() {
		return fragmentArray;
	}
	public void setFragmentArray(ArrayList<WJFragment> fragmentArray) {
		this.fragmentArray = fragmentArray;
	}
	public ArrayList<WJMessage> getMessageArray() {
		return messageArray;
	}
	public void setMessageArray(ArrayList<WJMessage> messageArray) {
		this.messageArray = messageArray;
	}
	public ArrayList<REF> getRefArray() {
		return refArray;
	}
	public void setRefArray(ArrayList<REF> refArray) {
		this.refArray = refArray;
	}
	public String getDiagramID() {
		return diagramID;
	}
	public void setDiagramID(String diagramID) {
		this.diagramID = diagramID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRefEndTo() {
		return RefEndTo;
	}
	public void setRefEndTo(int refEndTo) {
		RefEndTo = refEndTo;
	}
	
}
