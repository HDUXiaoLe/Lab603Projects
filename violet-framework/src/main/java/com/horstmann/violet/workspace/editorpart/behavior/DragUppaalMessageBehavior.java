package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPartSelectionHandler;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

public class DragUppaalMessageBehavior extends AbstractEditorPartBehavior{

	    private IGraph graph;  
	    private Point2D temp=null;
	    private Point2D lastMousePoint = null;
	    private IEditorPartSelectionHandler selectionHandler;
	    private IEditorPart editorPart;	 
	    private boolean isReadyForDragging = false;
	    private Cursor initialCursor = null;
	    private Cursor dragCursor = new Cursor(Cursor.SE_RESIZE_CURSOR);
	    private IEdge selectedEdge;

	

	  public DragUppaalMessageBehavior(IEditorPart editorPart)
	    {
	        this.editorPart = editorPart;
	        this.graph = editorPart.getGraph();
	        this.selectionHandler = editorPart.getSelectionHandler();      
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
	       String str = "class com.horstmann.violet.product.diagram.uppaal.TransitionEdge";	 
	        double zoom = editorPart.getZoomFactor();
	        final Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);
	       for(IEdge edge:graph.getAllEdges())
	       {
	    	   //保证是针对时间自动机
	    	   if(!edge.getClass().toString().equals(str))
	    	   {
	    		   return;
	    	   }
	    	
	    	 if(edge.getLabelBounds().contains(mousePoint))
	    	 {	    		
	    		  selectedEdge=edge;
	    		  this.isReadyForDragging = true;
	              this.lastMousePoint = mousePoint;
	              this.initialCursor = this.editorPart.getSwingComponent().getCursor();
	              this.editorPart.getSwingComponent().setCursor(this.dragCursor);
	             
	    	 }
	    	 else{
	    		 this.editorPart.getSwingComponent().setCursor(initialCursor); 
	    		
	    	    } 
	    	 }
	       }
	   
	    
	    @Override
	    public void onMouseDragged(MouseEvent event)
	    {
	    	if(!isReadyForDragging)
	    	{	    		
	    		return;
	    	}
	        
	        double zoom = editorPart.getZoomFactor();
	        Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);	       
	        Rectangle2D rectangle=new Rectangle2D.Double(mousePoint.getX(),mousePoint.getY(),200,30);
	        selectedEdge.setLabelBounds(rectangle);
	       
	            if (!mousePoint.equals(lastMousePoint)) {	            	
	               editorPart.getSwingComponent().invalidate();
	               editorPart.getSwingComponent().repaint();
	            }
	            lastMousePoint = mousePoint;
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

	   

}
