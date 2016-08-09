package com.horstmann.violet.application.gui.util.wqq;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("automata")
public class Automata extends Entity{
	@XStreamAlias("templateList")
	private ArrayList<Template> templateList = new ArrayList<Template>();

	public ArrayList<Template> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(ArrayList<Template> templateList) {
		this.templateList = templateList;
	}
	
}
