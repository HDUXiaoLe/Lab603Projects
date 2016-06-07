package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.Id;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPartBehaviorManager;
import com.horstmann.violet.workspace.editorpart.IEditorPartSelectionHandler;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;

public class AddNodeBehavior extends AbstractEditorPartBehavior
{

    public AddNodeBehavior(IEditorPart editorPart, IGraphToolsBar graphToolsBar)
    {
        this.editorPart = editorPart;
        this.graph = editorPart.getGraph();
        this.selectionHandler = editorPart.getSelectionHandler();
        this.behaviorManager = editorPart.getBehaviorManager();
        this.graphToolsBar = graphToolsBar;
    }  
    public void onMouseClicked(MouseEvent event)
    {
    
        if (event.getClickCount() > 1)
        {
            return;
        }
        if (event.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        if (GraphTool.SELECTION_TOOL.equals(this.graphToolsBar.getSelectedTool()))
        {
        	
            return;
        }
     
        GraphTool selectedTool = this.selectionHandler.getSelectedTool();
        if (!INode.class.isInstance(selectedTool.getNodeOrEdge()))
        {
            return;
        }
       
        double zoom = editorPart.getZoomFactor();
        final Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);
        //IGridSticker gridSticker = graph.getGridSticker();
        //Point2D newNodeLocation = gridSticker.snap(mousePoint);
        Point2D newNodeLocation = mousePoint;
        INode prototype = (INode) selectedTool.getNodeOrEdge();
        INode newNode = (INode) prototype.clone();
        newNode.setId(new Id()); 
        newNode.setID("EAID_"+UUID.randomUUID().toString());
        boolean added = addNodeAtPoint(newNode, newNodeLocation);     
        String str= "class com.horstmann.violet.product.diagram.timing.State_Lifeline";	    
        if (added){ 
            selectionHandler.setSelectedElement(newNode);
            editorPart.getSwingComponent().invalidate();
            newNode.setFlag(true);  
          
          if(editorPart.getGraph().getAllNodes().size()>1&&
        		  newNode.getClass().toString().equals(str)){
        	//使得每次添加新StateLifeline的时候都能自动调整位置  
        	  Collection<INode> allNodes=editorPart.getGraph().getAllNodes();
          	  List<INode> allnodes=new ArrayList<INode>();
          	  for(INode node: allNodes)
          	  {
          		  allnodes.add(node);          		 
          	  }         	
          	  INode BeforeaddedNode=allnodes.get(1);//这里为1，每次添加新节点都在最前面添加，即为第2个点。        
          	  newNode.setLocation(new Point2D.Double(BeforeaddedNode.getLocation().getX(),        
              BeforeaddedNode.getLocation().getY()-150));//这里的200为初始高度
             //
              newNode.setFlag(false);     	  
    	    }     
         }
    }
    /**
     * Adds a new at a precise location
     * 
     * @param newNode to be added
     * @param location
     * @return true if the node has been added
     */
    public boolean addNodeAtPoint(INode newNode, Point2D location)
    {
        boolean isAdded = false;
       
        this.behaviorManager.fireBeforeAddingNodeAtPoint(newNode, location);
        try
        {
            if (graph.addNode(newNode, location))
            {
                newNode.incrementRevision();
                isAdded = true;
            }
        }
        finally
        {
            this.behaviorManager.fireAfterAddingNodeAtPoint(newNode, location);
        }
        return isAdded;
    }

    private IEditorPart editorPart;

    private IGraph graph;

    private IEditorPartSelectionHandler selectionHandler;

    private IEditorPartBehaviorManager behaviorManager;

    private IGraphToolsBar graphToolsBar;
}
