package com.horstmann.violet.product.diagram.abstracts.edge;


import java.awt.Graphics2D;
import java.io.Serializable;
public interface ISequenceTimeEdge extends Cloneable,Serializable{
	/*
	 * 获取起始边
	 */
	IEdge getStartEdge();
	/*
	 * 设置起始边
	 */
	void setStartEdge(IEdge edge);
	/*
	 * 获取终止边
	 */
    IEdge getEndEdge();
    /*
     * 设置终止边
     */
    void setEndEdge(IEdge edge);
    /*
     * 设置时间约束
     */
    void setTimeCondition(String condition);
    /*
     * 获取时间约束
     */
    String getTimeCondition();
    
    ISequenceTimeEdge clone();
    
    void draw(Graphics2D g2);
}