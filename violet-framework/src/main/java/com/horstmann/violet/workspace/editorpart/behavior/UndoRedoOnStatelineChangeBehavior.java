package com.horstmann.violet.workspace.editorpart.behavior;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;   
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEdit;

import com.horstmann.violet.product.diagram.abstracts.edge.IHorizontalChild;
import com.horstmann.violet.product.diagram.abstracts.node.INode;

public class UndoRedoOnStatelineChangeBehavior extends AbstractEditorPartBehavior{
	
	private UndoRedoCompoundBehavior compoundBehavior;	
	private List<IHorizontalChild>  horizontalchildsBeforeChanges=new ArrayList<IHorizontalChild>();	
	private List<IHorizontalChild>  horizontalchildsAfterChanges=new ArrayList<IHorizontalChild>();
	
	public UndoRedoOnStatelineChangeBehavior(UndoRedoCompoundBehavior compoundBehavior)
	{	
		this.compoundBehavior = compoundBehavior;
	}
	@Override
	public void beforeChangingStateline(INode n)
	{		
		
		
		this.horizontalchildsBeforeChanges.clear();
		
		for(IHorizontalChild ahorizontalchild : n.getChild().gethorizontalChild())
		{
			this.horizontalchildsBeforeChanges.add(ahorizontalchild);
		}
	}
	
	@Override
	public void afterChangingStateline(final INode n)
	{
		
	
		this.horizontalchildsAfterChanges.clear();
	
		for(IHorizontalChild ahorizontalchild : n.getChild().gethorizontalChild())
		{
			this.horizontalchildsAfterChanges.add(ahorizontalchild);
		}
		this.compoundBehavior.startHistoryCapture();
		CompoundEdit capturedEdit =this.compoundBehavior.getCurrentCapturedEdit();
		captureAddedTranPoints(n,capturedEdit);
		this.compoundBehavior.stopHistoryCapture();
	}
	private void captureAddedTranPoints(final INode n,CompoundEdit captureEdit)
	{
		boolean isAdded = (this.horizontalchildsBeforeChanges.size()!=this.horizontalchildsAfterChanges.size());
		if(!isAdded)
		{			
			return;
		}		
		final List<IHorizontalChild> horizontalchildsBeforeChangesCopy = new ArrayList<IHorizontalChild>(horizontalchildsBeforeChanges);		
	//	System.out.println(pointsAndPosition);
		UndoableEdit edit = new AbstractUndoableEdit(){
			@Override
			public void undo() throws CannotUndoException{
				
				if(horizontalchildsBeforeChanges!=n.getChild().gethorizontalChild())
				{				
					 n.getChild().sethorizontalChild(horizontalchildsBeforeChangesCopy);	
				}     						
			}
	       @Override
	       public void redo() throws CannotRedoException{
		  
		    n.getChild().sethorizontalChild(horizontalchildsAfterChanges);
		    
	       }			
		};
		captureEdit.addEdit(edit);		
	}	
}
	
	
	
	
	
	
	
	
	
	
	

