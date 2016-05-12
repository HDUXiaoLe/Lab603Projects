package com.horstmann.violet.product.diagram.timing;

import java.awt.geom.Line2D;

import com.horstmann.violet.product.diagram.abstracts.edge.Message;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;

public class SendMessageEdge extends Message{
	
	 @Override
	    public ArrowHead getEndArrowHead()//获取尾部箭头
	    {
	       
	        return ArrowHead.BLACK_TRIANGLE;
	    }	
}
