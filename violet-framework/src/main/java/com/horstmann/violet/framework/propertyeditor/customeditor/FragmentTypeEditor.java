package com.horstmann.violet.framework.propertyeditor.customeditor;

import com.horstmann.violet.framework.propertyeditor.CustomPropertyEditorSupport;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentType;

public class FragmentTypeEditor extends CustomPropertyEditorSupport{
	
	
	public FragmentTypeEditor()
	{
		super(NAMES,VALUES);
	}

	public static final String[] NAMES=
		{
		    "alt",
		    "opt",
		    "break",
		    "par",
		    "loop"					
		};
	public static final Object[] VALUES={
		FragmentType.ALT,
		FragmentType.OPT,
		FragmentType.BREAK,
		FragmentType.PAR,
		FragmentType.LOOP				
	};
				
}
