package com.horstmann.violet.application.menu.util;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

import com.horstmann.violet.framework.file.IFile;

public class readActivityXMLFromEA {

	private  List<Edge> edgeList=new ArrayList<Edge>();

	private  List<Node> nodeList=new ArrayList<Node>();
	

	public readActivityXMLFromEA(String str,IFile file){
		String aimPath="C:/Users/Administrator/Desktop/ModelDriveProjectFile/ActivityDiagram/EA";
		XMLUtils.AutoSave(str, aimPath,file.getFilename());
		getInformationFormXML(str);
	}
	
	/**
	 * @param document
	 */
	private  void getInformationFormXML(String path) {
		Document document =XMLUtils.load(path);
		Element root=document.getRootElement();//鑾峰彇鏍硅妭鐐�
		Element extension= root.element("Extension");//鑾峰彇Extension鑺傜偣
		List<Element> nodes= extension.element("elements").elements();
		for(Element element:nodes){
			String type=element.attributeValue("type");
			
			if(type.toString().equals("uml:Activity")){
				Node acNode=new Node();
				acNode.setType("ActivityNode");
				acNode.setId(element.attributeValue("idref"));
				acNode.setName(element.attributeValue("name"));
				nodeList.add(acNode);
			}else if(type.toString().equals("uml:Decision")){
				Node dcNode=new Node();
				dcNode.setType("DecisionNode");
				dcNode.setId(element.attributeValue("idref"));
				dcNode.setName(element.attributeValue(" "));
				nodeList.add(dcNode);
			}else if(type.toString().equals("uml:StateNode")){
				Node stNode=new Node();
				if(element.element("properties").attributeValue("nType").equals("101")){
				stNode.setType("ScenarioEndNode");
				}else if(element.element("properties").attributeValue("nType").equals("100")){
					stNode.setType("ScenarioStartNode");
				}
				stNode.setId(element.attributeValue("idref"));
				stNode.setName(element.attributeValue("name"));
				nodeList.add(stNode);
			}else{
				continue;
			}
		}
		
		List<Element> connectors= extension.element("connectors").elements();
//			System.out.println(connectors.size());
		for(Element conn:connectors){
			String id=conn.attributeValue("idref");
			String name=conn.element("labels").attributeValue("mb");
			String type=conn.element("properties").attributeValue("ea_type");
			String starNodeId=conn.element("source").attributeValue("idref");
			String endNodeId=conn.element("target").attributeValue("idref");
			Edge cedge=new Edge();
				cedge.setId(id);
				cedge.setName(name);
				cedge.setType(type);
				cedge.setStarNodeid(starNodeId);
				cedge.setEndNodeid(endNodeId);	
				edgeList.add(cedge);
				
		}
		
	
		List<Element> diagrams =extension.element("diagrams").element("diagram").element("elements").elements();//为元锟截节碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷准锟斤拷
			for(Element diagram:diagrams){
				for(Node node:nodeList){
					String id=diagram.attributeValue("subject");
					if(id!=null&&node.getId().equals(id)){
						String str=diagram.attributeValue("geometry");
						String[] strs=str.split(";");
						int x=Integer.parseInt(XMLUtils.getIndex(strs[0]));
						int y=Integer.parseInt(XMLUtils.getIndex(strs[1]));
						Point p =new Point(x,y);
						node.setLocation(p);	
						int x2=Integer.parseInt(XMLUtils.getIndex(strs[2]));
						int y2=Integer.parseInt(XMLUtils.getIndex(strs[3]));
						Point p2=new Point(x2,y2);
						node.setRightLocation(p2);
					}
				}
			}
	}

	/**
	 * 杩斿洖鑺傜偣鐨刲ist
	 * @return
	 */
	public  List<Node> getNodeList() {
		return nodeList;
	}
	
	/**
	 * 杩斿洖娑堟伅鐨刲ist
	 * @return
	 */
	public  List<Edge> getEdgeList() {
		return edgeList;
	}
}
