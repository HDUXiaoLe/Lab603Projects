package com.horstmann.violet.application.gui.util.wqq;

import java.io.File;

import com.horstmann.violet.application.gui.util.wqq.other.UppaalTemPlate;
/*
		 * 1.xml格式和之前的不一样
		 * 2.使用时 需要导入xstream-1.4.8.jar  需要包util和bean
		 * 3.修改了UppaalTemPlate类中 Clocks的set方法重命名（setClockSet -> setClocks）
		 * 4.使用方法如下
		 * */
public class Main {
	public static void main(String args[]) {
		// xml file)")
		XML2UppaalUtil util = new XML2UppaalUtil(new File("Draw MoneyForXStream(2).xml"));
		UppaalTemPlate temPlate = util.getTemplates().get(0);
		temPlate.getName();
	}
}
