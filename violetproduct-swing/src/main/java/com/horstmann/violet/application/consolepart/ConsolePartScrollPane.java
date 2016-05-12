package com.horstmann.violet.application.consolepart;
import javax.swing.JScrollPane;


public class ConsolePartScrollPane extends JScrollPane{
	private static final long serialVersionUID = -5258637918997915428L;
public ConsolePartScrollPane(){
	init();
}
private void init() {
	setAutoscrolls(true);
	setViewportView(new ConsolePartDetailInfoTable());//±í¸ñ
	setVisible(true);
}
}
