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

public class TestCaseInstantiationPanel extends JPanel{
  
    private JTaskPane TestCaseFilePanel;//上一步生成的测试用例展示面板
    private JPanel OperationPanel;//操作面板
    private JList<String> testcaseFileList;
   
     
	public TestCaseInstantiationPanel() {
		// TODO Auto-generated constructor stub			
		initUI();		
	}

	private void initUI() {
		// TODO Auto-generated method stub
		initUppaalFilePanel();
	    initOpreationPanel();
	    this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));    
	    this.add(TestCaseFilePanel);	   
	    this.add(OperationPanel);
	  	    
	}

	private void initOpreationPanel() {
		// TODO Auto-generated method stub
		OperationPanel=new JPanel();		
		JButton De_migration=new JButton("抽象测试用例实例化");
		OperationPanel.setLayout(new BoxLayout(OperationPanel,BoxLayout.X_AXIS));
		OperationPanel.add(De_migration);
								
	}

	private void initUppaalFilePanel() {
		// TODO Auto-generated method stub
		TestCaseFilePanel=new JTaskPane();
		TestCaseFilePanel.setLayout(new BoxLayout(TestCaseFilePanel,BoxLayout.Y_AXIS));  
		
		String[] testcaseFileLists={"1.抽象测试用例","2.抽象测试用例","3.抽象测试用例"};//UPPAAL文件列表
		testcaseFileList=new JList<String>(testcaseFileLists);	
		JTaskPaneGroup group = new JTaskPaneGroup();
        Font font = group.getFont().deriveFont(Font.PLAIN);
        group.setFont(font);
        group.setTitle("抽象测试用例文件");
        group.setLayout(new BorderLayout());  
        group.add(testcaseFileList, BorderLayout.CENTER);   
        TestCaseFilePanel.add(group);
	}

}
