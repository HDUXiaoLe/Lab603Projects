package com.horstmann.violet.application.menu.util;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.horstmann.violet.framework.file.IFile;


public class readStateXMLFromEA {

	private  List<Edge> edgeList=new ArrayList<Edge>();

	private  List<Node> nodeList=new ArrayList<Node>();
	

	public readStateXMLFromEA(String url,IFile file){
		String aimPath="C:/Users/Administrator/Desktop/ModelDriveProjectFile/StateDiagram/EA";
		XMLUtils.AutoSave(url, aimPath,file.getFilename());
		getInformationFormXML(url);
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
			if(type.toString().equals("uml:State")){
				Node acNode=new Node();
				acNode.setType("StateNode");
				acNode.setId(element.attributeValue("idref"));
				acNode.setName(element.attributeValue("name"));
				nodeList.add(acNode);
			}else if(type.toString().equals("uml:StateNode")){
				Node stNode=new Node();
				if(element.element("properties").attributeValue("nType").equals("3")){
					stNode.setType("CircularInitialStateNode");
				}else if(element.element("properties").attributeValue("nType").equals("4")){
					stNode.setType("CircularFinalStateNode");
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
			String type=conn.element("properties").attributeValue("ea_type");
			String name=conn.element("extendedProperties").attributeValue("privatedata2");
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
