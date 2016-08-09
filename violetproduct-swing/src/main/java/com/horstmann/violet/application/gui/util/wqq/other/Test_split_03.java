package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;
/**
 * 验证l00有没有被正确拆分
 * @author Seryna
 *
 */
public class Test_split_03 {
	public static void main(String[] args) {
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
		ArrayList<String> ar00 =new ArrayList<String>();
		ar00.add("x>1");
		ar00.add("x<2");
		ar00.add("x<=y");
		ar00.add("y<=x");
		ArrayList<String> ar01 =new ArrayList<String>();
		ar01.add("x<=1");
		ar01.add("x<=y");
		ar01.add("y<=x");
		ArrayList<String> ar1 =new ArrayList<String>();
		ar1.add("y<=2");
		
		
		UppaalLocation l0=new UppaalLocation();
		l0.setName("l0");
		l0.setInvariant(ar0);
		UppaalLocation l00=new UppaalLocation();
		l00.setName("l00");
		l00.setInvariant(ar00);
		UppaalLocation l01=new UppaalLocation();
		l01.setName("l01");
		l01.setInvariant(ar01);
		UppaalLocation l1=new UppaalLocation();
		l1.setName("l1");
		l1.setInvariant(ar1);
		UppaalLocation l2=new UppaalLocation();
		l2.setName("l2");
		UppaalLocation l3=new UppaalLocation();
		l3.setName("l3");
			
		locations.add(l00);
		locations.add(l01);
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
		
		Automatic automatic=TemPlateToAutomatic.temPlateToAutomatic(template);
		ArrayList<State> StateSet=automatic.getStateSet();
		StateSet.get(0).setPosition("l0");
		StateSet.get(1).setPosition("l0");
		StateSet.get(2).setPosition("l1");
		StateSet.get(3).setPosition("l2");
		StateSet.get(4).setPosition("l3");
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<String> ClockSet=automatic.getClockSet();
		State state_l01=StateSet.get(1);
		
		ArrayList<State> newStates=SplitSuseSs.splitSuseSs(state_l01, StateSet, TransitionSet, ClockSet);//l0被拆分成新的状态集合
		System.out.println(newStates.size());
		for(State s:newStates){
			System.out.println("状态name: "+s.getName());
			System.out.println("状态position: "+s.getPosition());
			DBM_element[][] DBM=s.getInvariantDBM();
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=DBM[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}
		
		ArrayList<State> Ps=automatic.getStateSet();//查看拆分过后，状态集合有没有被改变
		/*System.out.println(Ps.size());
		for(State s:Ps){
			System.out.println("name: "+s.getName());
			System.out.println("position: "+s.getPosition());
			DBM_element[][] state=s.getInvariantDBM();
			DBM_element[][] fstate=Floyds.floyds(state);
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=state[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}
			System.out.println("*******************************");
		}*/
	}
}
