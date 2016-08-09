package com.horstmann.violet.application.gui.util.wqq.other;

public class JoinReset {
	/**
	 * 将提取的时钟复位后的信息加入到DBM矩阵中
	 * @param dbm1 只包含复位后时钟信息的矩阵
	 * @param dbm2要加入时钟复位后信息的矩阵
	 * @return 返回加入时钟复位后信息的矩阵
	 */
	public static DBM_element[][] joinClock( DBM_element[][] dbm1,DBM_element[][] dbm2) {
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
