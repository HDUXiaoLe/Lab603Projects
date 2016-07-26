package com.horstmann.violet.application.gui.util.xiaole;

public class VioletMessageInfo {
    private String ID;
    private String name;
	private String startReferenceID;
	private String endReferenceID;
	private String startLocationX;
	private String StartLocationY;
	private String endLocationX;
	private String endLocationY;
	private String transitionPoints;//可去，暂时不处理
	private final String revision="1";
	private String TimeConstraint;
	private String Condition;
	private String starttimePoint;
	private String endtimePoint;
	private String endstate;
	private String belongtostartflag;
	private String belongtoendflag;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getStartReferenceID() {
		return startReferenceID;
	}
	public void setStartReferenceID(String startReferenceID) {
		this.startReferenceID = startReferenceID;
	}
	public String getEndReferenceID() {
		return endReferenceID;
	}
	public void setEndReferenceID(String endReferenceID) {
		this.endReferenceID = endReferenceID;
	}
	public String getStartLocationX() {
		return startLocationX;
	}
	public void setStartLocationX(String startLocationX) {
		this.startLocationX = startLocationX;
	}
	public String getStartLocationY() {
		return StartLocationY;
	}
	public void setStartLocationY(String startLocationY) {
		StartLocationY = startLocationY;
	}
	public String getEndLocationX() {
		return endLocationX;
	}
	public void setEndLocationX(String endLocationX) {
		this.endLocationX = endLocationX;
	}
	public String getEndLocationY() {
		return endLocationY;
	}
	public void setEndLocationY(String endLocationY) {
		this.endLocationY = endLocationY;
	}
	public String getTransitionPoints() {
		return transitionPoints;
	}
	public void setTransitionPoints(String transitionPoints) {
		this.transitionPoints = transitionPoints;
	}
	public String getTimeConstraint() {
		return TimeConstraint;
	}
	public void setTimeConstraint(String timeConstraint) {
		TimeConstraint = timeConstraint;
	}
	public String getCondition() {
		return Condition;
	}
	public void setCondition(String condition) {
		Condition = condition;
	}
	public String getStarttimePoint() {
		return starttimePoint;
	}
	public void setStarttimePoint(String starttimePoint) {
		this.starttimePoint = starttimePoint;
	}
	public String getEndtimePoint() {
		return endtimePoint;
	}
	public void setEndtimePoint(String endtimePoint) {
		this.endtimePoint = endtimePoint;
	}
	public String getEndstate() {
		return endstate;
	}
	public void setEndstate(String endstate) {
		this.endstate = endstate;
	}
	public String getBelongtostartflag() {
		return belongtostartflag;
	}
	public void setBelongtostartflag(String belongtostartflag) {
		this.belongtostartflag = belongtostartflag;
	}
	public String getBelongtoendflag() {
		return belongtoendflag;
	}
	public void setBelongtoendflag(String belingtoendflag) {
		this.belongtoendflag = belingtoendflag;
	}
	public String getRevision() {
		return revision;
	}		
}
