package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.List;

import javax.swing.RepaintManager;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.IGridSticker;
import com.horstmann.violet.product.diagram.abstracts.edge.HorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.IHorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.SEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.StatelineParent;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.node.TimingDiragramConstants;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPartBehaviorManager;
import com.horstmann.violet.workspace.editorpart.IEditorPartSelectionHandler;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

public class TimmingDiagramStateLineChange extends AbstractEditorPartBehavior{
	  public TimmingDiagramStateLineChange(IEditorPart editorPart, IGraphToolsBar graphToolsBar)
	    {
	        this.editorPart = editorPart;
	        this.graph = editorPart.getGraph();
	        this.selectionHandler = editorPart.getSelectionHandler();
	        this.graphToolsBar = graphToolsBar;
	        this.behaviorManager = editorPart.getBehaviorManager();
	    }
	  @Override
	  public void onMouseReleased(MouseEvent e){				
	  }   
      @Override
	  public void onMousePressed(MouseEvent e){
		  //添加所有的
	        if (e.getClickCount() > 1){
	            return;
	        }           
	        if (e.getButton() != MouseEvent.BUTTON1){
	            return;
	        } 
	        GraphTool selectedTool = this.selectionHandler.getSelectedTool();
	        if (GraphTool.SELECTION_TOOL.equals(selectedTool))
	        {
	            return;
	        }	      
	        List<INode> selectedNodes = this.selectionHandler.getSelectedNodes();
	        List<IEdge> selectedEdges = selectionHandler.getSelectedEdges();  
	        boolean isOnlyOneNodeSelected = (selectedNodes.size() == 1 && selectedEdges.size() == 0);
	        //只有一个节点被选取
	        if (!isOnlyOneNodeSelected) {
	           return;//跳出函数
	        }  		     
	        selectedNode=selectedNodes.get(0);       	     
	        String str1= "class com.horstmann.violet.product.diagram.timing.State_Lifeline";
	        if (!selectedNode.getClass().toString().equals(str1)){
	           return;
	        } 
	        s= ((StatelineParent)selectedNode).getStateLine();	     
	      	Rectangle2D bounds=selectedNode.getBounds();
	      	double width=selectedNode.getWidth();
	        Point2D P1=new Point2D.Double((double)(bounds.getX()+150), (double)(bounds.getMaxY()-TimingDiragramConstants.StateSpace));
  		     Point2D P2=new Point2D.Double((double)(bounds.getX()+width), (double)(bounds.getMaxY()-TimingDiragramConstants.StateSpace));	
	   //   IGridSticker gridSticker = graph.getGridSticker();
	      //获取stateline对象
	        Point mousePressPoint =e.getPoint();//获取鼠标按下时候的点	                		                         				    		 	       
	      if(s.gethorizontalChild().size()==0){
	    	     startUndoRedoCapture();	    	  
	    	     double witdh=P2.getX()-P1.getX();//设置bounds的宽度	    	   
		         double height=20;//设置bounds的高度
	    	     initBounds =new Rectangle2D.Double(P1.getX(),P1.getY()-10,witdh,height);//设置鼠标感应区域
	    	  if(initBounds.contains(mousePressPoint)){		    		
	    		 double  w=mousePressPoint.getX()-P1.getX();//记录转折点与开始点的距离
		    	 Point2D p1 =new Point2D.Double(P1.getX()+w,P1.getY()-TimingDiragramConstants.StateSpace);//构造转折点（这里new一个点对象造成不能改变）
		    	 Point2D p0=new Point2D.Double(P1.getX()+w,P1.getY());
		    	 statelineEndLocation=new Point2D.Double(P2.getX(),p1.getY());		    		    	 	    	
		    	 IHorizontalChild newchild1=new HorizontalChild();		
				 newchild1.setStart(P1);
				 newchild1.setEnd(p0);
		    	 s.addhorizontalChild(newchild1);
		    	 beforeLastPoint=p1;
		    	 IHorizontalChild newchild2=new HorizontalChild();
				 newchild2.setStart(beforeLastPoint);
				 newchild2.setEnd(statelineEndLocation);
		    	 s.addhorizontalChild(newchild2); 
		    	 stopUndoRedoCapture();
		    	 //加入转折点产生的点		    		    
	    	  }
	      }
	    	  if(s.gethorizontalChild().size()>0){ 	    		 
	    		  Point2D bp=(s.gethorizontalChild()).get((s.gethorizontalChild().size()-1)).getStart();
	    		 //bp节点是倒数第二个节点
	    		  double x=bp.getX();
	    		  double y=bp.getY();
	    		  double witdh2=P2.getX()-bp.getX();
	    		  double height2 =10;
	    		  bound1=new Rectangle2D.Double(x, y-10, witdh2,height2);//上方bound
	    		  bound2=new Rectangle2D.Double(x,y,witdh2,height2);//下方bound	    	
	    		  if(bound2.contains(mousePressPoint)){		    		
	    		  startUndoRedoCapture();
	    		  Point2D tp0= new Point2D.Double(mousePressPoint.getX(),y);
	    		  Point2D tp1= new Point2D.Double(mousePressPoint.getX(),y-TimingDiragramConstants.StateSpace);
	    			//editorPart.getSwingComponent().setCursor();
	    		  statelineEndLocation=new Point2D.Double(P2.getX(),tp1.getY());	    			    			    		   		
	    		  s.gethorizontalChild().remove(s.gethorizontalChild().size()-1);
	    		  if(mousePressPoint.x!=bp.getX()){	
	    			IHorizontalChild newchild1=new HorizontalChild();
					newchild1.setStart(bp);
					newchild1.setEnd(tp0);
	    		    s.addhorizontalChild(newchild1);
	    			}
	    		  beforeLastPoint=tp1;
	    		  IHorizontalChild newchild2=new HorizontalChild();
				  newchild2.setStart(beforeLastPoint);
				  newchild2.setEnd(statelineEndLocation);
	    		  s.addhorizontalChild(newchild2);	
	    		  stopUndoRedoCapture();
	    	  }
	    		  if(bound1.contains(mousePressPoint)){		    		 
	    		  startUndoRedoCapture();
	    		  Point2D tp2= new Point2D.Double(mousePressPoint.getX(),y);
	    		  Point2D tp3= new Point2D.Double(mousePressPoint.getX(),y+TimingDiragramConstants.StateSpace);
	    		  statelineEndLocation=new Point2D.Double(P2.getX(),tp3.getY());	    		    		    					    		
	    		  s.gethorizontalChild().remove(s.gethorizontalChild().size()-1);
	    		  if(mousePressPoint.x!=bp.getX()){	
	    			  IHorizontalChild newchild1=new HorizontalChild();
						newchild1.setStart(bp);
						newchild1.setEnd(tp2);
		    			s.addhorizontalChild(newchild1);	    			
	    		  }
	    		    beforeLastPoint=tp3;
	    		    IHorizontalChild newchild2=new HorizontalChild();
					newchild2.setStart(beforeLastPoint);
					newchild2.setEnd(statelineEndLocation);
	    			s.addhorizontalChild(newchild2);	    		 	
	    		    stopUndoRedoCapture();
	    		  }	 	    		 
	    	  }	    
      } 
	  @Override
	  public void onMouseMoved(MouseEvent e){
		  GraphTool selectedTool = this.selectionHandler.getSelectedTool();
		  if (GraphTool.SELECTION_TOOL.equals(selectedTool))
	        {
	            return;
	        }	      
	        List<INode> selectedNodes = this.selectionHandler.getSelectedNodes();
	        List<IEdge> selectedEdges = selectionHandler.getSelectedEdges();  
	        boolean isOnlyOneNodeSelected = (selectedNodes.size() == 1 && selectedEdges.size() == 0);
	        //只有一个节点被选取
	       if (!isOnlyOneNodeSelected) {
	           return;//跳出函数
	       }  		       
	       selectedNode=selectedNodes.get(0);       
	       String str1= "class com.horstmann.violet.product.diagram.timing.State_Lifeline";
	       if (!selectedNode.getClass().toString().equals(str1)){
	           return;
	       }
	      s= ((StatelineParent)selectedNode).getStateLine();
	      this.editorPart.getSwingComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	      //默认的鼠标样式
	      for(IHorizontalChild horizontalChild :s.gethorizontalChild())
	       { 
	    	 
	    	  if(s.getChildTopBounds(horizontalChild).contains(e.getPoint()))
	    	  {
	    		  //暂时设置成这种鼠标样式
	    		  this.editorPart.getSwingComponent().setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
	    	  }
	       
	    	  else if(s.getChildBottomBounds(horizontalChild).contains(e.getPoint()))
	    	  {	    		 
	    		  //暂时设置成这种鼠标样式
	    		  this.editorPart.getSwingComponent().setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
	    	  }	    	
	       }		      
	  }	
	  private INode getSelectedNode()
	    {
	        if (this.selectedNode == null)
	        {
	            if (this.selectionHandler.getSelectedNodes().size() == 1)
	            {
	                this.selectedNode = this.selectionHandler.getSelectedNodes().get(0);
	            }
	        }
	        return this.selectedNode;
	    }
	  private void startUndoRedoCapture()
	    {
	        if (getSelectedNode() == null)
	        {
	            return;
	        }
	        this.behaviorManager.fireBeforeChangingStateline(getSelectedNode());
	    }
	    private void stopUndoRedoCapture()
	    {
	        if (getSelectedNode() == null)
	        {
	            return;
	        }
	        this.behaviorManager.fireAfterChangingStateline(getSelectedNode());
	    }
	  	private INode selectedNode;
	  	private Rectangle2D initBounds=null;//定义初始的bounds
		private Point2D statelineEndLocation=null;//定义状态线的终点
		private Rectangle2D bound1=null;
		private Rectangle2D bound2=null;
		private Point2D beforeLastPoint=null;
	    private SEdge s;
	    private IGraph graph;
	    private IEditorPartSelectionHandler selectionHandler;
	    private IEditorPart editorPart;
	    private IGraphToolsBar graphToolsBar;
	    private IEditorPartBehaviorManager behaviorManager;
	
}

