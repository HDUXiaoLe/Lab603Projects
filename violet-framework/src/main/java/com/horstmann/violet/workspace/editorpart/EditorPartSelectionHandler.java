package com.horstmann.violet.workspace.editorpart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.IHorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.edge.ISequenceTimeEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.SEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;

public class EditorPartSelectionHandler implements IEditorPartSelectionHandler
{

    public void setSelectedElement(INode node)
    {
        this.selectedNodes.clear();
        this.selectedEdges.clear();
        this.selectedLines.clear();
        addSelectedElement(node);
    }
    public void setSelectedElement(IHorizontalChild edge)
    {
    	this.selectedEdges.clear();
    	this.selectedLines.clear();
    	this.selectedNodes.clear();
    	addSelectedElement(edge);
    }
    public void setSelectedElement(IEdge edge)
    {
        this.selectedNodes.clear();
        this.selectedEdges.clear();
        this.selectedLines.clear();
        addSelectedElement(edge);
    }
    @Override
	public void setSelectedElement(ISequenceTimeEdge sequenceTimeEdge) {
		// TODO Auto-generated method stub
		    this.selectedNodes.clear();
	        this.selectedEdges.clear();
	        this.selectedLines.clear();
	        addSelectedElement(sequenceTimeEdge);
	}
    public void updateSelectedElements(INode[] nodes)
    {
        for (int i = 0; i < nodes.length; i++)
        {
            if (isElementAlreadySelected(nodes[i]))
            {
                addSelectedElement(nodes[i]);
            }
        }
    }
    public void updateSelectedElements(ISequenceTimeEdge[] edges)
    {
        for (int i = 0; i < edges.length; i++)
        {
            if (isElementAlreadySelected(edges[i]))
            {
                addSelectedElement(edges[i]);
            }
        }
    }
    public void updateSelectedElements(IHorizontalChild [] edges)
    {
    	for(int i=0;i<edges.length;i++)
    	{
    		if(isElementAlreadySelected(edges[i]))
    				{
    			      addSelectedElement(edges[i]);
    				}
    	}
    }
    public void updateSelectedElements(IEdge[] edges)
    {
        for (int i = 0; i < edges.length; i++)
        {
            if (isElementAlreadySelected(edges[i]))
            {
                addSelectedElement(edges[i]);
            }
        }
    }
    public void addSelectedElement(IHorizontalChild edge)
    {
    	if(this.selectedLines.contains(edge))
    	{
    		this.removeElementFromSelection(edge);
    	}
    	this.selectedLines.add(edge);
    }
    public void addSelectedElement(INode node)
    {
        if (this.selectedNodes.contains(node))
        {
            this.removeElementFromSelection(node);
        }
        this.selectedNodes.add(node);
    }

    public void addSelectedElement(IEdge edge)
    {
        if (this.selectedEdges.contains(edge))
        {
            this.removeElementFromSelection(edge);
        }
        this.selectedEdges.add(edge);
    }
    public void addSelectedElement(ISequenceTimeEdge sequenceTimeEdge)
    {
        if (this.selectedNodes.contains(sequenceTimeEdge))
        {
            this.removeElementFromSelection(sequenceTimeEdge);
        }
        this.selectedSequenceTimeEdges.add(sequenceTimeEdge);
    }
    public void removeElementFromSelection(INode node)
    {
        if (this.selectedNodes.contains(node))
        {
            int i = this.selectedNodes.indexOf(node);
            this.selectedNodes.remove(i);
        }
    }
    public void removeElementFromSelection(IHorizontalChild edge)
    {
    	if(this.selectedLines.contains(edge))
    	{
    		int i=this.selectedLines.indexOf(edge);
    		this.selectedLines.remove(i);
    	}
    }
    public void removeElementFromSelection(IEdge edge)
    {
        if (this.selectedEdges.contains(edge))
        {
            int i = this.selectedEdges.indexOf(edge);
            this.selectedEdges.remove(i);
        }
    }
    public void removeElementFromSelection(ISequenceTimeEdge edge)
    {
    	if(this.selectedSequenceTimeEdges.contains(edge))
    	{
    		int i=this.selectedSequenceTimeEdges.indexOf(edge);
    		this.selectedSequenceTimeEdges.remove(i);
    	}
    }
    public boolean isElementAlreadySelected(INode node)
    {
        if (this.selectedNodes.contains(node)) return true;
        return false;
    }
    public boolean isElementAlreadySelected(IHorizontalChild edge)
    {
    	if(this.selectedLines.contains(edge)) return true;
    	return false;
    }
    public boolean isElementAlreadySelected(IEdge edge)
    {
        if (this.selectedEdges.contains(edge)) return true;
        return false;
    }
    public boolean isElementAlreadySelected(ISequenceTimeEdge edge)
    {
        if (this.selectedSequenceTimeEdges.contains(edge)) return true;
        return false;
    }
    public void clearSelection()
    {
        this.selectedNodes.clear();
        this.selectedEdges.clear();
        this.selectedLines.clear();
        this.selectedSequenceTimeEdges.clear();
    }
 
    
   
