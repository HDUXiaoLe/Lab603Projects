package com.horstmann.violet.application.gui.util.wujun;

import java.util.ArrayList;

import org.dom4j.Element;

public class WJMessage implements Cloneable{//消息
	public Object clone() {   
		WJMessage o = null;   
        try {   
            o = (WJMessage) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }   
        return o;   
    }   
	String inFragId="null";//在哪个片段中
	String inFragName="null";
	String connectorId="null";//zijide id
	String sourceId="null";
	String tragetId="null";
	String name="null";
	String sendEvent="null";
	String fromId;//从哪个对象到哪个对象
	String toId;	
	String receiveAndSend = "null";
	String inString;
	String outString;
	
	String T1;
	String T2;
	String Energe;
	String R1;
	String R2;
	
	String SEQDC;
	String SEQDO;
	String SEQTC;
	String SEQTO;
	String DCBM;
	
	double pointY;
	
	
	public double getPointY() {
		return pointY;
	}
	public void setPointY(double pointY) {
		this.pointY = pointY;
	}
	public String getInString() {
		return inString;
	}
	public void setInString(String inString) {
		this.inString = inString;
	}
	public String getOutString() {
		return outString;
	}
	public void setOutString(String outString) {
		this.outString = outString;
	}
	public String getSEQDC() {
		return SEQDC;
	}
	public void setSEQDC(String sEQDC) {
		SEQDC = sEQDC;
	}
	public String getSEQDO() {
		return SEQDO;
	}
	public void setSEQDO(String sEQDO) {
		SEQDO = sEQDO;
	}
	public String getSEQTC() {
		return SEQTC;
	}
	public void setSEQTC(String sEQTC) {
		SEQTC = sEQTC;
	}
	public String getSEQTO() {
		return SEQTO;
	}
	public void setSEQTO(String sEQTO) {
		SEQTO = sEQTO;
	}
	public String getDCBM() {
		return DCBM;
	}
	public void setDCBM(String dCBM) {
		DCBM = dCBM;
	}
	public String getReceiveAndSend() {
		return receiveAndSend;
	}
	public void setReceiveAndSend(String receiveAndSend) {
		this.receiveAndSend = receiveAndSend;
	}
	public String getT1() {
		return T1;
	}
	public void setT1(String t1) {
		T1 = t1;
	}
	public String getT2() {
		return T2;
	}
	public void setT2(String t2) {
		T2 = t2;
	}
	public String getEnerge() {
		return Energe;
	}
	public void setEnerge(String energe) {
		Energe = energe;
	}
	public String getR1() {
		return R1;
	}
	public void setR1(String r1) {
		R1 = r1;
	}
	public String getR2() {
		return R2;
	}
	public void setR2(String r2) {
		R2 = r2;
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	
	public String getInFragName() {
		return inFragName;
	}
	public void setInFragName(String inFragName) {
		this.inFragName = inFragName;
	}
	public String getInFragId() {
		return inFragId;
	}
	public void setInFragId(String inFragId) {
		this.inFragId = inFragId;
	}
	ArrayList<Element> frag=new ArrayList();
	
	public String getConnectorId() 
	{
		return connectorId;
	}
	public void setConnectorId(String connectorId)
	{
		this.connectorId = connectorId;
	}
	public String getSourceId()
	{
		return sourceId;
	}
	public void setSourceId(String sourceId)
	{
		this.sourceId = sourceId;
	}
	public String getTragetId() 
	{
		return tragetId;
	}
	public void setTragetId(String tragetId) 
	{
		this.tragetId = tragetId;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getSendEvent() 
	{
		return sendEvent;
	}
	public void setSendEvent(String sendEvent) 
	{
		this.sendEvent = name;
	}
	
	
	
	
	public ArrayList<Element> getFrag()
	{
		return frag;
	}
	public void setFrag(ArrayList<Element> frag)
	{
		this.frag=frag;
	}
}
