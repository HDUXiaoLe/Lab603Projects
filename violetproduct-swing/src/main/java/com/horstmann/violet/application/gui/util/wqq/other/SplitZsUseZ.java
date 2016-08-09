package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class SplitZsUseZ {
	/**
	 * 用Z拆分{Z1,Z2....}
	 * @param Zs
	 * @param Z
	 * @return 返回一个矩阵集合，返回的矩阵集合是没有规范化的
	 */
	public static ArrayList<DBM_element[][]> splitZsUseZ(ArrayList<DBM_element[][]> Zs,DBM_element[][] Z) {
		ArrayList<DBM_element[][]> Zones=new ArrayList<DBM_element[][]>();
		
		ArrayList<DBM_element[][]>fZs=new ArrayList<DBM_element[][]>();
		for(DBM_element[][] z:Zs){//先将Zs中的矩阵规范化
			fZs.add(Floyds.floyds(z));
		}
		
		for(DBM_element[][] zone:fZs){
			ArrayList<DBM_element[][]> DBMs=new ArrayList<DBM_element[][]>();
			DBMs=SplitZuseZ.splitZuseZ(zone, Floyds.floyds(Z));//将Z也规范化
			for(DBM_element[][] dbm:DBMs){
				Zones.add(dbm);
			}
			
		}
		
		return Zones;
	}
}
