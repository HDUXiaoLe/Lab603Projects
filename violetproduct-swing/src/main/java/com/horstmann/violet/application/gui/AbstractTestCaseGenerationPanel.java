package com.horstmann.violet.application.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.l2fprod.common.swing.JTaskPane;
import com.l2fprod.common.swing.JTaskPaneGroup;

public class AbstractTestCaseGenerationPanel extends JPanel{
  
    private JTaskPane UppaalFilePanel;//上一步生成的时间自动机展示面板
    private JPanel OperationPanel;//操作面板
    private JList<String> uppaalFileList;
   
     
	public AbstractTestCaseGenerationPanel() {
		// TODO Auto-generated constructor stub			
		initUI();		
	}

	private void initUI() {
		// TODO Auto-generated method stub
		initUppaalFilePanel();
	    initOpreationPanel();
	    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));    
	    this.add(UppaalFilePanel);	   
	    this.add(OperationPanel);
	  	    
	}

	private void initOpreationPanel() {
		// TODO Auto-generated method stub
		OperationPanel=new JPanel();		
		JButton De_migration=new JButton("生成抽象测试用例");
		OperationPanel.setLayout(new BoxLayout(OperationPanel,BoxLayout.X_AXIS));
		OperationPanel.add(De_migration);
								
	}

	private void initUppaalFilePanel() {
		// TODO Auto-generated method stub
		UppaalFilePanel=new JTaskPane();
		UppaalFilePanel.setLayout(new BoxLayout(UppaalFilePanel,BoxLayout.Y_AXIS));  
		
		String[] uppaalFiles={"1.uppaal","2.uppaal","3.uppaal"};//UPPAAL文件列表
		uppaalFileList=new JList<String>(uppaalFiles);	
		JTaskPaneGroup group = new JTaskPaneGroup();
        Font font = group.getFont().deriveFont(Font.PLAIN);
        group.setFont(font);
        group.setTitle("时间自动机文件");
        group.setLayout(new BorderLayout());  
        group.add(uppaalFileList, BorderLayout.CENTER);   
        UppaalFilePanel.add(group);
	}

}
