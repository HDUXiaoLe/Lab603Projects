package com.horstmann.violet.application.gui.util.layout;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.horstmann.violet.application.gui.util.layout.TestGraph;

import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.horstmann.violet.application.gui.util.layout.UpdateXml;
public class LayoutUppaal {
	
	
	public static void layout(String filename) throws Exception{
		
	
		ReadXml readxml=new ReadXml(filename);		
	    int TemplateNum=readxml.getTemplateNum();
	    TestGraph testgraph=new TestGraph(filename); 
	    UpdateXml updatexml=new UpdateXml(filename);
	    for(int Index=0;Index<TemplateNum;Index++)
	    {   	    	
	    	testgraph.init(Index);
	    	updatexml.Update(Index);          		
	    }	   	  
	}
}
