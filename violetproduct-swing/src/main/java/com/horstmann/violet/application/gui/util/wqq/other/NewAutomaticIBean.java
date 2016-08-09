package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class NewAutomaticIBean {
	public static Automatic newAutomatic() {
		Automatic oldAutomatic=AutomaticIBean.automic();
		State initstate=oldAutomatic.getInitState();
		ArrayList<State> StateSet=oldAutomatic.getStateSet();
		
		
		return null;
	}
}
