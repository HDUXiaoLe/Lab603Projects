package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.IGridSticker;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.StatelineParent;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentPart;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPartSelectionHandler;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

public class ResizeElement extends AbstractEditorPartBehavior
{

    public ResizeElement(IEditorPart editorPart, IGraphToolsBar graphToolsBar)
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
        List<INode> selectedNodes = this.selectionHandler.getSelectedNodes();
        List<IEdge> selectedEdges = selectionHandler.getSelectedEdges();  
        boolean isOnlyOneNodeSelected = (selectedNodes.size() == 1 && selectedEdges.size() == 0);
        //只有一个节点被选取
       if (!isOnlyOneNodeSelected) {
           return;//跳出函数
       } 
       INode selectedNode=selectedNodes.get(0);
       String str = "class com.horstmann.violet.product.diagram.sequence.CombinedFragment";
       String str1= "class com.horstmann.violet.product.diagram.timing.State_Lifeline";
       if (!(selectedNode.getClass().toString().equals(str)||selectedNode.getClass().toString().equals(str1)))
       {
           return;
       }  
       
        double zoom = editorPart.getZoomFactor();
        double x=selectedNode.getBounds().getX();
        double y=selectedNode.getBounds().getY();
        Rectangle2D bounds = new Rectangle2D.Double(selectedNode.getBounds().getX()+selectedNode.getBounds().getWidth()-5,
        		selectedNode.getBounds().getY()+selectedNode.getBounds().getHeight()-5,5,5);
        Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);
        temp=new Point2D.Double(x,y);
        	if(bounds.contains(mousePoint))
            {
            changeSelectedElementIfNeeded(mousePoint);
            this.isReadyForDragging = true;
            this.lastMousePoint = mousePoint;
            this.initialCursor = this.editorPart.getSwingComponent().getCursor();
            this.editorPart.getSwingComponent().setCursor(this.dragCursor);
            }
        	else this.editorPart.getSwingComponent().setCursor(initialCursor);      
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
        // behaviorManager.fireOnElementsDragged(selectionHandler.getSelectedNodes(),
        // selectionHandler.getSelectedEdges());
        INode lastNode = selectionHandler.getLastSelectedNode();
        if (lastNode == null)
        {
            return;
        }   
        double dx = mousePoint.getX() - lastMousePoint.getX();
        double dy = mousePoint.getY() - lastMousePoint.getY();    
        // we don't want to drag nodes into negative coordinates
        // particularly with multiple selection, we might never be
        // able to get them back.
        List<INode> selectedNodes = selectionHandler.getSelectedNodes();  
        boolean isAtLeastOneNodeMoved = false;
      //  IGridSticker gridSticker = graph.getGridSticker();
          INode n=selectedNodes.get(0);
            double currentNodeHeight=n.getHeight();
            double currentNodeWidth=n.getWidth();
             n.setLocation(temp);
             n.setWidth(currentNodeWidth+dx);    
             n.setHeight(currentNodeHeight+dy);
             isAtLeastOneNodeMoved = true;
             String str2= "class com.horstmann.violet.product.diagram.sequence.CombinedFragment";           
             if(n.getClass().toString().equals(str2)){
                 List<FragmentPart> fragmentParts= n.getFragmentParts();
                 for(FragmentPart fragmentPart : fragmentParts)
                 {
                	 Line2D borderline=fragmentPart.getBorderline();
                	
                	 Point2D p1=new Point2D.Double(borderline.getP1().getX(),
                	 borderline.getP1().getY());
                	 Point2D p2=new Point2D.Double(borderline.getP2().getX()+dx,
                     borderline.getP2().getY());
                	 Line2D newborderline=new Line2D.Double(p1,p2);
                	 if(!borderline.equals(newborderline)&&fragmentPart.isBorderlinehaschanged()==true)
                	 { 
                	 fragmentPart.setBorderline(newborderline);              
                	 }
                 }  
             }
        if (isAtLeastOneNodeMoved)
        {
         
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
    private Point2D temp=null;
    private Point2D lastMousePoint = null;
    private IEditorPartSelectionHandler selectionHandler;
    private IEditorPart editorPart;
    private IGraphToolsBar graphToolsBar;
    private boolean isReadyForDragging = false;
    private Cursor initialCursor = null;
    private Cursor dragCursor = new Cursor(Cursor.SE_RESIZE_CURSOR);
}
