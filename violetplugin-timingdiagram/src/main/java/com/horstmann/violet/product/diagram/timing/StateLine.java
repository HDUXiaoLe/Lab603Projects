package com.horstmann.violet.product.diagram.timing;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.Id;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.IHorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.SEdge;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class StateLine implements SEdge ,Cloneable {	
	private List<IHorizontalChild> horizontalchild=new ArrayList<IHorizontalChild>(); 
	//水平子节点	
	public StateLine(){
		
	}	
	public List<IHorizontalChild> gethorizontalChild() {
		return horizontalchild;
	}
// 根据鼠标事件添加水平子节点
	public void addhorizontalChild(IHorizontalChild e) {
		horizontalchild.add(e);			
		}
	public void sethorizontalChild(List<IHorizontalChild> lines)
	{
		horizontalchild=lines;
	}		
    public StateLine clone(){
        try{
        	StateLine cloned = (StateLine) super.clone();         
            return cloned;
        }
        catch (CloneNotSupportedException exception)
        {
            return null;
        }
    }
	
    public Rectangle2D getChildTopBounds(IHorizontalChild child)
    {	
    	return new Rectangle2D.Double(child.getStart().getX(),child.getStart().getY()-10,
    			child.getEnd().getX()-child.getStart().getX(),10);
    }
    public Rectangle2D getChildBottomBounds(IHorizontalChild child)
    {
    	return new Rectangle2D.Double(child.getStart().getX(),child.getStart().getY(),
    			child.getEnd().getX()-child.getStart().getX(),10);
    }
	

}


