//package com.horstmann.violet.workspace.editorpart.behavior;
//
//import java.awt.Cursor;
//import java.awt.event.MouseEvent;
//import java.awt.geom.Point2D;
//import java.awt.geom.Point2D.Double;
//import java.util.List;
//
//import com.horstmann.violet.product.diagram.abstracts.IGridSticker;
//import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
//import com.horstmann.violet.product.diagram.abstracts.node.INode;
//import com.horstmann.violet.workspace.editorpart.IEditorPart;
//import com.horstmann.violet.workspace.editorpart.IEditorPartBehaviorManager;
//import com.horstmann.violet.workspace.editorpart.IEditorPartSelectionHandler;
//import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;
//import com.horstmann.violet.workspace.sidebar.graphtools.IGraphToolsBar;
//
//public class DragMessageBehavaior extends AbstractEditorPartBehavior{
//
//	private IEditorPartBehaviorManager behaviorManager;
//	private Point2D firstMousePoint = null;
//	private Point2D lastMousePoint =null;
//	private IEditorPartSelectionHandler selectionHandler;
//    private IEditorPart editorPart;
//    private IGraphToolsBar graphToolsBar;
//    private boolean isReadyForDragging = false;
//    private IEdge selectedEdge = null;
//	
//	public DragMessageBehavaior(IEditorPart editorPart,IGraphToolsBar graphToolsBar)
//	{
//		this.editorPart=editorPart;
//		this.selectionHandler = editorPart.getSelectionHandler();
//        this.graphToolsBar = graphToolsBar;
//        this.behaviorManager = editorPart.getBehaviorManager();		
//	}
//	@Override
//	public void onMousePressed(MouseEvent event)
//	{
//		if(event.getClickCount()>1)
//		{
//			return;
//		}
//		if(event.getButton() != MouseEvent.BUTTON1)
//		{
//			return;
//		}
//		if(!isPrerequisitesOK())
//		{
//			
//			return;
//		}
//		if (!isSelectedToolOK())
//	    {
//			
//	            return;
//	    } 
//		
//		List<IEdge> selectedEdges = selectionHandler.getSelectedEdges();
//		String str="class com.horstmann.violet.product.diagram.timing.SendMessageEdge";
//		if(! selectedEdges.get(0).getClass().toString().equals(str))
//		{
//	
//			return;
//		}
//		
//		double zoom =editorPart.getZoomFactor();
//		final Point2D mousePoint = new Point2D.Double(event.getX()/zoom,event.getY()/zoom);
//		isReadyForDragging = true;
//		firstMousePoint=mousePoint;
//		lastMousePoint=mousePoint;
//		if(selectedEdges.get(0).getBounds().contains(mousePoint))
//		{		
//	    this.editorPart.getSwingComponent().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
//		}
//		
//	}
//	@Override
//	public void onMouseDragged(MouseEvent event)
//	{
//		if(!isReadyForDragging)
//		{
//			
//			return;
//		}
//		List<IEdge> selectedEdges = selectionHandler.getSelectedEdges();
//		String str="class com.horstmann.violet.product.diagram.timing.SendMessageEdge";
//		if(! selectedEdges.get(0).getClass().toString().equals(str))
//		{
//			return;
//		}
//		double zoom =editorPart.getZoomFactor();
//		Point2D mousePoint = new Point2D.Double(event.getX() / zoom, event.getY() / zoom);
//	    double dx = mousePoint.getX() - lastMousePoint.getX();
//	    double dy = mousePoint.getY() - lastMousePoint.getY();
//	    IGridSticker gridSticker = editorPart.getGraph().getGridSticker();
//	    IEdge s = selectedEdges.get(0);
//	    Point2D tempStartLocation=s.getStartLocationOnGraph();
//	    Point2D tempEndLocation=s.getEndLocationOnGraph();
//	    //INode startNode=s.getStart();
//	    //INode endNode=s.getEnd();
//	    //Point2D Tempstart=startNode.getConnectionPoint(s);
//	    //Point2D Tempend=endNode.getConnectionPoint(s);
//	    s.setStartLocationOnGraph(new Point2D.Double(tempStartLocation.getX()+dx,
//	    		tempStartLocation.getY()+dy));
//	    s.setEndLocationOnGraph(new Point2D.Double(tempEndLocation.getX()+dx,
//	    		tempEndLocation.getY()+dy));
//	    Point2D snappedMousePoint = gridSticker.snap(mousePoint);
//        if (!snappedMousePoint.equals(lastMousePoint)) {
//            editorPart.getSwingComponent().invalidate();
//            editorPart.getSwingComponent().repaint();
//        }
//        lastMousePoint = snappedMousePoint;
//        return;
//	    
//		
//	}
//	 @Override
//	    public void onMouseReleased(MouseEvent event)
//	    {
//	       
//	        firstMousePoint = null;
//	        lastMousePoint = null;
//	        isReadyForDragging = false;
//	        selectedEdge = null;
//	    }
//
//	
//	 private boolean isSelectedToolOK()
//	    {
//	        if (getSelectedEdge() == null)
//	        {
//	        	
//	            return false;
//	        }
//	        GraphTool selectedTool = this.graphToolsBar.getSelectedTool();
//	        if (GraphTool.SELECTION_TOOL.equals(selectedTool))
//	        {
//	            return true;
//	        }
//	        if (selectedTool.getNodeOrEdge().getClass().isInstance(getSelectedEdge()))
//	        {	
//	            return true;
//	        }
//	        return false;
//	    }
//	private IEdge getSelectedEdge()
//	{
//		if(this.selectedEdge==null)
//		{
//		    if(this.selectionHandler.getSelectedEdges().size()==1)
//		    {
//		    	this.selectedEdge=this.selectionHandler.getSelectedEdges().get(0);
//		    }
//		}
//		return this.selectedEdge;
//		
//	}
//	private boolean isPrerequisitesOK() {
//		// TODO Auto-generated method stub
//		if(getSelectedEdge() ==null)
//		{
//			return false;
//		}
//		return true;
//	}
//	
//	
//	
//}
