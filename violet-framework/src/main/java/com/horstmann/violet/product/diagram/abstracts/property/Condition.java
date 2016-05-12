package com.horstmann.violet.product.diagram.abstracts.property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Condition implements Serializable, Cloneable {
	@XStreamImplicit
  private List<String> conditionTexts=new ArrayList<String>();
	@XStreamOmitField
  private INode parentNode;
public List<String> getConditionTexts() {
	return conditionTexts;
}
public void setConditionTexts(List<String> conditionTexts) {
	this.conditionTexts = conditionTexts;
}
public Condition clone() {
	Condition cloned = new Condition();
	return cloned;
}
public INode getParentNode() {
	return parentNode;
}
public void setParentNode(INode parentNode) {
	this.parentNode = parentNode;
}

}