/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2008 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.application.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.beans.BeanInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.horstmann.violet.application.consolepart.ConsolePart;
import com.horstmann.violet.application.help.AboutDialog;
import com.horstmann.violet.application.menu.MenuFactory;
import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.file.GraphFile;
import com.horstmann.violet.framework.file.IFile;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.theme.ITheme;
import com.horstmann.violet.framework.theme.ThemeManager;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.product.diagram.abstracts.node.AbstractNode;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;
import com.horstmann.violet.product.diagram.abstracts.property.BentStyle;
import com.horstmann.violet.product.diagram.abstracts.property.LineStyle;
import com.horstmann.violet.workspace.IWorkspace;
import com.horstmann.violet.workspace.IWorkspaceListener;
import com.horstmann.violet.workspace.Workspace;

/**
 * This desktop frame contains panes that show graphs.
 * 
 * @author Alexandre de Pellegrin
 */
@ResourceBundleBean(resourceReference = AboutDialog.class)
public class MainFrame extends JFrame
{
    /**
     * Constructs a blank frame with a desktop pane but no graph windows.
     * 
     */
    public MainFrame()
    {
        BeanInjector.getInjector().inject(this);
        ResourceBundleInjector.getInjector().inject(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.dialogFactory.setDialogOwner(this);
        decorateFrame();
        setInitialSize();
        createMenuBar();
        getContentPane().add(this.getMainPanel());
      
    }

    /**
     * Sets initial size on startup
     */
    private void setInitialSize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        setBounds(screenWidth / 16, screenHeight / 16, screenWidth * 7 / 8, screenHeight * 7 / 8);
        // For screenshots only -> setBounds(50, 50, 850, 650);
    }

    /**
     * Decorates the frame (title, icon...)
     */
    private void decorateFrame()
    {
        setTitle(this.applicationName);
        setIconImage(this.applicationIcon);
    }

