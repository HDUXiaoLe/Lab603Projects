package com.horstmann.violet.application.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.horstmann.violet.framework.theme.ThemeManager;
import com.horstmann.violet.workspace.editorpart.IEditorPart;

public class StepTwoCenterTabbedPane extends JTabbedPane{

	private JPanel UppaalDiagramTabbedPane;
	private JPanel UMLDiagramTabbedPane;
	private JPanel MarkovDiagramTabbedPane;

	public StepTwoCenterTabbedPane()
	{
		UppaalDiagramTabbedPane=new JPanel();
		UMLDiagramTabbedPane=new JPanel();
		MarkovDiagramTabbedPane=new JPanel();
		UppaalDiagramTabbedPane.setLayout(new GridBagLayout());
		UMLDiagramTabbedPane.setLayout(new GridBagLayout());
		MarkovDiagramTabbedPane.setLayout(new GridBagLayout());
		this.add("UML模型解析",UMLDiagramTabbedPane);
		this.add("自动机解析",UppaalDiagramTabbedPane);
		this.add("MarKov链",MarkovDiagramTabbedPane);
			
	}
  
	public JPanel getUppaalDiagramTabbedPane() {
		return UppaalDiagramTabbedPane;
	}
	public void setUppaalDiagramTabbedPane(JPanel uppaalDiagramTabbedPane) {
		UppaalDiagramTabbedPane = uppaalDiagramTabbedPane;
	}
	public JPanel getUMLDiagramTabbedPane() {
		return UMLDiagramTabbedPane;
	}
	public void setUMLDiagramTabbedPane(JPanel uMLDiagramTabbedPane) {
		UMLDiagramTabbedPane = uMLDiagramTabbedPane;
	}
	public JPanel getMarkovDiagramTabbedPane() {
		return MarkovDiagramTabbedPane;
	}
	public void setMarkovDiagramTabbedPane(JPanel markovDiagramTabbedPane) {
		MarkovDiagramTabbedPane = markovDiagramTabbedPane;
	}	
}
