package com.horstmann.violet.application.gui;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class AbstractTestCaseGenerationPanel extends JPanel {

	public DefaultMutableTreeNode abstractuppaalDiagram;
	public DefaultMutableTreeNode uppaalDiagram;
	public DefaultMutableTreeNode alluppaalDiagram;
	public JTree UppaalDiagramTree;
	private String[] abuppaallists;
	private String[] uppaallists;

	public AbstractTestCaseGenerationPanel() {
		initFileList();
		initUI();
		this.setLayout(new GridLayout(1, 1));
		add(UppaalDiagramTree);
	}

	private void initUI() {
		// TODO Auto-generated method stub

		alluppaalDiagram = new DefaultMutableTreeNode("时间自动机文件");
		uppaalDiagram = new DefaultMutableTreeNode("含有时间迁移的时间自动机文件");
		abstractuppaalDiagram = new DefaultMutableTreeNode("不含有时间迁移的自动机文件");

		for (String ud : uppaallists) {
			uppaalDiagram.add(new DefaultMutableTreeNode(ud));
		}
		for (String ab : abuppaallists) {
			abstractuppaalDiagram.add(new DefaultMutableTreeNode(ab));
		}
		alluppaalDiagram.add(uppaalDiagram);
		alluppaalDiagram.add(abstractuppaalDiagram);
		UppaalDiagramTree = new JTree(alluppaalDiagram);
	}

	private void initFileList() {
		abuppaallists = new String[] { "1.uppaaldiagram", "2.uppaaldiagram", "3.uppaaldiagram" };
		uppaallists = new String[] { "4.uppaaldiagram", "5.uppaaldiagram", "6.uppaaldiagram" };
	}

}
