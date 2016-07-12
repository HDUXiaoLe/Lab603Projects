package com.horstmann.violet.application.gui;

import java.awt.GridBagLayout;
import java.io.SequenceInputStream;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.horstmann.violet.workspace.IWorkspace;

public class StepOneCenterTabbedPane extends JTabbedPane{
	private JPanel sequenceDiagramTabbedPane;
	private JPanel timingDiagramTabbedPane;
	private JPanel stateDiagramTabbedPane;
	private JPanel usecaseDiagramTabbedPane;
	/*
	 * 添加4个不同类型图的TabbedPane
	 */
	public StepOneCenterTabbedPane()
	{
		sequenceDiagramTabbedPane=new JPanel();
		timingDiagramTabbedPane=new JPanel();
		stateDiagramTabbedPane=new JPanel();
		usecaseDiagramTabbedPane=new JPanel();
		sequenceDiagramTabbedPane.setLayout(new GridBagLayout());
		timingDiagramTabbedPane.setLayout(new GridBagLayout());
		stateDiagramTabbedPane.setLayout(new GridBagLayout());
		usecaseDiagramTabbedPane.setLayout(new GridBagLayout());
		this.add("顺序图",sequenceDiagramTabbedPane);
		this.add("时序图",timingDiagramTabbedPane);
		this.add("状态图",stateDiagramTabbedPane);
		this.add("用例图",usecaseDiagramTabbedPane);
		
	}
	/*
	 * 通过传递Iworkspace参数来确定是在哪个TabbedPane下面添加图形
	 */
    public JPanel getUMLTabbedPane(IWorkspace workspace)
    {
    	//这里分两种情况
    	//1、新建:判断是不是类似于N.XXX类型
    	//2、导入:判断文件名后缀是不是.XXX.violet.xml
       if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
    		   ||workspace.getTitle().toString().substring(2,4).equals("Se"))
    	{
    		return this.getSequenceDiagramTabbedPane();
        }
    	if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
    			||workspace.getTitle().toString().substring(2,4).equals("Us"))
		{
	        return this.getUsecaseDiagramTabbedPane();
		}
    	if(workspace.getTitle().toString().endsWith(".timing.violet.xml")
    ||workspace.getTitle().toString().substring(2,4).equals("Ti"))
		{
	        return this.getTimingDiagramTabbedPane();
		}
    	if(workspace.getTitle().toString().endsWith(".state.violet.xml")
    			||workspace.getTitle().toString().substring(2,4).equals("St"))
		{
	        return this.getStateDiagramTabbedPane();
		}
    	    return null;
    }
	public JPanel getSequenceDiagramTabbedPane() {
		return sequenceDiagramTabbedPane;
	}
	public void setSequenceDiagramTabbedPane(JPanel sequenceDiagramTabbedPane) {
		this.sequenceDiagramTabbedPane = sequenceDiagramTabbedPane;
	}
	public JPanel getTimingDiagramTabbedPane() {
		return timingDiagramTabbedPane;
	}
	public JPanel getStateDiagramTabbedPane() {
		return stateDiagramTabbedPane;
	}	
	public JPanel getUsecaseDiagramTabbedPane() {
		return usecaseDiagramTabbedPane;
	}
	
}
