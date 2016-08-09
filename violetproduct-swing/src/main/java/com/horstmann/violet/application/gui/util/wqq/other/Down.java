package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class Down {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x-y>1");
		ar2.add("x-y<2");
		ar2.add("y<=2");
		ar2.add("y>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
		
		DBM_element[][] down=down(DBM2);
		DBM_element fdown[][]=Floyds.floyds(down);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fdown[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				
								
			}
		}
	}
	/**
	 * 计算一个DBM区域的时间前驱
	 * @param DBM
	 * @return 返回一个没有规范化的结果
	 */
	public static DBM_element[][] down(DBM_element[][] DBM) {
		int n=DBM.length;
		DBM_element[][] fDBM=Floyds.floyds(DBM);//将DBM规范化
		DBM_element[][] newDBM = new DBM_element[n][n];
		for(int i=0;i<n;i++){//初始化newDBM
			for(int j=0;j<n;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(fDBM[i][j].getValue());
				ele.setStrictness(fDBM[i][j].isStrictness());
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				newDBM[i][j]=ele;	
			}
		}
		
		for(int j=0;j<n;j++){
			newDBM[0][j].setStrictness(true);
			newDBM[0][j].setValue(0);
		}	
		return newDBM;
	}
	
	/**
	 * 计算一个DBM区域的时间前驱
	 * @param DBM
	 * @return 返回一个规范化后的结果
	 */
	public static DBM_element[][] down_1(DBM_element[][] DBM) {
		int n=DBM.length;
		DBM_element[][] fDBM=Floyds.floyds(DBM);//将DBM规范化
		DBM_element[][] newDBM = new DBM_element[n][n];
		for(int i=0;i<n;i++){//初始化newDBM
			for(int j=0;j<n;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(fDBM[i][j].getValue());
				ele.setStrictness(fDBM[i][j].isStrictness());
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				newDBM[i][j]=ele;	
			}
		}
		
		for(int j=0;j<n;j++){
			newDBM[0][j].setStrictness(true);
			newDBM[0][j].setValue(0);
			for(int i=0;i<n;i++){
				if(Floyds.compareTo(newDBM[i][j], newDBM[0][j])==1){
					 newDBM[0][j]=newDBM[i][j];
				}
			}
		}	
		return newDBM;
	}
}
