package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;
/**
 * 测试stringToDBM_element（）、buildDBM（）、floyds（）、andDBM（）方法。
 * @author Seryna
 *
 */
public class Test_02 {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		
		ArrayList<String> c1=new ArrayList<String>();
		c1.add("x<2");
		ArrayList<String> c2=new ArrayList<String>();
		c2.add("x>1");
		DBM_element[][] DBM1=Floyds.floyds(DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, c1)));//将状态中的不变式转成DBM矩阵
		DBM_element[][] DBM2=Floyds.floyds(DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, c2)));//将状态中的不变式转成DBM矩阵
		DBM_element[][] DBM3=AndDBM.andDBM(DBM1, DBM2);
		int len=DBM3.length;
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				System.out.println(i+" "+j+": "+DBM3[i][j]);
				if(DBM3[i][j]==null){
					System.out.println("null");
				}
				else{
					DBM_element cons=DBM3[i][j];
					System.out.println("DBM_i:"+cons.getDBM_i());
					System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());
					System.out.println("***********");
				}				
			}
		}
		
	}
}
