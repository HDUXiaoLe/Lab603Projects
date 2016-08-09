package com.horstmann.violet.product.diagram.abstracts.edge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import javax.swing.JLabel;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;
import com.horstmann.violet.product.diagram.abstracts.property.BentStyle;
import com.horstmann.violet.product.diagram.abstracts.property.LineStyle;

public  class TimeConditionEdge extends ShapeEdge{

	
	    public TimeConditionEdge()
	    {
	        
	        middleLabel = "";
	     
	    }
	    
	    @Override
	    public boolean isTransitionPointsSupported()
	    {
	        return false;
	    }
	    	  	  
	    public LineStyle getLineStyle()
	    {
	        if (this.lineStyle == null)
	        {
	            this.lineStyle = LineStyle.SOLID;
	        }
	        return this.lineStyle;
	    }
  
	    /**
	     * Sets the end arrow head property
	     * 
	     * @param newValue the new value
	     */
	    public void setEndArrowHead(ArrowHead newValue)
	    {
	        this.endArrowHead = newValue;
	    }

	    /**
	     * Gets the end arrow head property
	     * 
	     * @return the end arrow head style
	     */
	    public ArrowHead getEndArrowHead()
	    {
	        if (this.endArrowHead == null)
	        {
	            this.endArrowHead = ArrowHead.NONE;
	        }
	        return this.endArrowHead;
	    }

	 


	  
	    public void setMiddleLabel(String newValue)
	    {
	        middleLabel = newValue;
	    }

	
	    public String getMiddleLabel()
	    {
	        return middleLabel;
	    }

	   
	    /**
	     * Draws the edge.
	     * 
	     * @param g2 the graphics context
	     */
	    public void draw(Graphics2D g2)
	    {
	    	
	    	//System.out.println("draw....+SegmentedLineEdge");
	    	Color oldColor = g2.getColor();
	    	g2.setColor(Color.RED);   	
	    	ArrayList<Point2D> points = getPoints();
	        Stroke oldStroke = g2.getStroke();
	        g2.setStroke(getLineStyle().getStroke());
	        g2.draw(getSegmentPath());  
	     
	        g2.setStroke(oldStroke);	       
	        getEndArrowHead().draw(g2, (Point2D) points.get(points.size() - 2), (Point2D) points.get(points.size() - 1));	       
	        drawString(g2, (Point2D) points.get(points.size() / 2 - 1), (Point2D) points.get(points.size() / 2), null, middleLabel,
	                true);
	    
	        g2.setColor(oldColor);
	    }

	    /**
	     * Draws a string.
	     * 
	     * @param g2 the graphics context
	     * @param p an endpoint of the segment along which to draw the string
	     * @param q the other endpoint of the segment along which to draw the string
	     * @param s the string to draw
	     * @param center true if the string should be centered along the segment
	     */
	    private void drawString(Graphics2D g2, Point2D p, Point2D q, ArrowHead arrow, String s, boolean center)
	    {
	        if (s == null || s.length() == 0) return;
	        label.setText(s);
	        label.setFont(g2.getFont());
	        Dimension d = label.getPreferredSize();
	        label.setBounds(0, 0, d.width, d.height);

	        Rectangle2D b = getStringBounds(p, q, arrow, s, center);

	        g2.translate(b.getX(), b.getY());
	        label.paint(g2);
	        g2.translate(-b.getX(), -b.getY());
	    }

	    /**
	     * Computes the attachment point for drawing a string.
	     * 
	     * @param p an endpoint of the segment along which to draw the string
	     * @param q the other endpoint of the segment along which to draw the string
	     * @param b the bounds of the string to draw
	     * @param center true if the string should be centered along the segment
	     * @return the point at which to draw the string
	     */
	    private static Point2D getAttachmentPoint(Point2D p, Point2D q, ArrowHead arrow, Dimension d, boolean center)
	    {
	        final int GAP = 3;
	        double xoff = GAP;
	        double yoff = -GAP - d.getHeight();
	        Point2D attach = q;
	        if (center)
	        {
	            if (p.getX() > q.getX())
	            {
	                return getAttachmentPoint(q, p, arrow, d, center);
	            }
	            attach = new Point2D.Double((p.getX() + q.getX()) / 2, (p.getY() + q.getY()) / 2);
	            if (p.getY() < q.getY()) yoff = -GAP - d.getHeight();
	            else if (p.getY() == q.getY()) xoff = -d.getWidth() / 2;
	            else yoff = GAP;
	        }
	        else
	        {
	            if (p.getX() < q.getX())
	            {
	                xoff = -GAP - d.getWidth();
	            }
	            if (p.getY() > q.getY())
	            {
	                yoff = GAP;
	            }
	            if (arrow != null)
	            {
	                Rectangle2D arrowBounds = arrow.getPath(p, q).getBounds2D();
	                if (p.getX() < q.getX())
	                {
	                    xoff -= arrowBounds.getWidth();
	                }
	                else
	                {
	                    xoff += arrowBounds.getWidth();
	                }
	            }
	        }
	        return new Point2D.Double(attach.getX() + xoff, attach.getY() + yoff);
	    }

