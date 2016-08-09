package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;
public class JoinLower {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		//l0中的时钟不变式
		ArrayList<String> l0 =new ArrayList<String>();
		l0.add("x<2");
		l0.add("x<=y");
		l0.add("y<=x");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, l0));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		
		//e0边上的约束
		ArrayList<String> e0 =new ArrayList<String>();
		e0.add("x>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, e0));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		
		//l0与e0相交
		DBM_element andDBM[][]=AndDBM.andDBM(fDBM1, fDBM2);
		DBM_element fandDBM[][]=Floyds.floyds(andDBM);
		
		//将fandDBM中y复位
		DBM_element rDBM[][]=Reset.reset(fandDBM, Clocks, "y");
		DBM_element frDBM[][]=Floyds.floyds(rDBM);
		
		//提取frDBM中时钟复位信息
		DBM_element extDBM[][]=ExtractReset.extract(frDBM);
		DBM_element fextDBM[][]=Floyds.floyds(extDBM);
		
		//将fextDBM加入到l1的不变式中
		ArrayList<String> l1 =new ArrayList<String>();
		l1.add("y<=2");
		DBM_element DBM3[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, l1));
		DBM_element fDBM3[][]=Floyds.floyds(DBM3);
		
		DBM_element joinclock[][]=JoinReset.joinClock(fDBM3, fextDBM);
		DBM_element fjoinclock[][]=Floyds.floyds(joinclock);
		//提取e0的时钟下界信息
		DBM_element extractLower[][]=ExtractLower.extractLower(fDBM2);
		DBM_element fextractLower[][]=Floyds.floyds(extractLower);
		//将fextractLower加入到fjoinclock
		DBM_element jionlower[][]=joinLower(fjoinclock,fextractLower);
		DBM_element fjionlower[][]=Floyds.floyds(jionlower);
		
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fjionlower[i][j];
				System.out.println("DBM_i:"+cons.getDBM_i());
				System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				System.out.println("***********");
								
			}
		}
	}
	
	/**
	 * 将时钟下界信息加入到状态不变式中
	 * @param dbm1 时钟下界矩阵
	 * @param dbm2  含有时钟复位信息的状态不变式矩阵
	 * @return 含有时钟下界和时钟复位信息的矩阵
	 */
	public static DBM_element[][] joinLower(DBM_element[][]dbm1,DBM_element[][]dbm2) {
		int dimension=dbm1.length;
		DBM_element andDBM[][]=new DBM_element [dimension][dimension];
	  
		for(int i=0;i<dimension;i++){
			for(int j=0;j<dimension;j++){
				DBM_element ele=new DBM_element();
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				ele.setStrictness(AndDBM.andDBM(dbm1, dbm2)[i][j].isStrictness());
				ele.setValue(AndDBM.andDBM(dbm1, dbm2)[i][j].getValue());
				andDBM[i][j]=ele;		
			}
		}
		
		return andDBM;
	}
}
