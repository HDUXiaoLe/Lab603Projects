package com.horstmann.violet.product.diagram.uppaal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.framework.plugin.extensionpoint.Violet016FileFilterExtensionPoint;
import com.horstmann.violet.product.diagram.abstracts.IGraph;

	public class UppaalPlugin implements IDiagramPlugin, Violet016FileFilterExtensionPoint
	{

	    @Override
	    public String getDescription()
	    {
	        return "Uppaal UML diagram";
	    }

	    @Override
	    public String getProvider()
	    {
	        return "Alexandre de Pellegrin / Cays S. Horstmann";
	    }

	    @Override
	    public String getVersion()
	    {
	        return "1.0.0";
	    }

	    @Override
	    public String getName()
	    {
	        return this.rs.getString("menu.uppaal_diagram.name");
	    }

	    @Override
	    public String getCategory()
	    {
	        return this.rs.getString("menu.uppaal_diagram.category");
	    }

	    @Override
	    public String getFileExtension()
	    {
	        return this.rs.getString("files.uppaal.extension");
	    }

	    @Override
	    public String getFileExtensionName()
	    {
	        return this.rs.getString("files.uppaal.name");
	    }

	    @Override
	    public String getSampleFilePath()
	    {
	    	
	        return this.rs.getString("sample.file.path");
	    }

	    @Override
	    public Class<? extends IGraph> getGraphClass()
	    {
	        return UppaalGraph.class;
	    }

	    public Map<String, String> getMappingToKeepViolet016Compatibility()
	    {
	        Map<String, String> replaceMap = new HashMap<String, String>();
	        replaceMap.put("com.horstmann.violet.CircularNode", CircularNode.class.getName());
	        replaceMap.put("com.horstmann.violet.CircularStartNode", CircularStartNode.class.getName());
	        replaceMap.put("com.horstmann.violet.UppaalGraph", UppaalGraph.class.getName());
	        replaceMap.put("com.horstmann.violet.TransitionEdge", TransitionEdge.class.getName());
	        return replaceMap;
	    }

	    private ResourceBundle rs = ResourceBundle.getBundle(UppaalConstant.STATE_DIAGRAM_STRINGS, Locale.getDefault());

	}


