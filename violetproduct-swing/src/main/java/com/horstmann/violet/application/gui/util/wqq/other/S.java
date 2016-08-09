package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class S {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("x<=1");
		ar1.add("x>=1");
		ar1.add("y>=1");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fDBM1[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				System.out.println("***********");
								
			}
		}
	}
}
