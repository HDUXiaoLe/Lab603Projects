package com.horstmann.violet.application.gui.util.wujun;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.print.DocFlavor.STRING;

import org.dom4j.Element;

public class Read
{
	ArrayList<WJLifeline> umlLifeLines=new ArrayList<WJLifeline>();
	ArrayList<MessageClass> umlMessages=new ArrayList<MessageClass>();
	ArrayList<ConnectorsClass> umlConnectors=new ArrayList<ConnectorsClass>();
	ArrayList<MessageComplete> umlMessageComplete=new ArrayList<MessageComplete>();
	ArrayList<WJFragment> umlFragment=new ArrayList<WJFragment>();
	ArrayList<WJFragment> umlFragmentInner=new ArrayList<WJFragment>();
	ArrayList<WJMessage> umlMessageFinal=new ArrayList<WJMessage>();
	ArrayList<WJDiagramsData> umlAllDiagramData = new ArrayList<WJDiagramsData>();
	ArrayList<REF> umlREF = new ArrayList<REF>();
	ArrayList<WJDiagramsData> displayDiagrams = new ArrayList<WJDiagramsData>();//展示用的图
	HashMap<String , String> findAltsFather = new HashMap<String , String>();
	static int markId = 0;//标记第几个ref 用于重复id冲突
	public boolean hasNoLifeline()
	{	
		if(umlLifeLines.isEmpty())                             	
			return true;
		else 
			return false;	
	}
	
