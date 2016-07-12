package com.horstmann.violet.application.gui;

import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.horstmann.violet.application.consolepart.ConsolePartScrollPane;

public class StepThreeCenterTabbedPane extends JTabbedPane{
	

		
		private JPanel abstractUppaalTabbedPane;
		private JPanel uppaalTabbedPane;	  
		private JPanel abstractTestCaseOptimize;
		private JScrollPane consolePartScrollPane;
		public StepThreeCenterTabbedPane()
		{
		
			uppaalTabbedPane=new JPanel();	
			abstractTestCaseOptimize=new JPanel();
			abstractUppaalTabbedPane=new JPanel();
			consolePartScrollPane=new JScrollPane();
			
			uppaalTabbedPane.setLayout(new GridBagLayout());
			abstractUppaalTabbedPane.setLayout(new GridBagLayout());
			this.add("抽象时间迁移的自动机",abstractUppaalTabbedPane);
			this.add("不含时间迁移的自动机",uppaalTabbedPane);
			this.add("抽象测试用例生成",consolePartScrollPane);
            this.add("抽象测试用例优化约简",abstractTestCaseOptimize);
				
		}
		public JPanel getAbstractUppaalTabbedPane() {
			return abstractUppaalTabbedPane;
		}
		public void setAbstractUppaalTabbedPane(JPanel abstractUppaalTabbedPane) {
			this.abstractUppaalTabbedPane = abstractUppaalTabbedPane;
		}
		public JPanel getUppaalTabbedPane() {
			return uppaalTabbedPane;
		}
		public void setUppaalTabbedPane(JPanel uppaalTabbedPane) {
			this.uppaalTabbedPane = uppaalTabbedPane;
		}
		public JPanel getAbstractTestCaseOptimize() {
			return abstractTestCaseOptimize;
		}
		public void setAbstractTestCaseOptimize(JPanel abstractTestCaseOptimize) {
			this.abstractTestCaseOptimize = abstractTestCaseOptimize;
		}
		public JScrollPane getConsolePartScrollPane() {
			return consolePartScrollPane;
		}
		public void setConsolePartScrollPane(JScrollPane consolePartScrollPane) {
			this.consolePartScrollPane = consolePartScrollPane;
		}
		
	}