	public INode getLastSelectedNode()
    {
        return getLastElement(this.selectedNodes);
    }
    public IHorizontalChild getLastSelectedLine()
    {
    	return getLastElement(this.selectedLines);
    }
    public IEdge getLastSelectedEdge()
    {
        return getLastElement(this.selectedEdges);
    }
    @Override
    public ISequenceTimeEdge getLastSelectedTimeEdge()
    {
        return getLastElement(this.selectedSequenceTimeEdges);
    }
    public boolean isNodeSelectedAtLeast()
    {
        return this.selectedNodes.size() > 0;
    }
    public boolean isLineSelectedAtLeast()
    {
    	return this.selectedLines.size() > 0;
    	
    }
    public boolean isEdgeSelectedAtLeast()
    {
        return this.selectedEdges.size() > 0;
    }
    public boolean isTimeEdgeSelectedAtLeast()
    {
        return this.selectedSequenceTimeEdges.size() > 0;
    }
    public List<INode> getSelectedNodes()
    {
        return Collections.unmodifiableList(selectedNodes);
    }

    public List<IEdge> getSelectedEdges()
    {
        return Collections.unmodifiableList(selectedEdges);
    }
    //by xiaole
    public List<IHorizontalChild> getSelectedLines()
    {
    	return Collections.unmodifiableList(selectedLines);
    }
    public List<ISequenceTimeEdge> getSelectedTimeLines()
    {
    	return Collections.unmodifiableList(selectedSequenceTimeEdges);
    }
    
   
    @Override
    public GraphTool getSelectedTool()
    {
        return this.selectedTool;
    }

    @Override
    public void setSelectedTool(GraphTool graphTool)
    {
        this.selectedTool = graphTool;
    }
    
    private <T> T getLastElement(List<T> list)
    {
        int size = list.size();
        if (size <= 0)
        {
            return null;
        }
        return list.get(size - 1);
    }
   
    
    //===================================================================
	//ÐÞ¸Ä´úÂë
//	public List<SEdge> getSelectedLine()
//	{
//	    return Collections.unmodifiableList(selectedLine);
//	}
//=================================================================================	
	
    private List<INode> selectedNodes = new ArrayList<INode>();
    private List<IEdge> selectedEdges = new ArrayList<IEdge>();
    //by xiaole
    private List<IHorizontalChild> selectedLines=new ArrayList<IHorizontalChild >();
    private List<ISequenceTimeEdge> selectedSequenceTimeEdges=new ArrayList<>();
    private GraphTool selectedTool;
	@Override
	public boolean isElmentAlreadySelected(ISequenceTimeEdge sequenceTimeEdge) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<ISequenceTimeEdge> getSelectedTimeEdges() {
		// TODO Auto-generated method stub
		return null;
	}
}