	public void load(Element root) throws Exception
	{
		ArrayList<Element> EAlifeLineList=new ArrayList();
		ArrayList<Element> EAconnectorList=new ArrayList();
		ArrayList<Element> EAfragmentList=new ArrayList();
		ArrayList<Element> EAmessagesList = new ArrayList();
		
		//新建一个hashMap用来存储遍历组合片段时得到的lastMessage（对应于diagram）
		HashMap<String, String> lastMessageIDByKeyWithDiagramID = new HashMap<String, String>();
		//新建一个hashMap来存储sourceID&targetID -》messageID （优化查找时间）
		HashMap<String, String> MessageIDByKeyWithSourceIDAndTargetID = new HashMap<String, String>();
		
		EAlifeLineList.addAll(root.element("Model").element("packagedElement").element("packagedElement").element("ownedBehavior").elements("lifeline"));
		EAconnectorList.addAll(root.element("Extension").element("connectors").elements("connector"));
		EAfragmentList.addAll(root.element("Model").element("packagedElement").element("packagedElement").element("ownedBehavior").elements("fragment"));
		EAmessagesList.addAll(root.element("Model").element("packagedElement").element("packagedElement").element("ownedBehavior").elements("message"));
		//成员变量 umlAllDiagramData 包含所有图的list
			//获得所有图的包含id情况
			ArrayList<Element> EADiagramsList = new ArrayList();//存放读取得到的element
					
			//1.取得所有的diagram 
			EADiagramsList.addAll(root.element("Extension").element("diagrams").elements("diagram"));
			
			//2.遍历EADiagramIDsList
			for(Iterator<Element>  EADiagramsListIterator=EADiagramsList.iterator();EADiagramsListIterator.hasNext();)
			{
				//取得第i张图
				Element diagramI=EADiagramsListIterator.next();
				
				//获得这张图所有elements 
				ArrayList <Element> elements = new ArrayList <Element>();
				try {
					elements.addAll(diagramI.element("elements").elements("element"));
				} catch (Exception e) {
					System.out.println("exception:图元素");
				}
				
				
				//遍历elements 设置ids
				ArrayList <String> ids = new ArrayList<String>();	
				for(Iterator<Element>  elementsIterator=elements.iterator();elementsIterator.hasNext();)
				{
					Element elementI = elementsIterator.next();
					String id = elementI.attributeValue("subject");
					String value = elementI.attributeValue("geometry");
					ids.add(id.substring(13));//取得13位之后的id属性 因为actor的id只有后面13位是相符的
					WJRectangle rectangle = FixFragmentTool.rectangleFromValueString(value);
					FixFragmentTool.rectangleById.put(id, rectangle);
				}
				
				//获得这张图的name
				String name = diagramI.element("properties").attributeValue("name");
				
				//创建DiagramsData对象
				WJDiagramsData diagramData = new WJDiagramsData();
				diagramData.ids = ids;
				diagramData.name = name;
				diagramData.diagramID = diagramI.attributeValue("id");
				lastMessageIDByKeyWithDiagramID.put(diagramData.diagramID, "init");
				//将DiagramsData对象 添加到成员变量umlAllDiagramData中
				umlAllDiagramData.add(diagramData);
			}
		
		//读取lifeline信息
		for(Iterator<Element> lifeLineIterator=EAlifeLineList.iterator();lifeLineIterator.hasNext();)
		{
			Element elLifeLine=lifeLineIterator.next();
			WJLifeline lifeLine=new WJLifeline();
			lifeLine.setlifeLineId(elLifeLine.attribute("id").getValue());
			lifeLine.setlifeLineName(elLifeLine.attribute("name").getValue());
			umlLifeLines.add(lifeLine);
		}
		//读取message信息
		for(Iterator<Element> EAmessagesIterator=EAmessagesList.iterator();EAmessagesIterator.hasNext();)
		{
			Element elmessage=EAmessagesIterator.next();
			MessageClass message=new MessageClass();
			message.setSequenceMsgId(elmessage.attribute("id").getValue());
			if(elmessage.attribute("name") != null)
			message.setSequenceMsgName(elmessage.attribute("name").getValue());
			message.setSequenceMsgSendEvent(elmessage.attribute("sendEvent").getValue());
			message.setMessageSort(elmessage.attribute("messageSort").getValue());
			umlMessages.add(message);
		}
		//读取connectors信息
		for(Iterator<Element> connectorIterator=EAconnectorList.iterator();connectorIterator.hasNext();)
		{
			Element elConnector=connectorIterator.next();
			ConnectorsClass connectorsMsg=new ConnectorsClass();
			connectorsMsg.setConnectorId(elConnector.attribute("idref").getValue());
			connectorsMsg.setSourceId(elConnector.element("source").attribute("idref").getValue());
			connectorsMsg.setTragetId(elConnector.element("target").attribute("idref").getValue());
			connectorsMsg.setPointY(FixFragmentTool.pointYFromValueString(elConnector.element("extendedProperties").attributeValue("sequence_points")));
			if(elConnector.element("properties").attribute("name") != null)
			connectorsMsg.setName(elConnector.element("properties").attribute("name").getValue());

			if (elConnector.element("style").attribute("value")!=null) {//io信息
				String styleValue=elConnector.element("style").attribute("value").getValue();
				connectorsMsg.setStyleValue(styleValue);
				String io = GetAliasIo(styleValue);
				String inString = "null";
				if (styleValue.contains(";paramvalues=")) {//instrung
					inString = styleValue.split(";paramvalues=")[1];
				}
				
				if (io != null && io.equals("in")) {
					connectorsMsg.setInString(inString);
				} else if (io != null && io.equals("out")){
					try {
						connectorsMsg.setOutString(elConnector.element("extendedProperties").attributeValue("privatedata2").split("retval=")[1]);

					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			} 
			umlConnectors.add(connectorsMsg);
		}
		//整合message和connectors的信息 ，把connectors中的source和targetID 读入到新类中
		for(Iterator<MessageClass> umlMessagesIterator=umlMessages.iterator();umlMessagesIterator.hasNext();)
		{
			MessageClass messageI = umlMessagesIterator.next();
			MessageComplete messageComplete=new MessageComplete();
			messageComplete.name = messageI.getSequenceMsgName();
			messageComplete.connectorId = messageI.getSequenceMsgId();
			messageComplete.sendEvent = messageI.getSequenceMsgSendEvent();
			messageComplete.messageSort = messageI.getMessageSort();
			
			for(Iterator<ConnectorsClass> umlConnectorsIterator=umlConnectors.iterator();umlConnectorsIterator.hasNext();)
			{
				ConnectorsClass connectorsI=umlConnectorsIterator.next();
				if(connectorsI.getConnectorId().equals(messageI.getSequenceMsgId()))
				{
					messageComplete.sourceId = connectorsI.getSourceId();
					messageComplete.tragetId = connectorsI.getTragetId();
					messageComplete.styleValue = connectorsI.getStyleValue();
					messageComplete.pointY = connectorsI.getPointY();
					if (connectorsI.getInString() != null) {
						messageComplete.setInString(connectorsI.getInString());
					} else if (connectorsI.getOutString() != null){
						messageComplete.setOutString(connectorsI.getOutString());
					}
				}
			}
			umlMessageComplete.add(messageComplete);
		}
		//设置messageList
				ArrayList <MessageComplete> messageList = new ArrayList <MessageComplete>();
				Iterator <MessageComplete> msgComplete = umlMessageComplete.iterator();
				Iterator <Element> messageIterator = EAmessagesList.iterator();
				while(messageIterator.hasNext()&&msgComplete.hasNext())
				{
					Element messageI = messageIterator.next();
					MessageComplete MC = msgComplete.next();
					
					ArrayList<Element> allargument = new ArrayList<Element>();
					allargument.addAll(messageI.elements("argument"));
					Iterator <Element> allargIterator = allargument.iterator();
					String T1 = null,T2 = null,Energe = null,R1 = null,R2 = null;
					while(allargIterator.hasNext())
					{
						Element allargI = allargIterator.next();
						if(allargI.attributeValue("name").equals("T1"))
							T1=allargI.element("defaultValue").attributeValue("value");
						if(allargI.attributeValue("name").equals("T2"))
							T2=allargI.element("defaultValue").attributeValue("value");
						if(allargI.attributeValue("name").equals("Energe"))
							Energe=allargI.element("defaultValue").attributeValue("value");
						if(allargI.attributeValue("name").equals("R1"))
							R1=allargI.element("defaultValue").attributeValue("value");
						if(allargI.attributeValue("name").equals("R2"))
							R2=allargI.element("defaultValue").attributeValue("value");
					}
					
					MessageComplete messageX = new MessageComplete();
					messageX.setName(messageI.attributeValue("name"));
					messageX.setConnectorId(messageI.attributeValue("id"));
					messageX.setSourceId(messageI.attributeValue("sendEvent"));
					messageX.setTragetId(messageI.attributeValue("receiveEvent"));
					messageX.setFromId(MC.getSourceId());
					messageX.setToId(MC.getTragetId());
					messageX.setStyleValue(MC.styleValue);
					messageX.setInString(MC.inString);
					messageX.setOutString(MC.outString);
					messageX.setT1(T1);
					messageX.setT2(T2);
					messageX.setEnerge(Energe);
					messageX.setR1(R1);
					messageX.setR2(R2);
					messageX.setPointY(MC.pointY);
					MessageIDByKeyWithSourceIDAndTargetID.put(messageX.getSourceId()+messageX.getTragetId(), messageX.getConnectorId());
					messageList.add(messageX);
				}
//***************************************	 组合片段的嵌套读取			
		for(Iterator<Element> fragListIterator=EAfragmentList.iterator();fragListIterator.hasNext();)//遍历所有fragment
		{
			Element fragment=fragListIterator.next();
			if (fragment.attributeValue("id").equals("EAID_BD50CFEE_3602_40ff_B884_E6438771B272")) {
				int a = 0;
				a++;
			}
			if(fragment.attribute("type").getValue().equals("uml:OccurrenceSpecification"))
			{	
				String sourceID = fragment.attribute("id").getValue();
				fragment = fragListIterator.next();
				String targetID = fragment.attribute("id").getValue();		
				//通过sourceID和targetID找到对应的messageID
				String lastMessageID = MessageIDByKeyWithSourceIDAndTargetID.get(sourceID+targetID);
				//设置lastMessageID对应于DiagramID
//				lastMessageIDByKeyWithDiagramID.put(findDiagramIDByChildID(lastMessageID), lastMessageID);
					
				
			}
//			if (fragment.attribute("type").getValue().equals("uml:InteractionUse")) {//最外层如果是一个ref
//				REF ref = new REF();
//				ref.setRefID(fragment.attributeValue("id"));
//				ref.setDiagramName(fragment.attributeValue("name"));
//				String diagramID = findDiagramIDByChildID(ref.getRefID());//找到这个ref所属的diagram
//				ref.setLastMessageID(lastMessageIDByKeyWithDiagramID.get(diagramID));//设置ref的lastmessageID为这个图的lastmessageID
//				
//				ref.setInFragID("null");
//				ref.setInFragName("SD");
//				lastMessageIDByKeyWithDiagramID.put(diagramID, "REF:"+ref.getInFragID());//做一个REF标记
//				umlREF.add(ref);
//			}
			if(fragment.attribute("type").getValue().equals("uml:CombinedFragment"))
			{//是组合片段
				Queue<Element> q = new LinkedList<Element>();
				Queue<String> q_AorP = new LinkedList<String>();
				Queue<String> q_BigId = new LinkedList<String>();
				q_BigId.add("null");
				q.add(fragment);
				while(!q.isEmpty())
				{
					Element parent = q.poll();
					/*if(parent.attribute("type").getValue().equals("uml:CombinedFragment"))//打印pop组合片段名字loop
						System.out.println("pop:"+parent.attribute("interactionOperator").getValue());
					else																//打印alt或者par
						System.out.println("pop:"+q_AorP.peek()+parent.attribute("id").getValue());*/
					
					ArrayList<Element> alfrags=new ArrayList<Element>();

					if(parent.attribute("type").getValue().equals("uml:CombinedFragment"))//是整个组合片段
					{
						
						WJFragment fragInfo = new WJFragment();
						//visit(parent);
						fragInfo.setFragId(parent.attribute("id").getValue());					
						fragInfo.setFragType(parent.attribute("interactionOperator").getValue());//loop
					
						if(fragInfo.getFragType().equals("opt") || fragInfo.getFragType().equals("loop") || fragInfo.getFragType().equals("break"))//非alt par 只有1个operand
						{
							//System.out.println(parent.element("operand").element("guard").element("specification").attribute("body").getValue());
							fragInfo.setFragCondition(parent.element("operand").element("guard").element("specification").attribute("body").getValue());
							alfrags.addAll(parent.element("operand").elements("fragment"));
								//很多fragment 代表迁移(uml:OccurrenceSpecification）和内嵌的组合片段(uml:CombinedFragment)																														
							Iterator<Element> alfragsIterator = alfrags.iterator();
							
							ArrayList<String> sID= new ArrayList<String>();//存放所有的开始id
							ArrayList<String> tID= new ArrayList<String>();
							while(alfragsIterator.hasNext())//遍历这一个operand的所有fragment
							{ 
								Element child_fragsI = alfragsIterator.next();
								
//								if (child_fragsI.attribute("type").getValue().equals("uml:InteractionUse")) {//opt loop break里面如果是一个ref
//									REF ref = new REF();
//									ref.setRefID(child_fragsI.attributeValue("id"));
//									ref.setDiagramName(child_fragsI.attributeValue("name"));
//									String diagramID = findDiagramIDByChildID(ref.getRefID());//找到这个ref所属的diagram
//									ref.setLastMessageID(lastMessageIDByKeyWithDiagramID.get(diagramID));//设置ref的lastmessageID为这个图的lastmessageID
//									
//									ref.setInFragID(fragInfo.getFragId());
//									ref.setInFragName(fragInfo.getFragType());
//									lastMessageIDByKeyWithDiagramID.put(diagramID, "REF:"+ref.getInFragID());//做一个REF标记
//									umlREF.add(ref);
//								}
								if(child_fragsI.attribute("type").getValue().equals("uml:OccurrenceSpecification"))//2个fragment对应一个message
								{	
									String sourceID = child_fragsI.attribute("id").getValue();
									sID.add(sourceID);
									
									child_fragsI = alfragsIterator.next();
									
									String targetID = child_fragsI.attribute("id").getValue();
									tID.add(targetID);
									
									//通过sourceID和targetID找到对应的messageID
									String lastMessageID = MessageIDByKeyWithSourceIDAndTargetID.get(sourceID+targetID);
									//设置lastMessageID对应于DiagramID
//									lastMessageIDByKeyWithDiagramID.put(findDiagramIDByChildID(lastMessageID), lastMessageID);
										
									
								}
								else if(child_fragsI.attribute("type").getValue().equals("uml:CombinedFragment"))
								{
									q.add(child_fragsI);
									q_BigId.add(parent.attribute("id").getValue());
									//System.out.println("push:"+child_fragsI.attribute("interactionOperator").getValue());
									if(child_fragsI.attribute("interactionOperator").getValue().equals("alt")
											||child_fragsI.attribute("interactionOperator").getValue().equals("par"))
										findAltsFather.put(child_fragsI.attribute("id").getValue(), parent.attribute("id").getValue());
								}
							}		
							String[] s = new String[sID.size()];
							sID.toArray(s);
							String[] t = new String[tID.size()];
							tID.toArray(t);
							fragInfo.setSourceId(s);
							fragInfo.setTargetId(t);
							fragInfo.setBigId(q_BigId.poll());
							umlFragment.add(fragInfo);						
							
						}//非alt par 只有1个operand
						else if(fragInfo.getFragType().equals("alt") || fragInfo.getFragType().equals("par"))//alt ||par 多个operand
						{
							ArrayList<Element> operandList=new ArrayList();
							operandList.addAll(parent.elements("operand"));//alt的所有小操作域
							q_BigId.poll();
							
							for(Iterator<Element> operandIterator=operandList.iterator();operandIterator.hasNext();)//遍历所有operand
							{
								Element operandI = operandIterator.next();
								
								q.add(operandI);
								q_AorP.add(parent.attribute("interactionOperator").getValue());
								q_BigId.add(parent.attribute("id").getValue());
							}
							
													
							//System.out.println("是一个"+parent.attribute("interactionOperator").getValue());
						}
						
					}
					else if(parent.attribute("type").getValue().equals("uml:InteractionOperand"))//是组合片段中的操作域
					{
						WJFragment fragInfo = new WJFragment();
						//visit(parent);
						
						fragInfo.setFragId(parent.attribute("id").getValue());					
						fragInfo.setFragType(q_AorP.poll());//出列 alt或者par
						fragInfo.setFragCondition(parent.element("guard").element("specification").attribute("body").getValue());
						
						alfrags.addAll(parent.elements("fragment"));
						///
						Iterator<Element> alfragsIterator = alfrags.iterator();
						
						ArrayList<String> sID= new ArrayList<String>();
						ArrayList<String> tID= new ArrayList<String>();
						while(alfragsIterator.hasNext())//遍历这一个操作域的所有fragment
						{
							Element child_fragsI = alfragsIterator.next();
//							if (child_fragsI.attribute("type").getValue().equals("uml:InteractionUse")) {//这个操作域里面如果是一个ref
//								REF ref = new REF();
//								ref.setRefID(child_fragsI.attributeValue("id"));
//								ref.setDiagramName(child_fragsI.attributeValue("name"));
//								String diagramID = findDiagramIDByChildID(ref.getRefID());//找到这个ref所属的diagram
//								ref.setLastMessageID(lastMessageIDByKeyWithDiagramID.get(diagramID));//设置ref的lastmessageID为这个图的lastmessageID
//								
//								ref.setInFragID(fragInfo.getFragId());
//								ref.setInFragName(fragInfo.getFragType());
//								lastMessageIDByKeyWithDiagramID.put(diagramID, "REF:"+ref.getInFragID());//做一个REF标记
//								umlREF.add(ref);
//							}
							if(child_fragsI.attribute("type").getValue().equals("uml:OccurrenceSpecification"))
							{	
								String sourceID = child_fragsI.attribute("id").getValue();
								sID.add(sourceID);
								child_fragsI = alfragsIterator.next();
								String targetID = child_fragsI.attribute("id").getValue();
								tID.add(targetID);
								
								//通过sourceID和targetID找到对应的messageID
								String lastMessageID = MessageIDByKeyWithSourceIDAndTargetID.get(sourceID+targetID);
								//设置lastMessageID对应于DiagramID
//								lastMessageIDByKeyWithDiagramID.put(findDiagramIDByChildID(lastMessageID), lastMessageID);
									
								
 							}
							else if(child_fragsI.attribute("type").getValue().equals("uml:CombinedFragment"))
							{
								q.add(child_fragsI);
								q_BigId.add(parent.attribute("id").getValue());
								//System.out.println("push:"+child_fragsI.attribute("interactionOperator").getValue());
								if(child_fragsI.attribute("interactionOperator").getValue().equals("alt")
										||child_fragsI.attribute("interactionOperator").getValue().equals("par"))
									findAltsFather.put(child_fragsI.attribute("id").getValue(), parent.attribute("id").getValue());
							}
						}		
						String[] s = new String[sID.size()];
						sID.toArray(s);
						String[] t = new String[tID.size()];
						tID.toArray(t);
						fragInfo.setSourceId(s);
						fragInfo.setTargetId(t);
						fragInfo.setBigId(q_BigId.poll());
						umlFragment.add(fragInfo);		
					}
						
					
				}
								
			}
		}
		
		// DFS 找出ref 和 ref插入的位置
		for(Iterator<Element> fragListIterator=EAfragmentList.iterator();fragListIterator.hasNext();) {
			Element fragment = fragListIterator.next();
			DFSfragmentListForRef(null,null,fragment,lastMessageIDByKeyWithDiagramID,fragListIterator,MessageIDByKeyWithSourceIDAndTargetID);
			
		}
		
		Iterator iterator=umlFragment.iterator();  //对fragment片段的结构进行调整
		while(iterator.hasNext())
		{
			WJFragment I = (WJFragment)iterator.next();
			String bigid=I.getBigId();
			if(findAltsFather.containsKey(bigid))//alt操作域的父亲是alt 为了算法的实现，这里改成其祖父
			{
				I.setBigId(findAltsFather.get(bigid));
				I.setComId(bigid);
			}
			//由于最外层的alt的操作域找不到祖父，则交换bigID（alt's ID）和comID（null）
			if(!I.getBigId().equals("null")&&I.getComId().equals("null")&&(I.getFragType().equals("alt")||I.getFragType().equals("par")))
    		{
    			String temp = I.getBigId();
    			I.setBigId(I.comId);
    			I.setComId(temp);	    			
    		}
		}
//***************************************	 组合片段的嵌套读取	end	
		//虽然这里已经获得了组合片段的嵌套读取， 由于EA导出时组合片段的嵌套关系有可能会错乱， 需要用坐标信息作验证， 不符合的进行修正
		ArrayList<Element> EAElements = new ArrayList<Element>();
		EAElements.addAll(root.element("Extension").element("elements").elements("element"));
		for(Element element : EAElements) {
			try{
				FixFragmentTool.xrefValueById.put(element.attributeValue("idref"), 
						element.element("xrefs").attributeValue("value"));//这个字符串中有操作域的高度size
//				for(REF ref: umlREF) {//index=1, 从alias中获取
//					if (element.attributeValue("idref").equals(ref.refID)) {
//						String aliasValue = element.element("properties").attributeValue("alias");
//						String[] strs = aliasValue.split(",");
//						for(String str: strs) {
//							if (str.contains("index=")) {
//								ref.index = Integer.parseInt(str.split("index=")[1]);
//							}
//						}
// 						
//					}
//				}
			} catch (Exception e) {
				System.out.println("exception:获取操作域高度");
			}
		}
		for(WJFragment fragment : umlFragment) {//设置fragment的rectangle
			if (!fragment.getComId().equals("null")) {//说明是operand 操作域 需要从父fragment中获取边框 ↑
				try {
					fragment.rectangle = FixFragmentTool.operandRectangle(fragment);//设置操作域的rectangle
				} catch (Exception e) {
					System.out.println("@@@没有找到fragment的rectangle---"+fragment.getFragCondition());
				}
			} else {
				try {
					fragment.rectangle = FixFragmentTool.rectangleById.get(fragment.fragId);
				} catch (Exception e) {
					System.out.println("@@@没有找到fragment的rectangle---"+fragment.getFragCondition());
				}
			}
		}
		
		
		//设定message最后的值 0.设定各种值 1.设置5种时间约束 2.在哪个fragment中
		for(Iterator<MessageComplete> messageListIterator=messageList.iterator();messageListIterator.hasNext();)
		{
			/////////////////////////EAmessage的遍历
			MessageComplete EAmessage=messageListIterator.next();
			WJMessage message=new WJMessage();
			//1.
			message.setName(EAmessage.getName());					//name
			message.setConnectorId(EAmessage.getConnectorId());//messageID						
			message.setSendEvent(EAmessage.getSendEvent());		//event	
			message.setSourceId(EAmessage.getSourceId());		//sourceid
			message.setTragetId(EAmessage.getTragetId());		//targetid
			message.setFromId(EAmessage.getFromId());
			message.setToId(EAmessage.getToId());
			message.setT1(EAmessage.getT1());
			message.setT2(EAmessage.getT2());
			message.setEnerge(EAmessage.getEnerge());
			message.setR1(EAmessage.getR1());
			message.setR2(EAmessage.getR2());
			message.setPointY(EAmessage.getPointY());
			if (EAmessage.getInString() != null) {
				message.setInString(EAmessage.getInString());
			}
			if (EAmessage.getOutString() != null) {
				message.setOutString(EAmessage.getOutString());
			}
			//2.
			if(EAmessage.getStyleValue() != null)
			setMessageTimeDurations(message, EAmessage.getStyleValue());
			for(Iterator<WJFragment> fragIterator=umlFragment.iterator();fragIterator.hasNext();)
			{
				WJFragment fragment=fragIterator.next();
				boolean finish = false;
				String[] s = fragment.getSourceId();//一个fragment有多个source
				String[] t = fragment.getTargetId();
				for(int i=0; i< fragment.getSourceId().length;i++)//看message在哪个fragment中
					if(message.getSourceId().equals(s[i]) && message.getTragetId().equals(t[i]))
					{	
						message.setInFragId(fragment.getFragId());
						message.setInFragName(fragment.getFragType());
						finish = true;
						break;
					}
				
				if(finish)
					break;
			}
			umlMessageFinal.add(message);//最终得到的message
			
		}
		///找出lifeline fragment message ref 的归属
		for(WJDiagramsData diagramData : umlAllDiagramData) {
			for(WJLifeline lifeline : umlLifeLines) {
				if (diagramData.getIds().contains(lifeline.getlifeLineId().substring(13))) {
					diagramData.getLifelineArray().add(lifeline);
				}
			}
			for(WJFragment fragment : umlFragment) {
				if (diagramData.getIds().contains(fragment.getFragId().substring(13))) {
					diagramData.getFragmentArray().add(fragment);
				}
			}
			
			for(WJMessage message : umlMessageFinal) {
				if (diagramData.getIds().contains(message.getConnectorId().substring(13))) {
					diagramData.getMessageArray().add(message);
				}
			}
			for(REF ref : umlREF) {
				if (diagramData.getIds().contains(ref.getRefID().substring(13))) {
					diagramData.getRefArray().add(ref);
					ref.rectangle = FixFragmentTool.rectangleById.get(ref.refID);
					ref.index = FixFragmentTool.refIndexInDiagram(ref, diagramData);
				}
			}
			
			//以top left right bottom 找到外一层的fragment
			FixFragmentTool.fixFragmentsOfOneDiagram(diagramData);
		}
		
		//图的连接
		
		//对ref进行处理 合并子图到父图
		for(WJDiagramsData diagramData : umlAllDiagramData) {
			DFSDiagramByREF(diagramData);
		}
		
		umlAllDiagramData.addAll(displayDiagrams);
		
	}
	
	private void DFSfragmentListForRef(Element fragmentFather,Element operandFather,Element fragment, HashMap<String, String> lastMessageIDByKeyWithDiagramID, Iterator<Element> fragListIterator, HashMap<String, String> messageIDByKeyWithSourceIDAndTargetID) {

		if (fragment.attributeValue("id").equals("EAID_BD50CFEE_3602_40ff_B884_E6438771B272")) {
			int a = 0;
			a ++;
		}
		if (fragment.attributeValue("type").equals("uml:OccurrenceSpecification")) {//是一条消息
			String sourceID = fragment.attribute("id").getValue();
			fragment = fragListIterator.next();
			String targetID = fragment.attribute("id").getValue();		
			//通过sourceID和targetID找到对应的messageID
			String lastMessageID = messageIDByKeyWithSourceIDAndTargetID.get(sourceID+targetID);
			//设置lastMessageID对应于DiagramID
			lastMessageIDByKeyWithDiagramID.put(findDiagramIDByChildID(lastMessageID), lastMessageID);
		}
		
		if (fragment.attribute("type").getValue().equals("uml:InteractionUse")) {//如果是一个ref
			REF ref = new REF();
			ref.setRefID(fragment.attributeValue("id"));
			ref.setDiagramName(fragment.attributeValue("name"));
			String diagramID = findDiagramIDByChildID(ref.getRefID());//找到这个ref所属的diagram
			ref.setLastMessageID(lastMessageIDByKeyWithDiagramID.get(diagramID));//设置ref的lastmessageID为这个图的lastmessageID
			if (operandFather == null) {
				ref.setInFragID("null");
				ref.setInFragName("SD");
			} else {
				if (fragmentFather.attributeValue("interactionOperator").equals("par") 
						|| fragmentFather.attributeValue("interactionOperator").equals("alt")) {
					ref.setInFragID(operandFather.attributeValue("id"));
				} else {
					ref.setInFragID(fragmentFather.attributeValue("id"));
				}
				
				ref.setInFragName(fragmentFather.attributeValue("interactionOperator"));
			}
			
			lastMessageIDByKeyWithDiagramID.put(diagramID, "REF:"+ref.getInFragID());//做一个REF标记
			umlREF.add(ref);
		}
		
		if(fragment.attribute("type").getValue().equals("uml:CombinedFragment")) {
			ArrayList<Element> operandList = new ArrayList<Element>();
			operandList.addAll(fragment.elements("operand"));
			
			
			for(Element operand : operandList) {
				ArrayList<Element> EAfragmentListChild=new ArrayList();
				EAfragmentListChild.addAll(operand.elements("fragment"));
				for(Iterator<Element> fragListIteratorChild = EAfragmentListChild.iterator();fragListIteratorChild.hasNext();) {
					Element fragmentChild = fragListIteratorChild.next();
					DFSfragmentListForRef(fragment,operand,fragmentChild,lastMessageIDByKeyWithDiagramID,fragListIteratorChild,messageIDByKeyWithSourceIDAndTargetID);
				}
			}
			
		}
	}

//"paramvalues=barometer;;;;;;alias=io=in,;"
	private String GetAliasIo(String styleValue) {
		String[] strings = styleValue.split(";");
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].contains("alias=")) {//strings[i] = "alias=io=in,a=1,"
				String stringValue = strings[i].split("alias=")[1];//"io=in,a=1,"
				String[] values = stringValue.split(",");
				for (int j = 0; j < values.length; j++) {
					if (values[j].contains("io")) {
						if (values[j].contains("in")) {
							return "in";
						} else {
							return "out";
						}
					}
				}
			}
		}
		
