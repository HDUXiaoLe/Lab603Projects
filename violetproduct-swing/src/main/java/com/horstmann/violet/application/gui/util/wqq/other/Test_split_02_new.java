package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;
/**
 * 验证l00有没有被正确拆分
 * @author Seryna
 *
 */
public class Test_split_02_new {
	public static void main(String[] args) {
		ArrayList<State> newStates=newState();
		ArrayList<State> getPs=getPs();
	}
	
	
	/**
	 * 拆分l00，返回l00被拆分后的状态集
	 * @return
	 */
	public static ArrayList<State> newState(){
		Automatic automatic=Test_split_01_new.getAutomatic();
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<String> ClockSet=automatic.getClockSet();
		
		ArrayList<State> StateSet=Test_split_01_new.getPs();//这里的状态集合是l0被拆分后的新的状态集合
		/*for(State s:StateSet){
			System.out.println("状态name: "+s.getName());
			System.out.println("状态position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
			DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=add_clock_DBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		State l00=StateSet.get(3);
		
		ArrayList<State> newStates=SplitSuseSs_new1.splitSuseSs(l00, StateSet, TransitionSet, ClockSet);//l00被拆分成新的状态集合
		
		/*System.out.println(newStates.size());
		for(State s:newStates){
			System.out.println("状态name: "+s.getName());
			System.out.println("状态position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
			DBM_element[][] add_clock_DBM=s.getAddClockRelationDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=add_clock_DBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		ArrayList<State> Ps=StateSet;//查看拆分过后，状态集合有没有改变（不变式不应该改变，但后继们的时钟复位后的约束应该改变）
		//System.out.println(Ps.size());
		/*for(State s:Ps){
			System.out.println("name: "+s.getName());
			System.out.println("position: "+s.getPosition());
			DBM_element[][] state=s.getInvariantDBM();
			DBM_element[][] add_clock_state=s.getAddClockRelationDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=add_clock_state[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		return newStates;
	}
	
	/**
	 * l00没有被拆分，Ps不变
	 * @return
	 */
	public static ArrayList<State> getPs(){
		Automatic automatic=Test_split_01_new.getAutomatic();
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<String> ClockSet=automatic.getClockSet();
		
		ArrayList<State> StateSet=Test_split_01_new.getPs();//这里的状态集合是l0被拆分后的新的状态集合
		State l00=StateSet.get(3);
		
		ArrayList<State> newStates=SplitSuseSs_new1.splitSuseSs(l00, StateSet, TransitionSet, ClockSet);//l00被拆分成新的状态集合
		
		
		ArrayList<State> Ps=new ArrayList<State>();
		
		for(State s:StateSet){//复制StateSet，以防StateSet被改变
			State ss=new State();
			ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
			ss.setInvariantDBM(s.getInvariantDBM());
			ss.setName(s.getName());
			ss.setPosition(s.getPosition());
			Ps.add(ss);
		}
		
		//System.out.println(Ps.size());
		/*for(State s:Ps){
			System.out.println("name: "+s.getName());
			System.out.println("position: "+s.getPosition());
			DBM_element[][] state=s.getInvariantDBM();
			DBM_element[][] add_clock_state=s.getAddClockRelationDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=add_clock_state[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		return Ps;
	}
}
