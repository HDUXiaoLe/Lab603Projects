package com.horstmann.violet.application.gui.util.xiaole;

public class Location {
  private String id;
  private String x;
  private String y;
  private String name;
  private String init_ref;
  public void setInit_ref(String init_ref)
  {
	  this.init_ref=init_ref;
  }
  public String getInit_ref()
  {
	  return init_ref;
  }
  public void setID(String id)
  {
	  this.id=id;
  }
  public String getID()
  {
	  return id;
  }
  public void setX(String x)
  {
	  this.x=x;
  }
  public String getX()
  {
	  return x;
  }
  public void setY(String y)
  {
	  this.y=y;  
  }
  public String getY()
  {
	  return y;
  }
  public void setName(String name)
  {
	  this.name=name;
  }
  public String getName()
  {
	  return name;
  }
}
