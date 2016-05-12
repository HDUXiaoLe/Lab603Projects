/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.product.diagram.abstracts.node;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.Direction;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.IIdentifiable;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.edge.SEdge;
import com.horstmann.violet.product.diagram.abstracts.property.Condition;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentPart;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;

/**
 * A node in a graph. To be more precise, a node is an graphical entity that represents a class, a sequence, a state or all other
 * type of entities that can or not handle edges.
 * 
 * @author Cay Horstmann
 */
public interface INode extends Serializable, Cloneable, IIdentifiable
{
    
    /**
     * Checks whether to add an edge that originates at this node.
     * 
     * @param e the edge to add
     * @return true if the edge was added
     */
    boolean addConnection(IEdge e);
    String getID();
    void setID(String id);
    /**
     * Notifies this node that an edge is being removed.
     * 
     * @param g the ambient graph
     * @param e the edge to be removed
     */
    void removeConnection(IEdge e);

    /**
     * Adds a node as a child node to this node.
     * 
     * @param n the child node
     * @param p the point at which the node is being added
     * @return true if this node accepts the given node as a child
     */
    boolean addChild(INode n, Point2D p);

    /**
     * Adds a child node and fires the graph modification event.
     * @param node the child node to add
     * @param index the position at which to add the child
     * @return true if this node accepts the given node as a child
     */
    boolean addChild(INode node, int index);

    /**
     * Notifies this node that a node is being removed.
     * 
     * @param g the ambient graph
     * @param n the node to be removed
     */
    void removeChild(INode n);

    /**
     * Gets the children of this node.
     * 
     * @return an unmodifiable list of the children
     */
    List<INode> getChildren();

    /**
     * Gets the parent of this node.
     * 
     * @return the parent node, or null if the node has no parent
     */
    INode getParent();

    /**
     * Sets node's parent (for decoder)
     * 
     * @param parentNode p
     */
    void setParent(INode parentNode);

    /**
     * Sets the graph that contains this node.
     * @param g the graph
     */
    void setGraph(IGraph g);
    double getHeight();
	void setHeight(double height);
	double getWidth();
	void  setWidth(double width);
    MultiLineString getStates();
    MultiLineString getState0();
    /**
     * Gets the graph that contains this node, or null if this node is not contained in any graph.
     * @return
     */
    IGraph getGraph();
    
    

    /**
     * Translates the node by a given amount
     * 
     * @param dx the amount to translate in the x-direction
     * @param dy the amount to translate in the y-direction
     */
    void translate(double dx, double dy);
    
    /**
     * Tests whether the node contains a point.
     * 
     * @param aPoint the point to test
     * @return true if this node contains aPoint
     */
    boolean contains(Point2D aPoint);

    /**
     * Get the best connection point to connect this node with another node. This should be a point on the boundary of the shape of
     * this node.
     * 
     * @param d the direction from the center of the bounding rectangle towards the boundary
     * @return the recommended connection point
     */
    Point2D getConnectionPoint(IEdge e);
 
    /**
     * Set or change node location 
     * @param aPoint
     */
    void setLocation(Point2D aPoint);
    /**
	 * 设置一个标志，指示是否绘制坐标轴
	 * @param flag
	 */
	void setFlag(boolean flag);
	/**
	 * 获取知识是否绘制坐标轴的标志
	 * @return
	 */
	boolean getFlag();
	
//	List<Line2D> getChildList();
	/**
	 * 返回时序图节点里面的状态线
	 * @return
	 */
	

    /**
     * Gets the location of this node on its parent (i.e. relative location)
     * @return the location
     */
    Point2D getLocation();
   
    /**
     * Gets the location of this node on the whole graph. (i.e. absolute location)
     * @return
     */
    Point2D getLocationOnGraph();

    /**
     * Get the visual bounding rectangle of the shape of this node
     * 
     * @return the bounding rectangle
     */
    Rectangle2D getBounds();

    /**
     * Draw the node.
     * 
     * @param g2 the graphics context
     * @param grid the grid to snap to
     */
    void draw(Graphics2D g2);
    
    //by xiaole
    
    void addchild(SEdge edge);
    
    SEdge getChild();
    /**
        
     * Gets the z-order. Nodes with higher z-order are drawn above those with lower z-order.
     * @return the z-order.
     */
    int getZ();
    
    /**
     * Sets the z-order.
     * @param z the desired z-order.
     */
    void setZ(int z);
    
    //double getHight();
    /**
     * Gets current node tool tip
     * @return
     */
    String getToolTip();
    
    Condition getCondition();
    
    List<FragmentPart> getFragmentParts();
    	   
    void setFragmentParts(List<FragmentPart> fragmentParts);
    
    void AddNestingChild(INode nestingChildNode);
    
    ArrayList<INode> getNestingChild();
      
    /**
     * @return a deep copy of this object
     */
    INode clone();

}
