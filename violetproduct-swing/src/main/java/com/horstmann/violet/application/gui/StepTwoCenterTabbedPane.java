package com.horstmann.violet.application.gui;

import javax.swing.JTabbedPane;

public class StepTwoCenterTabbedPane extends JTabbedPane{

	private JTabbedPane UppaalDiagramTabbedPane;
	private JTabbedPane UMLDiagramTabbedPane;
	public StepTwoCenterTabbedPane()
	{
		UppaalDiagramTabbedPane=new JTabbedPane();
		UMLDiagramTabbedPane=new JTabbedPane();
		
		this.add("UML模型解析",UMLDiagramTabbedPane);
		this.add("自动机解析",UppaalDiagramTabbedPane);
			
	}
}
