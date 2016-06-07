package com.horstmann.violet.application.gui;

import javax.swing.JTabbedPane;

import com.horstmann.violet.application.consolepart.ConsolePartScrollPane;

public class StepFourCenterTabbedPane extends JTabbedPane{
	
	public StepFourCenterTabbedPane()
	{					
		this.add("抽象测试用例实例化",new ConsolePartScrollPane(1));			
	}
}
