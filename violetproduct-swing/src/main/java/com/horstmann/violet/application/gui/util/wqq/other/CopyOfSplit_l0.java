package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class CopyOfSplit_l0 {
	public static void main(String[] args) {
		Automatic automatic=AutomaticIBean.automic();
		ArrayList<String> ClockSet=automatic.getClockSet();
		State initstate=automatic.getInitState();
		//如果状态中的不变式不为空
		//if(initstate.getInvariantDBM()!=null){//对l0进行拆分
			DBM_element[][] initDBM=initstate.getInvariantDBM();
			int len=initDBM.length;
			
			for(Transition t:automatic.getTransitionSet()){//遍历转换
				if(t.getSource().equals(initstate.getName())){//获取从l0发出的边的DBM	
					DBM_element[][] andDBM=null;
					DBM_element[][] fandDBM=null;
					DBM_element[][] andcomDBM=null;
					DBM_element[][] fandcomDBM=null;
					if(t.getConstraintDBM()!=null){//如果转换上有时钟约束
						DBM_element[][] tranDBM=t.getConstraintDBM();
						andDBM=AndDBM.andDBM(initDBM, tranDBM);//与边上的约束相交
						fandDBM=Floyds.floyds(andDBM);//将交的结果正规化
									
						/*ArrayList<DBM_element[][]> com_constraint=t.getCom_constraint();
						System.out.println(com_constraint.size());
						for(DBM_element[][] com:com_constraint){
							andcomDBM=AndDBM.andDBM(initDBM, com);//与边上的约束的补集相交
							fandcomDBM=Floyds.floyds(andcomDBM);//将交的结果正规化
												
						}*/					
					}
					else{//如果转换上约束为空
						andDBM=initDBM;
						fandDBM=Floyds.floyds(andDBM);					
					}
					
					//处理时钟复位
					ArrayList<String> ResetClockSet=t.getResetClockSet();//获得转换上复位的时钟
					if(ResetClockSet!=null){
						DBM_element[][] resetDBM = null;	
						for(String clock:ResetClockSet){
							resetDBM=Reset.reset(andDBM, ClockSet, clock);//如果边上的时钟复位集合不为空，则对相应时钟复位
						}
						//获得时间复位后的时钟间的约束，将其存成一个DBM矩阵，空的位置填（无穷，<）,将这个矩阵与这条转换的目的状态的约束相交，就可将时钟复位导致的时钟约束加入状态中
						DBM_element [][] clockConstraint= new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
						for(int i=0;i<ClockSet.size()+1;i++){//初始化clockConstraint
							for(int j=0;j<ClockSet.size()+1;j++){
								DBM_element ele=new DBM_element();
								ele.setDBM_i(i);
								ele.setDBM_j(j);
								ele.setValue(88888);
								ele.setStrictness(false);
								clockConstraint[i][j]=ele;
							}
						}
						
						for(int i=1;i<ClockSet.size()+1;i++){//提取resetDBM中的时钟约束
							for(int j=1;j<ClockSet.size()+1;j++){
								if(i!=j){
									clockConstraint[i][j]=resetDBM[i][j];
								}
							}
						}							
					}
					
				}
			}
		//}
		//如果状态中的不变式为空
		/*else{
			for(Transition t:automatic.getTransitionSet()){//遍历转换
				if(t.getSource().equals(initstate.getName())){//获取从l0发出的边的DBM	
					DBM_element[][] andDBM=null;
					DBM_element[][] fandDBM=null;
					DBM_element[][] andcomDBM=null;
					DBM_element[][] fandcomDBM=null;
					if(t.getConstraintDBM()!=null){//如果转换上有时钟约束
						DBM_element[][] tranDBM=t.getConstraintDBM();
						andDBM=AndDBM.andDBM(initDBM, tranDBM);//与边上的约束相交
						fandDBM=Floyds.floyds(andDBM);//将交的结果正规化
									
						ArrayList<DBM_element[][]> com_constraint=t.getCom_constraint();
						System.out.println(com_constraint.size());
						for(DBM_element[][] com:com_constraint){
							andcomDBM=AndDBM.andDBM(initDBM, com);//与边上的约束的补集相交
							fandcomDBM=Floyds.floyds(andcomDBM);//将交的结果正规化
												
						}					
					}
					else{//如果转换上约束为空
						andDBM=initDBM;
						fandDBM=Floyds.floyds(andDBM);					
					}
					
					//处理时钟复位
					ArrayList<String> ResetClockSet=t.getResetClockSet();//获得转换上复位的时钟
					if(ResetClockSet!=null){
						DBM_element[][] resetDBM = null;	
						for(String clock:ResetClockSet){
							resetDBM=Reset.reset(andDBM, ClockSet, clock);//如果边上的时钟复位集合不为空，则对相应时钟复位
						}
						//获得时间复位后的时钟间的约束，将其存成一个DBM矩阵，空的位置填（无穷，<）,将这个矩阵与这条转换的目的状态的约束相交，就可将时钟复位导致的时钟约束加入状态中
						DBM_element [][] clockConstraint= new DBM_element[ClockSet.size()+1][ClockSet.size()+1];
						for(int i=0;i<ClockSet.size()+1;i++){//初始化clockConstraint
							for(int j=0;j<ClockSet.size()+1;j++){
								DBM_element ele=new DBM_element();
								ele.setDBM_i(i);
								ele.setDBM_j(j);
								ele.setValue(88888);
								ele.setStrictness(false);
								clockConstraint[i][j]=ele;
							}
						}
						
						for(int i=1;i<ClockSet.size()+1;i++){//提取resetDBM中的时钟约束
							for(int j=1;j<ClockSet.size()+1;j++){
								if(i!=j){
									clockConstraint[i][j]=resetDBM[i][j];
								}
							}
						}							
					}
					
				}
			}
		}*/
	}
}

/*for(int i=0;i<len;i++){
								for(int j=0;j<len;j++){
									DBM_element cons=fandcomDBM[i][j];
									System.out.println("DBM_i:"+cons.getDBM_i());
									System.out.println("DBM_j:"+cons.getDBM_j());
									System.out.println("value:"+cons.getValue());
									System.out.println("Strictness:"+cons.isStrictness());
									System.out.println("***********");
													
								}
							}*/