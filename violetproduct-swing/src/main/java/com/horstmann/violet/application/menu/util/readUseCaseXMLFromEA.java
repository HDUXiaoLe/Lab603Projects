package com.horstmann.violet.application.menu.util;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.horstmann.violet.framework.file.IFile;

public class readUseCaseXMLFromEA{

	public readUseCaseXMLFromEA(String url,IFile file){
		String aimPath="C:/Users/Administrator/Desktop/ModelDriveProjectFile/UseCaseDiagram/EA";
		XMLUtils.AutoSave(url, aimPath,file.getFilename());
		getInformationFormXML(url);
	}

	private  void getInformationFormXML(String url) {
		Document document =XMLUtils.load(url);
		Element root=document.getRootElement();
		Element Model=root.element("Model");
		List<Element> packagedElement=Model.element("packagedElement").elements();
	
		for(Element element:packagedElement){
			String type=element.attributeValue("type");
			if(type.toString().equals("uml:Actor")||type.toString().equals("uml:UseCase")){
				Node an=new Node();
				if(type.toString().equals("uml:Actor")){
					an.setType("ActorNode");
				}else{
					an.setType("UseCaseNode");
				}
				
				an.setId(element.attributeValue("id"));
				an.setName(element.attributeValue("name"));
				nodeList.add(an);
			}else{
				Edge edge=new Edge();
				edge.setType(type);
				edge.setId(element.attributeValue("id"));
				edge.setName(element.attributeValue("name"));
				edgeList.add(edge);	
			}
		}
		
		
		Element extension= root.element("Extension");
		List<Element> connectors= extension.element("connectors").elements();
		for(Element conn:connectors){
			boolean flag=SelectEdge(conn.attributeValue("idref"),edgeList);
			if(flag){
				
				Edge edge=new Edge();
				edge.setId(conn.attributeValue("idref"));
				edge.setName(conn.attributeValue("name"));
				edge.setType("Inheritance");
				edgeList.add(edge);
			}	
			
		}
		List<Element> elements =extension.element("elements").elements();
			for(Element element:elements){
				if(element.element("links")!=null){
					List<Element> links=element.element("links").elements();
					if(links!=null){
						for(Element link:links){
							String id=link.attributeValue("id");
							 for(Edge l:edgeList){
								 if(l.getId().trim().equals(id)){
									 l.setStarNodeid(link.attributeValue("start"));
									 l.setStarNodeType( getNodeType(link.attributeValue("start"), nodeList));
									 l.setEndNodeid(link.attributeValue("end"));
									 l.setEndNodeType(getNodeType(link.attributeValue("end"), nodeList));
								 }
							 }
						}
					}
				}	
			}
			
		List<Element> diagrams =extension.element("diagrams").element("diagram").element("elements").elements();//ΪԪ�ؽڵ�����������׼��
			for(Element diagram:diagrams){
				for(Node node:nodeList){
					String id=diagram.attributeValue("subject");
					if(id!=null&&node.getId().equals(id)){
						String str=diagram.attributeValue("geometry");
						String[] strs=str.split(";");
//						
						int x =Integer.parseInt(XMLUtils.getIndex(strs[0]));
						int y =Integer.parseInt(XMLUtils.getIndex(strs[1]));
						Point p =new Point(x,y);
						node.setLeftLocation(p);
						int x2=Integer.parseInt(XMLUtils.getIndex(strs[2]));
						int y2=Integer.parseInt(XMLUtils.getIndex(strs[3]));
						Point p2=new Point(x2,y2);
						node.setRightLocation(p2);
					}
				}
				
			}
	}
	
	
	
	private  boolean SelectEdge(String id,List<Edge> list){
		for(Edge edge:list){
			String uid=edge.getId();
			if(id.equals(uid)){
				return false;
			}
		}
		return true;
	}
	
	private  String getNodeType(String id,List<Node> list){
		for(Node user:list){
			String uid=user.getId();
			if(id.equals(uid)){
				return user.getType().toString();
			}
		}
		return null;
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
