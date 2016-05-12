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

package com.horstmann.violet.framework.file.persistence;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class CustomPersistenceDelegate extends DefaultPersistenceDelegate
{

    protected Expression instantiate(Object oldInstance, Encoder out)
    //Encoder 是这样的类，它可用于创建根据其公共 API 对 JavaBeans 集合状态进行编码的文件或流。
    //Encoder 结合其持久委托，负责将对象图形拆分成一系列可用于创建它的 Statements 和 Expression。
    //子类通常使用某种可读形式（比如 Java 源代码或 XML）提供这些表达式的语法。 
    {
        try
        {
            Class<?> cl = oldInstance.getClass();
            Field[] fields = cl.getFields();
            for (int i = 0; i < fields.length; i++)
            {
                if (Modifier.isStatic(fields[i].getModifiers()) && fields[i].get(null) == oldInstance)
                {
                    return new Expression(fields[i], "get", new Object[]
                    {
                        null
                    });
                }
            }//持久化
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    protected boolean mutatesTo(Object oldInstance, Object newInstance)
    {
        return oldInstance == newInstance;
    }
//比较新实例和旧实例
}
