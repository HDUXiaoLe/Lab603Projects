package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class SplitZuseZs {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=2");
		ar1.add("x-y>1");
		ar1.add("x-y<2");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		
	
		ArrayList<DBM_element[][]> Zs=new ArrayList<DBM_element[][]>();
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x<=3");
		ar2.add("y>1");
		ar2.add("x-y>1");
		ar2.add("x-y<2");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		Zs.add(DBM2);
		ArrayList<String> ar3 =new ArrayList<String>();
		ar3.add("y<=2");
		ar3.add("y>=2");
		ar3.add("x-y>1");
		ar3.add("x-y<2");
		DBM_element DBM3[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar3));
		DBM_element fDBM3[][]=Floyds.floyds(DBM3);
		Zs.add(DBM3);
		
		ArrayList<DBM_element[][]> Zones=splitZuseZs(DBM1,Zs);
		System.out.println(Zones.size());
		for(DBM_element[][] dbm:Zones){
			 DBM_element fdbm[][]=Floyds.floyds(dbm);
			 for(int i=0;i<3;i++){
					for(int j=0;j<3;j++){
						DBM_element cons=dbm[i][j];
						//System.out.println("DBM_i:"+cons.getDBM_i());
						//System.out.println("DBM_j:"+cons.getDBM_j());
						System.out.println("value:"+cons.getValue());
						System.out.println("Strictness:"+cons.isStrictness());		
					}
				}
			 System.out.println("#################################################");
		 }
	}
	/**
	 * 用多个时间域去拆分一个时间域，返回的时间域是没有规范化的。
	 * @param Z
	 * @param Zs
	 * @return
	 */
	public static ArrayList<DBM_element[][]> splitZuseZs(DBM_element[][] Z,ArrayList<DBM_element[][]> Zs) {		
		ArrayList<DBM_element[][]> A=new ArrayList<DBM_element[][]>();
		A=SplitZuseZ.splitZuseZ(Z, Zs.get(0));//用Zs中第一个时间域去拆分Z
		
		ArrayList<DBM_element[][]> B=new ArrayList<DBM_element[][]>();
		for(DBM_element[][] e:A){
			B.add(e);
		}
		
		for(int i=1;i<Zs.size();i++){
			B=SplitZsUseZ.splitZsUseZ(B, Zs.get(i));
		}
		
		return B;
	}
}
