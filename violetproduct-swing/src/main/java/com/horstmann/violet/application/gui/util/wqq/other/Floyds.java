package com.horstmann.violet.application.gui.util.wqq.other;

public class Floyds {
	public static void main(String[] args) {
		DBM_element d1=new DBM_element();
		d1.setValue(88888);
		d1.setStrictness(true);
		
		DBM_element d2=new DBM_element();
		d2.setValue(6);
		d2.setStrictness(false);
		DBM_element d3=add(d1,d2);//测试add方法
		System.out.println(d3.getValue());
		System.out.println(d3.isStrictness());
		System.out.println("*********");
		System.out.println(compareTo(d1,d2));//测试compareTo方法
	}
	/**
	 * 将DBM矩阵正规化，返回正规化的DBM
	 * @param DBM
	 * @return
	 */
	public static DBM_element[][] floyds(DBM_element[][] DBM){
		int size=DBM.length;
		DBM_element[][] newDBM = new DBM_element[size][size];
		for(int i=0;i<size;i++){//初始化newDBM
			for(int j=0;j<size;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(DBM[i][j].getValue());
				ele.setStrictness(DBM[i][j].isStrictness());
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				newDBM[i][j]=ele;	
			}
		}
				
		for(int k=0;k<size;k++){
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					DBM_element sum=new DBM_element();
					sum=add(DBM[i][k],DBM[k][j]);
					if(compareTo(sum,DBM[i][j])==1){	
						DBM_element ele=new DBM_element();
						ele.setValue(sum.getValue());
						ele.setStrictness(sum.isStrictness());
						ele.setDBM_i(i);
						ele.setDBM_j(j);
						newDBM[i][j]=ele;						
						}
				}
			}
		}
		return newDBM;
	}
	/**
	 * 两个DBM元素相加，返回新的DBM元素
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static DBM_element add(DBM_element d1,DBM_element d2){
		DBM_element d3=new DBM_element();
		if(d1.getValue()==88888||d2.getValue()==88888){
			d3.setValue(88888);
			d3.setStrictness(false);
		}
		else{
			d3.setValue(d1.getValue()+d2.getValue());
			if(d1.isStrictness()!=d2.isStrictness()){
				d3.setStrictness(false);
			}
			else{
				d3.setStrictness(d1.isStrictness());
			}
		}
		return d3;
	}
	
	/**
	 * 比较两个DBM元素的大小，如果d1<d2，返回1,d1=d2,返回0；d1>d2,返回-1
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compareTo(DBM_element d1,DBM_element d2){
		if(d1.getValue()==88888&&d2.getValue()!=88888){
			return -1;
		}
		if(d1.getValue()!=88888&&d2.getValue()==88888){
			return 1;
		}
		if(d1.getValue()==88888&&d2.getValue()==88888){
			return 0;
		}
		if(d1.getValue()<d2.getValue()){
			return 1;
		}
		else if(d1.getValue()==d2.getValue()){
			if(d1.isStrictness()!=d2.isStrictness()){
				if(d1.isStrictness()==false){return 1;}
				if(d1.isStrictness()==true){return -1;}
			}
			else return 0;
			
		}
		return -1;
	}
}
