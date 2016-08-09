package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

import com.horstmann.violet.application.gui.util.wqq.GetAutomatic;

public class Complement_2 {
	public static void main(String[] args) {
		/*ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x<=3");
		ar2.add("y>1");*/
		//DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		//DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		
		
		//ArrayList<DBM_element[][]> complements=complement(DBM2);
	/*	System.out.println(complements.size());
		for(DBM_element[][] dbm:complements){
			DBM_element fdbm[][]=Floyds.floyds(dbm);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=dbm[i][j];
					System.out.println("DBM_i:"+cons.getDBM_i());
					System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());
					
									
				}
			}
			System.out.println("***********");
		}*/
	
		
		String xml="Draw MoneyForXStream(2).xml";
		Automatic automatic=GetAutomatic.getAutomatic(xml);
		ArrayList<DBM_element[][]> cc=Complement_2.complement(automatic.getTransitionSet().get(5).getConstraintDBM());
		System.out.println(cc.size() );
		for(DBM_element[][] dbm:cc){
			DBM_element fdbm[][]=Floyds.floyds(dbm);
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					System.out.println("DBM_i:"+cons.getDBM_i());
					System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());
					
									
				}
			}
			System.out.println("***********");
		}
	}
	
	/**
	 * 求一个矩阵的补集，补集是一个矩阵集合,集合中的矩阵没有规范化
	 * @param DBM 不将DBM规范化 
	 * @return
	 */
	public static ArrayList<DBM_element[][]> complement(DBM_element[][] DBM){
		int dimension=DBM.length;//获取DBM的维度
		//DBM_element[][] fDBM=Floyds.floyds(DBM);//不将DBM规范化 
		ArrayList<DBM_element[][]> complements=new ArrayList<DBM_element[][]>();
	
		for(int i=0;i<dimension;i++){
			for(int j=0;j<dimension;j++){
				
				if(i==0&&DBM[i][j].getValue()!=0){//处理第一行元素
					DBM_element com_DBM[][]=new DBM_element [dimension][dimension];
					DBM_element d=new DBM_element();
					d.setDBM_i(j);
					d.setDBM_j(i);
					d.setStrictness(!DBM[i][j].isStrictness());
					d.setValue(-DBM[i][j].getValue());
					com_DBM[j][i]=d;	
					
					complements.add(com_DBM);
				}
				
				if(i>0&&i!=j&&DBM[i][j].getValue()!=88888){//处理剩下的元素(对角线上元素不处理)
					DBM_element com_DBM[][]=new DBM_element [dimension][dimension];
					DBM_element d=new DBM_element();
					d.setDBM_i(j);
					d.setDBM_j(i);
					d.setStrictness(!DBM[i][j].isStrictness());
					d.setValue(-DBM[i][j].getValue());
					com_DBM[j][i]=d;	
					
					complements.add(com_DBM);
				}
				
			}
		}
		
		for(DBM_element[][] dbm:complements){	
			for(int i=0;i<dimension;i++){
				for(int j=0;j<dimension;j++){
					if(i==0&&dbm[i][j]==null){//设置第一行元素	
						DBM_element firstline=new DBM_element();
						firstline.setDBM_i(0);
						firstline.setDBM_j(j);
						firstline.setStrictness(true);
						firstline.setValue(0);
						dbm[i][j]=firstline;
					}
					
					if(i==j){//设置对角线上元素
						DBM_element diagonal=new DBM_element();
						diagonal.setDBM_i(i);
						diagonal.setDBM_j(j);
						diagonal.setStrictness(true);
						diagonal.setValue(0);
						dbm[i][j]=diagonal;
					}
					
					
					if(dbm[i][j]==null){//设置DBM中无关元素的值,数值部分为88888
						DBM_element unrelated=new DBM_element();
						unrelated.setDBM_i(i);
						unrelated.setDBM_j(j);
						unrelated.setValue(88888);
						unrelated.setStrictness(false);
						dbm[i][j]=unrelated;		
					}
				}
			}
		}
			
		
		return complements;
	}
}
