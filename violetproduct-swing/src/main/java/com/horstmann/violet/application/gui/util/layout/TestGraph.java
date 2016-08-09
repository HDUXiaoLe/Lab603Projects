package com.horstmann.violet.application.gui.util.layout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.graph.DirectedGraph;
import org.eclipse.draw2d.graph.DirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.Node;
import org.jgraph.JGraph;
import org.jgraph.graph.ConnectionSet;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;

import com.horstmann.violet.application.gui.util.layout.NodeBean;
import com.horstmann.violet.application.gui.util.layout.EdgeBean;
import com.horstmann.violet.application.gui.util.layout.UnionEdge;
import com.horstmann.violet.application.gui.util.layout.ReadXml;//导入xml

import com.realpersist.gef.layout.NodeJoiningDirectedGraphLayout;

/**
 * @author walnut
 * @version 创建时间：2007-7-18
 */
public class TestGraph extends JApplet {

	public Map gefNodeMap = null;//建立gef图,初始化

	public Map graphNodeMap = null;//建立graph图，初始化

	public List edgeList = null;//边初始化

	DirectedGraph directedGraph = null;//

	JGraph graph = null;
	
	String filename;
		
	public TestGraph(String filename) {
     	this.filename=filename;
	}
	public int[] init(int a){
		
		graphInit();
		return (paintGraph(a));			
	}
	public int[] paintGraph(int a) {
		int EdgePosition[]=new int[30];
		try {		
		//测试数据
			ReadXml read=new ReadXml(filename);
			int i=0,I,J;
			String[] VertexID=read.GetID(a);
			int Vertexcount=read.getLocationNum(a);		
			int Edgecount=read.getTransitionNum(a);		
		    List edgeBeanList =new ArrayList();
			List<NodeBean> list=new ArrayList<NodeBean>();
			List<EdgeBean> List=new ArrayList<EdgeBean>();	
			int A[][]=new ReadXml(filename).find(a);
			/*for(int m=0;m<Vertexcount;m++)		
				for(int n=0;n<Vertexcount;n++)
					System.out.println(A[m][n]);*/
			int B[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
			int flag=0;//表示回路边
			int Flag=0;//表示自己到自己的边
		while(i<Vertexcount)
		{
 			list.add(new NodeBean(VertexID[i]));
 			i++;
		}
			for( I=0;I<Vertexcount;I++)			
			{	
				for(J=0;J<Vertexcount;J++)
					if(A[I][J]==1&&A[I][J]!=A[J][I]&&J!=I)
		    {
						List.add(new EdgeBean(list.get(I),list.get(J),new Long(100)));
			}
					else if(A[I][J]==1&&A[I][J]==A[J][I]&&J!=I&&B[I]!=1)
					{
						List.add(new EdgeBean(list.get(I),list.get(J),new Long(100)));
						B[I]=B[J]=1;
						flag++;
					}
					else if(A[I][J]==1&&I==J)
						Flag++;				       
			}				
			for(int K=0;K<Edgecount-flag-Flag;K++)
			{
				edgeBeanList.add(List.get(K));		
			}
			// 解析数据，构造图
			gefNodeMap = new HashMap();
			graphNodeMap = new HashMap();
			edgeList = new ArrayList();
			directedGraph = new DirectedGraph();
			GraphModel model = new DefaultGraphModel();
			graph.setModel(model);
			Map attributes = new Hashtable();
			// Set Arrow
			Map edgeAttrib = new Hashtable();
			GraphConstants.setLineEnd(edgeAttrib, GraphConstants.ARROW_CLASSIC);
			GraphConstants.setEndFill(edgeAttrib, true);
			graph.setJumpToDefaultPort(true);	
			if (edgeBeanList == null || edgeBeanList.size() == 0) {
				graph.repaint();
				return null;
			}
			Iterator edgeBeanIt = edgeBeanList.iterator();
			while (edgeBeanIt.hasNext()) {
				EdgeBean edgeBean = (EdgeBean) edgeBeanIt.next();
				NodeBean sourceAction = edgeBean.getsourceNodeBean();
				NodeBean targetAction = edgeBean.gettargetNodeBean();
				Long ageLong = edgeBean.getStatCount();
				String edgeString = "(" + ageLong +  ")";
				addEdge(sourceAction, targetAction, 100, edgeString);
			}
			// 自动布局 首先用DirectedGraphLayout如果出现异常（图不是整体连通的）则采用NodeJoiningDirectedGraphLayout
			// 后者可以对非连通图进行布局坐标计算，但效果不如前者好，所以首选前者，当前者不可以处理时采用后者
			try{
				new DirectedGraphLayout().visit(directedGraph);
			}catch(Exception e1){
				new NodeJoiningDirectedGraphLayout().visit(directedGraph);
			}	
			int self_x=50;
			int self_y=50;
			int base_y=10;
			if(graphNodeMap!=null&&gefNodeMap!=null&&graphNodeMap.size()>gefNodeMap.size()){
				base_y=self_y+GraphProp.NODE_HEIGHT;
			}		
			// 向图中添加节点node
			Collection nodeCollection = graphNodeMap.values();
			if (nodeCollection != null) {
				Iterator nodeIterator = nodeCollection.iterator();
				if (nodeIterator != null) {
					while (nodeIterator.hasNext()) {
						DefaultGraphCell node = (DefaultGraphCell) nodeIterator.next();
						NodeBean userObject = (NodeBean) node.getUserObject();
						if (userObject == null) {
							continue;
						}
						Node gefNode = (Node) gefNodeMap.get(userObject);			
						if(gefNode==null){
							// 这是因为当一个节点有一个指向自身的边的时候，我们在gefGraph中并没有计算这条边（gefGraph不能计算包含指向自己边的布局），
							// 所以当在所要画的图中该节点只有一条指向自身的边的时候，我们在gefNodeMap中就找不到相应节点了
							// 这个时候，我们手工给出该节点的 X,Y 坐标
							gefNode=new Node();
							gefNode.x=self_x;
							gefNode.y=self_y-base_y;
							self_x+=(10+GraphProp.NODE_WIDTH);
						}
						for(int h=0;h<Vertexcount;h++)
						{
							if(list.get(h).equals(userObject))						
						{
							EdgePosition[2*h]=gefNode.x;
							EdgePosition[2*h+1]=gefNode.y;
						}							
						}					
						Map nodeAttrib = new Hashtable();
						GraphConstants.setBorderColor(nodeAttrib, Color.black);
						Rectangle2D Bounds = new Rectangle2D.Double(gefNode.x,gefNode.y+base_y, GraphProp.NODE_WIDTH,GraphProp.NODE_HEIGHT);
						GraphConstants.setBounds(nodeAttrib, Bounds);
						attributes.put(node, nodeAttrib);						
					}// while		
				}
			}
			for(int L=0;L<Vertexcount;L++)
						System.out.println(EdgePosition[L]);			
			// 向图中添加边  
			if (edgeList == null) {
				//logger.error("edge list is null");
				return null ;
			}
			for (int i1 = 0; i1 < edgeList.size(); i1++) {
				UnionEdge unionEdge = (UnionEdge) edgeList.get(i1);
				if (unionEdge == null) {
					//logger.error("union edge is null");
					continue;
				}
				ConnectionSet cs = new ConnectionSet(unionEdge.getEdge(),unionEdge.getSourceNode().getChildAt(0), unionEdge.getTargetNode().getChildAt(0));
				Object[] cells = new Object[] { unionEdge.getEdge(),unionEdge.getSourceNode(), unionEdge.getTargetNode() };
				attributes.put(unionEdge.getEdge(), edgeAttrib);
				model.insert(cells, attributes, cs, null, null);
			}
		//	graph.repaint();		
	} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return EdgePosition;
	}
	
	private void graphInit() {

		// Construct Model and Graph
		GraphModel model = new DefaultGraphModel();
	    graph = new JGraph(model);
		graph.setSelectionEnabled(true);
		
		// 显示applet
		JScrollPane scroll=new JScrollPane(graph);
		this.getContentPane().add(scroll);
		
		this.setSize(new Dimension(800, 800));

	}


	/**
	 * @param source
	 * @param target
	 */
	private void addEdge(NodeBean source, NodeBean target, int weight,String edgeString) {

		if (source == null || target == null) {
			return;
		}
		if (gefNodeMap == null) {
			gefNodeMap = new HashMap();
		}
		if (graphNodeMap == null) {
			graphNodeMap = new HashMap();
		}
		if (edgeList == null) {
			edgeList = new ArrayList();
		}
		if (directedGraph == null) {
			directedGraph = new DirectedGraph();
		}

		// 建立GEF的 node edge将来用来计算graph node的layout
		addEdgeGef(source, target,weight,edgeString);
		
		// 建立真正要用的graph的 node edge
		DefaultGraphCell sourceNode = null;
		DefaultGraphCell targetNode = null;
		sourceNode = (DefaultGraphCell) graphNodeMap.get(source);
		if (sourceNode == null) {
			sourceNode = new DefaultGraphCell(source);
			sourceNode.addPort();
			graphNodeMap.put(source, sourceNode);
		}
		targetNode = (DefaultGraphCell) graphNodeMap.get(target);
		if (targetNode == null) {
			targetNode = new DefaultGraphCell(target);
			targetNode.addPort();
			graphNodeMap.put(target, targetNode);
		}
		DefaultEdge edge = new DefaultEdge(edgeString);
		UnionEdge unionEdge = new UnionEdge();
		unionEdge.setEdge(edge);
		unionEdge.setSourceNode(sourceNode);
		unionEdge.setTargetNode(targetNode);
		edgeList.add(unionEdge);
	}
	/**
	 * @param source
	 * @param target
	 * @param weight
	 * @param edgeString
	 */
	private void addEdgeGef(NodeBean source, NodeBean target, int weight, String edgeString) {

		if(source.equals(target)){
			return;
		}
		// 建立GEF的 node edge将来用来计算graph node的layout
		Node gefSourceNode = null;
		Node gefTargetNode = null;
		gefSourceNode = (Node) gefNodeMap.get(source);
		if (gefSourceNode == null) {
			gefSourceNode = new Node();
			gefSourceNode.width = GraphProp.NODE_WIDTH;
			gefSourceNode.height = GraphProp.NODE_WIDTH;
			//gefSourceNode.setPadding(new Insets(GraphProp.NODE_TOP_PAD,GraphProp.NODE_LEFT_PAD, GraphProp.NODE_BOTTOM_PAD,GraphProp.NODE_RIGHT_PAD));
			directedGraph.nodes.add(gefSourceNode);
			gefNodeMap.put(source, gefSourceNode);
		}
		
		gefTargetNode = (Node) gefNodeMap.get(target);
		if (gefTargetNode == null) {
			gefTargetNode = new Node();
			gefTargetNode.width = GraphProp.NODE_WIDTH;
			gefTargetNode.height = GraphProp.NODE_WIDTH;
			//gefTargetNode.setPadding(new Insets(GraphProp.NODE_TOP_PAD,GraphProp.NODE_LEFT_PAD, GraphProp.NODE_BOTTOM_PAD,GraphProp.NODE_RIGHT_PAD));
			directedGraph.nodes.add(gefTargetNode);
			gefNodeMap.put(target, gefTargetNode);
		}
		
		Edge gefEdge1=null;
		try{
			gefEdge1 = new Edge(gefSourceNode, gefTargetNode);
			gefEdge1.weight = weight;
			directedGraph.edges.add(gefEdge1);
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	

}