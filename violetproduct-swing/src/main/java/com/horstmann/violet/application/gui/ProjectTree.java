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

package com.horstmann.violet.application.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.persistence.IFileReader;
import com.horstmann.violet.product.diagram.classes.ClassDiagramGraph;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;

public class ProjectTree extends JPanel
{

    public ProjectTree(FileMenu fileMenu,MainFrame mainFrame)
    {      
        this.fileMenu = fileMenu;
        this.mainFrame=mainFrame;
        this.setBackground(Color.DARK_GRAY);
        setLayout(new GridLayout(1, 1));
        add(getProjectTree());//添加项目树          
    } 
    private JTree getProjectTree()
    {
    	init();
    	return projectjTree;
    }
    private void init() {
		// TODO Auto-generated method stub		 		   
		 
	       top = new DefaultMutableTreeNode("Project");                  					    
	       final JMenu newMenu = this.fileMenu.getFileNewMenu();
	        for (int i = 0; i < newMenu.getItemCount(); i++)
            {      	
                final JMenuItem item = newMenu.getItem(i);
                boolean isSubMenu = JMenu.class.isInstance(item);
                if (isSubMenu)
                {
                 for (int j = 0; j < ((JMenu) item).getItemCount(); j++)
                    {
                        final JMenuItem subItem = ((JMenu) item).getItem(j);
                    String label = subItem.getText();                 
                    DefaultMutableTreeNode newTreeNode = new DefaultMutableTreeNode(label.toLowerCase());
                    top.add(newTreeNode);
                    }
                 }
            }
            projectjTree=new JTree(top);
            projectjTree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				    if (e.getButton() == e.BUTTON3) {  //BUTTON3是鼠标右键
				     final DefaultMutableTreeNode  node =  (DefaultMutableTreeNode)projectjTree.getLastSelectedPathComponent();
				  
				     popupMenu=new JPopupMenu();
				     newDiagram=new JMenuItem("新建");
				     importDiagram = new JMenuItem("导入");
				     popupMenu.add(newDiagram);
				     popupMenu.add(importDiagram);
				     importDiagram.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								//导入文件
								fileMenu.fileOpenItem.doClick();//File目录下的导入标签										                 
								node.add(new DefaultMutableTreeNode(1));				
							}
						});
				     popupMenu.show(e.getComponent(),e.getX(),e.getY());
				      for (int i = 0; i < newMenu.getItemCount(); i++)
			            {
				    	  final JMenuItem item = newMenu.getItem(i);
				    	  for (int j = 0; j < ((JMenu) item).getItemCount(); j++)
		                    {
		                        final JMenuItem subItem = ((JMenu) item).getItem(j);		                      
				    	  if(node.toString().equals(subItem.getText().toLowerCase()))
				    	  {				    		
				    		  newDiagram.addActionListener(new ActionListener() {								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub						
									subItem.doClick();
			                        node.add(new DefaultMutableTreeNode(new File("1")));						
								}
							});				    		
				    	  }
		                    }
			            }
				    }
				    if(e.getClickCount()==2)
				    {
				    	  final DefaultMutableTreeNode  node =  (DefaultMutableTreeNode)projectjTree.getLastSelectedPathComponent();
				    	  if(node.isLeaf()) //是叶节点
				    	  {
				    	     //对node.getUserObject()进行处理;
				    	  }
				    }
			}});
    }			            			            				    											     					             
	  public DefaultMutableTreeNode top;
	  public JPopupMenu popupMenu;
	  public JMenuItem newDiagram;
	  public JMenuItem importDiagram;
	  public JTree projectjTree;	     
      private FileMenu fileMenu;
      private MainFrame mainFrame;
    
}
