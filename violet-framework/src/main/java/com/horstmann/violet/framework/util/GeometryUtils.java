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

package com.horstmann.violet.framework.util;

import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Miscellaneous geometry utilities.
 */
public class GeometryUtils //几何工具类
{
    /**
     * Moves this rectangle by a given x- and y-offset
     * @param r the rectangle to move
     * @param dx the x-offset
     * @param dy the y-offset
     */
    public static void translate(RectangularShape r, double dx, double dy)
    {
    	//根据给定的坐标X和Y移动矩形窗体，并不改变它的大小
        r.setFrame(r.getX() + dx, r.getY() + dy, r.getWidth(), r.getHeight()); 
    }
    
    public static Point2D getMin(RectangularShape r)
    {
        return new Point2D.Double(r.getX(), r.getY());//以 double 精度返回窗体矩形左上角的 X 坐标。
    }
}
