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

package com.horstmann.violet.framework.propertyeditor.customeditor;


import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.horstmann.violet.product.diagram.abstracts.node.CombinedFragmentNode;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.property.Condition;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentPart;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;

public class ConditionEditor extends PropertyEditorSupport
{
    public boolean supportsCustomEditor()
    {
        return true;
    } 

    public Component getCustomEditor()
    {     
        final JPanel panel = new JPanel();
        panel.add(getOtherComponent());
        return panel;
    }
    public JComponent getOtherComponent()
    {
    	this.condition=(Condition) getValue(); 
    	combinedFragmentNode=condition.getParentNode();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("监护条件"));
        conditiontable.getTableHeader().setVisible(false);
        conditiontable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        InitConditions();
        conditionAddButton.setText("新建");
        conditionAddButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt)
        	{
        		 addCondition(evt); 
        		 SetFragmentPartConditionText();
        		// firePropertyChange();
        	}
        });
        conditionSaveButton.setText("保存");
        conditionSaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveCondition(e);
			}
		});
        conditionDeleteButton.setText("删除");
        conditionDeleteButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent evt)
        	{
        		deleteCondition(evt);
        	}
        });
        jScrollPane.setBorder(BorderFactory.createEtchedBorder());
        conditiontable.setModel(conditionTableModel);
        jScrollPane.setViewportView(conditiontable);        
        GroupLayout panelLayout=new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
        	panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        	.addGroup(panelLayout.createSequentialGroup()       	    	    
        	       .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
        		       .addGroup(panelLayout.createSequentialGroup()
        		           .addContainerGap()
        		           .addComponent(conditionAddButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
        		           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        		           .addComponent(conditionDeleteButton, GroupLayout.PREFERRED_SIZE, 60,GroupLayout.PREFERRED_SIZE)
        		           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        		           .addComponent(conditionSaveButton,GroupLayout.PREFERRED_SIZE,60,GroupLayout.PREFERRED_SIZE))
        		           .addComponent(jScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)))
        		                		                 		          
        		);
       panelLayout.setVerticalGroup(
        	      panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        	      .addGroup(panelLayout.createSequentialGroup()
        	    		  
        	        .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        	          .addGroup(panelLayout.createSequentialGroup()          
        	          .addComponent(jScrollPane,GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))       	         
        	          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
        	          .addGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	          .addComponent(conditionDeleteButton)
        	          .addComponent(conditionAddButton)
        	          .addComponent(conditionSaveButton))
        	        .addContainerGap())
        	    );
       insertUpate();
       return panel;
        
    }
    protected void saveCondition(ActionEvent e) {
		// TODO Auto-generated method stub
		SetFragmentPartConditionText();
	}

	public void SetFragmentPartConditionText()
    {	
    	   List<String> ConditionTexts=((StringTableModel)conditiontable.getModel()).getEntries();//获取到所有监护条件	 
    	   List<FragmentPart> fragmentParts=new ArrayList<FragmentPart>();
    	   for(String conditiontext : ConditionTexts)
    	   {
    		 int conditionIndex=ConditionTexts.indexOf(conditiontext);
    		    //根据监护条件的数量新建分块
    		fragmentParts.add(new FragmentPart());
    		fragmentParts.get(conditionIndex).setConditionText(conditiontext);	   	   		 	   
    	   }	
    	   combinedFragmentNode.setFragmentParts(fragmentParts);
    }
   
    protected void deleteCondition(ActionEvent evt) {
		// TODO Auto-generated method stub
    	 deleteSelectedTableEntry(conditiontable);
	}

	private void deleteSelectedTableEntry(JTable table) {
		// TODO Auto-generated method stub
		 StringTableModel tableModel = (StringTableModel) table.getModel();
		    int row = table.getSelectedRow();
		    if (row >= 0 && row < tableModel.getRowCount()) {
		      tableModel.removeEntryAt(row);
		    }
	}
   
	private void InitConditions() {
	
	List<String> conditionTexts= condition.getConditionTexts();
	for(String conditionText: conditionTexts)
		{			
	     conditionTableModel.addEntry(conditionText);
	    }
	}	
	public void insertUpate()
	{									
	condition.setConditionTexts(((StringTableModel)conditiontable.getModel()).getEntries());		  
	}

	private void addCondition(ActionEvent evt)
    {
	
    	((StringTableModel)conditiontable.getModel()).addEntry("");    	 	
    }	   
    private Condition condition=new Condition(); 
    private INode combinedFragmentNode;
    private JTable conditiontable=new JTable();	
    private JButton conditionAddButton=new JButton();
    private JButton conditionDeleteButton=new JButton();
    private JButton conditionSaveButton=new JButton();
    private JScrollPane jScrollPane=new JScrollPane(); 
    private StringTableModel conditionTableModel = new StringTableModel(); 
}
