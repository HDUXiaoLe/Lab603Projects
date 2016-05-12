package com.horstmann.violet.product.diagram.abstracts;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.horstmann.violet.framework.plugin.IDiagramPlugin;

/**
 * Graph type registry. Each graph should be registered here to be accessible. 
 * 
 * @author Alexandre de Pellegrin
 *
 */
public class GraphRegistry
{
    
    /**
     * Singleton instance
     */
    private static GraphRegistry instance; //图形实例注册类
    
    /**
     * Resgistry map
     */
    private Map<Class<? extends IGraph>, IDiagramPlugin> registry = new HashMap<Class<? extends IGraph>, IDiagramPlugin>();
    
    /**
     * Singleton constructor
     */
    private GraphRegistry() {
        // Singleton
    }
    
    /**
     * @return registry instance
     */
    public static GraphRegistry getInstance() {
        if (instance == null) {
            instance = new GraphRegistry();
        }
        return instance;
    }
    
    /**
     * Registers a new graph type
     * @param newGraphType t
     */
    public void registerGraphType(IDiagramPlugin newGraphType) {
            this.registry.put(newGraphType.getGraphClass(), newGraphType);
    }
    //加入一个新的图形实例
    /**
     * @return already registered graph types
     */
    public Collection<IDiagramPlugin> getRegisteredGraphTypes() {
        Collection<IDiagramPlugin> values = this.registry.values();
        return values;
    }//获取所有的图形类型值
    
}
