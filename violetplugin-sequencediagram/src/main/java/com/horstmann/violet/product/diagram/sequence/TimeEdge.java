package com.horstmann.violet.product.diagram.sequence;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.horstmann.violet.product.diagram.abstracts.edge.TimeConditionEdge;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;

public class TimeEdge extends TimeConditionEdge{
	 @Override
	    public ArrowHead getEndArrowHead()//获取尾部箭头
	    {
	       
	        return ArrowHead.BLACK_TRIANGLE;
	    }
	  @Override
	    public Line2D getConnectionPoints()//获取两端的连接点
	    {
	        ArrayList<Point2D> points = getPoints();
	        Point2D p1 = points.get(0);
	        Point2D p2 = points.get(points.size() - 1);
	        return new Line2D.Double(p1, p2);
	    }
}
