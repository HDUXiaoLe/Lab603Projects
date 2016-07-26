package com.horstmann.violet.application.menu.util;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
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

public class CreateStateDiagramVioletXML {
	private  List<Edge> edgeList;
	private  List<Node> nodeList;
	private   Map<String, String > map =new HashMap<String, String>();
	private  int l;

	public void create(readStateXMLFromEA ra,String path) throws IOException {
		nodeList=ra.getNodeList();
		edgeList=ra.getEdgeList();
		Document document=createDocument();
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
		
		String backgroundid="3";
		String borderid="4";
		String textid="5";
		int k =9;
		Document document=DocumentHelper.createDocument();	
			Element root=document.addElement("StateDiagramGraph");
					root.addAttribute(" id","1");
					root.addText("");		
			createNode(backgroundid, borderid, textid, k, root);
			createEdge(root);					
			return document;		
		}

	/**
	 * 创建edge节点
	 * @param root
	 */
	private  void createEdge(Element root) {
		int k;//定义k来计算转换成平台xml时的id�?
		Element edges =root.addElement("edges").addAttribute("id", l+1+"");
		k=l+2;
		for(int j =0;j<edgeList.size();j++){
			Edge edge=edgeList.get(j);
			int bentid=6;  
				Element ActivityTransitionEdge=edges.addElement("StateTransitionEdge").addAttribute("id",k+"").addText("");
					ActivityTransitionEdge.addElement("start").addAttribute("class", XMLUtils.getNode(edge.getStarNodeid(),nodeList).getType()).addAttribute("reference", XMLUtils.getMapId(map, edge.getStarNodeid()));
					ActivityTransitionEdge.addElement("end").addAttribute("class", XMLUtils.getNode(edge.getEndNodeid(),nodeList).getType()).addAttribute("reference", XMLUtils.getMapId(map, edge.getEndNodeid()));
					
					ActivityTransitionEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id", k+1+"")
															.addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getX()+""))
															.addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(0).getY()+""));
					
					ActivityTransitionEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id",k+2+"")
														  .addAttribute("x", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getX()+""))
														  .addAttribute("y", (XMLUtils.getEdgePontionList(edge, nodeList).get(1).getY()+""));
					
					ActivityTransitionEdge.addElement("id").addAttribute("id", k+3+"");
					ActivityTransitionEdge.addElement("angle").addText("10.0");
					ActivityTransitionEdge.addElement("labelText").addText(edge.getName());
					k=k+4;
			}	
		}


	/**
	 * 创建node节点
	 * @param root
	 */
	private  void createNode(String backgroundid, String borderid,
			String textid, int k, Element root) {
		
		Element nodes=root.addElement("nodes");
			nodes.addAttribute("id", "2");
			nodes.addText("");
			
			for(int i=0 ;i<nodeList.size();i++){
				Node node=nodeList.get(i);
				if(node.getType().trim().equals("StateNode")){
					
					Element ActivityNode =nodes.addElement(node.getType()).addAttribute("id", k+"");
					
					map.put(node.getId(),k+"");
					
					ActivityNode.addElement("children")
								.addAttribute("id", k+1+"");
					ActivityNode.addElement("location")
								.addAttribute("class", "Point2D.Double")
								.addAttribute("id", k+2+"")
								.addAttribute("x", node.getLocation().getX()+"")
								.addAttribute("y",node.getLocation().getY()+"");
					ActivityNode.addElement("id")
								.addAttribute("id", k+3+"");
						if(i==0){
						Element backcolor=ActivityNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
							backcolor.addElement("red").addText("255");
							backcolor.addElement("green").addText("255");
							backcolor.addElement("blue").addText("255");
							backcolor.addElement("alpha").addText("255");
							
						Element bordercolor=ActivityNode.addElement("borderColor").addAttribute("id", borderid).addText("");
							bordercolor.addElement("red").addText("191");
							bordercolor.addElement("green").addText("191");
							bordercolor.addElement("blue").addText("191");
							bordercolor.addElement("alpha").addText("255");
							
						Element textcolor=ActivityNode.addElement("textColor").addAttribute("id", textid).addText("");
							textcolor.addElement("red").addText("51");
							textcolor.addElement("green").addText("51");
							textcolor.addElement("blue").addText("51");
							textcolor.addElement("alpha").addText("255");
						}else{
							Element backcolor=ActivityNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
							Element bordercolor=ActivityNode.addElement("borderColor").addAttribute("reference", borderid);
							Element textcolor=ActivityNode.addElement("textColor").addAttribute("reference", textid);
							
						}		
							
						Element name =ActivityNode.addElement("name").addAttribute("id", k+4+"");
								name.addElement("text").addText(node.getName());//节点的name属�?	
								k=k+5;
						
				}else if(node.getType().trim().equals("CircularFinalStateNode")||node.getType().trim().equals("CircularInitialStateNode")){
						Element DecisionNode =nodes.addElement(node.getType()).addAttribute("id", k+"");
						map.put(node.getId(), k+"");
							DecisionNode.addElement("children").addAttribute("id", k+1+"");
								DecisionNode.addElement("location")
									.addAttribute("class", "Point2D.Double")
									.addAttribute("id", k+2+"")
									.addAttribute("x", node.getLocation().getX()+"")
									.addAttribute("y", node.getLocation().getY()+"");
							
							DecisionNode.addElement("id").addAttribute("id", k+3+"");
							if(i==0){
								Element backcolor=DecisionNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
									backcolor.addElement("red").addText("255");
									backcolor.addElement("green").addText("255");
									backcolor.addElement("blue").addText("255");
									backcolor.addElement("alpha").addText("255");
									
								Element bordercolor=DecisionNode.addElement("borderColor").addAttribute("id", borderid).addText("");
									bordercolor.addElement("red").addText("191");
									bordercolor.addElement("green").addText("191");
									bordercolor.addElement("blue").addText("191");
									bordercolor.addElement("alpha").addText("255");
									
								Element textcolor=DecisionNode.addElement("textColor").addAttribute("id", textid).addText("");
									textcolor.addElement("red").addText("51");
									textcolor.addElement("green").addText("51");
									textcolor.addElement("blue").addText("51");
									textcolor.addElement("alpha").addText("255");
								}else{
									DecisionNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
									DecisionNode.addElement("borderColor").addAttribute("reference",  borderid);
									DecisionNode.addElement("textColor").addAttribute("reference", textid);
								}
							k=k+4;
					}
				l=k;
			}
	}
		
}