		return null;
	}

/*method-----------------------------------------------------------*/	
	private void DFSDiagramByREF(WJDiagramsData diagramData) {
		if (diagramData.refArray.size() == 0) {//本身没有引用 或者引用已经被删光了 已经是完全图 
			return ;
		}
		
		for(REF ref : diagramData.refArray) {
			WJDiagramsData childDiagram = findDiagramByName(ref.getDiagramName());
			DFSDiagramByREF(childDiagram);//处理子图 使其变成无引用的完全图
			fixDiagramData(diagramData,childDiagram,ref);
			//diagramData.refArray.remove(ref);
		}
		diagramData.refArray.clear();
	}
	public static WJMessage testMessage = new WJMessage();
	//将合并完成的没有引用的完全子图 合并到 父图
	private void fixDiagramData(WJDiagramsData diagramData, WJDiagramsData childDiagram, REF ref) {
		//添加lifeline
		for(WJLifeline lifeline : childDiagram.getLifelineArray()) {
			if (!diagramData.getLifelineArray().contains(lifeline)) {//添加父图没有的lifeline
				diagramData.getLifelineArray().add(lifeline);
			}
		}
		
		//修改最外层的fragment 复制一份到父图中
		ArrayList<WJFragment> copyFragmentArray = new ArrayList<WJFragment>();
		for(WJFragment fragment : childDiagram.getFragmentArray()) {//添加所有的组合片段
			WJFragment copyFragment = (WJFragment) fragment.clone();
			//改变fragment的id 避免重复引用导致的重复fragment id
			addFragmentMarkId(copyFragment);
			if (fragment.BigId.equals("null")) {//最外面层的sd
				copyFragment.setBigId(ref.getInFragID());	
			}
			copyFragmentArray.add(copyFragment);	
		}
		diagramData.getFragmentArray().addAll(copyFragmentArray);
		
		//先复制一份子图的messageArray
		ArrayList<WJMessage> copyMessageArray = new ArrayList<WJMessage>(); 
		for(WJMessage message : childDiagram.getMessageArray()) {
			WJMessage copyMessage = (WJMessage) message.clone();
			copyMessageArray.add(copyMessage);
		}
		//然后将infragID为"null"(说明在SD中) 改为ref片段所在的组合片段id
		for(WJMessage message : copyMessageArray) {
			addMessageMarkId(message);
			if (message.getInFragId().equals("null")) {
				message.setInFragId(ref.getInFragID());
				message.setInFragName(ref.getInFragName());
			}
		}
		
		//把copyMessage 放到index之后
		diagramData.getMessageArray().addAll(ref.index, copyMessageArray);
		for(REF refi : diagramData.refArray) {
			refi.index += copyMessageArray.size();
		}
		
		markId ++;
//		//将copyMessage加到特定位置 : init REF EA
//		//1. init 表示子图的引用在初始 
//		if (ref.getLastMessageID().equals("init")) {
//			diagramData.getMessageArray().addAll(0, copyMessageArray);
//			diagramData.setRefEndTo(copyMessageArray.size());
//		} else if (ref.getLastMessageID().substring(0, 3).equals("REF")) {
//		//2. REF 说明上一个是ref 插入到上一个ref结束之后
//			diagramData.getMessageArray().addAll(diagramData.getRefEndTo(), copyMessageArray);
//			diagramData.setRefEndTo(diagramData.getRefEndTo() + copyMessageArray.size());//设置新的endTo
//		} else {//最后一种最普通的情况 就是直接插入到某个message之后
//			for(int i = 0; i < diagramData.getMessageArray().size(); i++) {
//				if (diagramData.getMessageArray().get(i).getConnectorId().equals(ref.getLastMessageID())) {
//					//找到第n个 插入到n+1位置之前
//					diagramData.getMessageArray().addAll(i + 1, copyMessageArray);
//					diagramData.setRefEndTo(i + copyMessageArray.size() + 1);
//					break;
//				}
//			}
//		}
		
		
		//添加要展示的图  复制diagramData
//		WJDiagramsData displaySD = new WJDiagramsData();
//		displaySD.diagramID = diagramData.diagramID + "-----display" + diagramData.displayCount;
//		displaySD.name = diagramData.name + "-----display" + diagramData.displayCount;
//		displaySD.fragmentArray.addAll(diagramData.fragmentArray);
//		displaySD.lifelineArray.addAll(diagramData.lifelineArray);
//		displaySD.messageArray.addAll(diagramData.messageArray);
//		displaySD.ids.addAll(diagramData.ids);
//		displaySD.refArray.addAll(diagramData.refArray);
//		displaySD.RefEndTo = diagramData.RefEndTo;
//		diagramData.displayCount++;
//		//去掉refendto之后的消息
//		while(displaySD.messageArray.size() > displaySD.RefEndTo) {
//			displaySD.messageArray.remove(displaySD.RefEndTo);
//		}
//		displayDiagrams.add(displaySD);
		

	}

	private void addMessageMarkId(WJMessage message) {
		if (message.inFragId !=  "null") {
			message.inFragId += "_"+markId;
		}
		
		message.connectorId += "_"+markId;
	}

	private void addFragmentMarkId(WJFragment copyFragment) {
		if (copyFragment.BigId != "null") {
			copyFragment.BigId += "_"+markId;
		}
		if (copyFragment.comId != "null") {
			copyFragment.comId += "_"+markId;
		}
		copyFragment.fragId += "_"+markId;
	}

	private WJDiagramsData findDiagramByName(String diagramName) {
		
		for(WJDiagramsData diagramsData :umlAllDiagramData) 
		{
			//由于diagram存储的是13位后的id 所以用后13位进行判断
			if (diagramsData.getName().equals(diagramName))//包含这个子id
				return diagramsData;
			
		}
		return null;
	}

	private String findDiagramIDByChildID(String childID) {

		for(WJDiagramsData diagramsData :umlAllDiagramData) 
		{
			//由于diagram存储的是13位后的id 所以用后13位进行判断
			if (diagramsData.ids.contains(childID.substring(13)))//包含这个子id
				return diagramsData.diagramID;
			
		}
		return null;
	}

