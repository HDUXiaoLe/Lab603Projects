package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class DBM_elementToDBM {
	public static DBM_element[][] buildDBM(ArrayList<String> ClockSet,ArrayList<DBM_element>consANDinv){
		int dimension=ClockSet.size()+1;//获取DBM的维度
		DBM_element DBM[][]=new DBM_element [dimension][dimension];
		
		for(int i=0;i<dimension;i++){//设置对角线上的元素为(0,true)
			for(int j=0;j<dimension;j++){
				if(i==j){
					DBM_element diagonal=new DBM_element();
					diagonal.setDBM_i(i);
					diagonal.setDBM_j(j);
					diagonal.setStrictness(true);
					diagonal.setValue(0);
					DBM[i][j]=diagonal;
				}
			}
		}
		
		for(int j=0;j<dimension;j++){//设置第一行元素为(0,true)		
			DBM_element firstline=new DBM_element();
			firstline.setDBM_i(0);
			firstline.setDBM_j(j);
			firstline.setStrictness(true);
			firstline.setValue(0);
			DBM[0][j]=firstline;						
		}
		
		for(DBM_element cons:consANDinv){//将约束或不变式中的原子约束对应的值填入DBM
			DBM[cons.getDBM_i()][cons.getDBM_j()]=cons;
		}
		
		for(int i=0;i<dimension;i++){//设置DBM中无关元素的值,数值部分为88888
			for(int j=0;j<dimension;j++){
				if(DBM[i][j]==null){
					DBM_element unrelated=new DBM_element();
					unrelated.setDBM_i(i);
					unrelated.setDBM_j(j);
					unrelated.setValue(88888);
					unrelated.setStrictness(false);
					DBM[i][j]=unrelated;		
				}
			}
		}
		return DBM;
	}
}
