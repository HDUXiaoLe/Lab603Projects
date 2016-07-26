package com.horstmann.violet.application.menu.util;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

import com.horstmann.violet.framework.file.IFile;
public class readClassXMLFromEA {

	private  List<Edge> edgeList=new ArrayList<Edge>();

	private  List<Node> nodeList=new ArrayList<Node>();
	
	public readClassXMLFromEA(String path,IFile file){
		String aimPath="C:/Users/Administrator/Desktop/ModelDriveProjectFile/ClassDiagram/EA";
		XMLUtils.AutoSave(path, aimPath,file.getFilename());
		System.out.println("绗簩涓偣");
		getInformationFormXML(path);
	}
//	public  readClassXMLFromEA(String path){
//		getInformationFormXML(path);
//	}
//	public static void main(String[] args) {
//		readClassXMLFromEA r =new readClassXMLFromEA("C:\\Users\\Administrator\\Desktop\\EA111112.class.xml");
//		List e=r.getNodeList();
//		for (Edge ed:r.getEdgeList()) {
//			System.out.println(ed);
//		}
//		for(Node n :r.getNodeList()){
//			System.out.println(n);
//		}
//	}
	/**
	 * @param document
	 */
	private  void getInformationFormXML(String path) {
		System.out.println("read閲岄潰鐨勶細"+path);
		Document document =XMLUtils.load(path);
		
		Element root=document.getRootElement();//鑾峰彇鏍硅妭鐐�
		System.out.println("鏍硅妭鐐癸細"+root.getName());
		
		Element Model=root.element("Model");
	
		List<Element> packagedElement=Model.element("packagedElement").elements();
	
		for(Element element:packagedElement){
			
			String type=element.attributeValue("type");
			 if(type.toString().equals("uml:Class")){
				String attribute="";
				String method="";
				List<Element> l=element.elements();
				for(Element attr:l){
					if(attr.getName().equals("ownedOperation")){
						if(attribute.equals("")){
							attribute=attr.attributeValue("name");
						}else{
							attribute=attribute+","+attr.attributeValue("name");
						}
					}else if(attr.getName().equals("ownedAttribute")){
						if(attribute.equals("")){method=attr.attributeValue("name");
						}else{
							method=method+","+attr.attributeValue("name");
						}
					}
				}
				Node an=new Node();
				an.setType(type);
				an.setId(element.attributeValue("id"));
				an.setName(element.attributeValue("name"));
//				an.setAttribute(attribute);
//				an.setMethod(method);
				an.setAttribute(method);
				an.setMethod(attribute);
				
				nodeList.add(an);
			}else if(type.toString().equals("uml:Interface")){//Interface锟接口节碉拷锟斤拷锟斤拷锟�
				Node an=new Node();
				an.setType(type);
				an.setId(element.attributeValue("id"));
				an.setName(element.attributeValue("name"));
				nodeList.add(an);
			}else {
				continue;
			}
			
		}
		
	
		Element extension= root.element("Extension");//锟斤拷取锟斤拷EA锟斤拷Extension锟节碉拷
		List<Element> connectors= extension.element("connectors").elements();
//			System.out.println(connectors.size());
		for(Element conn:connectors){
			String id=conn.attributeValue("idref");
			String name=conn.element("labels").attributeValue("mt");
			String starNodeId=conn.element("source").attributeValue("idref");
			String endNodeId=conn.element("target").attributeValue("idref");
			String ea_type=conn.element("properties").attributeValue("ea_type");
			String subtype=conn.element("properties").attributeValue("subtype");
			String direction=conn.element("properties").attributeValue("direction");
			Edge cedge=new Edge();
			
				cedge.setId(id);
				cedge.setName(name);
				cedge.setStarNodeid(starNodeId);
				cedge.setEndNodeid(endNodeId);
				
				if(ea_type.trim().equals("Aggregation")){
					if(subtype.trim().equals("Strong")){
						String t1="Composition";
						cedge.setType(t1);
//					}else if(subtype.trim().equals("Weak")&&direction.equals("Source -&gt; Destination")){
//						String t2="InterfaceInheritance";
//						cedge.setType(t2);
					}else{
						cedge.setType("Aggregation");
					}
				}else{
					
					cedge.setType(ea_type);
				}
				
				edgeList.add(cedge);
				
		}
		
	
		List<Element> diagrams =extension.element("diagrams").element("diagram").element("elements").elements();
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
