package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class SplitZuseZ {
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
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x<=3");
		ar2.add("y>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
	
		ArrayList<String> ar3 =new ArrayList<String>();
		ar3.add("x-y>1");
		ar3.add("x-y<2");
		DBM_element DBM3[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar3));
		DBM_element fDBM3[][]=Floyds.floyds(DBM3);
		
		DBM_element and[][]=AndDBM.andDBM(DBM2, DBM3);
		DBM_element fand[][]=Floyds.floyds(and);
		
		ArrayList<DBM_element[][]> com_and=Complement.complement(and);
		//System.out.println(com_and.size());
		
		ArrayList<DBM_element[][]> s1=splitZuseZ(DBM1, and);
		//System.out.println(s1.size());
		
		 
		ArrayList<String> ar4 =new ArrayList<String>();
		ar4.add("y<=2");	
		ar4.add("y>=2");
		DBM_element DBM4[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar4));
		DBM_element fDBM4[][]=Floyds.floyds(DBM4);
		
		DBM_element and_1[][]=AndDBM.andDBM(DBM4, DBM3);
		DBM_element fand_1[][]=Floyds.floyds(and_1);
		ArrayList<DBM_element[][]> com_and_1=Complement.complement(and_1);
		//System.out.println(com_and_1.size());
		 
		 
		
		ArrayList<DBM_element[][]> DBMs=SplitZsUseZ.splitZsUseZ(s1, and_1);
		System.out.println(DBMs.size());
		for(DBM_element[][] dbm:DBMs){
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
	 * 用矩阵Z2拆分矩阵Z1，Z1和Z2都规范化了
	 * @param Z1
	 * @param Z2
	 * @return Z1被拆分成多个矩阵，返回一个矩阵集合，返回的矩阵是没有正规化的（结果DBMs中有相交的部分）
	 */
	public static ArrayList<DBM_element[][]> splitZuseZ(DBM_element[][] Z1,DBM_element[][] Z2) {
		ArrayList<DBM_element[][]> Zones=new ArrayList<DBM_element[][]>();
		
		DBM_element[][] fZ1=Floyds.floyds(Z1);
		DBM_element[][] fZ2=Floyds.floyds(Z2);//先将矩阵Z1、Z2规范化，再用Z2拆分Z1
		
		if(IsEmpty.isEmpty(AndDBM.andDBM(fZ1, fZ2))==-1){
			Zones.add(AndDBM.andDBM(fZ1, fZ2));
		}
		
		ArrayList<DBM_element[][]> complements=Complement.complement(fZ2);
		//System.out.println(complements.size());
		for(DBM_element[][] com:complements){
			if(IsEmpty.isEmpty(AndDBM.andDBM(fZ1, Floyds.floyds(com)))==-1){
				Zones.add(AndDBM.andDBM(fZ1, Floyds.floyds(com)));
			}
		
		}
		
		return Zones;
	}
}
