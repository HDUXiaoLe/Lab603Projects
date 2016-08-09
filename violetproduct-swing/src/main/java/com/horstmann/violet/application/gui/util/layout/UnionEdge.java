package com.horstmann.violet.application.gui.util.layout;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;

public class UnionEdge {
	
	private DefaultEdge edge;
	private DefaultGraphCell sourceNode;
	private DefaultGraphCell targetNode;

	public void setEdge(DefaultEdge edge) {
		this.edge=edge;
	}

	public void setSourceNode(DefaultGraphCell sourceNode) {
		this.sourceNode=sourceNode;
	}

	public void setTargetNode(DefaultGraphCell targetNode) {
		this.targetNode=targetNode;
	}

	public DefaultEdge getEdge() {
		return edge;
	}

	public DefaultGraphCell getSourceNode() {
		return sourceNode;
	}

	public DefaultGraphCell getTargetNode() {
		return targetNode;
	}
	
	

}
