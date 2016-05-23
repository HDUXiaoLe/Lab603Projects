package com.horstmann.violet.application.consolepart;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JTextArea;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.workspace.IWorkspace;


public class ConsolePart extends JInternalFrame{
  
	private ConsoleMessageTabbedPane consoleMessageTabbedPane;
	public ConsolePart(MainFrame mainFrame) {		
		this.setResizable(true);					
		setupUI();
	}				
	private void setupUI() {
		// TODO Auto-generated method stub	
		 consoleMessageTabbedPane=new ConsoleMessageTabbedPane("œÍœ∏–≈œ¢",new JTextArea());
		this.getContentPane().add(getConsoleMessageTabbedPane());
	}
	public ConsoleMessageTabbedPane getConsoleMessageTabbedPane() {
		return consoleMessageTabbedPane;
	}
	public void setConsoleMessageTabbedPane(ConsoleMessageTabbedPane consoleMessageTabbedPane) {
		this.consoleMessageTabbedPane = consoleMessageTabbedPane;
	}
	


	 


}
