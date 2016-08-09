package com.horstmann.violet.application.gui.util.wqq.other;

public class AndDBM {
	/**
	 * 返回两个DBM相交的结果
	 * @param DBM1
	 * @param DBM2
	 * @return
	 */
	public static DBM_element[][] andDBM(DBM_element[][] DBM1,DBM_element[][] DBM2){
		int n=DBM1.length;
		DBM_element[][] newDBM = new DBM_element[n][n];
		for(int i=0;i<n;i++){//初始化newDBM
			for(int j=0;j<n;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(DBM1[i][j].getValue());
				ele.setStrictness(DBM1[i][j].isStrictness());
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				newDBM[i][j]=ele;	
			}
		}
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(Floyds.compareTo(DBM2[i][j], DBM1[i][j])==1){	
					DBM_element ele=new DBM_element();
					ele.setValue(DBM2[i][j].getValue());
					ele.setStrictness(DBM2[i][j].isStrictness());
					ele.setDBM_i(i);
					ele.setDBM_j(j);
					newDBM[i][j]=ele;
					
					if(DBM1[i][j].getValue()==DBM2[i][j].getValue()&&DBM1[i][j].isStrictness()!=DBM2[i][j].isStrictness()){
						newDBM[i][j].setStrictness(false);
					}
				}
			}
		}
		/*if(IsEmpty.isEmpty(DBM1)==-1){
			return Floyds.floyds(DBM1);			
		}
		else return null;*/
		return newDBM;
	}
}
