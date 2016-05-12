package com.horstmann.violet.product.diagram.abstracts.edge;

import java.awt.Graphics;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.horstmann.violet.product.diagram.abstracts.IIdentifiable;
import com.horstmann.violet.product.diagram.abstracts.node.AbstractNode;
import com.horstmann.violet.product.diagram.abstracts.node.INode;

public interface SEdge extends Serializable, Cloneable{
			 
	 public List<IHorizontalChild> gethorizontalChild(); 
		
     public void sethorizontalChild(List<IHorizontalChild> lines);
     
	 public void addhorizontalChild(IHorizontalChild edge);
		
	 public Rectangle2D getChildTopBounds(IHorizontalChild child);
	 
	 public Rectangle2D getChildBottomBounds(IHorizontalChild child);
	  
	 SEdge clone();	
}
