package com.horstmann.violet.application.gui.util;

public class UppaalTransition 
{
	String sourceId;//源点状态id
	String targetId;//目标状态id
	
	int id;
	String Kind = "synchronisation";
	String nameText;//消息名（包含条件）
	String nameS="null";//源点状态名称
	String nameT="null";//目标状态名称
	
	String SEQDC="null";//1
	String DCBM="null";//2   对应状态上的时间约束
	String SEQDO="null";//3 消息上的时间约束
	String SEQTC="null";//4
	String SEQTO="null";//5
	
	String inString;//输入
	String outString;//输出
	
	String T1 = "0";	
	String T2 = "0";
	
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
	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id=id;
	}
	public String getSourceId() 
	{
		return sourceId;
	}
	public void setSourceId(String sourceId) 
	{
		this.sourceId = sourceId;
	}
	public String getTargetId() 
	{
		return targetId;
	}
	public void setTargetId(String targetId) 
	{
		this.targetId = targetId;
	}
	public String getKind() 
	{
		return Kind;
	}
	public void setKind(String kind) 
	{
		Kind = kind;
	}
	public String getNameText() 
	{
		return nameText;
	}
	public void setNameText(String nameText) 
	{
		this.nameText = nameText;
	}
	public String getNameS() 
	{
		return nameS;
	}
	public void setNameS(String nameS) 
	{
		this.nameS = nameS;
	}
	public String getNameT() 
	{
		return nameT;
	}
	public void setNameT(String nameT) 
	{
		this.nameT = nameT;
	}
}