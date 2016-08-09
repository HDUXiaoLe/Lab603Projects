package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;
/**
 * 将UppaalTemPlate时间自动机转换成Automatic时间自动机
 * @author Seryna
 *
 */
public class AutomaticIBean {
	public static void main(String[] args) {
		Automatic a=automic();
		/*ArrayList<String> ClockSet=a.getClockSet();
		for(String s:ClockSet){
			System.out.println(s);
		}*/
		//System.out.println(a.getName());
		State InitState=a.getInitState();
		/*System.out.println(InitState.getName());
		System.out.println(InitState.getPosition());
		DBM_element[][] invariantDBM=InitState.getInvariantDBM();
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				DBM_element cons=invariantDBM[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());
				System.out.println("***********");
								
			}
		}*/
		
		
		
		ArrayList<State> StateSet=a.getStateSet();
		/*System.out.println(StateSet.size());
		for(State s:StateSet){
			System.out.println("name: "+s.getName());
			System.out.println("position: "+s.getPosition());
			DBM_element[][] state=s.getInvariantDBM();
			DBM_element[][] fstate=Floyds.floyds(state);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=fstate[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
		ArrayList<Transition> TransitionSet=a.getTransitionSet();
		/*for(Transition tran:TransitionSet){
			System.out.println("源："+tran.getSource());
			System.out.println("目的："+tran.getTarget());
			ArrayList<String> ResetClockSet=tran.getResetClockSet();
			if(ResetClockSet!=null){
				for(String c:ResetClockSet){
					System.out.println(c);
				}
			}
			DBM_element[][] constraint=tran.getConstraintDBM();
			DBM_element[][] fconstraint=Floyds.floyds(constraint);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=fconstraint[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
		
	}
	
	public static Automatic automic(){
		UppaalTemPlate template=new UppaalTemPlate();
		ArrayList<UppaalTransition> transitions=new ArrayList<UppaalTransition>();
		ArrayList<UppaalLocation> locations=new ArrayList<UppaalLocation>();
		UppaalLocation InitState=new UppaalLocation();
		String name="第一个时间自动机";
		ArrayList<String> Clocks=new ArrayList<String>();
		Clocks.add("x");
		Clocks.add("y");

		ArrayList<String> ar0 =new ArrayList<String>();
		ar0.add("x<2");
		ar0.add("x<=y");
		ar0.add("y<=x");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=2");
		
		UppaalLocation l0=new UppaalLocation();
		l0.setName("l0");
		l0.setInvariant(ar0);
		UppaalLocation l1=new UppaalLocation();
		l1.setName("l1");
		l1.setInvariant(ar1);
		UppaalLocation l2=new UppaalLocation();
		l2.setName("l2");
		UppaalLocation l3=new UppaalLocation();
		l3.setName("l3");
			
		locations.add(l0);
		locations.add(l1);
		locations.add(l2);
		locations.add(l3);
		
		UppaalTransition e0 =new UppaalTransition();
		e0.setSource(l0.getName());
		e0.setTarget(l1.getName());
		ArrayList<String> reset0 =new ArrayList<String>();
		reset0.add("y");
		e0.setResetClocks(reset0);
		ArrayList<String>constraint0 =new ArrayList<String>();
		constraint0.add("x>1");
		e0.setConstraint(constraint0);
		
		
		UppaalTransition e1=new UppaalTransition();
		e1.setSource(l1.getName());
		e1.setTarget(l3.getName());
		ArrayList<String> constraint1 =new ArrayList<String>();
		constraint1.add("y>1");
		constraint1.add("x<=3");
		e1.setConstraint(constraint1);
		
		UppaalTransition e2 =new UppaalTransition();
		e2.setSource(l3.getName());
		e2.setTarget(l1.getName());
		ArrayList<String> constraint2 =new ArrayList<String>();
		constraint2.add("y<2");
		e2.setConstraint(constraint2);
		
		UppaalTransition e3 =new UppaalTransition();
		e3.setSource(l1.getName());
		e3.setTarget(l2.getName());
		ArrayList<String> constraint3 =new ArrayList<String>();
		constraint3.add("y<=2");
		constraint3.add("y>=2");
		e3.setConstraint(constraint3);
		
		UppaalTransition e4 =new UppaalTransition();
		e4.setSource(l2.getName());
		e4.setTarget(l3.getName());
		ArrayList<String> constraint4 =new ArrayList<String>();
		constraint4.add("x<=3");
		e4.setConstraint(constraint4);
		
		
		transitions.add(e0);
		transitions.add(e1);
		transitions.add(e2);
		transitions.add(e3);
		transitions.add(e4);
		
		template.setTransitions(transitions);
		template.setLocations(locations);
		template.setClockSet(Clocks);
		template.setInitState(l0);
		template.setName(name);
		
		InitState=l0;
		template.setInitState(InitState);
	
		
		/*System.out.println("&&&&&&&&&&&&&&&&&&&");
		ArrayList<State> StateSet = new ArrayList<State>();//automatic中的状态集合
		for(UppaalLocation loc:locations){//遍历所有状态
			if(loc.getInvariant()!=null){
				ArrayList<String> invariant=loc.getInvariant();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式转成DBM矩阵
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				state.setFinalState(loc.isFinalState());
				StateSet.add(state);
			}
			else{
				State state=new State();
				state.setName(loc.getName());
				state.setFinalState(loc.isFinalState());
				StateSet.add(state);
			}
			
		}
		*/

		/*ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//automatic中的转换集合
		for(UppaalTransition tran:template.getTransitions()){//遍历转换集合
			if(tran.getConstraint()!=null){
				ArrayList<String> constraint=tran.getConstraint();//获取转换中的约束
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, constraint));//将转换中的约束转成DBM矩阵
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
				transition.setEventSet(tran.getEvents());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				TransitionSet.add(transition);	
			}
			else{
				Transition transition=new Transition();
				transition.setEventSet(tran.getEvents());
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				TransitionSet.add(transition);
			}
		}*/
	
		Automatic automatic=TemPlateToAutomatic.temPlateToAutomatic(template);
		
		return automatic;
	
	
		
	}
}