    /**
     * Creates menu bar
     */
    private void createMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(this.themeManager.getTheme().getMenubarFont());
        MenuFactory menuFactory = getMenuFactory();
        menuBar.add(menuFactory.getFileMenu(this));
        menuBar.add(menuFactory.getEditMenu(this));
        menuBar.add(menuFactory.getViewMenu(this));
        menuBar.add(menuFactory.getHelpMenu(this));
        setJMenuBar(menuBar);
      
    }

    /**
     * Adds a tabbed pane (only if not already added)
     * 
     * @param c the component to display in the internal frame
     */
    public void  addTabbedPane(final IWorkspace workspace )
    {
//       replaceWelcomePanelByTabbedPane(); 
    	//在添加图形元素的时候，首先判断下是哪种图形
    	
        if(workspace.getTitle().toString().endsWith(".ucase.violet.xml")
        		||workspace.getTitle().toString().substring(2, 4).equals("Us"))//如果是用例图
     	{
     		if(this.UseCaseworkspaceList.contains(workspace))
     		{
     			
     			return;
     		}
     		this.UseCaseworkspaceList.clear();//清空
     		this.UseCaseworkspaceList.add(workspace);
     		
     		this.getStepOneCenterTabbedPane().getUsecaseDiagramTabbedPane().removeAll();//只保留一个Tab页
     		this.getStepOneCenterTabbedPane().getUsecaseDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		
     		 listenToDiagramPanelEvents(workspace,UseCaseworkspaceList);
     		
   		     repaint();    		    
     	}
        if(workspace.getTitle().toString().endsWith(".timing.violet.xml")
        		||workspace.getTitle().toString().substring(2, 4).equals("Ti"))//时序图
     	{
     		if(this.TimingDiagramspaceList.contains(workspace))
     		{
     			return;
     		}
     		this.TimingDiagramspaceList.clear();//保证每一次新建或导入只会有1个Type页
     		this.TimingDiagramspaceList.add(workspace);
     		this.getStepOneCenterTabbedPane().getTimingDiagramTabbedPane().removeAll();
     		this.getStepOneCenterTabbedPane().getTimingDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		 listenToDiagramPanelEvents(workspace,TimingDiagramspaceList);
     		
     	   
     	    repaint();    		    
     	}
     	if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Se"))//如果是顺序图
     			
 		{
     	
     		if(this.SequencespaceList.contains(workspace))
     		{
     			return;
     		}
     		this.SequencespaceList.clear();
     		this.SequencespaceList.add(workspace);
     		this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane().removeAll();
     		this.getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		listenToDiagramPanelEvents(workspace,SequencespaceList);   		
     	    repaint();    		              
 		}    
     	if(workspace.getTitle().toString().endsWith(".state.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("St"))//如果是状态图
 		{
     		if(this.StatespaceList.contains(workspace))
     		{
     			return;
     		}
     		this.StatespaceList.clear();
     		this.StatespaceList.add(workspace);
     		this.getStepOneCenterTabbedPane().getStateDiagramTabbedPane().removeAll();
     		this.getStepOneCenterTabbedPane().getStateDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		
     		 listenToDiagramPanelEvents(workspace,StatespaceList);
     	  
     	    repaint();     		    
 		}
     	 /*
     	  * 展示uppaal
     	  */
     	if(workspace.getTitle().toString().endsWith(".uppaal.violet.xml")
     			&&!workspace.getTitle().toString().substring(0, 3).equals(("abs")))
     	{		
     		if(this.UppaalspaceList.contains(workspace))
     		{
     			return;
     		}
     		this.UppaalspaceList.clear();
     		this.UppaalspaceList.add(workspace);
     		this.getStepTwoCenterTabbedPane().getUppaalDiagramTabbedPane().removeAll();
     		this.getStepTwoCenterTabbedPane().getUppaalDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));    		
     		listenToDiagramPanelEvents(workspace,UppaalspaceList);    	  
     	    repaint();     		    
     	}
     	//展示MarKov链
     	if(workspace.getTitle().toString().endsWith(".markov.violet.xml"))
     	{	   		
     		this.getStepTwoCenterTabbedPane().getMarkovDiagramTabbedPane().removeAll();
     		this.getStepTwoCenterTabbedPane().getMarkovDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));    		    		   	  
     	    repaint();     		    
     	}
     
     	if(workspace.getTitle().toString().substring(0, 3).equals(("abs")))
     	{
     		
     		this.getStepThreeCenterTabbedPane().getAbstractUppaalTabbedPane().removeAll();
     		this.getStepThreeCenterTabbedPane().getAbstractUppaalTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));    		    		   	  
     	    repaint();    
     	 
     	}
    }
    public void addTabbedPane(final IWorkspace workspace,int flag )
    {
//       replaceWelcomePanelByTabbedPane(); 
    	//在添加图形元素的时候，首先判断下是哪种图形
    if(flag==2){
        if(workspace.getTitle().toString().endsWith(".timing.violet.xml")
        		||workspace.getTitle().toString().substring(2, 4).equals("Ti"))//时序图
     	{
     		if(this.TimingDiagramspaceList.contains(workspace))
     		{
     			return;
     		}
     		this.TimingDiagramspaceList.clear();//保证每一次新建或导入只会有1个Type页
     		this.TimingDiagramspaceList.add(workspace);
     		this.getStepTwoCenterTabbedPane().getUMLDiagramTabbedPane().removeAll();
     		this.getStepTwoCenterTabbedPane().getUMLDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		 listenToDiagramPanelEvents(workspace,TimingDiagramspaceList);    			  
     	    repaint();    		    
     	}
     	if(workspace.getTitle().toString().endsWith(".seq.violet.xml")
     			||workspace.getTitle().toString().substring(2, 4).equals("Se"))//如果是顺序图
     			
 		{
     	
     		if(this.SequencespaceList.contains(workspace))
     		{
     			return;
     		}
     		this.SequencespaceList.clear();
     		this.SequencespaceList.add(workspace);
     		this.getStepTwoCenterTabbedPane().getUMLDiagramTabbedPane().removeAll();
     		this.getStepTwoCenterTabbedPane().getUMLDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
     		listenToDiagramPanelEvents(workspace,SequencespaceList);   		
     	    repaint();    		              
 		}    
    
     	 /*
     	  * 展示uppaal
     	  */
     	if(workspace.getTitle().toString().endsWith(".uppaal.violet.xml"))
     	{		
     		if(this.UppaalspaceList.contains(workspace))
     		{
     			return;
     		}
     		this.UppaalspaceList.clear();
     		this.UppaalspaceList.add(workspace);
     		this.getStepTwoCenterTabbedPane().getUppaalDiagramTabbedPane().removeAll();
     		this.getStepTwoCenterTabbedPane().getUppaalDiagramTabbedPane()
     		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));    		
     		listenToDiagramPanelEvents(workspace,UppaalspaceList);    	  
     	    repaint();     		    
     	}
    }
    if(flag==3)
    {
    	/*
    	 * 含有时间迁移自动机
    	 */
    	if(workspace.getTitle().toString().startsWith("abs"))         		
      	{
      		if(this.UppaalspaceList.contains(workspace))
      		{
      			return;
      		}
      		this.UppaalspaceList.clear();//保证每一次新建或导入只会有1个Type页
      		this.UppaalspaceList.add(workspace);
      		this.getStepThreeCenterTabbedPane().getAbstractUppaalTabbedPane().removeAll();
      		this.getStepThreeCenterTabbedPane().getAbstractUppaalTabbedPane()
      		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
      		 listenToDiagramPanelEvents(workspace,UppaalspaceList);    			  
      	    repaint();    		    
      	}
    	/*
    	 * 去时间迁移自动机
    	 */
    	if(workspace.getTitle().toString().startsWith("no_time_abs"))         		
      	{
      		if(this.UppaalspaceList.contains(workspace))
      		{
      			return;
      		}
      		this.UppaalspaceList.clear();//保证每一次新建或导入只会有1个Type页
      		this.UppaalspaceList.add(workspace);
      		this.getStepThreeCenterTabbedPane().getUppaalTabbedPane().removeAll();
      		this.getStepThreeCenterTabbedPane().getUppaalTabbedPane()
      		.add(workspace.getAWTComponent(),new GBC(0,0).setWeight(1, 1).setFill(GBC.BOTH));
      		 listenToDiagramPanelEvents(workspace,UppaalspaceList);    			  
      	    repaint();    		    
      	}
    }
     	
    }
    /**
     * Add a listener to perform action when something happens on this diagram
     * 
     * @param diagramPanel
     */
    private void listenToDiagramPanelEvents(final IWorkspace diagramPanel,final List<IWorkspace> workspaceList)
    {
        diagramPanel.addListener(new IWorkspaceListener()
        {
            public void titleChanged(String newTitle)
            {
                int pos = workspaceList.indexOf(diagramPanel);
                
            }

            public void graphCouldBeSaved()
            {
                // nothing to do here
            }

            public void mustOpenfile(IFile file)
            {
                try
                {
                    IGraphFile graphFile = new GraphFile(file);
                    IWorkspace newWorkspace = new Workspace(graphFile);
                    addTabbedPane(newWorkspace);
                }
                catch (IOException e)
                {
                    DialogFactory.getInstance().showErrorDialog(e.getMessage());
                }
            }
        });
    }

    private void replaceWelcomePanelByTabbedPane()
    {
        WelcomePanel welcomePanel = this.getWelcomePanel();
        //JTabbedPane tabbedPane = getTabbedPane();     
        getMainPanel().remove(welcomePanel);        
       // getMainPanel().add(tabbedPane, new GBC(1,1,1,1).setFill(GBC.BOTH).setWeight(1, 1));
        repaint();
    }

    private void replaceTabbedPaneByWelcomePanel()
    {
        this.welcomePanel = null;
        WelcomePanel welcomePanel = this.getWelcomePanel();
        JTabbedPane tabbedPane = getTabbedPane();
        getMainPanel().remove(tabbedPane);
        getMainPanel().add(welcomePanel, BorderLayout.CENTER);
        repaint();
    }

    /**
     * @return the tabbed pane that contains diagram panels
     */
    public JTabbedPane getTabbedPane()
    {
        if (this.tabbedPane == null)
        {
            this.tabbedPane = new JTabbedPane()
            {
                public void paint(Graphics g)
                {
                    Graphics2D g2 = (Graphics2D) g;
                    Paint currentPaint = g2.getPaint();
                    ITheme LAF = themeManager.getTheme();
                    GradientPaint paint = new GradientPaint(getWidth() / 2, -getHeight() / 4, LAF.getWelcomeBackgroundStartColor(),
                            getWidth() / 2, getHeight() + getHeight() / 4, LAF.getWelcomeBackgroundEndColor());
                    g2.setPaint(paint);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setPaint(currentPaint);
                    super.paint(g);
                }
            };
            this.tabbedPane.setOpaque(false);
            MouseWheelListener[] mouseWheelListeners = this.tabbedPane.getMouseWheelListeners();
            for (int i = 0; i < mouseWheelListeners.length; i++)
            {
                this.tabbedPane.removeMouseWheelListener(mouseWheelListeners[i]);
            }
        }
        return this.tabbedPane;
    }

    /**
     * Removes a diagram panel from this editor frame
     * 
     * @param diagramPanel
     */
    public void removeDiagramPanel(IWorkspace diagramPanel)
    {
        if (this.UseCaseworkspaceList.contains(diagramPanel))       		
        {   
        int pos = this.UseCaseworkspaceList.indexOf(diagramPanel);
        getStepOneCenterTabbedPane().getUMLTabbedPane(diagramPanel).remove(pos);
        this.UseCaseworkspaceList.remove(diagramPanel);
        repaint();
        }
        if (this.TimingDiagramspaceList.contains(diagramPanel))       		
        {   
        int pos = this.TimingDiagramspaceList.indexOf(diagramPanel);
        getStepOneCenterTabbedPane().getUMLTabbedPane(diagramPanel).remove(pos);
        this.TimingDiagramspaceList.remove(diagramPanel);
        repaint();
        }
        if (this.SequencespaceList.contains(diagramPanel))       		
        {   
        int pos = this.SequencespaceList.indexOf(diagramPanel);
        getStepOneCenterTabbedPane().getUMLTabbedPane(diagramPanel).remove(pos);
        this.SequencespaceList.remove(diagramPanel);
        repaint();
        }
        if (this.StatespaceList.contains(diagramPanel))       		
        {   
        int pos = this.StatespaceList.indexOf(diagramPanel);
        getStepOneCenterTabbedPane().getUMLTabbedPane(diagramPanel).remove(pos);
        this.StatespaceList.remove(diagramPanel);
        repaint();
        }
        if (this.UppaalspaceList.contains(diagramPanel))       		
        {   
        int pos = this.UppaalspaceList.indexOf(diagramPanel);
        getStepTwoCenterTabbedPane().getUppaalDiagramTabbedPane().remove(pos);
        this.UppaalspaceList.remove(diagramPanel);
        repaint();
        }
    }
    
    /**
     * Looks for an opened diagram from its file path and focus it
     * 
     * @param diagramFilePath diagram file path
     */
    public void setActiveDiagramPanel(IFile aGraphFile)
    {
        if (aGraphFile == null) return;
        for (IWorkspace aDiagramPanel : this.UseCaseworkspaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.UseCaseworkspaceList.indexOf(aDiagramPanel);
//                JTabbedPane tp = getStepOneCenterTabbedPane().getUsecaseDiagramTabbedPane();
//                tp.setSelectedIndex(pos);
                return;
            }
        }
        for (IWorkspace aDiagramPanel : this.TimingDiagramspaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.TimingDiagramspaceList.indexOf(aDiagramPanel);
//                JTabbedPane tp = getStepOneCenterTabbedPane().getTimingDiagramTabbedPane();
//                tp.setSelectedIndex(pos);
                return;
            }
        }
        for (IWorkspace aDiagramPanel : this.SequencespaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.SequencespaceList.indexOf(aDiagramPanel);
//                JTabbedPane tp = getStepOneCenterTabbedPane().getSequenceDiagramTabbedPane();
//                tp.setSelectedIndex(pos);
                return;
            }
        }
        for (IWorkspace aDiagramPanel : this.StatespaceList)
        {
            IFile toCompare = aDiagramPanel.getGraphFile();
            boolean isSameFilename = aGraphFile.getFilename().equals(toCompare.getFilename());
            if (isSameFilename)
            {
                int pos = this.StatespaceList.indexOf(aDiagramPanel);
//                JTabbedPane tp = getStepOneCenterTabbedPane().getStateDiagramTabbedPane();
//                tp.setSelectedIndex(pos);
                return;
            }
        }
    }

    /**
     * @return true if at least a diagram is displayed
     */
    public boolean isThereAnyDiagramDisplayed()
    {
        return !this.UseCaseworkspaceList.isEmpty()
        		||!this.TimingDiagramspaceList.isEmpty()
        		||!this.StatespaceList.isEmpty()
        		||!this.SequencespaceList.isEmpty()
        		||!this.UppaalspaceList.isEmpty();
    }

    public List<IWorkspace> getUseCaseWorkspaceList()
    {
        return UseCaseworkspaceList;
    }
    public List<IWorkspace> getTimingWorkspaceList()
    {
        return TimingDiagramspaceList;
    }
    public List<IWorkspace> getSequenceWorkspaceList()
    {
        return SequencespaceList;
    }
    public List<IWorkspace> getStateWorkspaceList()
    {
        return StatespaceList;
    }
    /**
     * @return selected diagram file path (or null if not one is selected; that should never happen)
     */
    public IWorkspace getActiveWorkspace()
    {
        JTabbedPane Onetp = getStepOneCenterTabbedPane();
        JTabbedPane Twotp=getStepTwoCenterTabbedPane();
        int pos1=Onetp.getSelectedIndex(); 
        int pos2=Twotp.getSelectedIndex();
        if (pos1==0&&SequencespaceList.size()>0)
        {
            return this.SequencespaceList.get(0);
        }
        if (pos1==1&&TimingDiagramspaceList.size()>0)
        {
            return this.TimingDiagramspaceList.get(0);
        }
        if (pos1==2&&StatespaceList.size()>0)
        {
            return this.StatespaceList.get(0);
        }
        if (pos1==3&&UseCaseworkspaceList.size()>0)
        {
            return this.UseCaseworkspaceList.get(0);
        }
        if (pos2==1&&UppaalspaceList.size()>0)
        {
        	return this.UppaalspaceList.get(0);
        }
        else{//说明没有workspace UML图形需要保存
           return null;
        }
    }
   
   public WelcomePanel getWelcomePanel()
    {
        if (this.welcomePanel == null)
        {
            this.welcomePanel = new WelcomePanel(this.getMenuFactory().getFileMenu(this));
        }
        return this.welcomePanel;
    }
   public HomePanel getHomePanel()
   {
   	if(this.homepanel==null)
   	{
   		this.homepanel=new HomePanel();
   	}
   	return this.homepanel;
   }
    private StepButtonPanel getStepButton()
    {
    	if(this.stepButton==null)
    	{
    		this.stepButton=new StepButtonPanel(this);
    	}
    	return this.stepButton;
    }
    public ConsolePart getConsolePart()
    {
    	if(this.consolePart==null)
    	{
    		this.consolePart=new ConsolePart(this);
    	}
    	return this.consolePart;
    }
    public ProjectTree getProjectTree()
    {
    	if(this.projectTree==null)
    	{
    		this.projectTree=new ProjectTree(this.getMenuFactory().getFileMenu(this),this);
    	}
    	return this.projectTree;
    }
    public ModelTransformationPanel getModelTransformationPanel()
    {
    	if(this.modelTransformationPanel==null)
    	{
    		this.modelTransformationPanel=new ModelTransformationPanel(this);
    		
    	}
    	return this.modelTransformationPanel;
    }
    public JPanel getMainPanel() {//主面板布局
        if (this.mainPanel == null) {
        	GridBagLayout layout=new GridBagLayout();
            this.mainPanel = new JPanel(layout);
            this.mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));                                                 
            this.mainPanel.add(this.getStepButton());
            this.mainPanel.add(this.getStepJLabel());
            this.mainPanel.add(this.getConsolePart());
            this.mainPanel.add(this.getOpreationPart());
            this.mainPanel.add(this.getCenterTabPanel());
            this.getCenterTabPanel().setLayout(new GridLayout(1, 1));
            this.getCenterTabPanel().add(this.getHomePanel());//默认添加首页    
            this.getStepJLabel().setBackground(new Color(67,87,113));
            JLabel initlabel=new JLabel("项目演示区");
            initlabel.setFont(new Font("宋体", Font.BOLD, 20));
			initlabel.setForeground(Color.white);
            this.getStepJLabel().add(initlabel,JLabel.CENTER);
            layout.setConstraints(this.getStepButton(),new GBC(0, 0, 1, 3).setFill(GBC.BOTH).setWeight(0, 1));                                    
            layout.setConstraints(this.getStepJLabel(), new GBC(1,0,2,1).setFill(GBC.BOTH).setWeight(1, 0));
            layout.setConstraints(this.getConsolePart(), new GBC(1,2,2,1).setFill(GBC.BOTH).setWeight(1, 0));
            layout.setConstraints(this.getOpreationPart(), new GBC(2,1,1,1).setFill(GBC.BOTH).setWeight(0, 1));
            layout.setConstraints(this.getCenterTabPanel(), new GBC(1,1,1,1).setFill(GBC.BOTH).setWeight(1, 1));
		    		                                     
        }
        return this.mainPanel;
    }
      	
    public StepOneCenterTabbedPane getStepOneCenterTabbedPane()
    {
    if (this.stepOneCenterTabbedPane== null)
    {
       stepOneCenterTabbedPane=new StepOneCenterTabbedPane();
    }
    return this.stepOneCenterTabbedPane;
    	
    }
    public StepTwoCenterTabbedPane getStepTwoCenterTabbedPane()
    {
    if (this.stepTwoCenterTabbedPane== null)
    {
       stepTwoCenterTabbedPane=new StepTwoCenterTabbedPane();
    }
    return this.stepTwoCenterTabbedPane;
    	
    }   
    public StepThreeCenterTabbedPane getStepThreeCenterTabbedPane()
    {
    if (this.stepThreeCenterTabbedPane== null)
    {
       stepThreeCenterTabbedPane=new StepThreeCenterTabbedPane();
    }
    return this.stepThreeCenterTabbedPane;
    	
    }   
	public JPanel getOpreationPart() {
		// TODO Auto-generated method stub
		return this.opreationpanel;
	}
	public JPanel getCenterTabPanel(){
		// TODO Auto-generated method stub
		return this.centerTabPanel;
	}
	/**
     * @return the menu factory instance
     */
    public MenuFactory getMenuFactory()
    {
        if (this.menuFactory == null)
        {
            menuFactory = new MenuFactory();
        }
        return this.menuFactory;
    }

    public JPanel getStepJLabel() {   	
		return stepJLabel;
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public void setStepJLabel(JPanel stepJLabel) {
		this.stepJLabel = stepJLabel;
	}

	/**
     * Tabbed pane instance
     */
    private JTabbedPane tabbedPane;

    /**
     * Panel added is not diagram is opened
     */
    private WelcomePanel welcomePanel;
    private HomePanel homepanel;
    private JPanel stepJLabel=new JPanel();
    private ModelTransformationPanel modelTransformationPanel;
    private StepOneCenterTabbedPane stepOneCenterTabbedPane;
    private StepTwoCenterTabbedPane stepTwoCenterTabbedPane;
    private StepThreeCenterTabbedPane stepThreeCenterTabbedPane;
    private ConsolePart consolePart;
    private ProjectTree projectTree;
    private StepButtonPanel stepButton;
    private JPanel centerPanel;
    private JPanel opreationpanel=new JPanel();
    private JPanel centerTabPanel=new JPanel();
    /**
     * Main panel
     */
    private JPanel mainPanel;

    /**
     * Menu factory instance
     */
    private MenuFactory menuFactory;

    /**
     * GUI Theme manager
     */
    @InjectedBean
    private ThemeManager themeManager;

    /**
     * Needed to display dialog boxes
     */
    @InjectedBean
    private DialogFactory dialogFactory;

    /**
     * Needed to open files
     */
    @InjectedBean
    private IFileChooserService fileChooserService;
    
    @ResourceBundleBean(key="app.name")
    private String applicationName;
    
    @ResourceBundleBean(key="app.icon")
    private Image applicationIcon;

    /**
     * All disgram workspaces
     */
    private List<IWorkspace> UseCaseworkspaceList = new ArrayList<IWorkspace>(); //用例图    
    private List<IWorkspace> SequencespaceList=new ArrayList<IWorkspace>();//顺序图
    private List<IWorkspace> TimingDiagramspaceList=new ArrayList<IWorkspace>();//时序图
    private List<IWorkspace> StatespaceList=new ArrayList<IWorkspace>();//状态图
    private List<IWorkspace> UppaalspaceList=new ArrayList<IWorkspace>();//时间自动机
    // workaround for bug #4646747 in J2SE SDK 1.4.0
    private static java.util.HashMap<Class<?>, BeanInfo> beanInfos;
    static
    {
        beanInfos = new java.util.HashMap<Class<?>, BeanInfo>();
        Class<?>[] cls = new Class<?>[]
        {
                Point2D.Double.class,
                BentStyle.class,
                ArrowHead.class,
                LineStyle.class,
                IGraph.class,
                AbstractNode.class,
        };
        for (int i = 0; i < cls.length; i++)
        { 
            try
            {
                beanInfos.put(cls[i], java.beans.Introspector.getBeanInfo(cls[i]));
            }
            catch (java.beans.IntrospectionException ex)
            {
            }
        }
    }
}
