package com.horstmann.violet.application.gui.util.xiaole;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ReadUppaal {
   List<Location> locationInfo=new ArrayList<Location>();
   List<Transition>transitionInfo=new ArrayList<Transition>();
   HashMap<String,String>transitionsource_Locationid=new HashMap<String, String>();
   HashMap<String,String>transitiontarget_Locationid=new HashMap<String, String>();
   SAXReader reader = new SAXReader();
   int i=1;
   int j=0; 
   String ref_initid;
   public void find() throws DocumentException{
	   Document dom=reader.read("uppaal_Ex1.xml");
	   Element root = dom.getRootElement();
	   Element nodes=root.element("nodes");
	   Element edges=root.element("edges");
	   Element CircularStart=nodes.element("CircularStartNode");
	   List<Element> CircularNodeLocations=nodes.elements("CircularNode");
	   List<Element> transitions=edges.elements("TransitionEdge");
	   int LocationSize=CircularNodeLocations.size()+1;
	   int TransitionSize=transitions.size();
	   for(int temp=0;temp<LocationSize;temp++)
	   {
		   locationInfo.add(new Location());
	   }
	   for(int temp1=0;temp1<TransitionSize;temp1++)
	   {
		   transitionInfo.add(new Transition());
	   }
	      locationInfo.get(0).setID(CircularStart.attribute("id").getValue());
		  locationInfo.get(0).setName(CircularStart.element("name").element("text").getText());
		  locationInfo.get(0).setX(CircularStart.element("location").attribute("x").getValue());
		  locationInfo.get(0).setY(CircularStart.element("location").attribute("y").getValue());
		  ref_initid=CircularStart.attribute("id").getValue();
	   while(i<LocationSize)
	   {
		  
		  locationInfo.get(i).setID(CircularNodeLocations.get(i-1).attribute("id").getValue());
		  locationInfo.get(i).setName(CircularNodeLocations.get(i-1).element("name").element("text").getText());
		  locationInfo.get(i).setX(CircularNodeLocations.get(i-1).element("location").attribute("x").getValue());
		  locationInfo.get(i).setY(CircularNodeLocations.get(i-1).element("location").attribute("y").getValue());
		  i++;	   
	   }
	   while(j<TransitionSize)
	   {
		  transitionInfo.get(j).setLabel_name(transitions.get(j).element("labelText").getText());
		  transitionInfo.get(j).setSource_ref(transitions.get(j).element("start").attribute("reference").getValue());
		  transitionInfo.get(j).setTarget_ref(transitions.get(j).element("end").attribute("reference").getValue());
		  String Startx=transitions.get(j).element("startLocation").attribute("x").getValue();
		  String Endx=transitions.get(j).element("endLocation").attribute("x").getValue();
		  String Starty=transitions.get(j).element("startLocation").attribute("y").getValue();
		  String Endy=transitions.get(j).element("endLocation").attribute("y").getValue();
		  transitionInfo.get(j).setX(String.valueOf(Integer.parseInt(Startx.substring(0,Startx.indexOf(".")))
				  +Integer.parseInt(Endx.substring(0,Endx.indexOf(".")))/2));
		  transitionInfo.get(j).setY(String.valueOf(Integer.parseInt(Starty.substring(0,Starty.indexOf(".")))
				  +Integer.parseInt(Endy.substring(0,Endy.indexOf(".")))/2));	    
		   j++; 
	   }	  
   }
   public void writeUppaal(String filename){
	   Document doc= DocumentHelper.createDocument();
	   Element nta = doc.addElement("nta");
	   Element declaration=nta.addElement("declaration");
	   declaration.setText("//Place global declartions here.");
	   
	   Element template = nta.addElement("template");
	   Element templatename=template.addElement("name");
	   templatename.addAttribute("x", "5");
	   templatename.addAttribute("y", "5");
	   templatename.setText("Template");
	   
	   Element templatedeclaration=template.addElement("declaration");
	   templatedeclaration.setText("//Place local declarations here.");
	  
	  
	   for(Location temp:locationInfo)
	   {
		   Element location=template.addElement("location");
		   location.addAttribute("id", temp.getID());
		   location.addAttribute("x", temp.getX());
		   location.addAttribute("y", temp.getY());
		   Element name=location.addElement("name");
		   name.addAttribute("x", temp.getX());
		   name.addAttribute("y", temp.getY());
		   name.setText(temp.getName());
	   }
	   Element init=template.addElement("init");
	   init.addAttribute("ref",ref_initid );
	   for(Transition temp:transitionInfo){
		   Element transition =template.addElement("transition");
		   Element source=transition.addElement("source");
		   Element target=transition.addElement("target");
		   Element label = transition.addElement("label");
		   source.addAttribute("ref", temp.getSource_ref());
		   target.addAttribute("ref", temp.getTarget_ref());
		   label.addAttribute("kind", temp.label_kind);
		   label.addAttribute("x", temp.getX());
		   label.addAttribute("y", temp.getY());
		   label.setText(temp.getLable_name());
		   
	   }
	   
	   Element system = nta.addElement("system");
	   system.setText("// Place template instantiations here. Process = Template(); // List one or more processes to be composed into a system. system Process;");
	   outputXml(doc, filename);
   }
	   
	   public void outputXml(Document doc, String filename) {
		    try {
		      //定义输出流的目的地
		      FileWriter fw = new FileWriter(filename);
		       
		      //定义输出格式和字符集
		      OutputFormat format 
		        = OutputFormat.createPrettyPrint();
		      format.setEncoding("UTF-8");
		       
		      //定义用于输出xml文件的XMLWriter对象
		      XMLWriter xmlWriter 
		        = new XMLWriter(fw, format);
		      xmlWriter.write(doc);//*****
		      xmlWriter.close(); 
		    } catch (IOException e) {
		      e.printStackTrace();
		    }   
		  }
   
   
}
