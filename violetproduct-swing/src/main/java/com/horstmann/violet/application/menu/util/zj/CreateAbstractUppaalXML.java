package com.horstmann.violet.application.menu.util.zj;


import java.io.File;
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

import com.horstmann.violet.application.menu.util.dataBase.zj.AbstractState;
import com.horstmann.violet.application.menu.util.dataBase.zj.AbstractTransition;


/**
 * 创建自动机的XML文件
 * @author ZhangJian
 *
 */
public class CreateAbstractUppaalXML {
	private  List<AbstractState> abNodeList;
	private  List<AbstractTransition> abTransitionList;
	public CreateAbstractUppaalXML(List<AbstractState> abNodeList, List<AbstractTransition> abTransitionList) {
		this.abNodeList = abNodeList;
		this.abTransitionList = abTransitionList;
	}
	private  Map<String, String > map =new HashMap<String, String>();
	private int l;

	public  void create(String path) throws IOException {
		File f =new File(path);
		if(!f.exists()){
			f.createNewFile();
		}
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
			Element root=document.addElement("UppaalGraph");
					root.addAttribute(" id","1");
					root.addText("");		
			createState(backgroundid, borderid, textid, k, root);
			createTransition(root);					
			return document;		
		}

	/**
	 * 创建时间自动机中的消息迁移节点
	 * @param root
	 */
	private  void createTransition(Element root) {
		int k;
		Element edges =root.addElement("edges").addAttribute("id", l+1+"");
		k=l+2;
		for(int j =0;j<abTransitionList.size();j++){
			AbstractTransition edge=abTransitionList.get(j);
			int bentid=6;  
				Element TransitionEdge=edges.addElement("TransitionEdge").addAttribute("id",k+"").addText("");
					String sourceID =edge.getSourceID();
					String targetID =edge.getTargetID();
//					TransitionEdge.addElement("start").addAttribute("class", XMLUtils.getState(sourceID,abNodeList).getType()).addAttribute("reference", XMLUtils.getMapId(map, sourceID));
//					TransitionEdge.addElement("end").addAttribute("class", XMLUtils.getState(targetID,abNodeList).getType()).addAttribute("reference", XMLUtils.getMapId(map, targetID));
					
					TransitionEdge.addElement("start").addAttribute("class","CircularNode"  ).addAttribute("reference", XMLUtils.getMapId(map, sourceID));
					TransitionEdge.addElement("end").addAttribute("class", "CircularNode" ).addAttribute("reference", XMLUtils.getMapId(map, targetID));
					
					
					TransitionEdge.addElement("startLocation").addAttribute("class", "Point2D.Double")
															.addAttribute("id", k+1+"")
															.addAttribute("x","30.0")
															.addAttribute("y", "30.0");
					
					TransitionEdge.addElement("endLocation").addAttribute("class", "Point2D.Double")
														  .addAttribute("id",k+2+"")
														  .addAttribute("x", "30.0")
														  .addAttribute("y", "30.0");
					TransitionEdge.addElement("id").addAttribute("id", k+3+"");
					TransitionEdge.addElement("labelText").addText("type:"+edge.getType()+" source："+edge.getSource()+" target："+edge.getTarget()+" ResetClockSet："+edge.getResetClockSet()+" DBM："+edge.getConstraintDBM());
					k=k+4;
			}	
		}
	/**
	 * 创建时间自动机中的状态节点
	 * @param backgroundid
	 * @param borderid
	 * @param textid
	 * @param k
	 * @param root
	 */
	private  void createState(String backgroundid, String borderid,
			String textid, int k, Element root) {
		
		Element nodes=root.addElement("nodes");
			nodes.addAttribute("id", "2");
			nodes.addText("");
			
			for(int i=0 ;i<abNodeList.size();i++){
				AbstractState node=abNodeList.get(i);
				if(node.getType().trim().equals("CircularStartNode")){
					
					Element cStartNode =nodes.addElement("CircularStartNode").addAttribute("id", k+"");
					
					map.put(node.getSid()+"",k+"");
					
					cStartNode.addElement("children")
								.addAttribute("id", k+1+"");
					cStartNode.addElement("location")
								.addAttribute("class", "Point2D.Double")
								.addAttribute("id", k+2+"")
								.addAttribute("x", "")//这里的坐标后期添加
								.addAttribute("y","");
					cStartNode.addElement("id")
								.addAttribute("id", k+3+"");
						if(i==0){
						Element backcolor=cStartNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
							backcolor.addElement("red").addText("255");
							backcolor.addElement("green").addText("255");
							backcolor.addElement("blue").addText("255");
							backcolor.addElement("alpha").addText("255");
							
						Element bordercolor=cStartNode.addElement("borderColor").addAttribute("id", borderid).addText("");
							bordercolor.addElement("red").addText("191");
							bordercolor.addElement("green").addText("191");
							bordercolor.addElement("blue").addText("191");
							bordercolor.addElement("alpha").addText("255");
							
						Element textcolor=cStartNode.addElement("textColor").addAttribute("id", textid).addText("");
							textcolor.addElement("red").addText("51");
							textcolor.addElement("green").addText("51");
							textcolor.addElement("blue").addText("51");
							textcolor.addElement("alpha").addText("255");
						}else{
							Element backcolor=cStartNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
							Element bordercolor=cStartNode.addElement("borderColor").addAttribute("reference", borderid);
							Element textcolor=cStartNode.addElement("textColor").addAttribute("reference", textid);
							
						}		
							
						Element name =cStartNode.addElement("name").addAttribute("id", k+4+"");
								name.addText(node.getSname());
								k=k+5;
						
				}else if(node.getType().trim().equals("CircularNode")){
						Element cNode =nodes.addElement("CircularNode").addAttribute("id", k+"");
						map.put(node.getSid()+"", k+"");
							cNode.addElement("children").addAttribute("id", k+1+"");
								cNode.addElement("location")
									.addAttribute("class", "Point2D.Double")
									.addAttribute("id", k+2+"")
									.addAttribute("x","")//这里的坐标需要设计
									.addAttribute("y","");
							
							cNode.addElement("id").addAttribute("id", k+3+"");
							if(i==0){
								Element backcolor=cNode.addElement("backgroundColor").addAttribute("id", backgroundid).addText("");
									backcolor.addElement("red").addText("255");
									backcolor.addElement("green").addText("255");
									backcolor.addElement("blue").addText("255");
									backcolor.addElement("alpha").addText("255");
									
								Element bordercolor=cNode.addElement("borderColor").addAttribute("id", borderid).addText("");
									bordercolor.addElement("red").addText("191");
									bordercolor.addElement("green").addText("191");
									bordercolor.addElement("blue").addText("191");
									bordercolor.addElement("alpha").addText("255");
									
								Element textcolor=cNode.addElement("textColor").addAttribute("id", textid).addText("");
									textcolor.addElement("red").addText("51");
									textcolor.addElement("green").addText("51");
									textcolor.addElement("blue").addText("51");
									textcolor.addElement("alpha").addText("255");
								}else{
									cNode.addElement("backgroundColor").addAttribute("reference", backgroundid);
									cNode.addElement("borderColor").addAttribute("reference",  borderid);
									cNode.addElement("textColor").addAttribute("reference", textid);
								}
							Element name =cNode.addElement("name").addAttribute("id", k+4+"");
								name.addText(" name:"+node.getSname()+";DBM:"+node.getInvariantDBM()+";position:"+node.getPosition());
							
							k=k+5;
						}
					l=k;
			}
	}
}
