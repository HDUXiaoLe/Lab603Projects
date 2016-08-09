package com.horstmann.violet.application.gui.util.wujun;

public class MessageClass {
	
	String SequenceMsgId="null";
	String name="null";
	String sendEvent="null";
	String messageSort="null";
	String sourceId="null";
	String targetId="null";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSendEvent() {
		return sendEvent;
	}
	public void setSendEvent(String sendEvent) {
		this.sendEvent = sendEvent;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getSequenceMsgId() 
	{
		return SequenceMsgId;
	}
	public void setSequenceMsgId(String SequenceMsgId)
	{
		this.SequenceMsgId = SequenceMsgId;
	}
	public String getSequenceMsgName() 
	{
		return name;
	}
	public void setSequenceMsgName(String name) 
	{
		this.name = name;
	}
	public String getSequenceMsgSendEvent() 
	{
		return sendEvent;
	}
	public void setSequenceMsgSendEvent(String sendEvent) 
	{
		this.sendEvent = sendEvent;
	}
	public String getMessageSort() 
	{
		return messageSort;
	}
	public void setMessageSort(String messageSort) 
	{
		this.messageSort = messageSort;
	}
}
