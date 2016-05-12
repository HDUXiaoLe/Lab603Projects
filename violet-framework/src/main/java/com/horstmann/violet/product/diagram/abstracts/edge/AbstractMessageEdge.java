package com.horstmann.violet.product.diagram.abstracts.edge;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.horstmann.violet.product.diagram.abstracts.Direction;
import com.horstmann.violet.product.diagram.abstracts.Id;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public abstract class AbstractMessageEdge implements IEdge{	
	private INode start;
	private INode end;
	@XStreamOmitField
	private Point2D startLocation;
	@XStreamOmitField
	private Point2D endLocation;
	private Point2D[] transitionPoints;
	private transient String toolTip;	
	

	
	@Override
	public void setBelongtoStartFlag(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBelongtoStartFlag() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setBelongtoEndFlag(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBelongtoEndFlag() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public void setTransitionPoints(Point2D[] transitionPoints) {
		// TODO Auto-generated method stub
		   this.transitionPoints = transitionPoints;
	}

	@Override
	public Point2D[] getTransitionPoints() {
		// TODO Auto-generated method stub
		 if (this.transitionPoints == null) {
	            return new Point2D[] {};
	        }
	        return this.transitionPoints;
	}
	@Override
	public boolean isTransitionPointsSupported() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Direction getDirection(INode node) {
		// TODO Auto-generated method stub
		 Rectangle2D startBounds = start.getBounds();
	        Rectangle2D endBounds = end.getBounds();
	        Point2D startLocationOnGraph = start.getLocationOnGraph();
	        Point2D endLocationOnGraph = end.getLocationOnGraph();
	        Point2D startCenter = new Point2D.Double(startLocationOnGraph.getX() + startBounds.getWidth() / 2, startLocationOnGraph.getY() + startBounds.getHeight() / 2);
	        Point2D endCenter = new Point2D.Double(endLocationOnGraph.getX() + endBounds.getWidth() / 2, endLocationOnGraph.getY() + endBounds.getHeight() / 2);
	        if (node.equals(start)) {//如果节点是起始节点
	            if (isTransitionPointsSupported() && this.transitionPoints != null && this.transitionPoints.length > 0) {
	                Point2D firstTransitionPoint = this.transitionPoints[0];
	                Direction fromStart = new Direction(firstTransitionPoint, startCenter);
	                return fromStart;
	            }
	            Direction fromStart = new Direction(endCenter, startCenter);
	            return fromStart;
	        }
	        if (node.equals(end)) {
	            if (isTransitionPointsSupported() && this.transitionPoints != null && this.transitionPoints.length > 0) {
	                Point2D lastTransitionPoint = this.transitionPoints[this.transitionPoints.length - 1];
	                Direction toEnd = new Direction(lastTransitionPoint, endCenter);
	                return toEnd;
	            }
	            Direction toEnd = new Direction(startCenter, endCenter);
	            return toEnd;
	        }
	        return null;
	}

	public AbstractMessageEdge()
	{
		
	}

	@Override
	public void setStart(INode startingNode) {
		// TODO Auto-generated method stub
		this.start=startingNode;
	}

	@Override
	public INode getStart() {
		// TODO Auto-generated method stub
		return start;
	}

	@Override
	public void setEnd(INode endingNode) {
		// TODO Auto-generated method stub
		this.end=endingNode;
	}

	@Override
	public INode getEnd() {
		// TODO Auto-generated method stub
		return end;
	}

	@Override
	public void setStartLocation(Point2D startingLocation) {
		// TODO Auto-generated method stub
		this.startLocation=startingLocation;
	}

	@Override
	public Point2D getStartLocation() {
		// TODO Auto-generated method stub
		return startLocation;
	}

	@Override
	public void setEndLocation(Point2D endLocation) {
		// TODO Auto-generated method stub
		this.endLocation=endLocation;
	}

	@Override
	public Point2D getEndLocation() {
		// TODO Auto-generated method stub
		return this.endLocation;
	}

	
	
	@Override
	public Id getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Id id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getRevision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRevision(Integer newRevisionNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incrementRevision() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public Line2D getConnectionPoints() {
		// TODO Auto-generated method stub
	    INode startingStateline =getStart();
	    INode endingStateline =getEnd();
	    Point2D startingStatelineStartLocation=startingStateline.getConnectionPoint(this);
	    Point2D endingStatelineStartLocation=endingStateline.getConnectionPoint(this);
	    return new Line2D.Double(startingStatelineStartLocation,endingStatelineStartLocation);
	}

	@Override
	public boolean contains(Point2D aPoint) {
		// TODO Auto-generated method stub
		return getBounds().contains(aPoint);
	}

	@Override
	public Rectangle2D getBounds() {
		// TODO Auto-generated method stub
		Line2D conn =getConnectionPoints();
		Rectangle2D r= new Rectangle2D.Double();
		r.setFrameFromDiagonal(conn.getX1(), conn.getY1(), conn.getX2(), conn.getY2());
		return r;
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}

	 public void setToolTip(String s)
	    {
	        this.toolTip = s;
	    }
	@Override
	public String getToolTip() {
		// TODO Auto-generated method stub
		  if (this.toolTip == null) {
	        	this.toolTip = "";
	        }
	    	return this.toolTip;
	}
	@Override
	public AbstractMessageEdge clone() {
		// TODO Auto-generated method stub
        try
        {
            AbstractMessageEdge cloned = (AbstractMessageEdge) super.clone();
            
            return cloned;
        }
        catch (CloneNotSupportedException ex)
        {
            return null;
        }
	}
	
}
