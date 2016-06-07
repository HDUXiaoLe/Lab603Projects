
package com.horstmann.violet.application.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.horstmann.violet.application.menu.FileMenu;
import com.horstmann.violet.application.swingextension.WelcomeButtonUI;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.InjectedBean;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.framework.theme.ITheme;
import com.horstmann.violet.framework.theme.ThemeManager;

public class HomePanel extends JPanel
{

    public HomePanel()
    {
        
        setOpaque(false);//设置背景色为透明
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(getflowchartPanel());

    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        Paint currentPaint = g2.getPaint();
        ITheme cLAF = ThemeManager.getInstance().getTheme();
        GradientPaint paint = new GradientPaint(getWidth() / 2, -getHeight() / 4, cLAF.getWelcomeBackgroundStartColor(),
                getWidth() / 2, getHeight() + getHeight() / 4, cLAF.getWelcomeBackgroundEndColor());
        g2.setPaint(paint);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(currentPaint);
        super.paint(g);
    }
 

    private JPanel getflowchartPanel()
    {
        if (this.flowchartPanel== null)
        {
        	this.flowchartPanel=new JPanel(){
        	 protected void paintComponent(Graphics g) {
        		  super.paintComponent(g);
        		    ImageIcon icon = new ImageIcon("C:\\Users\\Xiaole\\Desktop"
        		    		+ "\\项目代码\\violetumleditor-master\\"
        		    		+ "violetproduct-swing\\src\\site\\"
        		    		+ "resources\\icons\\flowchart.PNG");
        		    Image img = icon.getImage();
        	        setBackground(Color.WHITE);
        	        if (img != null) {
        	            int height = img.getHeight(this);
        	            int width = img.getWidth(this);
        	  
        	            if (height != -1 && height > getHeight())
        	                height = getHeight();
        	  
        	            if (width != -1 && width > getWidth())
        	                width = getWidth();
        	            int x = (int) (((double) (getWidth() - width)) / 2.0);
        	            int y = (int) (((double) (getHeight() - height)) / 2.0);
        	            g.drawImage(img, x, y, width, height, this);
        	        }        		          		
        		   }
        		  };
        }
        return this.flowchartPanel;
    }

    private JPanel getFootTextPanel()
    {
        if (this.footTextPanel == null)
        {
            this.footTextPanel = new JPanel();
            this.footTextPanel.setOpaque(false);
            this.footTextPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
            this.footTextPanel.setLayout(new BoxLayout(this.footTextPanel, BoxLayout.Y_AXIS));
            this.footTextPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel text = new JLabel(this.footText);
            ITheme cLAF = ThemeManager.getInstance().getTheme();
            text.setFont(cLAF.getWelcomeSmallFont());
            text.setForeground(cLAF.getWelcomeBigForegroundColor());
            text.setAlignmentX(Component.CENTER_ALIGNMENT);

            this.footTextPanel.add(text);
        }

        return this.footTextPanel;
    }



    private JPanel flowchartPanel;
    
    private JPanel introducPanel;

    private JPanel footTextPanel;

    
    private String footText="Automated test platform .\n made by Le.Xiao & Jian.Zhang";

    
 
}
