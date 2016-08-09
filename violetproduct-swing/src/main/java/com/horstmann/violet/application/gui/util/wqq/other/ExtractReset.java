package com.horstmann.violet.application.gui.util.wqq.other;

/**
 * 提取矩阵中的时钟复位信息，返回一个只包含复位后时钟信息的矩阵
 * @author Seryna
 *
 */
public class ExtractReset {
	public static DBM_element[][] extract(DBM_element[][] dbm) {
		int dimension=dbm.length;//获取DBM的维度
		 DBM_element[][] fdbm=Floyds.floyds(dbm);//将dbm规范化后再提取时钟复位信息
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
		
		for(int i=1;i<dimension;i++){//提取时钟复位后的信息
			for(int j=1;j<dimension;j++){
				if(i!=j){
					DBM_element con=new DBM_element();
					con.setDBM_i(i);
					con.setDBM_j(j);
					con.setValue(fdbm[i][j].getValue());
					con.setStrictness(fdbm[i][j].isStrictness());
					DBM[i][j]=con;
				}
			}
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
