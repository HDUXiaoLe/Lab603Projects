package com.horstmann.violet.application.menu.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class CreateActivityDiagramVioletXML {
	private  List<Edge> edgeList;
	private  List<Node> nodeList;
	private   Map<String, String > map =new HashMap<String, String>();
	private  int l;

	public  void create(readActivityXMLFromEA ra,String path) throws IOException {
		edgeList=ra.getEdgeList();
		nodeList=ra.getNodeList();
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
			Element root=document.addElement("ActivityDiagramGraph");
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
				Element ActivityTransitionEdge=edges.addElement("ActivityTransitionEdge").addAttribute("id",k+"").addText("");
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
					
					if(j==0){
						ActivityTransitionEdge.addElement("lineStyle").addAttribute("id", bentid+"").addAttribute("name", "SOLID");
						ActivityTransitionEdge.addElement("startArrowHead").addAttribute("id", bentid+1+"").addAttribute("name", "NONE");
						ActivityTransitionEdge.addElement("bentStyle").addAttribute("id", bentid+2+"").addAttribute("name", "AUTO");
					}else{
						ActivityTransitionEdge.addElement("lineStyle").addAttribute("reference", bentid+"");
						ActivityTransitionEdge.addElement("startArrowHead").addAttribute("reference", bentid+1+"");
						ActivityTransitionEdge.addElement("bentStyle").addAttribute("reference", bentid+2+"");
					}
					ActivityTransitionEdge.addElement("middleLabel").addText(edge.getName());
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
				if(node.getType().trim().equals("ActivityNode")){
					
					Element ActivityNode =nodes.addElement("ActivityNode").addAttribute("id", k+"");
					
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
						
				}else if(node.getType().trim().equals("DecisionNode")){
					
				//创建DecisionNode节点
						Element DecisionNode =nodes.addElement("DecisionNode ").addAttribute("id", k+"");
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
							Element name =DecisionNode.addElement("condition").addAttribute("id", k+4+"");
								name.addElement("text").addText(node.getName()+"");//创建condition属�?
							
							k=k+5;
					}else if(node.getType().trim().equals("ScenarioEndNode")||node.getType().trim().equals("ScenarioStartNode")){
						
						//创建StateNode节点
								Element StateNode =nodes.addElement(node.getType().trim()).addAttribute("id", k+"");
								map.put(node.getId(), k+"");
								StateNode.addElement("children").addAttribute("id", k+1+"");
								StateNode.addElement("location")
											.addAttribute("class", "Point2D.Double")
											.addAttribute("id", k+2+"")
											.addAttribute("x", node.getLocation().getX()+"")
											.addAttribute("y", node.getLocation().getY()+"");
									
								StateNode.addElement("id").addAttribute("id", k+3+"");
									if(i==0){
										Element backcolor=StateNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
											backcolor.addElement("red").addText("255");
											backcolor.addElement("green").addText("255");
											backcolor.addElement("blue").addText("255");
											backcolor.addElement("alpha").addText("255");
											
										Element bordercolor=StateNode.addElement("borderColor").addAttribute("id", borderid).addText("");
											bordercolor.addElement("red").addText("191");
											bordercolor.addElement("green").addText("191");
											bordercolor.addElement("blue").addText("191");
											bordercolor.addElement("alpha").addText("255");
											
										Element textcolor=StateNode.addElement("textColor").addAttribute("id", textid).addText("");
											textcolor.addElement("red").addText("51");
											textcolor.addElement("green").addText("51");
											textcolor.addElement("blue").addText("51");
											textcolor.addElement("alpha").addText("255");
										}else{
											StateNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
											StateNode.addElement("borderColor").addAttribute("reference",  borderid);
											StateNode.addElement("textColor").addAttribute("reference", textid);
										}					
									k=k+4;
							}
				l=k;
			}
	}
	
	
	
	/**
	 * 根据id获取location的�?
	 * @param id
	 * @param list
	 * @return
	 */
//	private static Point getLocationPoint(String id,List<Node> list){
//		for(Node cn:list){
//			if(cn.getId().trim().equals(id)){
//				return cn.getLocation();
//			}
//		}
//		return null;
//	}
	

	/**
	 * 根据id获取节点类型
	 * @param name
	 * @return
	 */
//	private static  String getTypeStr(String id,List<Node> list){
//		for(Node cn:list){
//			if(cn.getId().trim().equals(id)){
//				if(cn.getType().trim().equals("uml:Activity")){
//					return "ActivityNode";
//				}else if(cn.getType().trim().equals("uml:Decision")){
//					return "DecisionNode";
//					}else {
//						return "ScenarioEndNode";
//					}
//				}
//			}
//		return null;
//	}
	
}
