package com.horstmann.violet.application.menu.util;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class CreateClassDiagramVioletXML {
	private  List<Edge> edgeList;
	private  List<Node> nodeList;
	private   Map<String, String > map =new HashMap<String, String>();
	private  int l;

	public void create(readClassXMLFromEA rc,String path) throws IOException {
		edgeList=rc.getEdgeList();
		nodeList=rc.getNodeList();
		for (Node n :nodeList) {
			System.out.println(n);
		}
		Document document=createDocument();
//		StringWriter stringWriter=new StringWriter();
		OutputFormat xmlFormat=new OutputFormat();
		xmlFormat.setEncoding("UTF-8");
		xmlFormat.setNewlines(true); 
		xmlFormat.setIndent(true);
		xmlFormat.setIndent("    ");
		XMLWriter xmlWriter=new XMLWriter(new FileWriter(path),xmlFormat);
		xmlWriter.write(document); 	
		xmlWriter.close();
	}
	
	
	private  Document createDocument() {
		//棰滆壊璁剧疆鍥哄畾鐨刬d锛屽悗鏈熷ソ澶勭悊
		String backgroundid="3";
		String borderid="4";
		String textid="5";
		int k =6;
		
		Document document=DocumentHelper.createDocument();	
			Element root=document.addElement("ClassDiagramGraph");
					root.addAttribute(" id","1");
					root.addText("");		
			createNode(backgroundid, borderid, textid, k, root);
			createEdge(root);					
			return document;		
		}

	
	private  void createEdge(Element root) {
		int k;
		
		Element edges =root.addElement("edges").addAttribute("id", l+1+"");
		k=l+2;
		for(int j =0;j<edgeList.size();j++){
			Edge edge=edgeList.get(j);
			String type =edge.getType().trim();
			String bentid=l+"";  
			if(type.equals("Dependency")){
				
				Element DependencyEdge=edges.addElement("DependencyEdge").addAttribute("id",k+"").addText("");
					DependencyEdge.addElement("start").addAttribute("class",XMLUtils.getTypeStr(edge.getStarNodeid(), nodeList))
													  .addAttribute("reference", XMLUtils.getMapId(map, edge.getStarNodeid()));
					DependencyEdge.addElement("end").addAttribute("class",XMLUtils.getTypeStr(edge.getEndNodeid(), nodeList))
													 .addAttribute("reference", XMLUtils.getMapId(map, edge.getEndNodeid()));
					DependencyEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id", k+1+"")
															.addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getX()+""))
															.addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getY()+""));
					
					DependencyEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id", k+2+"")
														  .addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getX()+""))
														  .addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getY()+""));
					DependencyEdge.addElement("id").addAttribute("id", k+3+"");
					if(j==0){
						DependencyEdge.addElement("bentStyle").addAttribute("id", bentid).addAttribute("name", "AUTO");
					}else{
						DependencyEdge.addElement("bentStyle").addAttribute("reference", bentid);
					}
					DependencyEdge.addElement("middleLabel").addText(edge.getName());
					k=k+4;
			}else if(type.equals("Generalization")){
				
				Element InheritanceEdge=edges.addElement("InheritanceEdge").addAttribute("id",k+"").addText("");
					InheritanceEdge.addElement("start").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getStarNodeid()));
					InheritanceEdge.addElement("end").addAttribute("class", "ClassNode").addAttribute("reference",XMLUtils.getMapId(map, edge.getEndNodeid()));
					InheritanceEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id", k+1+"")
															.addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getX()+""))
															.addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getY()+""));
					
					InheritanceEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id", k+2+"")
														  .addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getX()+""))
														  .addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getY()+""));
					InheritanceEdge.addElement("id").addAttribute("id",  k+3+"");
					if(j==0){
						InheritanceEdge.addElement("bentStyle").addAttribute("id", bentid).addAttribute("name", "AUTO");
					}else{
						InheritanceEdge.addElement("bentStyle").addAttribute("reference", bentid);
					}
					InheritanceEdge.addElement("middleLabel").addText(edge.getName());
					k=k+4;
			}else if(type.equals("Aggregation")){
				
				Element AggregationEdge=edges.addElement("AggregationEdge").addAttribute("id",k+"").addText("");
				 	AggregationEdge.addElement("start").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getStarNodeid()));
					AggregationEdge.addElement("end").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getEndNodeid()));
					
					AggregationEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id",  k+1+"")
															.addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getX()+""))
															.addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getY()+""));
					
					AggregationEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id",  k+2+"")
														  .addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getX()+""))
														  .addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getY()+""));
					AggregationEdge.addElement("id").addAttribute("id",  k+3+"");
					if(j==0){
						AggregationEdge.addElement("bentStyle").addAttribute("id", bentid).addAttribute("name", "AUTO");
					}else{
						AggregationEdge.addElement("bentStyle").addAttribute("reference", bentid);
					}
					AggregationEdge.addElement("middleLabel").addText(edge.getName());
					k=k+4;
			}else if(type.equals("Association")){
				
				Element AssociationEdge=edges.addElement("AssociationEdge").addAttribute("id",k+"").addText("");
					AssociationEdge.addElement("start").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getStarNodeid()));
					AssociationEdge.addElement("end").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getEndNodeid()));
					
					AssociationEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id",  k+1+"")
															.addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getX()+""))
															.addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getY()+""));
					
					AssociationEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id", k+2+"")
														  .addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getX()+""))
														  .addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getY()+""));
					
					AssociationEdge.addElement("id").addAttribute("id", k+3+"");
					if(j==0){
						AssociationEdge.addElement("bentStyle").addAttribute("id", bentid).addAttribute("name", "AUTO");
					}else{
						AssociationEdge.addElement("bentStyle").addAttribute("reference", bentid);
					}
					AssociationEdge.addElement("middleLabel").addText(edge.getName());
					k=k+4;
			}else if(type.equals("Composition")){
				
				Element CompositionEdge=edges.addElement("CompositionEdge").addAttribute("id",k+"").addText("");
				 	CompositionEdge.addElement("start").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getStarNodeid()));
					CompositionEdge.addElement("end").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getEndNodeid()));
					CompositionEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id", k+1+"")
															.addAttribute("x",  (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getX()+""))
															.addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getY()+""));
					CompositionEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id", k+2+"")
														  .addAttribute("x",  (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getX()+""))
														  .addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getY()+""));
					CompositionEdge.addElement("id").addAttribute("id",k+3+"");
					if(j==0){
						CompositionEdge.addElement("bentStyle").addAttribute("id", bentid).addAttribute("name", "AUTO");
					}else{
						CompositionEdge.addElement("bentStyle").addAttribute("reference", bentid);
					}
					CompositionEdge.addElement("middleLabel").addText(edge.getName());//锟斤拷息锟斤拷锟斤拷锟�
					k=k+4;
			}else{
				
				Element InterfaceInheritanceEdge=edges.addElement("InterfaceInheritanceEdge").addAttribute("id",k+"").addText("");
					InterfaceInheritanceEdge.addElement("start").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getStarNodeid()));
					InterfaceInheritanceEdge.addElement("end").addAttribute("class", "ClassNode").addAttribute("reference", XMLUtils.getMapId(map, edge.getEndNodeid()));
					
					InterfaceInheritanceEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id", k+1+"")
															.addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getX()+""))
															.addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getY()+""));
					
					InterfaceInheritanceEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id",k+2+"")
														  .addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getX()+""))
														  .addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getY()+""));
					
					InterfaceInheritanceEdge.addElement("id").addAttribute("id", k+3+"");
					if(j==0){
						InterfaceInheritanceEdge.addElement("bentStyle").addAttribute("id", bentid).addAttribute("name", "AUTO");
					}else{
						InterfaceInheritanceEdge.addElement("bentStyle").addAttribute("reference", bentid);
					}
					InterfaceInheritanceEdge.addElement("middleLabel").addText(edge.getName());
					k=k+4;
			}	
		}
	}

	
	private  void createNode(String backgroundid, String borderid,
			String textid, int k, Element root) {
		
		Element nodes=root.addElement("nodes");
			nodes.addAttribute("id", "2");
			nodes.addText("");
			
			for(int i=0 ;i<nodeList.size();i++){
				Node node=nodeList.get(i);
				if(node.getType().trim().equals("uml:Class")){
					
					Element Node =nodes.addElement("ClassNode").addAttribute("id", k+"");
					
					map.put(node.getId(),k+"");
					
						Node.addElement("children")
								.addAttribute("id", k+1+"");
						Node.addElement("location")
								.addAttribute("class", "Point2D.Double")
								.addAttribute("id", k+2+"")
								.addAttribute("x", node.getLocation().getX()+"")
								.addAttribute("y",node.getLocation().getY()+"");
						Node.addElement("id")
								.addAttribute("id", k+3+"");
						if(i==0){
						Element backcolor=Node.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
							backcolor.addElement("red").addText("255");
							backcolor.addElement("green").addText("255");
							backcolor.addElement("blue").addText("255");
							backcolor.addElement("alpha").addText("255");
							
						Element bordercolor=Node.addElement("borderColor").addAttribute("id", borderid).addText("");
							bordercolor.addElement("red").addText("191");
							bordercolor.addElement("green").addText("191");
							bordercolor.addElement("blue").addText("191");
							bordercolor.addElement("alpha").addText("255");
							
						Element textcolor=Node.addElement("textColor").addAttribute("id", textid).addText("");
							textcolor.addElement("red").addText("51");
							textcolor.addElement("green").addText("51");
							textcolor.addElement("blue").addText("51");
							textcolor.addElement("alpha").addText("255");
						}else{
							Element backcolor=Node.addElement("backgroundColor").addAttribute("reference", backgroundid);
							Element bordercolor=Node.addElement("borderColor").addAttribute("reference", borderid);
							Element textcolor=Node.addElement("textColor").addAttribute("reference", textid);
							
						}		
							
						Element name =Node.addElement("name").addAttribute("id", k+4+"");
								name.addElement("text").addText(node.getName());//锟剿达拷锟斤拷锟侥憋拷锟斤拷锟斤拷锟角该节碉拷锟斤拷锟斤拷
						Element attributes =Node.addElement("attributes").addAttribute("id", k+5+"");
						if(node.getAttribute()==null||node.getAttribute().equals("")){
							attributes.addElement("text").addText("null");
						}else{
							attributes.addElement("text").addText("-"+node.getAttribute());
						}
						
						Element methods =Node.addElement("methods").addAttribute("id", k+6+"");
						StringBuffer sb =new StringBuffer(node.getMethod());
							if(node.getMethod()!=null&&node.getMethod()!=""){
								
								sb.append("()");
								methods.addElement("text").addText("+"+sb.toString());
							}else{
								methods.addElement("text").addText(sb.toString());
							}
							
							k=k+7;
							
						
				}else if(node.getType().trim().equals("uml:Interface")){
					
						Element InterfaceNode =nodes.addElement("InterfaceNode").addAttribute("id", k+"");
						map.put(node.getId(), k+"");
							InterfaceNode.addElement("children").addAttribute("id", k+1+"");
								InterfaceNode.addElement("location")
									.addAttribute("class", "Point2D.Double")
									.addAttribute("id", k+2+"")
									.addAttribute("x", node.getLocation().getX()+"")
									.addAttribute("y", node.getLocation().getY()+"");
							
							InterfaceNode.addElement("id").addAttribute("id", k+3+"");
							if(i==0){
								Element backcolor=InterfaceNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
									backcolor.addElement("red").addText("255");
									backcolor.addElement("green").addText("255");
									backcolor.addElement("blue").addText("255");
									backcolor.addElement("alpha").addText("255");
									
								Element bordercolor=InterfaceNode.addElement("borderColor").addAttribute("id", borderid).addText("");
									bordercolor.addElement("red").addText("191");
									bordercolor.addElement("green").addText("191");
									bordercolor.addElement("blue").addText("191");
									bordercolor.addElement("alpha").addText("255");
									
								Element textcolor=InterfaceNode.addElement("textColor").addAttribute("id", textid).addText("");
									textcolor.addElement("red").addText("51");
									textcolor.addElement("green").addText("51");
									textcolor.addElement("blue").addText("51");
									textcolor.addElement("alpha").addText("255");
								}else{
									InterfaceNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
									InterfaceNode.addElement("borderColor").addAttribute("reference",  borderid);
									InterfaceNode.addElement("textColor").addAttribute("reference", textid);
								}
							Element name =InterfaceNode.addElement("name").addAttribute("id", k+4+"");
								name.addElement("text").addText("<?interface> "+node.getName());//锟剿达拷锟斤拷锟侥憋拷锟斤拷锟斤拷锟角该接口节碉拷锟斤拷锟斤拷
							Element methods=InterfaceNode.addElement("methods").addAttribute("id", k+5+"");
								methods.addElement("text").addText(node.getMethod()+" ");//锟剿达拷锟斤拷锟侥憋拷锟斤拷锟斤拷锟角该接口节碉拷姆锟斤拷锟�
							k=k+6;
					}
				l=k;
			}
	}
		
}
