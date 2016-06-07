package com.horstmann.violet.application.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.horstmann.violet.application.consolepart.ConsolePartTextArea;
import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

public class ModelTransformationPanel extends JPanel{
	 //转换功能区文本提示
		
	private MainFrame mainFrame;
	private JDialog messageDialog;
     
    public DefaultMutableTreeNode allDiagram;
    public DefaultMutableTreeNode timingDiagram;
    public DefaultMutableTreeNode sequenceDiagram;
    public DefaultMutableTreeNode uppaalDiagram;
    public JTree UMLDiagramTree;
	private String[] tdlists;
	public JTree UppaalDiagramTree;
	private String[] sdlists;
	private String[] uppaallists;	     
		
	public ModelTransformationPanel(MainFrame mainFram){	
		this.mainFrame=mainFram;
		initFileList();
		initUI();
		this.setLayout(new GridLayout(2, 1));
		add(UMLDiagramTree);
		add(UppaalDiagramTree);
	}  		   
	    private void initUI() {
			// TODO Auto-generated method stub		 		   
			 
		       allDiagram = new DefaultMutableTreeNode("要转化的UML模型文件");
		       uppaalDiagram=new DefaultMutableTreeNode("生成的时间自动机文件");
		       sequenceDiagram =new DefaultMutableTreeNode("顺序图模型文件");
		       timingDiagram=new DefaultMutableTreeNode("时序图模型文件");
		       for(String td:tdlists)
		       {
		    	   timingDiagram.add(new DefaultMutableTreeNode(td));
		       }
		       for(String sd:sdlists)
		       {
		    	   sequenceDiagram.add(new DefaultMutableTreeNode(sd));
		       }
		       for(String up :uppaallists){
		    	   uppaalDiagram.add(new DefaultMutableTreeNode(up));
		       }
	           allDiagram.add(sequenceDiagram);
	           allDiagram.add(timingDiagram);
	           UMLDiagramTree=new JTree(allDiagram);
	           UppaalDiagramTree=new JTree(uppaalDiagram);
	           
	    }				            				            					 			
	private void initFileList() {
		sdlists = new String[] {"1.sequencediagram",
				"2.sequencediagram",
				"3.sequencediagram"};
	
		tdlists = new String[] {"1.timingdiagram",
				"2.timingdiagram",
				"3.timingdiagram"};	
	
		uppaallists = new String[] {"1.uppaal","2.uppaal","3.uppaal","4.uppaal"};				
	}

	
	
	}
	


