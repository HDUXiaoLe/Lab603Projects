package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;
/**
 * 测试copy（）方法
 * @author Administrator
 *
 */
public class Test_03 {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		
		ArrayList<String> c1=new ArrayList<String>();
		c1.add("x<2");
		ArrayList<String> c2=new ArrayList<String>();
		c2.add("x>1");
		
		ArrayList<String> c3=new ArrayList<String>();
		c3.add("x<2");
		c3.add("x<=y");
		c3.add("x>=y");
		
		DBM_element[][] DBM1=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, c1));//将状态中的不变式转成DBM矩阵
		DBM_element[][] copyDBM1=Copy.copy(DBM1, 2, 1);//DBM1中隐含条件y=x
		DBM_element[][] fcopyDBM1=Floyds.floyds(copyDBM1);//将copyDBM1规范化
		DBM_element[][] DBM3=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, c3));//将状态中的不变式转成DBM矩阵
		//DBM_element[][] copyDBM3=Copy.copy(DBM3, 2, 1);//DBM3中隐含条件y=x
		DBM_element[][] fDBM3=Floyds.floyds(DBM3);//将DBM3规范化
		
		//DBM_element[][] DBM2=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, c2));//将状态中的不变式转成DBM矩阵
		//DBM_element[][] fDBM2=Floyds.floyds(DBM2);//将DBM2规范化
		//DBM_element[][] fDBM3ANDfDBM2=AndDBM.andDBM(fDBM3, fDBM2);//fcopyDBM1交DBM2
		//System.out.println(IsEmpty.isEmpty(DBM3));//判断DBM3是否为空
		//DBM_element[][] fAnd=Floyds.floyds(fDBM3ANDfDBM2);//如果不为空，将fDBM3ANDfDBM2正规化
		
		int len=DBM1.length;
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				System.out.println(i+" "+j+": ");
				DBM_element cons=fDBM3[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				System.out.println("***********");
								
			}
		}
		
	}
}
