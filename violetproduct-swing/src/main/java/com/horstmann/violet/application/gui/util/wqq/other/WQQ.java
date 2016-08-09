package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class WQQ {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("x<2");
		ar1.add("x>1");
		ar1.add("x<=y");
		ar1.add("y<=x");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		DBM_element rDBM[][]=Reset.reset(fDBM1, Clocks, "y");
		DBM_element frDBM[][]=Floyds.floyds(rDBM);
		
		DBM_element extDBM[][]=ExtractReset.extract(frDBM);
		DBM_element fextDBM[][]=Floyds.floyds(extDBM);
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("y<=2");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		
		DBM_element andDBM[][]=JoinReset.joinClock(fextDBM, fDBM2);
		//DBM_element andDBM[][]=AndDBM.andDBM(fextDBM, fDBM2);
		DBM_element fandDBM[][]=Floyds.floyds(andDBM);
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fandDBM[i][j];
				System.out.println("DBM_i:"+cons.getDBM_i());
				System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				System.out.println("***********");
								
			}
		}
	}
}
