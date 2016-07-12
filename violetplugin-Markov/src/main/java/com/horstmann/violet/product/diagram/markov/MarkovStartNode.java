package com.horstmann.violet.product.diagram.markov;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.horstmann.violet.product.diagram.abstracts.node.EllipticalNode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;


public class MarkovStartNode extends EllipticalNode{
	
	    public MarkovStartNode()
	    {
		  name = "";
	    } 
	    @Override
	    public Rectangle2D getBounds()
	    {
	        Point2D currentLocation = getLocation();
	        double x = currentLocation.getX();
	        double y = currentLocation.getY();
	        double w = DEFAULT_DIAMETER + 2 * DEFAULT_GAP;
	        double h = DEFAULT_DIAMETER + 2 * DEFAULT_GAP;
	        Rectangle2D currentBounds = new Rectangle2D.Double(x, y, w, h);
	        Rectangle2D snappedBounds = getGraph().getGridSticker().snap(currentBounds);
	        return snappedBounds;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see com.horstmann.violet.framework.Node#draw(java.awt.Graphics2D)
	     */
	    public void draw(Graphics2D g2)
	    {
	        super.draw(g2);

	        // Backup current color;
	        Color oldColor = g2.getColor();

	        // Draw circles
	        Ellipse2D circle = new Ellipse2D.Double(getBounds().getX(), getBounds().getY(), getBounds().getWidth(), getBounds()
	                .getHeight());

	        Rectangle2D bounds = getBounds();
	        Ellipse2D inside = new Ellipse2D.Double(bounds.getX() + DEFAULT_GAP, bounds.getY() + DEFAULT_GAP, bounds.getWidth() - 2
	                * DEFAULT_GAP, bounds.getHeight() - 2 * DEFAULT_GAP);
	        g2.setColor(getBackgroundColor());
	        g2.fill(circle);
	        g2.setColor(getBorderColor());
	        g2.fill(inside);
	        g2.draw(circle);

	        // Restore first color
	        g2.setColor(oldColor);
	        g2.drawString(name,(int)getBounds().getX(),(int)getBounds().getY());
	    }
	    public void setName(String newValue)
	    {
	    	name = newValue;
	    }
	    public String getName()
	    {
	    	return name;
	    }
	    public MarkovStartNode clone()
	    {
	    	MarkovStartNode cloned =(MarkovStartNode)super.clone();
	    	
	    	return cloned;
	    }
        private String name;
	    /** default node diameter */
	    private static int DEFAULT_DIAMETER = 29;

	    /** default gap between the main circle and the ring for a final node */
	    private static int DEFAULT_GAP = 5;

	

	}
