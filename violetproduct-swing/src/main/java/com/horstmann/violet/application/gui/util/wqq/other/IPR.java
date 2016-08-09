package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class IPR {
	public static void main(String[] args) {
		Automatic automatic=Test_split_01_new.getAutomatic();
		Automatic newAutomatic=iPR(automatic);
	}
	/*
	 运用最小化算法构造一个新的时间自动机
	 */
	public static Automatic iPR(Automatic automatic) {
		ArrayList<State> new_stateSet=Minimization.minimization(automatic);
		//System.out.println("拆分后的状态总数： "+new_stateSet.size());
		/*for(State s:new_stateSet){
			System.out.println("状态name: "+s.getName());
			System.out.println("状态position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
			DBM_element[][] fDBM=Floyds.floyds(DBM);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=fDBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		
		ArrayList<Transition> transitions=BuildRelation.bulidRelation(automatic);
		/*System.out.println("边的数量："+transitions.size());
		for(Transition t:transitions){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println("********");
		}
		*/
		Automatic newaotu=new Automatic();//构建一个新的时间自动机
		State intiState=new State();
		for(State s:new_stateSet){//判断哪个是初始状态
			int h=Minimization.includeZero(s.getInvariantDBM());
			if(h==1){
				intiState.setName(s.getName());
				intiState.setPosition(s.getPosition());
				intiState.setInvariantDBM(s.getInvariantDBM());
				//System.out.println(intiState.getName());
				//System.out.println(intiState.getPosition());
			}
		}
		newaotu.setClockSet(automatic.getClockSet());
		newaotu.setInitState(intiState);
		newaotu.setStateSet(new_stateSet);
		newaotu.setTransitionSet(transitions);
		newaotu.setName("G");
		 
		return newaotu;
	}
}