	    /**
	     * Computes the extent of a string that is drawn along a line segment.
	     * 
	     * @param g2 the graphics context
	     * @param p an endpoint of the segment along which to draw the string
	     * @param q the other endpoint of the segment along which to draw the string
	     * @param s the string to draw
	     * @param center true if the string should be centered along the segment
	     * @return the rectangle enclosing the string
	     */
	    private static Rectangle2D getStringBounds(Point2D p, Point2D q, ArrowHead arrow, String s, boolean center)
	    {
	        BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
	        // need a dummy image to get a Graphics to
	        // measure the size
	        Graphics2D g2 = (Graphics2D) dummy.getGraphics();
	        if (s == null || s.equals("")) return new Rectangle2D.Double(q.getX(), q.getY(), 0, 0);
	        label.setText("<html>" + s + "</html>");
	        label.setFont(g2.getFont());
	        Dimension d = label.getPreferredSize();
	        Point2D a = getAttachmentPoint(p, q, arrow, d, center);
	        return new Rectangle2D.Double(a.getX(), a.getY(), d.getWidth(), d.getHeight());
	    }

	    @Override
	    public Rectangle2D getBounds()
	    {
	    	  ArrayList<Point2D> points = getPoints();
	          Rectangle2D r = super.getBounds();
	          r.add(getStringBounds((Point2D) points.get(1), (Point2D) points.get(0), getStartArrowHead(),"", false));
	          r.add(getStringBounds((Point2D) points.get(points.size() / 2 - 1), (Point2D) points.get(points.size() / 2), null,
	                  middleLabel, true));
	          r.add(getStringBounds((Point2D) points.get(points.size() - 2), (Point2D) points.get(points.size() - 1), getEndArrowHead(),
	                  "", false));	     	        
	        return r;
	    }
	    public ArrayList<Point2D> getPoints()
	    {	  
	    	
	        Point2D startingPoint = new Point2D.Double(getStartLocation().getX()+50,
	        		getStartLocation().getY());
	        Point2D endingPoint = new Point2D.Double(getEndLocation().getX()+50,
	        		getEndLocation().getY());          
	        return BentStyle.STRAIGHT.getPath(startingPoint, endingPoint);
	    }
	    @Override
	    public Shape getShape()
	    {
	        GeneralPath path = getSegmentPath();
	        ArrayList<Point2D> points = getPoints();
	        path.append(getStartArrowHead().getPath((Point2D) points.get(1), (Point2D) points.get(0)), false);
	        path.append(getEndArrowHead().getPath((Point2D) points.get(points.size() - 2), (Point2D) points.get(points.size() - 1)),
	                false);
	        return path;
	    }
	    public ArrowHead getStartArrowHead()
	    {
	        if (this.startArrowHead == null)
	        {
	            this.startArrowHead = ArrowHead.NONE;
	        }
	        return this.startArrowHead;
	    }

	    public GeneralPath getSegmentPath()
	    {
	        GeneralPath path = new GeneralPath();
	        Point2D p1=new Point2D.Double(getStartLocation().getX()+50,getStartLocation().getY());
	        Point2D p2=new Point2D.Double(getEndLocation().getX()+50,getEndLocation().getY());
	        path.moveTo(p1.getX(), p1.getY());
	        path.lineTo(p2.getX(), p2.getY());
	        return path;
	    }

	   

	      

	    
	    private LineStyle lineStyle;
	 
	    private ArrowHead endArrowHead;
	    
	    private ArrowHead startArrowHead;
	 
	    private String middleLabel;
	  
	    private static JLabel label = new JLabel();
	

	
	 
}
