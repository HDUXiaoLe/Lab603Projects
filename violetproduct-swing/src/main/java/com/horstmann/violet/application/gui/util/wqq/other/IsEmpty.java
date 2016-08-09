package com.horstmann.violet.application.gui.util.wqq.other;

public class IsEmpty {
	/**
	 * 判断一个DBM_element[][]是否为空，如果为空返回1，否则返回-1
	 * @param DBM
	 * @return
	 */
	public static int isEmpty(DBM_element[][] DBM){
		DBM_element[][] fDBM=Floyds.floyds(DBM);//将DBM规范化后再判断是否为空
		int len=DBM.length;
		for(int i=0;i<len;i++){
			if(fDBM[i][i].getValue()!=0||fDBM[i][i].isStrictness()!=true){
				return 1;
			}
		}
		return -1;
	}
	
}
