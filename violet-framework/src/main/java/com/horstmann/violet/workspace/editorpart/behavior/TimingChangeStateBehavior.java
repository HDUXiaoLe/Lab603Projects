package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.HorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.IHorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.SEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.StatelineParent;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.node.TimingDiragramConstants;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPartBehaviorManager;
import com.horstmann.violet.workspace.editorpart.IEditorPartSelectionHandler;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

public class TimingChangeStateBehavior extends AbstractEditorPartBehavior{
	    private IGraph graph;
	    private IEditorPartSelectionHandler selectionHandler;
	    private IEditorPart editorPart;
	    private IGraphToolsBar graphToolsBar;
	 	private INode selectedNode;
	 	private SEdge s;
	 	private IEditorPartBehaviorManager behaviorManager;
	 	private Point2D mousePressPoint=null;
	 public TimingChangeStateBehavior(IEditorPart editorPart, IGraphToolsBar graphToolsBar)
	    {
	        this.editorPart = editorPart;
	        this.graph = editorPart.getGraph();
	        this.selectionHandler = editorPart.getSelectionHandler();
	        this.graphToolsBar = graphToolsBar;
	        this.behaviorManager = editorPart.getBehaviorManager();
	    }
	@Override
    public void onMouseReleased(MouseEvent event) {	
		
	}
	@Override 
	 public void onMousePressed(MouseEvent e){
		 if(e.getClickCount()>1)
		 {
			 return;
		 }
		 if(e.getButton()!=MouseEvent.BUTTON1)
		 {
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
	   
	     IHorizontalChild horizontalchild=null;
	      //获取stateline对象
	       mousePressPoint =e.getPoint();//获取鼠标按下时候的点	   	    		  
			 int horizontalChildSize=s.gethorizontalChild().size();
			
			 
			 for(int i=0;i<horizontalChildSize;i++)
			 {  
			 horizontalchild=s.gethorizontalChild().get(i);//获取水平子节点
			 if(s.getChildTopBounds(horizontalchild).contains(mousePressPoint))				
				  {				
					  //如果line上有起始边	
						startUndoRedoCapture();  
				  s.gethorizontalChild().remove(horizontalchild);
			
				  Point2D firstPoint=new Point2D.Double(mousePressPoint.getX(),
						  horizontalchild.getStart().getY());
				  Point2D secondPoint=new Point2D.Double(mousePressPoint.getX(),
						  horizontalchild.getStart().getY()+TimingDiragramConstants.StateSpace);
				  Point2D thirdPoint=new Point2D.Double(horizontalchild.getEnd().getX(),
						  horizontalchild.getStart().getY()+TimingDiragramConstants.StateSpace);
				  IHorizontalChild newchild1=new HorizontalChild();
				  IHorizontalChild newchild2=new HorizontalChild();			
			      newchild1.setStart(horizontalchild.getStart());
			      newchild1.setEnd(firstPoint);
				  newchild2.setStart(secondPoint);
				  newchild2.setEnd(thirdPoint);
				  s.gethorizontalChild().add(i, newchild1);
				  s.gethorizontalChild().add(i+1,newchild2);
				
				
//				  if(s.getstartedgeonChild().get(horizontalchild)!=null)
//				  {
//					  IEdge edge=s.getstartedgeonChild().get(horizontalchild);
//					  edge.setStartLocationOnGraph(firstPoint);
//				  }
//				  if(s.getendedgeonChild().get(horizontalchild)!=null)
//				  {
//					  IEdge edge=s.getendedgeonChild().get(horizontalchild);
//					  edge.setEndLocationOnGraph(firstPoint);
//				  }

					stopUndoRedoCapture();	
				  }
			 if(s.getChildBottomBounds(horizontalchild).contains(mousePressPoint))
			  {
			  startUndoRedoCapture();  
			  s.gethorizontalChild().remove(horizontalchild);
			
			  Point2D firstPoint=new Point2D.Double(mousePressPoint.getX(),
					  horizontalchild.getStart().getY());
			  Point2D secondPoint=new Point2D.Double(mousePressPoint.getX(),
					  horizontalchild.getStart().getY()-TimingDiragramConstants.StateSpace);
			  Point2D thirdPoint=new Point2D.Double(horizontalchild.getEnd().getX(),
					  horizontalchild.getStart().getY()-TimingDiragramConstants.StateSpace);
			  IHorizontalChild newchild1=new HorizontalChild(); 			
			  IHorizontalChild newchild2=new HorizontalChild();			
			  newchild1.setStart(horizontalchild.getStart());
			  newchild1.setEnd(firstPoint);
			  newchild2.setStart(secondPoint);
			  newchild2.setEnd(thirdPoint);
			  s.gethorizontalChild().add(i, newchild1);
			  s.gethorizontalChild().add(i+1,newchild2);
			
			stopUndoRedoCapture();
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
}
