package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
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

public class DragCombinedFragmentBorderLineBehavior extends AbstractEditorPartBehavior
{

    public DragCombinedFragmentBorderLineBehavior(IEditorPart editorPart, IGraphToolsBar graphToolsBar)
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
        GraphTool selectedTool = this.selectionHandler.getSelectedTool();
        if (!INode.class.isInstance(selectedTool.getNodeOrEdge()))
        {       	
            return;
        }
        List<INode> selectedNodes = this.selectionHandler.getSelectedNodes(); 
        if(selectedNodes.size()!=1)
        {
        	return;
        }
        selectedNode=selectedNodes.get(0);      
        String combinedStr = "class com.horstmann.violet.product.diagram.sequence.CombinedFragment";
        if(!selectedNode.getClass().toString().equals(combinedStr))
        {
        	return;
        }   
        boolean isOnlyOneNodeSelected = (selectedNodes.size() == 1);
        if (!isOnlyOneNodeSelected) {
           return;
        }                    		      
            
        double zoom = editorPart.getZoomFactor();
        final Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);
        List<FragmentPart> fragmentparts=selectedNode.getFragmentParts();
        for(FragmentPart fragmentPart: fragmentparts)
        {
         
          Line2D borderline= fragmentPart.getBorderline();
          double locationX=borderline.getP1().getX();
          double locationY=borderline.getP1().getY()-5;
          Rectangle2D borderlinebound=new Rectangle2D.Double(locationX,locationY,selectedNode.getWidth(),Default_BorderlineBoundHeight);
          if(borderlinebound.contains(mousePoint))
          {
            changeSelectedElementIfNeeded(mousePoint);
         	borderlineflag=fragmentparts.indexOf(fragmentPart);
        	this.isReadyForDragging=true;
        	this.lastMousePoint=mousePoint;
        	this.editorPart.getSwingComponent().setCursor(dragCursor);
          }
        }                             
    } 
    @Override
    public void onMouseDragged(MouseEvent event)
    {
    	
    	
        if (!isReadyForDragging)
        {
            return;
        }
        String combinedStr = "class com.horstmann.violet.product.diagram.sequence.CombinedFragment";    
        if(!selectedNode.getClass().toString().equals(combinedStr))
        {
        	return;
        }
        boolean isAtLeastOneNodeMoved = false;
        double zoom = editorPart.getZoomFactor();
        Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);      
        List<FragmentPart> fragmentparts=selectedNode.getFragmentParts(); 
        double dy = mousePoint.getY() - lastMousePoint.getY();  
        Line2D Borderline=fragmentparts.get(borderlineflag).getBorderline();       
        Point2D newBorderLineStartPoint=new Point2D.Double(Borderline.getX1(),Borderline.getY1()+dy);
        Point2D newBorderLineEndPoint=new Point2D.Double(Borderline.getX2(),Borderline.getY2()+dy);
        Line2D newBorderline=new Line2D.Double(newBorderLineStartPoint,newBorderLineEndPoint);
        if(!newBorderline.equals(Borderline))
        {       
        fragmentparts.get(borderlineflag).setBorderline(newBorderline);      
        fragmentparts.get(borderlineflag).setBorderlinehaschanged(true); 
        isAtLeastOneNodeMoved=true;
        }
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
        //this.lastMousePoint = null;
    	this.lastMousePoint = null;
        this.isReadyForDragging = false;  
         
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

    private boolean isReadyForDragging = false;
       
    private Cursor dragCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
    
    private static double Default_BorderlineBoundHeight=10;
    
    private int borderlineflag;
}
