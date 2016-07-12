package com.horstmann.violet.product.diagram.uppaal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.horstmann.violet.product.diagram.abstracts.node.EllipticalNode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
public class CircularNode extends EllipticalNode {
	
	public CircularNode()
	{
		name="";
	}
	
	   
	 @Override
	    public Rectangle2D getBounds()
	    {
	        Point2D currentLocation = getLocation();
	        double x = currentLocation.getX();
	        double y = currentLocation.getY();
	        double w = DEFAULT_DIAMETER;
	        double h = DEFAULT_DIAMETER;
	        Rectangle2D currentBounds = new Rectangle2D.Double(x, y, w, h);
	        Rectangle2D snappedBounds = getGraph().getGridSticker().snap(currentBounds);
	        return snappedBounds;
	    }

	    public void draw(Graphics2D g2)
	    {
	        super.draw(g2);

	        // Backup current color;
	        Color oldColor = g2.getColor();

	        // Draw circle
	        g2.setColor(getBorderColor());
	        Rectangle2D bounds = getBounds();
	        Ellipse2D circle = new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
	        g2.fill(circle);  
	        // Restore first color
	        g2.setColor(oldColor);
	        g2.drawString(getName(),(int)getBounds().getX(),(int)getBounds().getY());
	    }

	    /**
	     * Kept for compatibility with old versions
	     * 
	     * @param dummy
	     */
	    public void setName(String newValue){
	    	name = newValue;
	    }
	    public String getName()
	    {
	    	return name;
	    }
	 
	    public void setFinal(boolean dummy)
	    {
	        // Nothing to do
	    }

	    /** default node diameter */
	    private String name;
	    private static int DEFAULT_DIAMETER = 34;
		

	}


