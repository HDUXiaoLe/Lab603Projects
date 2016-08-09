package com.horstmann.violet.product.diagram.markov;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.horstmann.violet.product.diagram.abstracts.AbstractGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.ISequenceTimeEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.common.NoteEdge;
import com.horstmann.violet.product.diagram.common.NoteNode;


public class MarkovGraph extends AbstractGraph
{
    public List<INode> getNodePrototypes()
    {
        return NODE_PROTOTYPES;
    }

    public List<IEdge> getEdgePrototypes()
    {
        return EDGE_PROTOTYPES;
    }

    private static final List<INode> NODE_PROTOTYPES = new ArrayList<INode>();

    private static final List<IEdge> EDGE_PROTOTYPES = new ArrayList<IEdge>();

    static
    {
        ResourceBundle rs = ResourceBundle.getBundle(MarkovConstant.MARKOV_DIAGRAM_STRINGS, Locale.getDefault());
        
        MarkovNode circularNode = new MarkovNode();
        circularNode.setToolTip(rs.getString("node0.tooltip"));
        NODE_PROTOTYPES.add(circularNode);
        
        
        
        MarkovStartNode circularStartNode = new MarkovStartNode();
        circularStartNode.setToolTip(rs.getString("node1.tooltip"));
        NODE_PROTOTYPES.add(circularStartNode);
        
       

        MarkovTransitionEdge TransitionEdge = new MarkovTransitionEdge();
        TransitionEdge.setToolTip(rs.getString("edge0.tooltip"));
        EDGE_PROTOTYPES.add(TransitionEdge);
        
      
    }

	

}
