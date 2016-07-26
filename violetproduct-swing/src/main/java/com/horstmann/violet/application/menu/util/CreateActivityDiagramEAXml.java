package com.horstmann.violet.application.menu.util;


import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
public class CreateActivityDiagramEAXml {
	
	
	private  Map<String, String> nodeMap =new HashMap<String, String>();
	private  Map<String, String> edgeMap =new HashMap<String, String>();
	private  List<Edge> edgeList;
	private  List<Node> nodeList;

	public void create(readActivityXMLFormViolet raf,String path) throws IOException {
		edgeList=raf.getEdgeList();
		nodeList=raf.getNodeList();
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
	
	
	public  Document createDocument() {
		
		//将消息id转化为EA格式的Id
		for(Edge edge:edgeList){
			edgeMap.put(edge.getId(), "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()));
		}
		String packId="";
		Document document=DocumentHelper.createDocument();
//============================================================================================	
		Element root=document.addElement("xmi:XMI");
			root.addAttribute(" xmi:version", "2.1");
			root.addNamespace("uml", "http://schema.omg.org/spec/UML/2.1");
			root.addNamespace("xmi", "http://schema.omg.org/spec/XMI/2.1");

		Element Welement=root.addElement("xmi:Documentation");
			Welement.addAttribute("exporter", "Enterprise Architect");
			Welement.addAttribute(" exporterVersion", "6.5");
//============================================================================================		
			
		
		//创建Mode节点
		Element element1 =root.addElement("uml:Model")
				.addAttribute("xmi:type", "uml:Model")
				.addAttribute("name", "EA_Model")
				.addAttribute("visibility", "public")
				.addText("");
			
			
				//建立Mode子节点packagedElement的包节点
				packId="EAPK_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase());
				Element packElement = element1.addElement("packagedElement")
						.addAttribute("xmi:type", "uml:Package")
						.addAttribute("xmi:id", packId)
						.addAttribute("name", "&#27963;&#21160")
						.addAttribute("visibility", "public")
						.addText("");
				//packageElement的子节点
				Element statePackElement=packElement.addElement("packagedElement")
						.addAttribute("xmi:type", "uml:Activity")
						.addAttribute("xmi:id", "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
						.addAttribute("name", "EA_Activity1")
						.addAttribute("visibility", "public")
						.addText("");
					
					for (Node node : nodeList) {
							if(node.getType().trim().equals("uml:Activity")){
								Element actinityElement =packElement.addElement("packagedElement");
									actinityElement.addAttribute("xmi:type", "uml:Activity");
									String nodeID="EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase());
									actinityElement.addAttribute("xmi:id", nodeID);
									nodeMap.put(node.getId(), nodeID);//将平台读出来的id转化为EA的id
									actinityElement.addAttribute("name", node.getName().toString());
									actinityElement.addAttribute("visibility", "public");
									actinityElement.addAttribute("isReadOnly", "false");
									actinityElement.addAttribute("isSingleExecution", "false");
							}else{
								Element nodeElement=statePackElement.addElement("node");
									String id="EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase());
									nodeMap.put(node.getId(), id);
									nodeElement.addAttribute("xmi:type",node.getType().toString());
									nodeElement.addAttribute("xmi:id",id);
									nodeElement.addAttribute("name",node.getName());
									nodeElement.addAttribute("visibility","public");
									/*
									 * 通过Node节点的id获取与Node节点相关的边
									 */
									for(Edge edge:edgeList){
										if(edge.getStarNodeid().equals(node.getId())){
											nodeElement.addElement("outgoing").addAttribute("xmi:idref", XMLUtils.getMapId(edgeMap, edge.getId()));
										}else if(edge.getEndNodeid().equals(node.getId())){
											nodeElement.addElement("incoming").addAttribute("xmi:idref", XMLUtils.getMapId(edgeMap, edge.getId()));
										}
									}
							}
						}
					
					
//===========================================================================================================					
					
		//创建xmi:Extension节点
        Element extension=root.addElement("xmi:Extension")
        		.addAttribute("extender", "Enterprise Architect")
				.addAttribute("extenderID", "6.5")
				.addText("");
        	
        	Element elements=extension.addElement("elements").addText("");
        	//创建element节点
	        	for (Node node:nodeList){	        		
		        		Element elementA=elements.addElement("element");
	        				 elementA.addAttribute("xmi:idref",XMLUtils.getMapId(nodeMap, node.getId()));
	        				 //设置节点的类�?
	        				 if(node.getType().equals("uml:ActivityFinalNode")||node.getType().equals("uml:InitialNode")){
	        					 elementA.addAttribute("xmi:type", "uml:StateNode"); 
	        				 }else{
	        					 elementA.addAttribute("xmi:type", node.getType());
	        				 }
        					 elementA.addAttribute("xmi:type", "");
	        				 elementA.addAttribute("name", node.getName());
	        				 elementA.addAttribute("scope", "public");
	        				 elementA.addText("");
	
	        			Element links =elementA.addElement("links").addText("");
	        					for(Edge edge:edgeList){
	        						if(edge.getStarNodeid().equals(node.getId())||edge.getEndNodeid().equals(node.getId())){
	        							links.addElement("ControlFlow")
        								.addAttribute("xmi:id", XMLUtils.getMapId(edgeMap, edge.getId()))
        								.addAttribute("start", XMLUtils.getMapId(nodeMap, edge.getStarNodeid()))
        								.addAttribute("end", XMLUtils.getMapId(nodeMap, edge.getEndNodeid()));
	        						}
//	        						else if(edge.getEndNodeid().equals(node.getId())){
//	        							links.addElement("ControlFlow")
//        								.addAttribute("xmi:id", XMLUtils.getMapId(edgeMap, edge.getId()))
//        								.addAttribute("start", XMLUtils.getMapId(nodeMap, edge.getStarNodeid()))
//        								.addAttribute("end", XMLUtils.getMapId(nodeMap, edge.getEndNodeid()));
//	        						}
	        					}
	        				
					}	
        	
        	
        	
        	Element connectors=extension.addElement("connectors").addText("");
        		//创建connector节点
	        	for (Edge edge :edgeList) {
	        		Element conn=connectors.addElement("connector")
	        				.addAttribute("xmi:idref", XMLUtils.getMapId(edgeMap, edge.getId()))
	        				.addText("");
	        				
	        			
	        			Element prop=conn.addElement("properties")
	        					.addAttribute("ea_type", "ControlFlow")
	        					.addAttribute("direction", "Source -&gt; Destination");
	        			Element appearance=conn.addElement("appearance")
	        								.addAttribute("linemode", "3")
	        								.addAttribute("linecolor", "-1")
	        								.addAttribute("linewidth", "0")
	        								.addAttribute("seqno", "0")
	        								.addAttribute("headStyle", "0")
	        								.addAttribute("lineStyle", "0");
	        			Element label=conn.addElement("labels").addAttribute("mb", "["+edge.getName()+"]");
	        			Element etp=conn.addElement("extendedProperties")
	        							.addAttribute("virtualInheritance", "0")
	        							.addAttribute("privatedata2", edge.getName());
					}
	        	

	        	
	        	Element diagrams=extension.addElement("diagrams").addText("");
        		Element diagram=diagrams.addElement("diagram")
        				.addAttribute("xmi:id", "EAID_"+ XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
        				.addText("");
        		
        			Element model=diagram.addElement("model")
        					.addAttribute("package", packId)
        					.addAttribute("localID", "212")
        					.addAttribute("owner", packId);
        			
        			Element eles=diagram.addElement("elements").addText("");
        				int i =1;
        				for (Node node:nodeList) {
        					Element ele=eles.addElement("element");
        					if(node.getType().equals("uml:Activity")||node.getType().equals("uml:DecisionNode")){
        						ele.addAttribute("geometry", 
    									"Left="+(int)node.getLocation().getX()+";Top="+(int)node.getLocation().getY()
    									+";Right="+((int)node.getLocation().getX()+80)+";Bottom="+
    									((int)node.getLocation().getY()+60)+";"
    									);
    							
        					}else{
        						ele.addAttribute("geometry", 
    									"Left="+(int)node.getLocation().getX()+";Top="+(int)node.getLocation().getY()
    									+";Right="+((int)node.getLocation().getX()+20)+";Bottom="+
    									((int)node.getLocation().getY()+15)+";"
    									);
        					}
        					ele.addAttribute("subject", XMLUtils.getMapId(nodeMap, node.getId()));
							ele.addAttribute("seqno", i+"");	
        					i++;		
						}
        				for (Edge edge:edgeList){
							Element ele=eles.addElement("element")
									.addAttribute("subject", XMLUtils.getMapId(edgeMap, edge.getId()));
//							System.out.println(edge.getName());
//							System.out.println(edge.getId());
//									
						}
//        				 Iterator iter=edgeMap.keySet().iterator();
//        				 while(iter.hasNext()){
//        					String key=iter.next().toString();
//        					System.out.println(key);
//        					 
//        					System.out.println(edgeMap.get(key)); 
//        				 }
//        				
        	
		return document;
		
	}

	
//	/**
//	 * 遍历edgeMap，当map里面有id信息则返回对应的EA的id
//	 * 否则添加�?��键�?对信�?
//	 * @param nodeId
//	 * @return 返回EA格式的Id
//	 */
//	private static  String  getEdgeIdByNodeId(String edgeId) {
//		String valueId="";
//		 Iterator iter=edgeMap.keySet().iterator();
//		 while(iter.hasNext()){
//			String key=iter.next().toString();
//			 if(edgeId.equals(key)){
//				 valueId=edgeMap.get(key);
//			 } 
//		 }
//		 String value="EAID_"+ XMLUtils.dealEAID(XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()));	
//		 edgeMap.put(edgeId, value);
//		 valueId=value;
//		 return valueId;
//	}
	

}
