package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class Reset {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("x<=y");
		ar1.add("y<=x");
		ar1.add("x<2");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		
		DBM_element and[][]=AndDBM.andDBM(fDBM1, fDBM2);
		DBM_element fand[][]=Floyds.floyds(and);
		
		
		DBM_element reset[][]=reset(fand,Clocks,"y");
		DBM_element freset[][]=Floyds.floyds(reset);
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=freset[i][j];
				System.out.println("DBM_i:"+cons.getDBM_i());
				System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());		
			}
		}
	}
	
	public static DBM_element[][] reset(DBM_element[][] DBM,ArrayList<String> ClockSet,String clock){
		int n=DBM.length;
		DBM_element[][] newDBM = new DBM_element[n][n];
		for(int i=0;i<n;i++){//³õÊ¼»¯newDBM
			for(int j=0;j<n;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(DBM[i][j].getValue());
				ele.setStrictness(DBM[i][j].isStrictness());
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				newDBM[i][j]=ele;	
			}
		}
		
		
		int k = 0;
		for(int i=0;i<ClockSet.size();i++){
			if(ClockSet.get(i).equals(clock)){
				k=i+1;
			}
		}
		for(int i=0;i<n;i++){
			newDBM[k][i].setValue(newDBM[0][i].getValue());
			newDBM[k][i].setStrictness(newDBM[0][i].isStrictness());	
			
			newDBM[i][k].setValue(newDBM[i][0].getValue());
			newDBM[i][k].setStrictness(newDBM[i][0].isStrictness());
		}
		
		if(IsEmpty.isEmpty(newDBM)!=1){
			return newDBM;			
		}
		return null;
	}
}
