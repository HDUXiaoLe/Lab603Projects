package com.horstmann.violet.application.menu.util.zj;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.dom4j.Document;
import org.dom4j.Element;

public class readStateXMLFormViolet {
	

	private   List<Node> nodeList=new ArrayList<Node>();

	private  List<Edge> edgeList= new ArrayList<Edge>();
	
	public readStateXMLFormViolet(String url){
		
		getInformationFormXML(url);
	}
	
	private  void getInformationFormXML(String url) {
		Document d =XMLUtils.load(url);
		Element root=d.getRootElement();
		List<Element> nodes =root.element("nodes").elements();	
		int i =1;
			for(Element node:nodes){
				String  NodeName=" ";
				String NodeType=node.getName();
				String NodeID=node.attributeValue("id");
				Element location=node.element("location");
					String ClassType=location.attributeValue("class");
					String X=location.attributeValue("x").toString();
					String Y=location.attributeValue("y").toString();
					if(node.element("name")!=null){
						NodeName =node.element("name").element("text").getText();
					}
					
					Node an=new Node();
					an.setId(NodeID);
					
					if(NodeType.equals("CircularInitialStateNode")){
						NodeType="uml:Pseudostate";
						an.setName("start"+i);
						i++;
					}else if(NodeType.equals("StateNode")){
						NodeType="uml:State";
						an.setName(NodeName);
					}else if(NodeType.equals("CircularFinalStateNode")){
						NodeType="uml:FinalState";
						an.setName("end"+i);
						i++;
					}
					an.setType(NodeType);
//					System.out.println(X+":"+Y);
					int l=(int)Double.parseDouble(X);
					int r =(int)Double.parseDouble(Y);
					an.setLocation(new Point(l,r));
					nodeList.add(an);
			}
		
			//遍历edges节点
		List<Element> edges =root.element("edges").elements();
			for(Element edge :edges){
				String edgeID =edge.attributeValue("id");
			
				String edgeName=edge.element("labelText").getText();
				String starNodeID=edge.element("start").attributeValue("reference");
				String endNodeID=edge.element("end").attributeValue("reference");
				String edgeType =edge.getName().toString();
				Edge ae=new Edge();
				ae.setId(edgeID);
				ae.setType(edgeType);
				ae.setName(edgeName);
				ae.setStarNodeid(starNodeID);
				ae.setEndNodeid(endNodeID);
				
				edgeList.add(ae);
			}
	}

	public  List<Node> getNodeList() {
		return nodeList;
	}

	public  List<Edge> getEdgeList() {
		return edgeList;
	}

	
}
