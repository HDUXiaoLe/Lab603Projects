package com.horstmann.violet.application.consolepart;
import javax.swing.JScrollPane;


public class ConsolePartScrollPane extends JScrollPane{
	private static final long serialVersionUID = -5258637918997915428L;
public ConsolePartScrollPane(int index){
	init(index);
}
private void init(int index) {
	setAutoscrolls(true);
	setViewportView(new ConsolePartDetailInfoTable(index));//±í¸ñ
	setVisible(true);
}
}
