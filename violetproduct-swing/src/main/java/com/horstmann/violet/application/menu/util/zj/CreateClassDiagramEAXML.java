package com.horstmann.violet.application.menu.util.zj;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class CreateClassDiagramEAXML {
	private  List<Edge> edgeList;
	private  List<Node> nodeList;
	private   Map<String, String > nodeMap =new HashMap<String, String>();
	private   Map<String, String > edgeMap =new HashMap<String, String>();
	private  String packageUID ="EAPK_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase());

	public void create(readClassXMLFormViolet rc,String path) throws IOException {
		edgeList=rc.getEdgeList();
		nodeList=rc.getNodeList();
		//Ω´nodeµƒid◊™ªØŒ™EA∏Ò Ωµƒid
		for(Node node:nodeList){
			nodeMap.put(node.getId(), "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()));
		}
		//Ω´edgeµƒid◊™ªØŒ™EA∏Ò Ωµƒid
		for(Edge edge:edgeList){
			edgeMap.put(edge.getId(),"EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()));
		}
		Document document=createXMLDocument();
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

	public  Document createXMLDocument(){
		Document document=DocumentHelper.createDocument();
		Element root = createRootElement(document);
		createPackageElement(root);						
		createExtensionElement(root);	
		return document;
		
	}


	public  Element createRootElement(Document document) {
		Element root=document.addElement("xmi:XMI");
			root.addAttribute(" xmi:version", "2.1");
			root.addNamespace("uml", "http://schema.omg.org/spec/UML/2.1");
			root.addNamespace("xmi", "http://schema.omg.org/spec/XMI/2.1");
		Element Welement=root.addElement("xmi:Documentation");
			Welement.addAttribute("exporter", "Enterprise Architect");
			Welement.addAttribute(" exporterVersion", "6.5");
		return root;
	}


	public  void createPackageElement(Element root) {
		Element modelNode =root.addElement("uml:Model")
				.addAttribute("xmi:type", "uml:Model")
				.addAttribute("name", "EA_Model")
				.addAttribute("visibility", "public")
				.addText("");
		Element packagedElement=modelNode.addElement("packagedElement ")
				.addAttribute("xmi:type", "uml:Package")
				.addAttribute("xmi:id", packageUID )
				.addAttribute("name", "ClassPack")
				.addAttribute("visibility", "public")
				.addText("");
		
			for(Node node:nodeList){
					Element element=packagedElement.addElement("packagedElement ");
					element.addAttribute("xmi:type",node.getType());
					element.addAttribute("xmi:id", XMLUtils.getMapId(nodeMap, node.getId()))
					.addAttribute("name", node.getName())
					.addAttribute("visibility", "public");
					if(node.getType().equals("uml:Interface")){
						element.addAttribute("isAbstract", "true").addText("");
//						modelNode.addElement("thecustomprofile:interface").addAttribute("base_Interface", XMLUtils.getMapId(nodeMap, node.getId()));
					}else{
						element.addText("");
					}
					
//			System.out.println("ËäÇÁÇπÁöÑÂ±ûÊÄß‰ø°ÊÅØÔºö"+node.getAttribute());
			if(node.getAttribute()!=null){//ËøôÈáåÈú?¶ÅÊîπËøõ
				String[] attr=node.getAttribute().split(",");
				for(int j=0;j<attr.length;j++){
					if(attr[j]!=""){
						Element ownedAttribute=element.addElement("ownedAttribute");
						ownedAttribute.addAttribute("xmi:type", "uml:Property");
						ownedAttribute.addAttribute("xmi:id",  "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))//ÔøΩÔøΩÔøΩÔøΩ“ªÔøΩÔøΩclassÔøΩ⁄µÔøΩÔøΩÔøΩÔøΩ‘µÔøΩid
						.addAttribute("name", attr[j])//classÔøΩ⁄µÔøΩÔøΩÔøΩÔøΩ‘µÔøΩÔøΩÔøΩÔø?
						.addAttribute("visibility", "private")
						.addAttribute("isStatic", "false")
						.addAttribute("isReadOnly", "false")
						.addAttribute("isDerived", "false")
						.addAttribute("isOrdered", "false")
						.addAttribute("isUnique", "true")
						.addAttribute("isDerivedUnion", "false")
						.addText("");
					ownedAttribute.addElement("lowerValue")
						.addAttribute("xmi:type", "uml:LiteralInteger")
						.addAttribute("xmi:id",  "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
						.addAttribute(" value", "1");
					ownedAttribute.addElement("upperValue")
						.addAttribute("xmi:type", "uml:LiteralInteger")
						.addAttribute("xmi:id",  "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
						.addAttribute(" value", "1");
					ownedAttribute.addElement("type")
						.addAttribute("xmi:idref", "EAJava_int");
					
					}
				}
			}
//			System.out.println("ËäÇÁÇπÁöÑÊñπÊ≥ïÔºö"+node.getMethod());	
			if(node.getMethod()!=null){
					String[] met=node.getMethod().split(",");
					for (int i = 0; i < met.length; i++) {
						if(met[i]!=""){
							Element ownedOperation=element.addElement("ownedOperation")
									.addAttribute("xmi:id", "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
									.addAttribute("name", met[i])
									.addAttribute("visibility", "public")
									.addAttribute("concurrency", "sequential")
									.addText("");
							ownedOperation.addElement("ownedParameter").addAttribute("xmi:id", "EAID_"+XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
									.addAttribute("name", "return")
									.addAttribute("direction", "return")
									.addAttribute("type", "EAJava_void");
						}
				}					
			}
		
		}						
			
				for(Edge edge:edgeList){
					if(!edge.getType().equals("Generalization")){
						Element edgeNode=packagedElement.addElement("packagedElement ");
							if(edge.getType().equals("Aggregation")){
								edgeNode.addAttribute("xmi:type","uml:Association");
							}else{
								edgeNode.addAttribute("xmi:type","uml:"+edge.getType());
							}
								edgeNode.addAttribute("xmi:id", XMLUtils.getMapId(edgeMap, edge.getId()))
								.addAttribute("name",edge.getName())
								.addAttribute("visibility", "public");
								if(!edge.getType().equals("Aggregation")&&!edge.getType().equals("Association")){
									edgeNode.addAttribute("supplier", XMLUtils.getMapId(nodeMap,edge.getEndNodeid()))
									.addAttribute("client", XMLUtils.getMapId(nodeMap, edge.getStarNodeid()));
								}
								
						if(edge.getType().equals("Aggregation")||edge.getType().equals("Association")){
							String edgeId=XMLUtils.getMapId(edgeMap, edge.getId());
							edgeId=edgeId.substring(7, edgeId.length()-1);
							Element	memberEnd =edgeNode.addElement("memberEnd").addAttribute("xmi:idref", "EAID_dst"+edgeId);
//							if(edge.getType().equals("Aggregation")){
								Element ownedEnd=edgeNode.addElement("ownedEnd").addAttribute(" xmi:type", "uml:Property")
										.addAttribute("xmi:id", "EAID_dst"+edgeId)
										.addAttribute("visibility", "public")
										.addAttribute("association", XMLUtils.getMapId(edgeMap,edge.getId()))
										.addAttribute("isStatic", "false")
										.addAttribute("isReadOnly", "false")
										.addAttribute("isDerived", "false")
										.addAttribute("isOrdered", "false")
										.addAttribute("isUnique", "true")
										.addAttribute("isDerivedUnion", "false")
										.addAttribute("aggregation", "none")
										.addText("");
										ownedEnd.addElement("type").addAttribute("xmi:idref",XMLUtils.getMapId(nodeMap,edge.getEndNodeid()));
									
//							}
							Element	memberEnd2 =edgeNode.addElement("memberEnd").addAttribute("xmi:idref", "EAID_src"+edgeId);
							Element ownedEnd2=edgeNode.addElement("ownedEnd").addAttribute(" xmi:type", "uml:Property")
								.addAttribute("xmi:id", "EAID_src"+edgeId)
								.addAttribute("visibility", "public")
								.addAttribute("association", XMLUtils.getMapId(edgeMap,edge.getId()))
								.addAttribute("isStatic", "false")
								.addAttribute("isReadOnly", "false")
								.addAttribute("isDerived", "false")
								.addAttribute("isOrdered", "false")
								.addAttribute("isUnique", "true")
								.addAttribute("isDerivedUnion", "false")
								.addAttribute("aggregation", "none")
								.addText("");
								ownedEnd2.addElement("type").addAttribute("xmi:idref",XMLUtils.getMapId(nodeMap,edge.getStarNodeid()));
						}
						
			}
		}
	}
	
	

	public void createExtensionElement(Element root) {
		
		//ÂàõÂª∫xmi:ExtensionËäÇÁÇπ
        Element extension=root.addElement("xmi:Extension")
        		.addAttribute("extender", "Enterprise Architect")
				.addAttribute("extenderID", "6.5")
				.addText("");
        	
        	Element elements=extension.addElement("elements").addText("");
        	//ÂàõÂª∫elementËäÇÁÇπ
	        	for (Node node:nodeList){	        		
		        		Element elementA=elements.addElement("element");
	        				 elementA.addAttribute("xmi:idref",XMLUtils.getMapId(nodeMap, node.getId()));
	        				 //ËÆæÁΩÆËäÇÁÇπÁöÑÁ±ªÂû?
	        				 elementA.addAttribute("xmi:type", "uml:Class"); 
	        				 elementA.addAttribute("name", node.getName());
	        				 elementA.addAttribute("scope", "public");
	        				 elementA.addText("");
	
	        			Element links =elementA.addElement("links").addText("");
	        					for(Edge edge:edgeList){
	        						if(edge.getStarNodeid().equals(node.getId())||edge.getEndNodeid().equals(node.getId())){
	        							links.addElement(edge.getType())
        								.addAttribute("xmi:id", XMLUtils.getMapId(edgeMap, edge.getId()))
        								.addAttribute("start", XMLUtils.getMapId(nodeMap, edge.getStarNodeid()))
        								.addAttribute("end", XMLUtils.getMapId(nodeMap, edge.getEndNodeid()));
	        						}
	        					}		
					}	
        	
	        	
	        	Element connectors=extension.addElement("connectors").addText("");
	        		//ÂàõÂª∫connectorËäÇÁÇπ
		        	for (Edge edge :edgeList) {
		        		Element conn=connectors.addElement("connector")
		        				.addAttribute("xmi:idref", XMLUtils.getMapId(edgeMap, edge.getId()));
		        		if(edge.getType().equals("Generalization")){
		        			conn.addAttribute("name", edge.getName()).addText("");
		        		}else{
		        			conn.addAttribute("name", edge.getName()).addText("");
		        		}
		        				
		        				
		        			
		        			Element prop=conn.addElement("properties");
		        			if(edge.getType().equals("Composition")||edge.getType().equals("Aggregation")){
		        				prop.addAttribute("ea_type","Aggregation" );
		        				if(edge.getType().equals("Composition")){
		        					prop.addAttribute("subtype","Strong" );
		        					prop.addAttribute("direction","Source -&gt; Destination" );
		        				}else{
		        					prop.addAttribute("subtype","Weak" );
		        					prop.addAttribute("direction","Source -&gt; Destination" );
		        				}
		        			}else{
		        				prop.addAttribute("ea_type", edge.getType());
		        				if(edge.getType().equals("Association")){
		        					prop.addAttribute("direction", "Unspecified");
		        				}else{
		        					prop.addAttribute("direction", "Source -&gt; Destination");
		        				}
		        				
		        			}
		        					
		        					
		        			Element appearance=conn.addElement("appearance")
		        								.addAttribute("linemode", "3");
						        			if(edge.getType().equals("Realisation")||edge.getType().equals("Aggregation")||edge.getType().equals("Composition")){
						        				appearance.addAttribute("linecolor", "-1");
						        			}else{
						        				appearance.addAttribute("linecolor", "0");
						        			}
		        							
		        							appearance.addAttribute("linewidth", "0")
		        								.addAttribute("seqno", "0")
		        								.addAttribute("headStyle", "0")
		        								.addAttribute("lineStyle", "0");
		        			Element label=conn.addElement("labels").addAttribute("mt",edge.getName());
		        			Element etp=conn.addElement("extendedProperties")
		        							.addAttribute("virtualInheritance", "0");
						}
		        	
		        	Element diagrams=extension.addElement("diagrams").addText("");
	        		Element diagram=diagrams.addElement("diagram")
	        				.addAttribute("xmi:id", "EAID_"+ XMLUtils.dealEAID(UUID.randomUUID().toString().toUpperCase()))
	        				.addText("");
	        		
	        			Element model=diagram.addElement("model")
	        					.addAttribute("package", packageUID)
	        					.addAttribute("localID", "258")
	        					.addAttribute("owner", packageUID);
	        			
	        			Element eles=diagram.addElement("elements").addText("");
	        				int i =1;
	        				for (Node node:nodeList) {
	        					Element ele=eles.addElement("element");
	        						ele.addAttribute("geometry", 
	    									"Left="+(int)node.getLocation().getX()+";Top="+(int)node.getLocation().getY()
	    									+";Right="+((int)node.getLocation().getX()+80)+";Bottom="+
	    									((int)node.getLocation().getY()+60)+";"
	    									);
	        					ele.addAttribute("subject", XMLUtils.getMapId(nodeMap, node.getId()));
								ele.addAttribute("seqno", i+"");	
	        					i++;		
							}
	        				for (Edge edge:edgeList){
								Element ele=eles.addElement("element")
										.addAttribute("subject", XMLUtils.getMapId(edgeMap, edge.getId()));								
							}

}
}