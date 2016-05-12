package com.horstmann.violet.application.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ModelTransformationPanel extends JPanel{
	 //转换功能区文本提示
	private JPanel selectpanel;
	private JPanel sdpanel;
	private JPanel timingpanel;
	private List<JCheckBox> sdCheckBoxList;
	private List<JCheckBox> tdCheckBoxList;
	private List<String> sdlist;  //第一步建模的所有SD图
	private List<String> tdlist;  //第一步建模的所有TD图
	private JTextArea selectedText;
	private JButton transformationButton;
	private String selectStringInfo;
	private JButton okbutton;
	private JButton resetbutton;
	private MainFrame mainFrame;
	private JDialog messageDialog;
	
	public ModelTransformationPanel(MainFrame mainFram){	
		this.mainFrame=mainFram;
		initFileList();
		initUI();		
	}

	private void getSelectedUMLDiagarmInfo() {
		// TODO Auto-generated method stub
		selectStringInfo="您当前选中的UML图有:\n";
		for(JCheckBox checkBox :sdCheckBoxList)
		{
			if(checkBox.isSelected())
			{
				selectStringInfo+=checkBox.getText()+"\n";
			}				
		}
		for(JCheckBox checkBox :tdCheckBoxList)
		{
			if(checkBox.isSelected())
		    {
				selectStringInfo+=checkBox.getText()+"\n";
		    }		
		}
	}

	private void initFileList() {
		// TODO Auto-generated method stub
		sdlist=new ArrayList<String>();
		tdlist=new ArrayList<String>();
		sdlist.add("sd1.xml");
		sdlist.add("sd2.xml");
		sdlist.add("sd3.xml");
		tdlist.add("td1.xml");
		tdlist.add("td2.xml");
		tdlist.add("td3.xml");
		
	}

	private void initUI() {
		// TODO Auto-generated method stub
		//顺序图选择面板
		this.sdpanel=new JPanel();
		this.sdpanel.setLayout(new BoxLayout(sdpanel,BoxLayout.Y_AXIS));
		sdpanel.setBorder(BorderFactory.createTitledBorder("顺序图:"));
		sdCheckBoxList=new ArrayList<JCheckBox>();
		for(String sd :sdlist)
		{
			JCheckBox jCheckBox=new JCheckBox(sd);
		    sdCheckBoxList.add(jCheckBox);
		    sdpanel.add(jCheckBox);
		}
	   
		//时序图选择面板
		this.timingpanel=new JPanel();
		timingpanel.setBorder(BorderFactory.createTitledBorder("时序图:"));
		this.timingpanel.setLayout(new BoxLayout(timingpanel,BoxLayout.Y_AXIS));
		tdCheckBoxList=new ArrayList<JCheckBox>();
		for(String td :tdlist)
		{
			JCheckBox jCheckBox=new JCheckBox(td);
		    tdCheckBoxList.add(jCheckBox);
		    timingpanel.add(jCheckBox);
		}
		this.selectpanel=new JPanel();
		selectpanel.setLayout(new GridLayout(2, 1));
		selectpanel.add(sdpanel);
		selectpanel.add(timingpanel);
		selectpanel.setBorder(BorderFactory.createTitledBorder("选择要转化的UML图"));
		this.okbutton=new JButton("确定");
		this.resetbutton=new JButton("重置");
		//转换按钮
		this.transformationButton=new JButton("将所选UML图转化成时间自动机");
		//选择信息
		this.selectedText=new JTextArea("您当前选中的UML图有:\n");
		selectedText.setEditable(false);
		//确定按钮事件
		okbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getSelectedUMLDiagarmInfo();
				selectedText.setText(selectStringInfo);
				//
			}
		});
		//重置按钮事件
		resetbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(JCheckBox checkBox :sdCheckBoxList)
				{
					checkBox.setSelected(false);
				}
				for(JCheckBox checkBox :tdCheckBoxList)
				{
					checkBox.setSelected(false);
				}
				selectedText.setText("您当前选中的UML图有:\n");
			}
		});
		transformationButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				messageDialog=new JDialog(mainFrame, "UML模型转化",false);
				messageDialog.getContentPane().setLayout(new BoxLayout(messageDialog.getContentPane(), BoxLayout.Y_AXIS));
				JTextArea messageInfo=new JTextArea("模型转化成功");
				messageInfo.setEditable(false);
				messageDialog.getContentPane().add(messageInfo);
				JButton okButton=new JButton("确定");
				messageDialog.getContentPane().add(okButton);
				centerDialog(messageDialog, mainFrame);
				messageDialog.setSize(200, 200);
				messageDialog.setVisible(true);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						messageDialog.dispose();
						ConsolePartTextArea consolePartTextArea=new ConsolePartTextArea("正在转化中.........\n转化完成");
						mainFrame.getConsolePart().getConsoleMessageTabbedPane().removeAll();
						mainFrame.getConsolePart().getConsoleMessageTabbedPane().add("详细信息",consolePartTextArea);
					}
				});
				
				ConsolePartTextArea consolePartTextArea=new ConsolePartTextArea("正在转化中.........");
				mainFrame.getConsolePart().getConsoleMessageTabbedPane().removeAll();
				mainFrame.getConsolePart().getConsoleMessageTabbedPane().add("详细信息",consolePartTextArea);
			}
		});
		JPanel buttonPanel1=new JPanel();
	    buttonPanel1.setLayout(new GridLayout(1,2));
		buttonPanel1.add(okbutton);		
		buttonPanel1.add(resetbutton);
		JPanel buttonPanel2=new JPanel();
		buttonPanel2.add(transformationButton);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(selectpanel);
		this.add(buttonPanel1);
		this.add(selectedText);		
		this.add(buttonPanel2);
		
		}
	private void centerDialog(JDialog dialog, Frame owner)
    {
        Rectangle b = owner.getBounds();

        double x = b.getX() + b.getWidth() / 2 - dialog.getWidth() / 2;
        double y = b.getY() + b.getHeight() / 2 - dialog.getHeight() / 2;
        Dimension screenSize = owner.getToolkit().getScreenSize();
        if (x + dialog.getWidth() > screenSize.getWidth())
        {
            x = screenSize.getWidth() - dialog.getWidth();
        }
        if (y + dialog.getHeight() > screenSize.getHeight())
        {
            y = screenSize.getHeight() - dialog.getHeight();
        }
        Point newLocation = new Point(Math.max((int) x, 0), Math.max((int) y, 0));
        dialog.setLocation(newLocation);
    }
	}
	


