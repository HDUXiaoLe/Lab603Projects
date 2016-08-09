package com.horstmann.violet.application.gui.util.wqq;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("location")
public class Location extends Entity{
	@XStreamAlias("id")
	private String id;
	@XStreamAlias("name")
	private String name;
	@XStreamAlias("timeDuration")
	private String timeDuration;
	@XStreamAlias("init")
	private boolean init;
	@XStreamAlias("final")
	private boolean finl;
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
	public String getTimeDuration() {
		return timeDuration;
	}
	public void setTimeDuration(String timeDuration) {
		this.timeDuration = timeDuration;
	}
	public boolean isInit() {
		return init;
	}
	public void setInit(boolean init) {
		this.init = init;
	}
	public boolean isFinl() {
		return finl;
	}
	public void setFinl(boolean finl) {
		this.finl = finl;
	}
	
}
