package com.horstmann.violet.application.gui.util.xiaole;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class WriteVioletUppaal {
    
    	SAXReader reader = new SAXReader();
    	List<CircularNode>CircularNodeInfo=new ArrayList<CircularNode>();
    	//节点信息  
    	List<TransitionEdge>TransitionEdgeInfo=new ArrayList<TransitionEdge>();
    	//边的信息 
    	CircularStartNode circularstartnode=new CircularStartNode();
    	//初始的节点信息
    	int i=0; 
    	int j=0;
    	public void find() throws DocumentException{
    		Document dom=reader.read("stabilize_run.xml");
    		Element root =dom.getRootElement();
    		Element template=root.element("template");
    		List<Element>locations=template.elements("location");
    		List<Element>transitions=template.elements("transition");
    		Element init =template.element("init");
    		String id=locations.get(0).attributeValue("id");//默认初始节点是第一个
    		circularstartnode.SetID(id);
    		circularstartnode.SetChilrden_id(UUID.randomUUID().toString());//这里随便设置ID
    		circularstartnode.SetLocation_id(UUID.randomUUID().toString());
    		circularstartnode.SetLocation_x(locations.get(0).attributeValue("x"));
    		circularstartnode.SetLocation_y(locations.get(0).attributeValue("y"));
    		circularstartnode.setUnderlocation_id(UUID.randomUUID().toString());
    		circularstartnode.SetName_id(UUID.randomUUID().toString());
    	
    		circularstartnode.SetText(locations.get(0).element("name").getText());
    		//给起点设置属性
    		//给其余的点设置属性
    		for(Element temp : locations.subList(1, locations.size())){
    			CircularNodeInfo.add(new CircularNode());
    			String id1=temp.attributeValue("id");
    			//locations包含着起点
    		   	CircularNodeInfo.get(i).SetID(id1);
    		   	CircularNodeInfo.get(i).SetChilrden_id(UUID.randomUUID().toString());
    		   	CircularNodeInfo.get(i).SetLocation_id(UUID.randomUUID().toString());
    		   	CircularNodeInfo.get(i).SetLocation_x(temp.attributeValue("x"));
    		   	CircularNodeInfo.get(i).SetLocation_y(temp.attributeValue("y"));
    		   	CircularNodeInfo.get(i).setUnderlocation_id(UUID.randomUUID().toString());
    		   	CircularNodeInfo.get(i).SetName_id(UUID.randomUUID().toString());
    		   	CircularNodeInfo.get(i).SetText(temp.element("name").getText());
    			i++;
    		}
    		for(Element temp : transitions){
    			TransitionEdgeInfo.add(new TransitionEdge());   	
    			TransitionEdgeInfo.get(j).setId(UUID.randomUUID().toString());
    			TransitionEdgeInfo.get(j).setStart_reference(temp.element("source").attributeValue("ref"));
    			TransitionEdgeInfo.get(j).setEnd_reference(temp.element("target").attributeValue("ref"));
    			TransitionEdgeInfo.get(j).setStartlocation_id(UUID.randomUUID().toString());
    			TransitionEdgeInfo.get(j).setStartlocation_x(temp.element("label").attributeValue("x"));
    			TransitionEdgeInfo.get(j).setStartlocation_y(temp.element("label").attributeValue("y"));
    			TransitionEdgeInfo.get(j).setEndlocation_id(UUID.randomUUID().toString());
    			TransitionEdgeInfo.get(j).setEndlocation_x(temp.element("label").attributeValue("x"));
    			TransitionEdgeInfo.get(j).setEndlocation_y(temp.element("label").attributeValue("y"));
    			TransitionEdgeInfo.get(j).setUnderEndLocation_id(UUID.randomUUID().toString());
    			TransitionEdgeInfo.get(j).setLabelText(temp.element("label").getText());
    			if(temp.element("source").attributeValue("ref").equals(id)){
    				TransitionEdgeInfo.get(j).setStartclass("CircularStartNode");
    				TransitionEdgeInfo.get(j).setEndlclass("CircularNode");
    			}
    			else{
    				TransitionEdgeInfo.get(j).setStartclass("CircularNode");
    				TransitionEdgeInfo.get(j).setEndlclass("CircularNode");
    				}
    			j++;
    		}
    	}	
    public void writeVioletUppaal(String filename){
    	Document doc = DocumentHelper.createDocument();
    	Element UppaalGraph=doc.addElement("UppaalGraph");
    	UppaalGraph.addAttribute("id", "1");
    	Element nodes=UppaalGraph.addElement("nodes");
    	nodes.addAttribute("id", "2");
    	//处理StartNode
    	Element CircularStartNode=nodes.addElement("CircularStartNode");
    	CircularStartNode.addAttribute("id", circularstartnode.GetId());
    	Element children=CircularStartNode.addElement("children");
    	children.addAttribute("id", circularstartnode.GetChilren_id());
    	Element location=CircularStartNode.addElement("location");
    	location.addAttribute("class", "Point2D.Double").addAttribute("id", circularstartnode.GetLocation_id());
    	location.addAttribute("x", circularstartnode.GetLocation_x()).addAttribute("y", circularstartnode.GetLocation_y());
    	Element id = CircularStartNode.addElement("id");
    	id.addAttribute("id", circularstartnode.getUnderlocation_id());
    	Element name=CircularStartNode.addElement("name");
    	name.addAttribute("id", circularstartnode.GetName_id());
    	setColor(CircularStartNode);
    	name.setText(circularstartnode.GetText());
    	//处理剩下节点
    	for(CircularNode temp :CircularNodeInfo){
    		Element CircularNode=nodes.addElement("CircularNode");
    		CircularNode.addAttribute("id", temp.GetId());
    		Element children1=CircularNode.addElement("children");
    		children1.addAttribute("id", temp.GetChilren_id());
    		Element location1=CircularNode.addElement("location");
        	location1.addAttribute("class", "Point2D.Double").addAttribute("id", temp.GetLocation_id());
        	location1.addAttribute("x",temp.GetLocation_x()).addAttribute("y", temp.GetLocation_y());
        	Element id1 = CircularNode.addElement("id");
        	id1.addAttribute("id", temp.getUnderlocation_id());
        	Element name1=CircularNode.addElement("name");
        	name.addAttribute("id", temp.GetName_id());
        	setColor(CircularNode);
        	name1.setText(temp.GetText());
    	}
    	Element edges=UppaalGraph.addElement("edges");
    	edges.addAttribute("id", "100");
    	for(TransitionEdge temp: TransitionEdgeInfo){
    		Element TransitionEdge = edges.addElement("TransitionEdge");
    		TransitionEdge.addAttribute("id", temp.getId());
    		Element start=TransitionEdge.addElement("start");
    		start.addAttribute("class",temp.getStartclass() ).addAttribute("reference", temp.getStart_reference());
    		Element end=TransitionEdge.addElement("end");
    		end.addAttribute("class",temp.getEndlclass() ).addAttribute("reference", temp.getEnd_reference());
    		Element startLocation = TransitionEdge.addElement("startLocation");
    		startLocation.addAttribute("class", "Point2D.Double").addAttribute("id", temp.getStartlocation_id());
    		startLocation.addAttribute("x", temp.getStartlocation_x());
    		startLocation.addAttribute("y", temp.getStartlocation_y());
    		Element endLocation = TransitionEdge.addElement("endLocation");
    		endLocation.addAttribute("class", "Point2D.Double").addAttribute("id", temp.getEndlocation_id());
    		endLocation.addAttribute("x", temp.getEndlocation_x());
    		endLocation.addAttribute("y", temp.getEndlocation_y());
    		Element id3=TransitionEdge.addElement("id");
    		id3.addAttribute("id", temp.getUnderEndLocation_id());
    		Element angle=TransitionEdge.addElement("angle");
    		angle.setText("10.0");
    		Element labelText=TransitionEdge.addElement("labelText");
    		labelText.setText(temp.getLabelText());
    	}
    	outputXml(doc,filename);
    }	
    public void setColor(Element Node)
	{
	Element backgroundColor=Node.addElement("backgroundColor");
	backgroundColor.addAttribute("id",UUID.randomUUID().toString());
	Element red =backgroundColor.addElement("red");
	red.addText("255");
	Element green =backgroundColor.addElement("green");
	green.addText("255");
	Element blue=backgroundColor.addElement("blue");
	blue.addText("255");
	Element alpha =backgroundColor.addElement("alpha");
	alpha.addText("255");
	Element borderColor=Node.addElement("borderColor");
	borderColor.addAttribute("id",UUID.randomUUID().toString());
	Element red1 =borderColor.addElement("red");
	red1.addText("191");
	Element green1 =borderColor.addElement("green");
	green1.addText("191");
	Element blue1=borderColor.addElement("blue");
	blue1.addText("191");
	Element alpha1 =borderColor.addElement("alpha");
	alpha1.addText("255");
	
	}			
	public void outputXml(Document doc, String filename) {
	    try {
	      //定义输出流的目的地
	      FileWriter fw = new FileWriter(filename);
	       
	      //定义输出格式和字符集
	      OutputFormat format 
	        = OutputFormat.createPrettyPrint();
	     // format.setEncoding("windows-1252");
	       
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

