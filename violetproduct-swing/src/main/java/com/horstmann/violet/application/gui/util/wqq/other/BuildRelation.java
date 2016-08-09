package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class BuildRelation {
	public static void main(String[] args) {
		Automatic automatic=Test_split_01_new.getAutomatic();
		ArrayList<Transition> transitions=bulidRelation(automatic);
		System.out.println("边的数量："+transitions.size());
		for(Transition t:transitions){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println("********");
		}
	}
	
	public static ArrayList<Transition> bulidRelation(Automatic automatic) {
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();//获得时间自动机迁移集合
		ArrayList<String> ClockSet=automatic.getClockSet();//获得时间自动机时钟集合

		ArrayList<State> new_stateSet=Minimization.minimization(automatic);//拆分时间自动机
		
		ArrayList<Transition> transitions=new ArrayList<Transition>();//要返回的迁移集合
		
		for(State source:new_stateSet){//遍历状态集合			
			ArrayList<State> posts=PostAndPre.post(source, new_stateSet, TransitionSet, ClockSet);//获得source后继
			for(State target:posts){//遍历后继集合，建立迁移
				Transition tran=new Transition();
				tran.setSource(source.getName());
				tran.setTarget(target.getName());
				
				if(source.getPosition().equals(target.getPosition())){//如果源和目的相同，则迁移上为*
					ArrayList<String> events=new ArrayList<String>();
					events.add("*");
					tran.setEventSet(events);
				}
				else{//如果源和目的不同，则迁移上为相应事件
					for(Transition t:TransitionSet){
						if(source.getPosition().equals(t.getSource())&&target.getPosition().equals(t.getTarget())){
							tran.setEventSet(t.getEventSet());
						}						
					}
					
				}						
				transitions.add(tran);
			}
		}
		return transitions;
	}
}
