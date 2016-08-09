package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class Commonboundary_old {
	public static void main(String[] args) {
		int a=adjacent(1,2,2,2);
		System.out.println(a);
		int ad=adjacent(1,2,2,4);
		System.out.println(ad);
		
		int[] e=eq(1,2,1,2);
		System.out.println(e[0]+" "+e[1]);

		int[] inte=intersect(1,3,2,4);
		System.out.println(inte[0]+" "+inte[1]);
		int[] inte1=intersect(1,3,2,3);
		System.out.println(inte1[0]+" "+inte1[1]);
		int[] inte2=intersect(2,4,1,3);
		System.out.println(inte2[0]+" "+inte2[1]);
		int[] inte3=intersect(2,4,1,4);
		System.out.println(inte3[0]+" "+inte3[1]);
		int[] inte4=intersect(1,4,2,3);
		System.out.println(inte4[0]+" "+inte4[1]);
		int[] inte5=intersect(1,4,2,4);
		System.out.println(inte5[0]+" "+inte5[1]);
		int[] inte6=intersect(1,3,1,2);
		System.out.println(inte6[0]+" "+inte6[1]);
		int[] inte7=intersect(3,4,2,5);
		System.out.println(inte7[0]+" "+inte7[1]);
		
		
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=1");
		ar1.add("x-y<2");
		ar1.add("x-y>1");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x<=3");
		ar2.add("y>1");
		ar2.add("x-y<2");
		ar2.add("x-y>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		
		DBM_element[][] fDBM1=Floyds.floyds(DBM1);
		DBM_element[][] fDBM2=Floyds.floyds(DBM2);
		/*for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=fDBM2[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				
								
			}
		}*/
		
		DBM_element[][] com=commonboudary(DBM1, DBM2);
		/*DBM_element[][] fcom=Floyds.floyds(com);
		if(com!=null){
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=fcom[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());
					
									
				}
			}
		}*/
		System.out.println("公共边界为空");
		
		
		DBM_element[][] down=Down.down(com);
		/*DBM_element[][] fdown=Floyds.floyds(down);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=down[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				
								
			}
		}*/
	}
	/**
	 * 返回两个矩阵的公共边界，边界也存储在矩阵中，边界是没有规范化的
	 * @param DBM1
	 * @param DBM2
	 * @return
	 */
	public static DBM_element[][] commonboudary(DBM_element[][] DBM1,DBM_element[][] DBM2) {
		DBM_element[][] fDBM1=Floyds.floyds(DBM1);
		DBM_element[][] fDBM2=Floyds.floyds(DBM2);//将矩阵规范化
		
		//y的三种关系返回值（相邻、相交、相等）
		int y_adjacent=adjacent(Math.abs(fDBM1[0][2].getValue()),Math.abs(fDBM1[2][0].getValue()),Math.abs(fDBM2[0][2].getValue()),Math.abs(fDBM2[2][0].getValue()));
		int[] y_intersect=intersect(Math.abs(fDBM1[0][2].getValue()),Math.abs(fDBM1[2][0].getValue()),Math.abs(fDBM2[0][2].getValue()),Math.abs(fDBM2[2][0].getValue()));
		int[] y_eq=eq(Math.abs(fDBM1[0][2].getValue()),Math.abs(fDBM1[2][0].getValue()),Math.abs(fDBM2[0][2].getValue()),Math.abs(fDBM2[2][0].getValue()));
		//x-y的三种返回值
		int xy_adjacent=adjacent(Math.abs(fDBM1[2][1].getValue()),Math.abs(fDBM1[1][2].getValue()),Math.abs(fDBM2[2][1].getValue()),Math.abs(fDBM2[1][2].getValue()));
		int[] xy_intersect=intersect(Math.abs(fDBM1[2][1].getValue()),Math.abs(fDBM1[1][2].getValue()),Math.abs(fDBM2[2][1].getValue()),Math.abs(fDBM2[1][2].getValue()));
		int[] xy_eq=eq(Math.abs(fDBM1[2][1].getValue()),Math.abs(fDBM1[1][2].getValue()),Math.abs(fDBM2[2][1].getValue()),Math.abs(fDBM2[1][2].getValue()));
		//x的三种关系返回值
		int x_adjacent=adjacent(Math.abs(fDBM1[0][1].getValue()),Math.abs(fDBM1[1][0].getValue()),Math.abs(fDBM2[0][1].getValue()),Math.abs(fDBM2[1][0].getValue()));
		int[] x_intersect=intersect(Math.abs(fDBM1[0][1].getValue()),Math.abs(fDBM1[1][0].getValue()),Math.abs(fDBM2[0][1].getValue()),Math.abs(fDBM2[1][0].getValue()));
		int[] x_eq=eq(Math.abs(fDBM1[0][1].getValue()),Math.abs(fDBM1[1][0].getValue()),Math.abs(fDBM2[0][1].getValue()),Math.abs(fDBM2[1][0].getValue()));
		
		//第一种情况，y相邻，x-y相交，x相交
		if(y_adjacent!=-1){
			if(xy_intersect[0]!=-1){
				if(x_intersect[0]!=-1){
					DBM_element[][] DBM_1=NewDBM.newDBM();
					DBM_1[0][2].setStrictness(true);//设置y值
					DBM_1[0][2].setValue(-y_adjacent);
					DBM_1[2][0].setStrictness(true);
					DBM_1[2][0].setValue(y_adjacent);
					
					DBM_1[0][1].setStrictness(true);//设置x值
					DBM_1[0][1].setValue(-x_intersect[0]);
					DBM_1[1][0].setStrictness(true);
					DBM_1[1][0].setValue(x_intersect[1]);
					
					return DBM_1;
				}
			}
		}
		//第二种情况，y相邻，x-y相等，x相等
		if(y_adjacent!=-1){
			if(xy_eq[0]!=-1){
				if(x_eq[0]!=-1){
					DBM_element[][] DBM_2=NewDBM.newDBM();
					DBM_2[0][2].setStrictness(true);//设置y值
					DBM_2[0][2].setValue(-y_adjacent);
					DBM_2[2][0].setStrictness(true);
					DBM_2[2][0].setValue(y_adjacent);
					
					DBM_2[0][1].setStrictness(true);//设置x值
					DBM_2[0][1].setValue(-x_intersect[0]);
					DBM_2[1][0].setStrictness(true);
					DBM_2[1][0].setValue(x_intersect[1]);
					
					return DBM_2;
				}
			}
		}
		//第三种情况，y相邻，x-y相等，x相邻
		if(y_adjacent!=-1){
			if(xy_eq[0]!=-1){
				if(x_adjacent!=-1){
					return DBM1;
				}
			}
		}
		/*//第四种情况，y相等，x-y相邻，x相邻
		if(y_eq[0]!=-1){
			if(xy_adjacent!=-1){
				if(x_adjacent!=-1){
					DBM_element[][] DBM_4=NewDBM.newDBM();
					DBM_4[0][2].setStrictness(true);//设置y值
					DBM_4[0][2].setValue(-y_eq[0]);
					DBM_4[2][0].setStrictness(true);
					DBM_4[2][0].setValue(y_eq[1]);
					
					DBM_4[1][2].setStrictness(true);//设置x-y值
					DBM_4[1][2].setValue(xy_adjacent);
					DBM_4[2][1].setStrictness(true);
					DBM_4[2][1].setValue(-xy_adjacent);
					
					return DBM_4;
				}
			}
		}*/
		//第五种情况，y相等，x-y相交，x相邻
		if(y_eq[0]!=-1){
			if(xy_intersect[0]!=-1){
				if(x_adjacent!=-1){
					DBM_element[][] DBM_5=NewDBM.newDBM();
					DBM_5[0][2].setStrictness(true);//设置y值
					DBM_5[0][2].setValue(-y_eq[0]);
					DBM_5[2][0].setStrictness(true);
					DBM_5[2][0].setValue(y_eq[1]);
					
					DBM_5[0][1].setStrictness(true);//设置x值
					DBM_5[0][1].setValue(-x_adjacent);
					DBM_5[1][0].setStrictness(true);
					DBM_5[1][0].setValue(x_adjacent);
					
					return DBM_5;
				}
			}
		}
		/*//第六种情况，y相交，x-y相邻，x相邻
		if(y_intersect[0]!=-1){
			if(xy_adjacent!=-1){
				if(x_adjacent!=-1){
					DBM_element[][] DBM_6=NewDBM.newDBM();
					DBM_6[0][2].setStrictness(true);//设置y值
					DBM_6[0][2].setValue(-y_eq[0]);
					DBM_6[2][0].setStrictness(true);
					DBM_6[2][0].setValue(y_eq[1]);
					
					DBM_6[1][2].setStrictness(true);//设置x-y值
					DBM_6[1][2].setValue(xy_adjacent);
					DBM_6[2][1].setStrictness(true);
					DBM_6[2][1].setValue(-xy_adjacent);
					
					return DBM_6;
				}
			}
		}*/
		//第七种情况，y相交，x-y相交，x相邻
		if(y_intersect[0]!=-1){
			if(xy_intersect[0]!=-1){
				if(x_adjacent!=-1){
					DBM_element[][] DBM_7=NewDBM.newDBM();
					DBM_7[0][2].setStrictness(true);//设置y值
					DBM_7[0][2].setValue(-y_intersect[0]);
					DBM_7[2][0].setStrictness(true);
					DBM_7[2][0].setValue(y_intersect[1]);
					
					DBM_7[0][1].setStrictness(true);//设置x值
					DBM_7[0][1].setValue(-x_adjacent);
					DBM_7[1][0].setStrictness(true);
					DBM_7[1][0].setValue(x_adjacent);
					
					return DBM_7;
				}
			}
		}
		//第八种情况，y相邻，x-y相同，x相交
				if(y_adjacent!=-1){
					if(xy_eq[0]!=-1){
						if(x_intersect[0]!=-1){
							DBM_element[][] DBM_8=NewDBM.newDBM();
							DBM_8[0][2].setStrictness(true);//设置y值
							DBM_8[0][2].setValue(-y_adjacent);
							DBM_8[2][0].setStrictness(true);
							DBM_8[2][0].setValue(y_adjacent);
							
							DBM_8[0][1].setStrictness(true);//设置x值
							DBM_8[0][1].setValue(-x_intersect[0]);
							DBM_8[1][0].setStrictness(true);
							DBM_8[1][0].setValue(x_intersect[1]);
							
							return DBM_8;
						}
					}
				}
		return null;
	}
		
	/**
	 * 如果[a0,a1] [b0,b1]相邻，输出相邻点
	 * @param a0
	 * @param a1
	 * @param b0
	 * @param b1
	 * @return
	 */
	public static int adjacent(int a0,int a1,int b0,int b1) {
		if(a0<b0&&a0<b1&&a1==b0&&a1<b1){return a1;}
		if(a0==a1&&a0==b0&&b0<b1){return a0;}
		if(a0<a1&&a1==b0&&b0==b1){return a1;}
		else return -1;
	}
	
	/**
	 * 如果[a0,a1] [b0,b1]相邻，输出相等的区域[a0,a1]
	 * @param a0
	 * @param a1
	 * @param b0
	 * @param b1
	 * @return
	 */
	public static int[] eq(int a0,int a1,int b0,int b1) {
		int[] x=new int[2];
		if(a0==b0&&a1==b1){
			x[0]=a0;
			x[1]=a1;
			return x;
		}
		else {
			x[0]=-1;
			x[1]=-1;
			return x;
		}
	}
	/**
	 * 如果[a0,a1] [b0,b1]相交，输出相交的区域
	 * @param a0
	 * @param a1
	 * @param b0
	 * @param b1
	 * @return
	 */
	public static int[] intersect(int a0,int a1,int b0,int b1) {
		int[] x=new int[2];
		if(a0<b0&&a1>b0&&a1<=b1){
			x[0]=b0;
			x[1]=a1;
			return x;
		}
		if(b0<a0&&b1>a0&&b1<=a1){
			x[0]=a0;
			x[1]=b1;
			return x;
		}
		if(a0<=b0&&b1<a1){
			x[0]=b0;
			x[1]=b1;
			return x;
		}
		if(b0<=a0&a1<b1){
			x[0]=a0;
			x[1]=a1;
			return x;
		}
		else{
			x[0]=-1;
			x[1]=-1;
			return x;
		}
	}
}
