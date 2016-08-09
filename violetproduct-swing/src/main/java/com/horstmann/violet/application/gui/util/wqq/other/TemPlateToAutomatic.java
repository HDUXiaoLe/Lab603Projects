package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class TemPlateToAutomatic {
	
	public static Automatic temPlateToAutomatic(UppaalTemPlate template){
		Automatic automatic=new Automatic();
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//automatic中的转换集合
		ArrayList<State> StateSet = new ArrayList<State>();//automatic中的状态集合

		
		ArrayList<String> Clocks=template.getClocks();//获取时间自动机中的时钟集合
		
		ArrayList<UppaalLocation> locations=template.getLocations();//获取时间自动机中的所有状态
		for(UppaalLocation loc:locations){//遍历所有状态
			if(loc.getInvariant()!=null){
				ArrayList<String> invariant=loc.getInvariant();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式转成DBM矩阵
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				StateSet.add(state);
			}
			else{
				ArrayList<String> invariant=new ArrayList<String>();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式为空，则DBM矩阵为全集
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				StateSet.add(state);
			}
			
		}
		automatic.setStateSet(StateSet);//设定automatic中的状态集合
		
		ArrayList<UppaalTransition> transitions=template.getTransitions();//获取template中的所有转换
		for(UppaalTransition tran:transitions){//遍历转换集合
			if(tran.getConstraint()!=null){
				ArrayList<String> constraint=tran.getConstraint();//获取转换中的约束
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, constraint));//将转换中的约束转成DBM矩阵
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
				transition.setEventSet(tran.getEvents());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				
				//临时添加
				//ArrayList<DBM_element[][]> com_constraint=Complement.complement(constraint, Clocks);
				//transition.setCom_constraint(com_constraint);
				
				TransitionSet.add(transition);
			}
			else{
				ArrayList<String> constraint=new ArrayList<String>();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, constraint));//转换中的约束为空，则DBM矩阵为全集
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
				transition.setEventSet(tran.getEvents());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				TransitionSet.add(transition);
			}
			
		}
		automatic.setTransitionSet(TransitionSet);//设定automatic中的转换集合
		
		
		
		ArrayList<String> ClockSet=template.getClocks();
		automatic.setClockSet(ClockSet);//设定autotimatic中的时钟集合
		
		//设定automatic中的初始状态
		State initstate=new State();
		//initstate.setFinalState(template.getInitState().isFinalState());
		initstate.setName(template.getInitState().getName());
		if(template.getInitState().getInvariant()!=null){
			initstate.setInvariantDBM(DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, template.getInitState().getInvariant())));
			initstate.setPosition(template.getInitState().getName());
		}
		else{
			ArrayList<String> invariant=new ArrayList<String>();
			DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式转成DBM矩阵
			initstate.setInvariantDBM(DBM);
			initstate.setPosition(template.getInitState().getName());
		}
		automatic.setInitState(initstate);
		//设定automatic的name
		String name=template.getName();
		automatic.setName(name);
		
		return automatic;
	}
}
