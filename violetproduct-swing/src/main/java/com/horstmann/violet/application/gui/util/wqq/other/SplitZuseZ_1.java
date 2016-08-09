package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class SplitZuseZ_1 {
	public static void main(String[] args) {
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");
		
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=2");
		ar1.add("x-y>1");
		ar1.add("x-y<2");
		DBM_element DBM1[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar1));
		DBM_element fDBM1[][]=Floyds.floyds(DBM1);
		
		ArrayList<String> ar2 =new ArrayList<String>();
		ar2.add("x<=3");
		ar2.add("y>1");
		DBM_element DBM2[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar2));
		DBM_element fDBM2[][]=Floyds.floyds(DBM2);
	
		ArrayList<String> ar3 =new ArrayList<String>();
		ar3.add("x-y>1");
		ar3.add("x-y<2");
		DBM_element DBM3[][]=DBM_elementToDBM.buildDBM(Clocks, StringToDBM_element.stringToDBM_element(Clocks, ar3));
		DBM_element fDBM3[][]=Floyds.floyds(DBM3);
		
		DBM_element and[][]=AndDBM.andDBM(DBM2, DBM3);
		DBM_element fand[][]=Floyds.floyds(and);
		
		ArrayList<DBM_element[][]> com_and=Complement_1.complement(and);
		//System.out.println(com_and.size());
		
		ArrayList<DBM_element[][]> zones=splitZuseZ(DBM1,and);
		System.out.println(zones.size());
		
		
		for(DBM_element[][] dbm:zones){
			DBM_element fdbm[][]=Floyds.floyds(dbm);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=fdbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());														
				}
			}
			System.out.println("***********");
		}
	}
	/**
	 * 用矩阵Z2拆分矩阵Z1，Z1和Z2都没有规范化
	 * @param Z1
	 * @param Z2
	 * @return Z1被拆分成多个矩阵，返回一个矩阵集合，返回的矩阵是没有正规化的（结果DBMs中都不相交）
	 */
	public static ArrayList<DBM_element[][]> splitZuseZ(DBM_element[][] Z1,DBM_element[][] Z2) {
		ArrayList<DBM_element[][]> Zones=new ArrayList<DBM_element[][]>();
		int n=Z1.length;
		DBM_element[][] copyZ1=new DBM_element[n][n];//把Z1赋给copyZ1，以免运算完成后Z1改变
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(Z1[i][j].getValue());
				ele.setStrictness(Z1[i][j].isStrictness());
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				copyZ1[i][j]=ele;	
			}
		}
		
		
		if(IsEmpty.isEmpty(AndDBM.andDBM(copyZ1, Z2))==1){//如果Z1∩Z2为空，则返回Z1，因为Z1不用被Z2拆分
			Zones.add(Z1);
		}
		
		else{//如果Z1∩Z2不为空
			Zones.add(AndDBM.andDBM(Z1, Z2));//将Z1∩Z2加入Zones
			ArrayList<DBM_element[][]> complements=Complement_1.complement(Z2);//求Z2的补集
			for(DBM_element[][] com:complements){//遍历Z2的补集
				if(IsEmpty.isEmpty(AndDBM.andDBM(copyZ1, com))==1){//如果一个补集与Z1相交为空，则什么都不用做
					
				}
				else{
					Zones.add(AndDBM.andDBM(copyZ1, com));//如果一个补集与copyZ1相交不为空，将交的结果加入zones
					ArrayList<DBM_element[][]> com_com=Complement_1.complement(com);//求这个补集的补集
					copyZ1=AndDBM.andDBM(copyZ1, com_com.get(0));//被补集切剩下的部分是补集的补集与copyZ1的叫					
				}
			
			}
		}
		return Zones;
	}

}
