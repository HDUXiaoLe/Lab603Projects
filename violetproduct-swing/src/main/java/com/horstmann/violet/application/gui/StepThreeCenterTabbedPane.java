package com.horstmann.violet.application.gui;

import javax.swing.JTabbedPane;

import com.horstmann.violet.application.consolepart.ConsolePartScrollPane;

public class StepThreeCenterTabbedPane extends JTabbedPane{
	

		
		private JTabbedPane abstractUppaalTabbedPane;
		private JTabbedPane uppaalTabbedPane;	  
		private JTabbedPane abstractTestCaseOptimize;
		public StepThreeCenterTabbedPane()
		{
		
			uppaalTabbedPane=new JTabbedPane();	
			abstractTestCaseOptimize=new JTabbedPane();
			abstractUppaalTabbedPane=new JTabbedPane();
			this.add("抽象时间迁移的自动机",abstractUppaalTabbedPane);
			this.add("不含时间迁移的自动机",uppaalTabbedPane);
			this.add("抽象测试用例生成",new ConsolePartScrollPane(0));
            this.add("抽象测试用例优化约简",abstractTestCaseOptimize);
				
		}
	}

