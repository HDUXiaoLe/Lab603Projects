package com.horstmann.violet.application.gui.util.xiaole;

import org.dom4j.DocumentException;



public class MainTransToUppaal {
        public static void main(String agrs[]) throws DocumentException
        {
        	ReadUppaal read=new ReadUppaal();
        	read.find();
      	    read.writeUppaal("e:/uppaal.xml");
        }
}