//DCBMX=0;DCBMGUID={65DF8856-63B5-423a-9BD1-952DEA23D616};SEQDC=10000;SEQDO=10002;SEQTC=10003;SEQTO=10004;DCBM=10001;
//alias=io=in,;paramvalues=ininininininininin;DCBMX=0;SEQDO=33333333333d>=10s;
	//message设定5种时间约束
	private void setMessageTimeDurations(WJMessage message, String styleValue) {
		if (styleValue == null) return;
//		String[] strArray = styleValue.split(";");
//		for (int i = 0; i < strArray.length; i++) {
//			String[] nameAndValue = strArray[i].split("=");
//			if (nameAndValue[0].equals("SEQDC")) {
//				message.setSEQDC(nameAndValue[1]);
//			} else if (nameAndValue[0].equals("SEQDO")){
//				message.setSEQDO(nameAndValue[1]);
//			} else if (nameAndValue[0].equals("SEQTC")){
//				message.setSEQTC(nameAndValue[1]);
//			} else if (nameAndValue[0].equals("SEQTO")){
//				message.setSEQTO(nameAndValue[1]);
//			} else if (nameAndValue[0].equals("DCBM")){
//				message.setDCBM(nameAndValue[1]);
//			}
//		}
		try {
			String DCBM = "null";
			String SEQDO = "null";
			
			String patternString = "DCBM=([A-Za-z0-9]+)([<>]?)(=?)(\\d+)([.]?)(\\d+)s;";
			Pattern pattern = Pattern.compile(patternString);
			Matcher matcher = pattern.matcher(styleValue);
			if (matcher.find()) {
				DCBM = matcher.group(0).split("DCBM=")[1];
			}
			String patternString1 = "SEQDO=([A-Za-z0-9]+)([<>]?)(=?)(\\d+)([.]?)(\\d+)s;";
			Pattern pattern1 = Pattern.compile(patternString1);
			Matcher matcher1 = pattern1.matcher(styleValue);
			if (matcher1.find()) {
				SEQDO = matcher1.group(0).split("SEQDO=")[1];
			}
			message.setSEQDO(SEQDO);
			message.setDCBM(DCBM);
		} catch (Exception e) {
			System.out.println("exception:时间约束");
		}
	}

	public ArrayList<REF> getUmlREF() {
		return umlREF;
	}


	public ArrayList<WJDiagramsData> getUmlAllDiagramData() {
		return umlAllDiagramData;
	}

	public HashMap<String , String>	getfindAltsFather()
	{
		return findAltsFather;
	}

	public void setUmlAllDiagramData(ArrayList<WJDiagramsData> umlAllDiagramData) {
		this.umlAllDiagramData = umlAllDiagramData;
	}

	public ArrayList<WJLifeline> getUmlLifeLines() {
		return umlLifeLines;
	}

	public void setUmlLifeLines(ArrayList<WJLifeline> umlLifeLines) {
		this.umlLifeLines = umlLifeLines;
	}

	public ArrayList<MessageClass> getUmlMessages() {
		return umlMessages;
	}

	public void setUmlMessages(ArrayList<MessageClass> umlMessages) {
		this.umlMessages = umlMessages;
	}

	public ArrayList<WJFragment> getUmlFragment() {
		return umlFragment;
	}

	public void setUmlFragment(ArrayList<WJFragment> umlFragment) {
		this.umlFragment = umlFragment;
	}

	public void setUmlREF(ArrayList<REF> umlREF) {
		this.umlREF = umlREF;
	}

	public ArrayList<WJMessage> getUmlMessageFinal() {
		return umlMessageFinal;
	}

	public void setUmlMessageFinal(ArrayList<WJMessage> umlMessageFinal) {
		this.umlMessageFinal = umlMessageFinal;
	}
	
}
	

