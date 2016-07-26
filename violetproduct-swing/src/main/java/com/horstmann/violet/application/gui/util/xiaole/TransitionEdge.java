package com.horstmann.violet.application.gui.util.xiaole;
public class TransitionEdge {
private String id;
private String start_reference;
private String end_reference;
private String startlocation_id;
private String startlocation_x;
private String startlocation_y;
private String endlocation_id;
private String endlocation_x;
private String endlocation_y;
private String labelText;
private String UnderEndLocation_id;
private String startclass;
private String endlclass;

public String getStartclass() {
	return startclass;
}
public void setStartclass(String startclass) {
	this.startclass = startclass;
}
public String getEndlclass() {
	return endlclass;
}
public void setEndlclass(String endlclass) {
	this.endlclass = endlclass;
}
public String getUnderEndLocation_id() {
	return UnderEndLocation_id;
}
public void setUnderEndLocation_id(String underEndLocation_id) {
	UnderEndLocation_id = underEndLocation_id;
}
public String getEnd_reference()
{
	return  end_reference;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getStart_reference() {
	return start_reference;
}
public void setStart_reference(String start_reference) {
	this.start_reference = start_reference;
}
public String getStartlocation_id() {
	return startlocation_id;
}
public void setStartlocation_id(String startlocation_id) {
	this.startlocation_id = startlocation_id;
}
public String getStartlocation_x() {
	return startlocation_x;
}
public void setStartlocation_x(String startlocation_x) {
	this.startlocation_x = startlocation_x;
}
public String getStartlocation_y() {
	return startlocation_y;
}
public void setStartlocation_y(String startlocation_y) {
	this.startlocation_y = startlocation_y;
}
public String getEndlocation_id() {
	return endlocation_id;
}
public void setEndlocation_id(String endlocation_id) {
	this.endlocation_id = endlocation_id;
}
public String getEndlocation_x() {
	return endlocation_x;
}
public void setEndlocation_x(String endlocation_x) {
	this.endlocation_x = endlocation_x;
}
public String getEndlocation_y() {
	return endlocation_y;
}
public void setEndlocation_y(String endlocation_y) {
	this.endlocation_y = endlocation_y;
}
public String getLabelText() {
	return labelText;
}
public void setLabelText(String labelText) {
	this.labelText = labelText;
}
public void setEnd_reference(String end_reference) {
	this.end_reference = end_reference;
}
}