package com.horstmann.violet.product.diagram.abstracts.edge;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


public abstract class Message extends AbstractMessageEdge{
 private String name;
 private String TimeConstraint;
 private ArrowHead endArrowHead;
 private String Condition; 
 private int StarttimePoint;
 private int EndtimePoint;
 private String EndState;
 private int belongtostartflag;
 private int belongtoendflag;
 public Message(){	 
		name="";	
		TimeConstraint="";
		Condition="";
		StarttimePoint=0;
		EndtimePoint=0;
		EndState="";
	}
 
   public int getStarttimePoint() {
	return StarttimePoint;
}

@Override
public String getID() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void setID(String id) {
	// TODO Auto-generated method stub
	
}

public void setStarttimePoint(int starttimePoint) {
	StarttimePoint = starttimePoint;
}

public int getEndtimePoint() {
	return EndtimePoint;
}

public void setEndtimePoint(int endtimePoint) {
	EndtimePoint = endtimePoint;
}

public String getEndState() {
	return EndState;
}

public void setEndState(String endState) {
	EndState = endState;
}

public void setEndArrowHead(ArrowHead newValue){
	   this.endArrowHead=newValue;
   }
   public ArrowHead getEndArrowHead(){
	   return this.endArrowHead;
   }
   public String getName() {
	return name;
   }
   public void setName(String name) {
	this.name = name;
   }
   public String getTimeConstraint() {
	return TimeConstraint;
   }
   public void setTimeConstraint(String timeConstraint) {
	TimeConstraint = timeConstraint;
   }     
   public String getCondition() {
	return Condition;
   }
   public void setCondition(String condition) {
	Condition = condition;
   }

  



@Override
public int getBelongtoStartFlag() {
	// TODO Auto-generated method stub
	return this.belongtostartflag;
}

@Override
public int  getBelongtoEndFlag() {
	// TODO Auto-generated method stub
	return this.belongtoendflag;
}

@Override
public void setBelongtoStartFlag(int index) {
	// TODO Auto-generated method stub
	this.belongtostartflag=index;
}

@Override
public void setBelongtoEndFlag(int index) {
	// TODO Auto-generated method stub
	this.belongtoendflag=index;
}
public int TransEndPointToTime(int pointx)
{
    int timePoint=(int) ((pointx-150-getEnd().getBounds().getX())/(getEnd().getWidth()-150)*100);
    return timePoint;
}
public int TransStartPointToTime(int pointx)
{

    int timePoint=(int) ((pointx-150-getStart().getBounds().getX())/(getStart().getWidth()-150)*100);
    return timePoint;
}

public void draw(Graphics2D g2){	
	  //绘制消息的边
	 g2.draw(getConnectionPoints());
	 int edgeStartXpoint=(int)getConnectionPoints().getP1().getX();
	 int edgeEndXpoint=(int)getConnectionPoints().getP2().getX();
	 this.setStarttimePoint(TransStartPointToTime(edgeStartXpoint));
	 this.setEndtimePoint(TransEndPointToTime(edgeEndXpoint));
	
   	
	  //绘制消息结束位置的箭头
	  getEndArrowHead().draw(g2, getConnectionPoints().getP1(), getConnectionPoints().getP2());
	  //drawString,绘制消息上的属性
	  g2.drawString(name,(int)((getConnectionPoints().getX1()+getConnectionPoints().getX2())/2),
			  (int)((getConnectionPoints().getY1()+getConnectionPoints().getY2())/2));
	  double by=this.getConnectionPoints().getY2()-this.getConnectionPoints().getY1();
	  if(by>0){
		  //向下的消息边
		  g2.drawString(Condition,(int)((getConnectionPoints().getX1())-15),
				  (int)(getConnectionPoints().getY1()+30));
	  }else{
		  //向上的消息 
		  g2.drawString(Condition,(int)((getConnectionPoints().getX1())-15),
				  (int)(getConnectionPoints().getY1()-30));  
	  }
	  if(this.getTimeConstraint().length()!=0){			
		if(this.getEnd().getChild()!=null){
			//消息结束点的坐标		
		  Point2D p=new Point2D.Double(this.getConnectionPoints().getX2(),this.getConnectionPoints().getY2());
		  //获取到状态线的list集合
		  List<IHorizontalChild> list= this.getEnd().getChild().gethorizontalChild();
		  	for(IHorizontalChild l:list){
		  		 if(l.getStart().getX()==p.getX())
		  		 {      
		  			double w=0;
		  			w=l.getEnd().getX()-l.getStart().getX();	
		  			g2.drawString("|->",(int)this.getConnectionPoints().getX2(), (int)this.getEnd().getBounds().getY()+10);
		  		    g2.drawString(TimeConstraint,(int)this.getConnectionPoints().getX2()+13, (int)this.getEnd().getBounds().getY()+10);
		  			g2.drawString("<-|", (int)(this.getConnectionPoints().getX2()+w-13), (int)this.getEnd().getBounds().getY()+10);
				 }
			}	
		  	 int endflag=this.getBelongtoEndFlag();		 	
		  	 IHorizontalChild nextTranstate=this.getEnd().getChild().gethorizontalChild().get(endflag+1);
			 String[] states=getEnd().getStates().toString().split("\\|");//获取states
			 int endstateindex=getIndexOfStates((int)nextTranstate.getEnd().getY());
			 if(endstateindex>=0)
			 {
				 this.setEndState(states[endstateindex]);
			 }
			 if(endstateindex<0)
			 {
				 String[] state0=this.getEnd().getState0().toString().split("\\|");
				 this.setEndState(state0[0]);			
			 }
				
		 }  		
	  }
   }

private int getIndexOfStates(int y1) {
	// TODO Auto-generated method stub
	int Index=(int) ((this.getEnd().getBounds().getMaxY()-y1)/40);
	return Index-2;
}  	
}
