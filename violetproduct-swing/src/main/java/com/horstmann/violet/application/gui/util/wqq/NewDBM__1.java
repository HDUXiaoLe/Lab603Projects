package com.horstmann.violet.application.gui.util.wqq;

import com.horstmann.violet.application.gui.util.wqq.other.DBM_element;

public class NewDBM__1 {
	public static void main(String[] args) {
		DBM_element[][] newDBM=newDBM(2);
		for(int i=0;i<2;i++){
			for(int j=0;j<2;j++){
				DBM_element cons=newDBM[i][j];
				System.out.println("DBM_i:"+cons.getDBM_i());
				System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				
								
			}
		}
	}
	/**
	 * 返回一个没有约束的DBM矩阵
	 * @return
	 */
	public static DBM_element[][] newDBM(int dimension) {
		//int dimension=3;
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
