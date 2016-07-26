package com.horstmann.violet.application.gui.util.xiaole;

import javax.swing.UIDefaults.LazyValue;

public class Transition {
 private String source_ref;
 private String target_ref;
 private String label_name;
 private String x;
 private String y;
 public final String label_kind="synchronisation";
 public void setSource_ref(String source_ref)
 {
	 this.source_ref=source_ref;
 }
 public String getSource_ref()
 {
	 return source_ref;
 }
 public void setTarget_ref(String target_ref)
 {
	 this.target_ref=target_ref;
 }
 public String getTarget_ref()
 {
	return target_ref; 
 }
 public void setLabel_name(String label_name)
 {
	 this.label_name=label_name;
 }
 public String getLable_name()
 {
	 return label_name;
 }
 public void setX(String x)
 {
	 this.x=x;
 }
 public String getX(){
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
}
