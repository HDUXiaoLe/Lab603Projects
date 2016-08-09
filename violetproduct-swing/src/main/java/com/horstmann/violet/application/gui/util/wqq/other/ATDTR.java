package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ATDTR {
	public static void main(String[] args) {
		Automatic automatic=Test_split_01_new.getAutomatic();
		Automatic newAutomatic=IPR.iPR(automatic);
		Automatic aTDRTAutomatic=aTDRT(newAutomatic,automatic);  
	}
	
	public static Automatic aTDRT(Automatic auto,Automatic original_auto) {
		//去除抽象时间延迟迁移
		ArrayList<Transition> auto_Transition=auto.getTransitionSet();//获得时间自动机迁移集合
		
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//保存约简后的迁移集合
		
		for(Transition t:auto_Transition){//遍历时间自动机迁移集合，装入TransitionSet中
			Transition tran=new Transition();
			tran.setSource(t.getSource());
			tran.setTarget(t.getTarget());
			tran.setEventSet(t.getEventSet());
			TransitionSet.add(tran);
		}
		
		ArrayList<Transition> ET=new ArrayList<Transition>();//获取边为抽象时间迁移的迁移集合
		for(Transition tran:TransitionSet){
			if(tran.getEventSet().get(0).equals("*")){
				ET.add(tran);
			}
		}	
		//System.out.println(ET.size());
		
		while(ET.size()>0){
			ListIterator<Transition> ET_iterator = ET.listIterator();
			while(ET_iterator.hasNext()){
				Transition X = ET_iterator.next();
				ET_iterator.remove();
				//System.out.println(ET.size());
				
				ArrayList<Transition> TT=new ArrayList<Transition>();//获得以X为目的为源的迁移集合
				for(Transition t:TransitionSet){
					if(t.getSource().equals(X.getTarget())){
						TT.add(t);
					}
				}
				
				for(Transition tt:TT){//遍历TT，判断新边是否存在于迁移集合中
					int flag=1;
					for(Transition tran:TransitionSet){
						if(X.getSource().equals(tran.getSource())&&tt.getTarget().equals(tran.getTarget())&&tt.getEventSet().get(0).equals(tran.getEventSet().get(0))){
							flag=0;
							break;
						}
						
					}
					if(flag==1){//如果新边不存在迁移集合中，则加入迁移集合
						Transition tran=new Transition();
						tran.setSource(X.getSource());
						tran.setTarget(tt.getTarget());
						ArrayList<String> events=new ArrayList<String>();
						events.add(tt.getEventSet().get(0));
						tran.setEventSet(events);
						
						TransitionSet.add(tran);//将新边加入迁移集合
						if(tran.getEventSet().get(0).equals("*")){//如果新边是含抽象时间迁移的边，则加入ET	
							ET_iterator.add(tran);
						}//将含有*的新边加入ET	
						
					}
				}
							
				Iterator<Transition> t_iterator = TransitionSet.iterator();//从迁移集合中移除X
				while(t_iterator.hasNext()){
					Transition tran = t_iterator.next();
					if(tran.equals(X)){					
						t_iterator.remove();
					}
				}
			}			
		}
		/*System.out.println("剩余边数： "+TransitionSet.size());
		for(Transition t:TransitionSet){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println("-------------");
		}	*/	
		
		
		
		//去除不可达状态和相关迁移
		ArrayList<State> auto_StateSet=auto.getStateSet();//获得时间自动机状态集合
		ArrayList<State> StateSet=new ArrayList<State>();//保存约简后的状态集合
		
		for(State s:auto_StateSet){//遍历时间自动机的状态集合，装入StateSet
			State state=new State();
			state.setName(s.getName());
			state.setPosition(s.getPosition());
			state.setInvariantDBM(s.getInvariantDBM());
			StateSet.add(state);
		}
		State intstate=auto.getInitState();//获得时间自动机的初始状态
		ArrayList<State> SV=new ArrayList<State>();
		for(State state:StateSet){//获得不可达状态
			int i=1;
			for(Transition tran:TransitionSet){
				if(tran.getTarget().equals(state.getName())){
					i=0;
					break;
				}
			}
			if(i==1&&!state.getName().equals(intstate.getName())){
				SV.add(state);
			}
		}
		for(State state:SV){//遍历不可达状态，删除不可达状态及其相关的迁移
			StateSet.remove(state);	
			 ListIterator<Transition> iterator = TransitionSet.listIterator();
		        while(iterator.hasNext()){
		        	Transition tran = iterator.next();
		            if(tran.getSource().equals(state.getName())){
		            	iterator.remove();  
		            }
		            
		        }			
		}
		
		/*System.out.println("剩余边数： "+TransitionSet.size());
		for(Transition t:TransitionSet){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println("-------------");
		}*/
		
		//每条迁移上加一个时间变量ti
		int k=1;
		for(Transition tran:TransitionSet){
			ArrayList<String> events=tran.getEventSet();
			String e="t"+k;
			events.add(e);
			tran.setEventSet(events);
			k++;
		}
		/*System.out.println("剩余边数： "+TransitionSet.size());
		for(Transition t:TransitionSet){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println(t.getEventSet().get(1));
			System.out.println("-------------");
		}*/
		/*System.out.println("剩余状态数： "+StateSet.size());
		for(State s:StateSet){
			System.out.println(s.getName());
			System.out.println(s.getPosition());
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=s.getInvariantDBM()[i][j];
					
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("-------------");
		}*/
		Automatic atstr=new Automatic();
		atstr.setClockSet(auto.getClockSet());
		atstr.setName("ATDTR");
		atstr.setStateSet(StateSet);
		atstr.setTransitionSet(TransitionSet);
		for(State s:StateSet){
			if(Minimization.includeZero(s.getInvariantDBM())==1){
				atstr.setInitState(s);//设置时间自动机的初始状态
			}
		}
		
		
		ArrayList<Transition> original_transet=original_auto.getTransitionSet();//将原时间自动机中的时钟复位操作添加到拆分后得到的图的相应的边上
		for(Transition t:atstr.getTransitionSet()){
			String source_position=new String();
			String target_position=new String();
			for(State s:atstr.getStateSet()){
				if(t.getSource().equals(s.getName())){
					source_position=s.getPosition();
				}
				if(t.getTarget().equals(s.getName())){
					target_position=s.getPosition();
				}
			}
			
			for(Transition original_t:original_transet){
				if(original_t.getSource().equals(source_position)&&original_t.getTarget().equals(target_position)){
					ArrayList<String> ResetClockSet=new ArrayList<String>();
					if(original_t.getResetClockSet()!=null){
						ResetClockSet=original_t.getResetClockSet();
					}
					t.setResetClockSet(ResetClockSet);
				}
			}
		}
		
		/*System.out.println("剩余边数： "+atstr.getTransitionSet().size());
		for(Transition t:atstr.getTransitionSet()){
			System.out.println(t.getSource());
			System.out.println(t.getTarget());
			System.out.println(t.getEventSet().get(0));
			System.out.println(t.getEventSet().get(1));
			if(t.getResetClockSet().size()!=0){
				System.out.println(t.getResetClockSet().get(0));
			}
			System.out.println("-------------");
		}*/
		return atstr;
	}
}
