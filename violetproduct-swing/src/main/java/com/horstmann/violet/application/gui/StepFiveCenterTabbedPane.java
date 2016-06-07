package com.horstmann.violet.application.gui;

import javax.swing.JTabbedPane;

public class StepFiveCenterTabbedPane extends JTabbedPane{
	private JTabbedPane testcaseFile;
	
	public StepFiveCenterTabbedPane()
	{
		testcaseFile=new JTabbedPane();		
		this.add("测试用例实例化测试报告",testcaseFile);;		
	}

}
