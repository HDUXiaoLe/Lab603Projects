package com.horstmann.violet.application.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.horstmann.violet.application.consolepart.ConsoleMessageTabbedPane;
import com.horstmann.violet.application.consolepart.ConsolePart;
import com.horstmann.violet.application.consolepart.ConsolePartDetailInfoTable;
import com.horstmann.violet.application.consolepart.ConsolePartTextArea;
import com.horstmann.violet.workspace.IWorkspace;

public class StepButtonPanel extends JPanel {
	private JButton homebutton;
	private JButton step1button;
	private JButton step2button;
	private JButton step3button;
	private JButton step4button;
	private JButton step5button;
	private List<JButton> stepButtonGroup;
	private MainFrame mainFrame;
    private ConsolePart consolePart;
    private JPanel operationPanel;
    StepThreeCenterTabbedPane stepThreeCenterTabbedPane;
    StepFourCenterTabbedPane stepFourCenterTabbedPane=new StepFourCenterTabbedPane();
    private JButton Threestart=new JButton("开始");
    private JButton Fourstart=new JButton("开始");
	public StepButtonPanel(MainFrame mainFrame) {
		this.setBackground(Color.DARK_GRAY);
		this.mainFrame=mainFrame;	
		init();
		stepThreeCenterTabbedPane=mainFrame.getStepThreeCenterTabbedPane();
		
		
	}
    
	private void init() {
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		initButton();
		GridBagConstraints s = new GridBagConstraints();// 定义一个GridBagConstraints，
		// 是用来控制添加进的组件的显示位置
		s.fill = GridBagConstraints.BOTH;
		s.anchor = GridBagConstraints.FIRST_LINE_START;
		s.insets = new Insets(0, 0, 0,0);
        this.add(homebutton);
		this.add(step1button);
		this.add(step2button);
		this.add(step3button);
		this.add(step4button);
		this.add(step5button);
		s.gridx = 0;
		s.gridy = 0;
		s.weighty = 1;
		s.weightx=1;
		layout.setConstraints(homebutton, s);// 设置组件
		s.gridx = 0;
		s.gridy = 1;
		layout.setConstraints(step1button, s);
		s.gridx = 0;
		s.gridy = 2;
		layout.setConstraints(step2button, s);
		s.gridx = 0;
		s.gridy = 3;
		layout.setConstraints(step3button, s);
		s.gridx = 0;
		s.gridy = 4;
		layout.setConstraints(step4button, s);
		s.gridx = 0;
		s.gridy = 5;
		layout.setConstraints(step5button, s);
		// TODO Auto-generated method stub

		SetButtonListener();

	}

	private void initButton() {
		homebutton =new JButton();
		step1button = new JButton();
		step2button = new JButton();
		step3button = new JButton();
		step4button = new JButton();
		step5button = new JButton(); 
		
	    homebutton.setText("首页");
	    homebutton.setForeground(Color.RED);
		step1button.setText("第一步:UML模型建立及导入");
		step2button.setText("第二步:UML模型转化时间自动机");
		step3button.setText("第三步:抽象测试用例生成");
		step4button.setText("第四步:测试用例实例化");
		step5button.setText("第五步:测试用例实例化验证");
		stepButtonGroup = new ArrayList<JButton>();
		stepButtonGroup.add(homebutton);
		stepButtonGroup.add(step1button);
		stepButtonGroup.add(step2button);
		stepButtonGroup.add(step3button);
		stepButtonGroup.add(step4button);
		stepButtonGroup.add(step5button);
		operationPanel=mainFrame.getOpreationPart();
		operationPanel.setLayout(new GridLayout(1,1));
		consolePart=mainFrame.getConsolePart();	
		// TODO Auto-generated method stub
        step2button.setEnabled(false);//初始其他步骤按钮都不可点击
        step3button.setEnabled(false);
        step4button.setEnabled(false);
        step5button.setEnabled(false);
	}
	public void clearSelection() {
		for (JButton stepButton : stepButtonGroup) {			
			stepButton.setForeground(Color.BLACK);
		}
	}
	public void clearConsolePart(){
		this.consolePart.getContentPane().removeAll();;
	}
  private void ClearOpreationPanel()
  {
	  this.operationPanel.removeAll();
  }
	private void SetButtonListener() {
Threestart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				stepThreeCenterTabbedPane.getConsolePartScrollPane()
				.getViewport().add(new ConsolePartDetailInfoTable(0));			
			stepThreeCenterTabbedPane.getConsolePartScrollPane().getViewport().repaint();
				
				
			}
		});
