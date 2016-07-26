package com.horstmann.violet.application.gui.util.xiaole;

public class CircularStartNode {
	  private String id;
	  private String name_id;
	  private String chilrden_id;
	  private String location_id;
	  private String location_x;
	  private String location_y;
	  private String underlocation_id;
	  private String text;
	  
	  public String getUnderlocation_id() {
		return underlocation_id;
	}
	public void setUnderlocation_id(String underlocation_id) {
		this.underlocation_id = underlocation_id;
	}
	public void SetID(String id)
	  {
		  this.id=id;
	  }
	  public String GetId()
	  {
		  return id;
	  }
	  public void SetName_id(String name_id)
	  {
		  this.name_id=name_id;
	  }
	  public String GetName_id()
	  {
		  return name_id;
	  }
	  public void SetChilrden_id(String chilrden_id)
	  {
		  this.chilrden_id=chilrden_id;
	  }
	  public String GetChilren_id()
	  {
		  return chilrden_id;
	  }
	  public void SetLocation_id(String location_id)
	  {
		  this.location_id=location_id;
	  }
	  public String GetLocation_id()
	  {
		  return location_id;  
	  }
	  public void SetLocation_x(String location_x)
	  {
		  this.location_x=location_x;
	  }
	  public String GetLocation_x()
	  {
		  return location_x;
	  }
	  public void SetLocation_y(String location_y)
	  {
		  this.location_y=location_y;
	  }
	  public String GetLocation_y()
	  {
		  return location_y;
	  }
	  public void SetText(String text)
	  {
		  this.text=text;
	  }
	  public String GetText()
	  {
		  return text;
	  }
	}


