package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class ExtractLower {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y>1");
		ar1.add("x<=3");
		DBM_element DBM[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element fDBM[][]=Floyds.floyds(DBM);
		DBM_element extDBM[][]=extractLower(DBM);
		DBM_element fextDBM[][]=Floyds.floyds(extDBM);
		
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fextDBM[i][j];
				System.out.println("DBM_i:"+cons.getDBM_i());
				System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				System.out.println("***********");
								
			}
		}
	}
	
	
	
	/**
	 * 将dbm中的时钟下界提取出来
	 * @param dbm
	 * @return 只含有dbm中时钟下界的矩阵
	 */
	public static DBM_element[][] extractLower(DBM_element[][] dbm) {
		int dimension=dbm.length;//获取DBM的维度
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
		
		for(int j=1;j<dimension;j++){//提取第一行的时钟下界	
			DBM_element firstline=new DBM_element();
			if(dbm[0][j].getValue()<0){
				firstline.setDBM_i(0);
				firstline.setDBM_j(j);
				firstline.setStrictness(dbm[0][j].isStrictness());
				firstline.setValue(dbm[0][j].getValue());		
				DBM[0][j]=firstline;						
			}
			else{
				firstline.setDBM_i(0);
				firstline.setDBM_j(j);
				firstline.setStrictness(true);
				firstline.setValue(0);		
				DBM[0][j]=firstline;			
			}
		}
		
		for(int i=1;i<dimension;i++){//提取第一列的时钟下界	
			DBM_element firstcolumn=new DBM_element();
			if(dbm[i][0].getValue()<0){
				firstcolumn.setDBM_i(i);
				firstcolumn.setDBM_j(0);
				firstcolumn.setStrictness(dbm[i][0].isStrictness());
				firstcolumn.setValue(dbm[i][0].getValue());		
				DBM[i][0]=firstcolumn;						
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
