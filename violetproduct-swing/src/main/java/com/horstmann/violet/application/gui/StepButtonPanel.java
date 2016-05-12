package com.horstmann.violet.application.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;

import com.horstmann.violet.application.consolepart.ConsoleMessageTabbedPane;
import com.horstmann.violet.application.consolepart.ConsolePartTextArea;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.product.diagram.usecase.UseCaseDiagramGraph;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.Workspace;

public class StepButtonPanel extends JPanel {
	private JButton step1button;
	private JButton step2button;
	private JButton step3button;
	private JButton step4button;
	private JButton step5button;
	private List<JButton> stepButtonGroup;
	private MainFrame mainFrame;
	private IWorkspace workspace;
	private ConsoleMessageTabbedPane consoleMessageTabbedPane;
    private ConsolePartTextArea consolePartTextArea;
	public StepButtonPanel(MainFrame mainFrame) {
		this.setBackground(Color.DARK_GRAY);
		this.mainFrame=mainFrame;	
		init();
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

		this.add(step1button);
		this.add(step2button);
		this.add(step3button);
		this.add(step4button);
		this.add(step5button);
		s.gridx = 0;
		s.gridy = 0;
		s.weighty = 1;
		layout.setConstraints(step1button, s);// 设置组件
		s.gridx = 0;
		s.gridy = 1;
		layout.setConstraints(step2button, s);
		s.gridx = 0;
		s.gridy = 2;
		layout.setConstraints(step3button, s);
		s.gridx = 0;
		s.gridy = 3;
		layout.setConstraints(step4button, s);
		s.gridx = 0;
		s.gridy = 4;
		layout.setConstraints(step5button, s);
		// TODO Auto-generated method stub

		SetButtonListener();

	}

	private void initButton() {
		step1button = new JButton();
		step2button = new JButton();
		step3button = new JButton();
		step4button = new JButton();
		step5button = new JButton();  
		step1button.setText("第一步:UML模型建立");
		step2button.setText("第二步:模型转化");
		step3button.setText("第三步:抽象测试用例生成");
		step4button.setText("第四步:测试用例实例化");
		step5button.setText("第五步:测试用例验证");
		stepButtonGroup = new ArrayList<JButton>();
		stepButtonGroup.add(step1button);
		stepButtonGroup.add(step2button);
		stepButtonGroup.add(step3button);
		stepButtonGroup.add(step4button);
		stepButtonGroup.add(step5button);
		// TODO Auto-generated method stub

	}

	public void clearSelection() {
		for (JButton stepButton : stepButtonGroup) {
			stepButton.setFocusable(false);
			stepButton.setForeground(Color.BLACK);
		}

	}

	private void SetButtonListener() {
		// TODO Auto-generated method stub
		step1button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();
				step1button.setFocusable(true);
				step1button.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
				JLabel step1Label=new JLabel(step1button.getText(),JLabel.CENTER);
				step1Label.setFont(new Font("宋体",Font.BOLD, 20));
				step1Label.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getStepJLabel());	
				mainFrame.setStepJLabel(step1Label);
				mainFrame.getConsolePart().setTitle("UML模型建立过程信息");
				mainFrame.getConsolePart().setVisible(true);//第一步过程信息组件显示出来
				mainFrame.getMainPanel().add(mainFrame.getProjectTree(),BorderLayout.EAST);
				mainFrame.getConsolePart().getConsoleMessageTabbedPane().removeAll();
				consolePartTextArea=new ConsolePartTextArea("正在建立模型........");
				mainFrame.getConsolePart().getConsoleMessageTabbedPane().add("详细信息",consolePartTextArea);
				mainFrame.getConsolePart().getConsoleMessageTabbedPane().
				add("详细信息",consolePartTextArea);	
				mainFrame.getMainPanel().remove(mainFrame.getModelTransformationPanel());
				mainFrame.getMainPanel().add(mainFrame.getStepJLabel(), BorderLayout.NORTH);
				mainFrame.revalidate();			    			
			}
		});
		step2button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();
				step2button.setFocusable(true);
				step2button.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());	  
				JLabel step2Label=new JLabel(step2button.getText(),JLabel.CENTER);
				step2Label.setFont(new Font("宋体",Font.BOLD, 20));
				step2Label.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getStepJLabel());		
				mainFrame.setStepJLabel(step2Label);
				mainFrame.getConsolePart().setTitle("UML模型转化时间自动机模型过程信息");
				mainFrame.getMainPanel().remove(mainFrame.getProjectTree());				
				consolePartTextArea=new ConsolePartTextArea("");
				consoleMessageTabbedPane=mainFrame.getConsolePart().getConsoleMessageTabbedPane();
				consoleMessageTabbedPane.removeAll();
				mainFrame.getModelTransformationPanel().setVisible(true);
			    mainFrame.getMainPanel().add(mainFrame.getModelTransformationPanel(),BorderLayout.EAST);					
				consoleMessageTabbedPane.add("详细信息",consolePartTextArea);		
				mainFrame.getMainPanel().add(mainFrame.getStepJLabel(), BorderLayout.NORTH);
				mainFrame.revalidate();
			}
		});
		step3button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();
				step3button.setFocusable(true);
				step3button.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
				JLabel step3Label=new JLabel(step3button.getText(),JLabel.CENTER);
				step3Label.setFont(new Font("宋体",Font.BOLD, 20));
				step3Label.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getStepJLabel());	
				mainFrame.setStepJLabel(step3Label);
				mainFrame.getConsolePart().setTitle("抽象测试用例生成过程信息");
				mainFrame.getMainPanel().add(mainFrame.getStepJLabel(), BorderLayout.NORTH);
				mainFrame.revalidate();
			}
		});
		step4button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();
				step4button.setFocusable(true);
				step4button.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
				JLabel step4Label=new JLabel(step4button.getText(),JLabel.CENTER);
				step4Label.setFont(new Font("宋体",Font.BOLD, 20));
				step4Label.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getStepJLabel());	
				mainFrame.setStepJLabel(step4Label);
				mainFrame.getConsolePart().setTitle("抽象测试用例实例化过程信息");
				mainFrame.getMainPanel().add(mainFrame.getStepJLabel(), BorderLayout.NORTH);
				mainFrame.revalidate();
			}
		});
		step5button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearSelection();
				step5button.setFocusable(true);
				step5button.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getWelcomePanel());
				JLabel step5Label=new JLabel(step5button.getText(),JLabel.CENTER);
				step5Label.setFont(new Font("宋体",Font.BOLD, 20));
				step5Label.setForeground(Color.RED);
				mainFrame.getMainPanel().remove(mainFrame.getStepJLabel());
				mainFrame.setStepJLabel(step5Label);
				mainFrame.getConsolePart().setTitle("测试用例认证过程信息");
				mainFrame.getMainPanel().add(mainFrame.getStepJLabel(), BorderLayout.NORTH);
				mainFrame.revalidate();
			}
		});
	}

}
