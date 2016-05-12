package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.IGridSticker;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.IHorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.SEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.StatelineParent;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentPart;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPartBehaviorManager;
import com.horstmann.violet.workspace.editorpart.IEditorPartSelectionHandler;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

public class DragSelectedBehavior extends AbstractEditorPartBehavior
{

    public DragSelectedBehavior(IEditorPart editorPart, IGraphToolsBar graphToolsBar)
    {
        this.editorPart = editorPart;
        this.graph = editorPart.getGraph();
        this.selectionHandler = editorPart.getSelectionHandler();
        this.graphToolsBar = graphToolsBar;
    }

    @Override
    public void onMousePressed(MouseEvent event)
    {
        if (event.getClickCount() > 1)
        {
            return;
        }
        if (event.getButton() != MouseEvent.BUTTON1)
        {
            return;
        }
        GraphTool selectedTool = this.selectionHandler.getSelectedTool();
        if (IEdge.class.isInstance(selectedTool.getNodeOrEdge()))
        {
            return;
        }
        selectedNode=selectionHandler.getLastSelectedNode();
        String combinedStr = "class com.horstmann.violet.product.diagram.sequence.CombinedFragment";
        double zoom = editorPart.getZoomFactor();
        final Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);
        if(selectedNode!=null)
        {
        Rectangle2D bounds = new Rectangle2D.Double(selectedNode.getBounds().getX()+selectedNode.getBounds().getWidth()-5,
        		selectedNode.getBounds().getY()+selectedNode.getBounds().getHeight()-5,5,5);
        if(bounds.contains(mousePoint))
        {
        	return;
        }
        }
        
     
        if (isMouseOnNode(mousePoint))
        {   
        	if(selectedNode.getClass().toString().equals(combinedStr))
        	
        	{    
        		 this.isReadyForDragging = true;
        	     changeSelectedElementIfNeeded(mousePoint);//转变选取目标节点
        		 List<FragmentPart> fragmentparts=selectedNode.getFragmentParts();       		
        	     for(FragmentPart fragmentPart: fragmentparts)
        	     { 
        		 Line2D borderline= fragmentPart.getBorderline();
                 double locationX=borderline.getP1().getX();
                 double locationY=borderline.getP1().getY()-5;
                 Rectangle2D borderlinebound=new Rectangle2D.Double(locationX,locationY,selectedNode.getWidth(),10);
                 if(borderlinebound.contains(mousePoint))
                 {	
                	 this.isReadyForDragging=false;
                	 break;
                 }
                }
                 this.lastMousePoint = mousePoint;
                 this.initialCursor = this.editorPart.getSwingComponent().getCursor();
                 this.editorPart.getSwingComponent().setCursor(this.dragCursor);       	            	   
        	}        
        else{
            changeSelectedElementIfNeeded(mousePoint);
            this.isReadyForDragging = true;
            this.lastMousePoint = mousePoint;
            this.initialCursor = this.editorPart.getSwingComponent().getCursor();
            this.editorPart.getSwingComponent().setCursor(this.dragCursor);
        	}
        }
    }

    private boolean isMouseOnNode(Point2D mouseLocation)
    {
        INode node = this.graph.findNode(mouseLocation);
        if (node == null)
        {
            return false;
        }
        return true;
    }

    @Override
    public void onMouseDragged(MouseEvent event)
    {
        if (!isReadyForDragging)
        {
            return;
        }
        double zoom = editorPart.getZoomFactor();
        Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);
        // TODO :
        // behaviorManager.fireOnElementsDragged(selectionHandler.getSelectedNodes(),
        // selectionHandler.getSelectedEdges());
        INode lastNode = selectionHandler.getLastSelectedNode();
        if (lastNode == null)
        {
            return;
        }
        Rectangle2D bounds = lastNode.getBounds();
        double dx = mousePoint.getX() - lastMousePoint.getX();
        double dy = mousePoint.getY() - lastMousePoint.getY();
        
       
        // we don't want to drag nodes into negative coordinates
        // particularly with multiple selection, we might never be
        // able to get them back.
        List<INode> selectedNodes = selectionHandler.getSelectedNodes();
        for (INode n : selectedNodes)
            bounds.add(n.getBounds());
        dx = Math.max(dx, -bounds.getX());
        dy = Math.max(dy, -bounds.getY());
      
	 
        boolean isAtLeastOneNodeMoved = false;
        IGridSticker gridSticker = graph.getGridSticker();
        for (INode n : selectedNodes)
        {
            if (selectedNodes.contains(n.getParent())) continue; // parents are responsible for translating their
            // children
            Point2D currentNodeLocation = n.getLocation();
            Point2D futureNodeLocation = new Point2D.Double(currentNodeLocation.getX() + dx, currentNodeLocation.getY() + dy);                     
            String str1= "class com.horstmann.violet.product.diagram.timing.State_Lifeline";
            String str2= "class com.horstmann.violet.product.diagram.sequence.CombinedFragment";
            if (!currentNodeLocation.equals(futureNodeLocation))
            {            	
                     n.setLocation(futureNodeLocation);                   
                     isAtLeastOneNodeMoved = true;               
            }
            if(n.getClass().toString().equals(str2)){
            	  Collection<INode> Nodes= editorPart.getGraph().getAllNodes();
            	  //获取到图中所有节点
            	  for(FragmentPart fragmentPart: n.getFragmentParts())
            	 {
            		 
            	  fragmentPart.getNestingChildNodesID().clear();
           	      fragmentPart.getCoveredMessagesID().clear();//每次在移动时重置，因为每次点击时都需要判断嵌套关系
            	 }
            	 
                  for(INode node : Nodes)
                  {	             	 
                 	 if(node.getClass().toString().equals(str2)&&!n.equals(node))
                 	 {   
                 		 for(FragmentPart fragmentPart: n.getFragmentParts())
                 		 {		 
                 		if(fragmentPart.getBounds().contains(node.getBounds()))
                 				{ 
//                 			System.out.println(n);
//                 			System.out.println(node);
                 			//System.out.println(node.getID());
                 			   fragmentPart.AddNestingChildNodesID(node.getID().toString());            			 
                 				}  
                 		 }
                 	 }
                  }
             List<FragmentPart> fragmentParts= n.getFragmentParts();
             for(FragmentPart fragmentPart : fragmentParts)
             {
            	 Line2D borderline=fragmentPart.getBorderline();
            	 Point2D p1=new Point2D.Double(borderline.getP1().getX()+dx,
            	 borderline.getP1().getY()+dy);
            	 Point2D p2=new Point2D.Double(borderline.getP2().getX()+dx,
                 borderline.getP2().getY()+dy);
            	 Line2D newborderline=new Line2D.Double(p1,p2);
            	 fragmentPart.setBorderline(newborderline);
            	 Collection<IEdge> edges=editorPart.getGraph().getAllEdges();
            	 for(IEdge edge :edges)
            	 {
            		if(fragmentPart.getBounds().contains(edge.getStartLocation()))
            		{      
            			
            			fragmentPart.AddCoveredMessagesID(edge.getID().toString());
            		}            		            		 
            	 }
              }           	         	
            }
   	     //这里的语句是为了在拖动的时候使得每个StateLifeLine中的状态线也得以改变位置
            if (n.getClass().toString().equals(str1)){
   	    	 SEdge s= ((StatelineParent)n).getStateLine();  	      
   	    	for(IHorizontalChild horizontalchild:s.gethorizontalChild())//改变孩子位置
   	    	{
   	    		
   	    		Point2D P1=new Point2D.Double(horizontalchild.getStart().getX()+dx,
   	    				horizontalchild.getStart().getY()+dy);
   	    		Point2D P2=new Point2D.Double(horizontalchild.getEnd().getX()+dx,
   	    				horizontalchild.getEnd().getY()+dy); 
   	    		horizontalchild.setStart(P1);
   	    		horizontalchild.setEnd(P2);  	    
   	    	}
            }
   	    	
   	              //    到这里为止                                 
           }
        // Drag transition points on edges
        Iterator<IEdge> iterOnEdges = graph.getAllEdges().iterator();
        while (iterOnEdges.hasNext())
        {
            IEdge e = (IEdge) iterOnEdges.next();
            INode startingNode = e.getStart();
            INode endinNode = e.getEnd();
            if (selectedNodes.contains(startingNode) && selectedNodes.contains(endinNode))
            {
                Point2D[] transitionPoints = e.getTransitionPoints();
                for (Point2D aTransitionPoint : transitionPoints)
                {
                    double newTransitionPointLocationX = aTransitionPoint.getX() + dx;
                    double newTransitionPointLocationY = aTransitionPoint.getY() + dy;
                    aTransitionPoint.setLocation(newTransitionPointLocationX, newTransitionPointLocationY);
                    aTransitionPoint = gridSticker.snap(aTransitionPoint);
                }
                e.setTransitionPoints(transitionPoints);
            }
        }

        // Save mouse location for next dragging sequence
        if (isAtLeastOneNodeMoved)
        {
            //Point2D snappedMousePoint = gridSticker.snap(mousePoint);
            if (!mousePoint.equals(lastMousePoint)) {
                editorPart.getSwingComponent().invalidate();
                editorPart.getSwingComponent().repaint();
            }
            lastMousePoint = mousePoint;
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event)
    {
    	
        this.editorPart.getSwingComponent().setCursor(this.initialCursor);
        this.lastMousePoint = null;
        this.isReadyForDragging = false;
        this.initialCursor = null;
    }

    private void changeSelectedElementIfNeeded(Point2D mouseLocation)
    {
        IEdge edge = this.graph.findEdge(mouseLocation);
        if (edge != null) {
            // We don't want to drag edges
            return;
        }
        INode node = this.graph.findNode(mouseLocation);
        if (node == null)
        {
            return;
        }
        List<INode> selectedNodes = this.selectionHandler.getSelectedNodes();
        if (!selectedNodes.contains(node))
        {
            this.selectionHandler.clearSelection();
            this.selectionHandler.addSelectedElement(node);
        }
    }
   

    private IGraph graph;

    private INode selectedNode;
       
    private Point2D lastMousePoint = null;

    private IEditorPartSelectionHandler selectionHandler;

    private IEditorPart editorPart;
  
    private IGraphToolsBar graphToolsBar;

    private boolean isReadyForDragging = false;
    
    private Cursor initialCursor = null;
    
    private Cursor dragCursor = new Cursor(Cursor.HAND_CURSOR);
}