Fourstart.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		stepFourCenterTabbedPane.getConsolePartScrollPane()
		.getViewport().add(new ConsolePartDetailInfoTable(0));			
	    stepFourCenterTabbedPane.getConsolePartScrollPane().getViewport().repaint();
		
		
	}
});
		homebutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();		
				homebutton.setForeground(Color.RED);	
				JLabel jLabel=new JLabel();
				jLabel.setText(homebutton.getText());
				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
				jLabel.setForeground(Color.white);
				JPanel labelpanel=mainFrame.getStepJLabel();
				labelpanel.setLayout(new GridBagLayout());
				labelpanel.removeAll();
				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));				     				
			    mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getHomePanel());
				mainFrame.getConsolePart().setVisible(false);
				mainFrame.getOpreationPart().setVisible(false);	
				mainFrame.getContentPane().repaint();
			}
		});						
		// TODO Auto-generated method stub
		step1button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();
				//显示控制面板和操作面板			  			
				//步骤一按钮高亮			
				step1button.setForeground(Color.RED);							
				//首先移除欢迎界面
			
				//修改原来的标题面板
				JLabel jLabel=new JLabel();
				jLabel.setText(step1button.getText());
				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
				jLabel.setForeground(Color.white);
				JPanel labelpanel=mainFrame.getStepJLabel();
				labelpanel.setLayout(new GridBagLayout());
				labelpanel.removeAll();
				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
													
			    //添加操作面板
				ClearOpreationPanel();
				operationPanel.add(mainFrame.getProjectTree());													
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getStepOneCenterTabbedPane());	
				
				//添加过程信息组件	
			    clearConsolePart();				  
			    consolePart.setTitle("UML模型建立过程信息");
				consolePart.add(new ConsoleMessageTabbedPane("详细信息",new JTextArea("UML模型正在建立中......\n\n\n\n\n\n")));				
				wakeupUI();
				mainFrame.getContentPane().repaint();
				step2button.setEnabled(true);//第一步点击之后，第二步可点击
			}
		});
		step2button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
				clearSelection();
										
				step2button.setForeground(Color.RED);
				//标题
				JLabel jLabel=new JLabel();
				jLabel.setText(step2button.getText());
				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
				jLabel.setForeground(Color.white);
				JPanel labelpanel=mainFrame.getStepJLabel();
				labelpanel.setLayout(new GridBagLayout());
				labelpanel.removeAll();
				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
				labelpanel.add(new JButton("开始"),new GBC(1, 0));
				labelpanel.add(new JButton("暂停"),new GBC(2, 0));
				
				ClearOpreationPanel();
			    operationPanel.add(mainFrame.getModelTransformationPanel());
			    
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(mainFrame.getStepTwoCenterTabbedPane());
			    clearConsolePart();			    
			    consolePart.setTitle("UML模型转化时间自动机过程信息");
				consolePart.add(new ConsoleMessageTabbedPane("详细信息",new JTextArea("UML模型正在转化中......\n\n\n\n\n\n")));	
				
							
				mainFrame.getContentPane().repaint();
				step3button.setEnabled(true);
			}
		});
		
		step3button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();
			 
				step3button.setForeground(Color.RED);
			
				mainFrame.getCenterTabPanel().removeAll();
				
				mainFrame.getCenterTabPanel().add(mainFrame.getStepThreeCenterTabbedPane());
				JLabel jLabel=new JLabel();
				jLabel.setText(step3button.getText());
				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
				jLabel.setForeground(Color.white);
				JPanel labelpanel=mainFrame.getStepJLabel();
				labelpanel.setLayout(new GridBagLayout());
				labelpanel.removeAll();
				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));																			
				labelpanel.add(Threestart,new GBC(1, 0));
				labelpanel.add(new JButton("暂停"),new GBC(2, 0));				
				ClearOpreationPanel();//
				operationPanel.add(new AbstractTestCaseGenerationPanel());				
				clearConsolePart();			    
				    consolePart.setTitle("抽象测试用例生成过程信息");
					consolePart.add(new ConsoleMessageTabbedPane("详细信息",new JTextArea("抽象测试用例正在生成...\n\n\n\n\n\n\n")));	
					
					mainFrame.getContentPane().repaint();
					step4button.setEnabled(true);
			}
		});
		step4button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();
			
				step4button.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
				
				JLabel jLabel=new JLabel();
				jLabel.setText(step4button.getText());
				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
				jLabel.setForeground(Color.white);
				JPanel labelpanel=mainFrame.getStepJLabel();
				labelpanel.setLayout(new GridBagLayout());
				labelpanel.removeAll();
				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
				labelpanel.add(Fourstart,new GBC(1, 0));
				labelpanel.add(new JButton("暂停"),new GBC(2, 0));
				
				ClearOpreationPanel();
				operationPanel.add(new TestCaseInstantiationPanel());
				
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(stepFourCenterTabbedPane);
				
				clearConsolePart();	
			
				consolePart.setTitle("抽象测试用例实例化过程信息");
				consolePart.add(new ConsoleMessageTabbedPane("详细信息",new JTextArea("抽象测试用例正在实例化...\n\n\n\n\n\n\n")));	
				
				mainFrame.getContentPane().repaint();
				step5button.setEnabled(true);
			}
		});
		step5button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();		
				step5button.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
				
				JLabel jLabel=new JLabel();
				jLabel.setText(step5button.getText());
				jLabel.setFont(new Font("宋体", Font.BOLD, 20));
				jLabel.setForeground(Color.white);
				JPanel labelpanel=mainFrame.getStepJLabel();
				labelpanel.setLayout(new GridBagLayout());
				labelpanel.removeAll();
				labelpanel.add(jLabel,new GBC(0, 0).setWeight(1, 0));
				labelpanel.add(new JButton("开始"),new GBC(1, 0));
				labelpanel.add(new JButton("暂停"),new GBC(2, 0));
				
				ClearOpreationPanel();
				operationPanel.add(new TestCaseConfirmationPanel());
				
				
				mainFrame.getCenterTabPanel().removeAll();
				mainFrame.getCenterTabPanel().add(new StepFiveCenterTabbedPane());
				clearConsolePart();
			
				consolePart.setTitle("测试用例实例验证过程信息");
    			consolePart.add(new ConsoleMessageTabbedPane("详细信息",new JTextArea("测试用例实例正在验证中......\n\n\n\n\n\n")));	
							
				mainFrame.getContentPane().repaint();
			}
		});
	}
	/*
	 * 使原来不可见的界面重新可见(除了首页,都需要重新可见,首页已使其他界面不可见)
	 */
	public void wakeupUI()
	{
		mainFrame.getConsolePart().setVisible(true);
		mainFrame.getOpreationPart().setVisible(true);
	}
}
