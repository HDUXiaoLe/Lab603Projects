package com.horstmann.violet.product.diagram.timing;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.framework.plugin.extensionpoint.Violet016FileFilterExtensionPoint;
import com.horstmann.violet.product.diagram.abstracts.IGraph;

/**
 * Describes sequence diagram graph type
 * 
 * @author Alexandre de Pellegrin
 * 
 */
public class TimingDiagramPlugin implements IDiagramPlugin, Violet016FileFilterExtensionPoint
{

    @Override
    public String getDescription()
    {
        return "Timing UML diagram";
    }

    @Override
    public String getProvider()
    {
        return "xiao_le&zhang_jian";
    }

    @Override
    public String getVersion()
    {
        return "1.0.0";
    }

    @Override
    public String getName()
    {
        return this.rs.getString("menu.timing_diagram.name");
    }

    @Override
    public String getCategory()
    {
        return this.rs.getString("menu.timing_diagram.category");
    }

    @Override
    public String getFileExtension()
    {
        return this.rs.getString("files.timing.extension");
    }

    @Override
    public String getFileExtensionName()
    {
        return this.rs.getString("files.timing.name");
    }

    @Override
    public String getSampleFilePath()
    {
        return this.rs.getString("sample.file.path");
    }

    @Override
    public Class<? extends IGraph> getGraphClass()
    {
        return TimingDiagramGraph.class;
    }

    public Map<String, String> getMappingToKeepViolet016Compatibility()
    {
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("com.horstmann.violet.State_Lifeline", State_Lifeline.class.getName());
        replaceMap.put("com.horstmann.violet.Stateline", StateLine.class.getName());
        replaceMap.put("com.horstmann.violet.TimingDiagramGraph", TimingDiagramGraph.class.getName());
        replaceMap.put("com.horstmann.violet.SendMessageEdge", SendMessageEdge.class.getName());
        
        return replaceMap;
    }

    private ResourceBundle rs = ResourceBundle.getBundle(TimingDiagramConstant.TIMING_DIAGRAM_STRINGS, Locale.getDefault());

}
