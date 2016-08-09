package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class SplitZuseZ_2 {
	/**
	 * 用矩阵Z2拆分矩阵Z1，Z1和Z2都没有规范化(判断两个矩阵相交是否为空是，要用两个矩阵的规范化进行相交)
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
		
		
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(copyZ1), Floyds.floyds(Z2)))==1){//如果Z1∩Z2为空，则返回Z1，因为Z1不用被Z2拆分(判断两个矩阵相交是否为空是，要用两个矩阵的规范化进行相交)
			Zones.add(Z1);
		}
		
		else{//如果Z1∩Z2不为空
			Zones.add(AndDBM.andDBM(Z1, Z2));//将Z1∩Z2加入Zones
			ArrayList<DBM_element[][]> complements=Complement_2.complement(Z2);//求Z2的补集
			for(DBM_element[][] com:complements){//遍历Z2的补集
				if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(copyZ1), Floyds.floyds(com)))==1){//如果一个补集与Z1相交为空，则什么都不用做
					
				}
				else{
					Zones.add(AndDBM.andDBM(copyZ1, com));//如果一个补集与copyZ1相交不为空，将交的结果加入zones
					ArrayList<DBM_element[][]> com_com=Complement_2.complement(com);//求这个补集的补集
					copyZ1=AndDBM.andDBM(copyZ1, com_com.get(0));//被补集切剩下的部分是补集的补集与copyZ1的叫					
				}
			
			}
		}
		return Zones;
	}

}
