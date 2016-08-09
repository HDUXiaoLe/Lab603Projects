package com.horstmann.violet.application.menu.util.zj;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
public class CreateStateDiagramEAXml {
	
	
	private  Map<String, String> nodeMap =new HashMap<String, String>();
	private  Map<String, String> edgeMap =new HashMap<String, String>();
	private  List<Edge> edgeList;
	private  List<Node> nodeList;

	public void create(readStateXMLFormViolet raf,String path) throws IOException {
		nodeList=raf.getNodeList();
		edgeList=raf.getEdgeList();
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
						.addAttribute("name", "&#29366;&#24577;&#22270;")
						.addAttribute("visibility", "public")
						.addText("");
			
				//packageElement的子节点
				Element statePackElement=packElement.addElement("packagedElement")
						.addAttribute("xmi:type", "uml:StateMachine")
						.addAttribute("xmi:id", "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
						.addAttribute("name", "EA_StateMachine1")
						.addAttribute("visibility", "public")
						.addText("");
				//创建region节点
				Element regionElement=statePackElement.addElement("region")
						.addAttribute("xmi:type", "uml:Region")
						.addAttribute("xmi:id", "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
						.addAttribute("name", "EA_Region1")
						.addAttribute("visibility", "public")
						.addText("");	
					
					for (Node node : nodeList) {
							Element nodeElement=regionElement.addElement("subvertex").addText("");
								String id="EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase());
								nodeMap.put(node.getId(), id);
								nodeElement.addAttribute("xmi:type",node.getType().toString());
								nodeElement.addAttribute("xmi:id",id);
								nodeElement.addAttribute("name",node.getName());
								nodeElement.addAttribute("visibility","public");
								nodeElement.addAttribute("isSubmachineState", "false");
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
					for(Edge edge:edgeList){
						Element edgeElement=regionElement.addElement("transition").addText("");
						edgeElement.addAttribute("xmi:type", "uml:Transition")
									.addAttribute("xmi:id", XMLUtils.getMapId(edgeMap, edge.getId()))
									.addAttribute("visibility", "public")
									.addAttribute("kind", "local")
									.addAttribute("source", XMLUtils.getMapId(nodeMap, edge.getStarNodeid()))
									.addAttribute("target", XMLUtils.getMapId(nodeMap, edge.getEndNodeid()));
						Element guard= edgeElement.addElement("guard").addAttribute("xmi:type", "uml:Constraint")
												  .addAttribute("xmi:id", "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase())).addText("");
						guard.addElement("specification").addAttribute("xmi:type", "uml:OpaqueExpression")
													     .addAttribute("xmi:id", "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
													     .addAttribute("body", edge.getName());
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
	        				 if(node.getType().equals("uml:Pseudostate")||node.getType().equals("uml:FinalState")){
	        					 elementA.addAttribute("xmi:type", "uml:StateNode"); 
	        				 }else{
	        					 elementA.addAttribute("xmi:type", node.getType());
	        				 }
        					 
	        				 elementA.addAttribute("name", node.getName());
	        				 elementA.addAttribute("scope", "public");
	        				 elementA.addText("");
	
	        			Element links =elementA.addElement("links").addText("");
	        					for(Edge edge:edgeList){
	        						if(edge.getStarNodeid().equals(node.getId())||edge.getEndNodeid().equals(node.getId())){
	        							links.addElement("StateFlow")
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
	        					.addAttribute("ea_type", "StateFlow")
	        					.addAttribute("direction", "Source -&gt; Destination");
	        			Element appearance=conn.addElement("appearance")
	        								.addAttribute("linemode", "3")
	        								.addAttribute("linecolor", "-1")
	        								.addAttribute("linewidth", "0")
	        								.addAttribute("seqno", "0")
	        								.addAttribute("headStyle", "0")
	        								.addAttribute("lineStyle", "0");
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
        					.addAttribute("localID", "214")
        					.addAttribute("owner", packId);
        			
        			Element eles=diagram.addElement("elements").addText("");
        				int i =1;
        				for (Node node:nodeList) {
        					Element ele=eles.addElement("element");
        					if(node.getType().equals("uml:Pseudostate")||node.getType().equals("uml:FinalState")){
        						ele.addAttribute("geometry", 
    									"Left="+(int)node.getLocation().getX()+";Top="+(int)node.getLocation().getY()
    									+";Right="+((int)node.getLocation().getX()+20)+";Bottom="+
    									((int)node.getLocation().getY()+15)+";"
    									);
    							
        					}else{
        						ele.addAttribute("geometry", 
    									"Left="+(int)node.getLocation().getX()+";Top="+(int)node.getLocation().getY()
    									+";Right="+((int)node.getLocation().getX()+80)+";Bottom="+
    									((int)node.getLocation().getY()+60)+";"
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


	
	


}
