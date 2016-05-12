package com.horstmann.violet.product.diagram.markov;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.framework.plugin.extensionpoint.Violet016FileFilterExtensionPoint;
import com.horstmann.violet.product.diagram.abstracts.IGraph;

	public class MarkovPlugin implements IDiagramPlugin, Violet016FileFilterExtensionPoint
	{

	  @Override
	    public String getDescription()
	    {
	        return "Markov UML diagram";
	    }

	  @Override
	    public String getProvider()
	    {
	        return "le.Xiao&jian.zhang";
	    }   

	  @Override
	    public String getVersion()
	    {
	        return "1.0.0";
	    }

	  @Override
	    public String getName()
	    {
	        return this.rs.getString("menu.markov_diagram.name");
	    }

	  @Override
	    public String getCategory()
	    {
	        return this.rs.getString("menu.markov_diagram.category");
	    }

	  @Override
	    public String getFileExtension()
	    {
	        return this.rs.getString("files.markov.extension");
	    }
	  @Override
	    public String getFileExtensionName()
	    {
	        return this.rs.getString("files.markov.name");
	    }

	    @Override
	    public String getSampleFilePath()
	    {
	        return this.rs.getString("sample.file.path");
	    }
	    @Override
	    public Class<? extends IGraph> getGraphClass()
	    {
	        return MarkovGraph.class;
	    }

	    public Map<String, String> getMappingToKeepViolet016Compatibility()
	    {
	        Map<String, String> replaceMap = new HashMap<String, String>();
	        replaceMap.put("com.horstmann.violet.MarkovNode", MarkovNode.class.getName());
	        replaceMap.put("com.horstmann.violet.MarkovStartNode", MarkovStartNode.class.getName());
	        replaceMap.put("com.horstmann.violet.MarkovGraph", MarkovGraph.class.getName());
	        replaceMap.put("com.horstmann.violet.TransitionEdge", MarkovTransitionEdge.class.getName());
	        return replaceMap;
	    }

	    private ResourceBundle rs = ResourceBundle.getBundle(MarkovConstant.MARKOV_DIAGRAM_STRINGS, Locale.getDefault());

	}


