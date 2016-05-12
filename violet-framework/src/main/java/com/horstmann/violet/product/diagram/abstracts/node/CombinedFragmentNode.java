package com.horstmann.violet.product.diagram.abstracts.node;

	
	import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;	
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
	import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentPart;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentType;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

	public class CombinedFragmentNode extends RectangularNode 
	{	   
	   public CombinedFragmentNode()
	   {	
	       type= new FragmentType().ALT;      
	       fragmentparts=new ArrayList<FragmentPart>();
	   }
	
	public List<FragmentPart> getFragmentparts() {
		return fragmentparts;
	}

	public void setFragmentparts(List<FragmentPart> fragmentparts) {
		this.fragmentparts = fragmentparts;
	}

	@Override
	   public Rectangle2D getBounds()//改变外部边框
	   {	
	       Point2D currentLocation = this.getLocation();
	       double x;
	       double y;
		   x = currentLocation.getX();
	       y =  currentLocation.getY();      
	       Rectangle2D currentBounds = new Rectangle2D.Double(x, y, getWidth(), getHeight());
	      // Rectangle2D snapperBounds = getGraph().getGridSticker().snap(currentBounds);
	       return currentBounds;
	   }
	
	   //==================================================================================
	 

	

	
	 
	public double getWidth() {
		return width;
	}
	public void setWidth(double witdh) {
		this.width = witdh;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	@Override
	   public Shape getShape()
	   {
	       Rectangle2D bounds = getBounds();
	       GeneralPath path = new GeneralPath();
	       path.moveTo((float) bounds.getX(), (float) bounds.getY()); 
	       path.lineTo((float) (bounds.getMaxX()), (float) bounds.getY());
	       path.lineTo((float) bounds.getMaxX(), (float) bounds.getY()) ;
	       path.lineTo((float) bounds.getMaxX(), (float) bounds.getMaxY());
	       path.lineTo((float) bounds.getX(), (float) bounds.getMaxY());
	       
	       path.closePath();
	       return path;
	   }
	   //==================================================================================
	   public FragmentType getType() 
	   {
	       return type;
	   }

	public void setType(FragmentType newValue)
	   {  
	       type= newValue;    
	   } 
	//绘制TimeNode节点
	   @Override
	   public void draw(Graphics2D g2)   
	   {	
		  
	       g2.setColor(Color.black);//已改
	       Shape path = getShape();
	       g2.draw(path);
	       Rectangle2D bounds = getBounds();
	       GeneralPath fold = new GeneralPath();
	       fold.moveTo((float) (bounds.getX()+5*d), (float) bounds.getY());  //将鼠标放置在某点
	       fold.lineTo((float) bounds.getX()+5*d, (float) bounds.getY()+(2*d));
	       fold.moveTo((float) bounds.getX()+5*d, (float) bounds.getY()+(2*d));
	       fold.lineTo((float) bounds.getX()+4*d, (float) bounds.getY()+(2.5*d));
	       fold.moveTo((float) bounds.getX() +4*d, (float) bounds.getY()+(2.5*d));
	       fold.lineTo((float) bounds.getX(), (float) bounds.getY()+(2.5*d));
	       fold.closePath();
	       g2.setColor(Color.BLACK);	   	     	     	      
	       type.drawType(g2, bounds);
	       g2.draw(fold);
	       g2.fillRect((int)bounds.getX(), (int)bounds.getY(), 5, 5);
	      
	   }	  	 	  	
	   private static int d =10;
	   private double width=200,height=100 ;
	   private static double Default_Distance=80;
	   private static double Default_FirstConditionTextDistance=40;
	   private static double Default_OtherConditionTextDistance=20;
	   private static Stroke DOTTED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0.0f, new float[]
				    {
				            3.0f,
				            3.0f
				    }, 0.0f);
	   private  static Stroke Defelt_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	 
	 
	   //将 覆盖的消息ID放在此标签下
     
	   private FragmentType type;
	   private List<FragmentPart> fragmentparts;//
	  
	}


