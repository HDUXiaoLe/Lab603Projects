package com.horstmann.violet.product.diagram.timing;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.horstmann.violet.product.diagram.abstracts.edge.Message;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;

public class SendMessageEdge extends Message{
	
	 @Override
	    public ArrowHead getEndArrowHead()//获取尾部箭头
	    {
	       
	        return ArrowHead.BLACK_TRIANGLE;
	    }

	@Override
	public Rectangle2D getLabelBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLabelBounds(Rectangle2D rectangle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLabelLocation(Point2D pLocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point2D getLabelLocation() {
		// TODO Auto-generated method stub
		return null;
	}	
	 
}
