package com.horstmann.violet.application.gui.util.wujun;

public class REF{

	String lastMessageID;//3种情况init REF:XXXXX EAXXXX

	String inFragName;//必须
	String inFragID;//必须
	String diagramName;//必须
	String refID;//暂时没用的属性
	
	WJRectangle rectangle;
	int index;
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	public String getInFragName() {
		return inFragName;
	}
	public void setInFragName(String inFragName) {
		this.inFragName = inFragName;
	}
	public String getDiagramName() {
		return diagramName;
	}
	public void setDiagramName(String diagramName) {
		this.diagramName = diagramName;
	}
	public String getLastMessageID() {
		return lastMessageID;
	}
	public void setLastMessageID(String lastMessageID) {
		this.lastMessageID = lastMessageID;
	}
	public String getInFragID() {
		return inFragID;
	}
	public void setInFragID(String inFragID) {
		this.inFragID = inFragID;
	}
	
	
}
