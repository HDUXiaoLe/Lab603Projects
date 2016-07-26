package com.horstmann.violet.application.gui.util.xiaole;

import org.dom4j.DocumentException;

public class TransToVioletUppaal {

	public static void TransToViolet() throws DocumentException {
		// TODO Auto-generated method stub
		WriteVioletUppaal writevioletuppaal=new WriteVioletUppaal();
    	writevioletuppaal.find();
  	    writevioletuppaal.writeVioletUppaal("D:\\ModelDriverProjectFile\\UPPAL"
  	    		+ "\\2.UML_Model_Transfer\\uppaalTest1.uppaal.violet.xml"
  	    		);
	}

}
