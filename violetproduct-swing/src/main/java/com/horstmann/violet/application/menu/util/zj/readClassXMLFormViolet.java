package com.horstmann.violet.application.menu.util.zj;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

public class readClassXMLFormViolet {
	public readClassXMLFormViolet(String url){
		getInformationFormXML(url);
	}

	private  void getInformationFormXML(String path) {
		Document d =XMLUtils.load(path);
		Element root=d.getRootElement();
		List<Element> nodes =root.element("nodes").elements();	
		
		
			for(Element node:nodes){
				String NodeType=node.getName().toLowerCase();
				String NodeID=node.attributeValue("id");
				Element location=node.element("location");
				Node u=new Node();
				String ClassType=location.attributeValue("class");
				if(NodeType.equals("classnode")){
					NodeType= "uml:Class";
				}else if(NodeType.equals("interfacenode")){
					NodeType= "uml:Interface";
				}
				String Loc_X=location.attributeValue("x");
				String Loc_Y=location.attributeValue("y");
				Point p =new Point((int)Double.parseDouble(Loc_X),(int)Double.parseDouble(Loc_Y));
				String  NodeName =node.element("name").element("text").getText();
					u.setId(NodeID);
					u.setName(NodeName);
					u.setType(NodeType);
					u.setLocation(p);
				if(node.element("attributes")!=null){
					String attribute=node.element("attributes").element("text").getText();
					u.setAttribute(attribute);
				}
				if(node.element("methods")!=null){
					String method=node.element("methods").element("text").getText();
					u.setMethod(method);
				}
					nodeList.add(u);
			}
		
		List<Element> edges =root.element("edges").elements();
			for(Element edge :edges){
				
				String edgeID =edge.attributeValue("id");
				
				String edgeType =edge.getName();
				String str =edgeType.substring(0, edgeType.length()-4);
				if(str.equals("Inheritance")){
					str="Generalization";
				}else if(str.equals("InterfaceInheritance")){
					str="Realisation";
				}
				String edgeName=edge.element("middleLabel").getText();
				
				String starNodeID=edge.element("start").attributeValue("reference");
				
				String endNodeID=edge.element("end").attributeValue("reference");//����ڵ��id
				
				Edge e=new Edge();
				e.setId(edgeID);
				
				e.setType(str);
				e.setName(edgeName);
				e.setStarNodeid(starNodeID);
				e.setEndNodeid(endNodeID);
				edgeList.add(e);
			}

			
	}
	
	
	public  List<Node> getNodeList() {
		return nodeList;
	}
	public  List<Edge> getEdgeList() {
		return edgeList;
	}
	private   List<Node> nodeList=new ArrayList<Node>();
	private  List<Edge> edgeList= new ArrayList<Edge>();	

}
