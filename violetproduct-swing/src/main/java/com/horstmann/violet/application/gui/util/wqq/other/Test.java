package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		ArrayList<String> ClockSet=new ArrayList<String>();
		ClockSet.add("x");
		ClockSet.add("y");

		UppaalTemPlate template=new UppaalTemPlate();
		template.setClockSet(ClockSet);
		
		ArrayList<String> constraintANDinvariant=new ArrayList<String>();
		constraintANDinvariant.add("x>=y");
		constraintANDinvariant.add("x<=y");
		constraintANDinvariant.add("x<2");
		//constraintANDinvariant.add("y<2");
		/*constraintANDinvariant.add("y>=1");
		constraintANDinvariant.add("y<=3");*/
		//测试stringToDBM_element方法
		ArrayList<DBM_element>consANDinv=StringToDBM_element.stringToDBM_element(ClockSet, constraintANDinvariant);
		/*System.out.println(consANDinv.size());
		System.out.println("------");
		for(DBM_element cons:consANDinv){
		System.out.println("DBM_i:"+cons.getDBM_i());
		System.out.println("DBM_j:"+cons.getDBM_j());
		System.out.println("value:"+cons.getValue());
		System.out.println("Strictness:"+cons.isStrictness());
		System.out.println("------");		
		}*/
		//测试buildDBM方法
		System.out.println("***********");
		DBM_element[][] DBM=DBM_elementToDBM.buildDBM(ClockSet, consANDinv);
	/*	int len=DBM.length;
		System.out.println("DBM度数："+len);
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				System.out.println(i+" "+j+": "+DBM[i][j]);
				if(DBM[i][j]==null){
					System.out.println("null");
				}
				else{
					DBM_element cons=DBM[i][j];
					System.out.println("DBM_i:"+cons.getDBM_i());
					System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());
					System.out.println("***********");
				}				
			}
		}*/
		
		//测试floyds方法
		System.out.println("##########");
		DBM_element[][] newDBM=Floyds.floyds(DBM);
		/*		int newlen=newDBM.length;
		System.out.println("DBM度数："+newlen);
		//System.out.println(newDBM[0][0].getValue());
		for(int i=0;i<newlen;i++){
			for(int j=0;j<newlen;j++){
				System.out.println(i+" "+j+": "+newDBM[i][j]);
				if(newDBM[i][j]==null){
					System.out.println("null");
				}
				else{
					DBM_element newcons=newDBM[i][j];
					System.out.println("DBM_i:"+newcons.getDBM_i());
					System.out.println("DBM_j:"+newcons.getDBM_j());
					System.out.println("value:"+newcons.getValue());
					System.out.println("Strictness:"+newcons.isStrictness());
					System.out.println("##########");
				}				
			}
		}*/
		
		
		
		//测试copy方法
		DBM_element[][] copyDBM=Copy.copy(newDBM, 2, 1);
	/*	int copylen=copyDBM.length;
		System.out.println("DBM度数："+copylen);
		for(int i=0;i<copylen;i++){
			for(int j=0;j<copylen;j++){
				System.out.println(i+" "+j+": "+copyDBM[i][j]);
				if(copyDBM[i][j]==null){
					System.out.println("null");
				}
				else{
					DBM_element newcons=copyDBM[i][j];
					System.out.println("DBM_i:"+newcons.getDBM_i());
					System.out.println("DBM_j:"+newcons.getDBM_j());
					System.out.println("value:"+newcons.getValue());
					System.out.println("Strictness:"+newcons.isStrictness());
					System.out.println("##########");
				}				
			}
		}*/
		
		
		
		
		DBM_element[][] cDBM=Floyds.floyds(copyDBM);
		/*int clen=cDBM.length;
		System.out.println("cDBM度数："+clen);
		for(int i=0;i<clen;i++){
			for(int j=0;j<clen;j++){
				System.out.println(i+" "+j+": "+cDBM[i][j]);
				if(cDBM[i][j]==null){
					System.out.println("null");
				}
				else{
					DBM_element newcons=cDBM[i][j];
					System.out.println("DBM_i:"+newcons.getDBM_i());
					System.out.println("DBM_j:"+newcons.getDBM_j());
					System.out.println("value:"+newcons.getValue());
					System.out.println("Strictness:"+newcons.isStrictness());
					System.out.println("##########");
				}				
			}
		}*/
		
	/*	for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(cDBM[i][j]==copyDBM[i][j]){
					System.out.println(1);
				}
				else System.out.println(-1);
				
			}
		}*/
	}

}
