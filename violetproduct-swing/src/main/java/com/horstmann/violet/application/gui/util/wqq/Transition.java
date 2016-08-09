package com.horstmann.violet.application.gui.util.wqq;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("transition")
public class Transition extends Entity{
	@XStreamAlias("id")
	private String id;
	@XStreamAlias("name")
	private String name;
	@XStreamAlias("source")
	private String source;
	@XStreamAlias("target")
	private String target;
	@XStreamAlias("timeDuration")
	private String timeDuration;
	@XStreamAlias("typeAndCondition")
	private String typeAndCondition;
	@XStreamAlias("typeId")
	private String typeId;
	@XStreamAlias("in")
	private String in;
	@XStreamAlias("out")
	private String out;
	@XStreamAlias("RESET")
	private String RESET;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getTimeDuration() {
		return timeDuration;
	}
	public void setTimeDuration(String timeDuration) {
		this.timeDuration = timeDuration;
	}
	public String getTypeAndCondition() {
		return typeAndCondition;
	}
	public void setTypeAndCondition(String typeAndCondition) {
		this.typeAndCondition = typeAndCondition;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getIn() {
		return in;
	}
	public void setIn(String in) {
		this.in = in;
	}
	public String getOut() {
		return out;
	}
	public void setOut(String out) {
		this.out = out;
	}
	public String getRESET() {
		return RESET;
	}
	public void setRESET(String rESET) {
		RESET = rESET;
	}
	
}
