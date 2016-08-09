package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class SD {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar1 =new ArrayList<String>();		
		ar1.add("x<=3");
		ar1.add("y<=2");
		ar1.add("y>1");
		ar1.add("x-y>1");
		ar1.add("x-y<2");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x-y<2");
		ar2.add("x-y>1");
		ar2.add("y<=2");
		ar2.add("y>=2");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		
		DBM_element[][] and=AndDBM.andDBM(fDBM1, fDBM2);
		int i=IsEmpty.isEmpty(and);
		System.out.println(i);
	}
}
