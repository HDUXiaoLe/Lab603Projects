package com.horstmann.violet.application.gui.util;

public class WJRectangle {
	double left,right,top,bottom;
	public WJRectangle(double l,double t, double r, double b) {
		this.left = l;
		this.right = r;
		this.top = t;
		this.bottom = b;
	}
	public WJRectangle() {
		this(0,0,0,0);
	}
	public WJRectangle(WJRectangle rec) {
		this(rec.left, rec.top, rec.right, rec.bottom);
	}
	public double getLeft() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
	public double getRight() {
		return right;
	}
	public void setRight(double right) {
		this.right = right;
	}
	public double getTop() {
		return top;
	}
	public void setTop(double top) {
		this.top = top;
	}
	public double getBottom() {
		return bottom;
	}
	public void setBottom(double bottom) {
		this.bottom = bottom;
	}
	
}
