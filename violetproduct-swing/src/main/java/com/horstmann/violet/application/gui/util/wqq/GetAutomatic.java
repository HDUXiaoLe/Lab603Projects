package com.horstmann.violet.application.gui.util.wqq;

import java.io.File;
import java.util.ArrayList;

import com.horstmann.violet.application.gui.util.wqq.other.Automatic;
import com.horstmann.violet.application.gui.util.wqq.other.DBM_element;
import com.horstmann.violet.application.gui.util.wqq.other.DBM_elementToDBM;
import com.horstmann.violet.application.gui.util.wqq.other.State;
import com.horstmann.violet.application.gui.util.wqq.other.StringToDBM_element;
import com.horstmann.violet.application.gui.util.wqq.other.UppaalLocation;
import com.horstmann.violet.application.gui.util.wqq.other.UppaalTemPlate;
import com.horstmann.violet.application.gui.util.wqq.other.UppaalTransition;
import com.horstmann.violet.application.gui.util.wqq.other.Transition;;

public class GetAutomatic {
	/*
	 * 返回张建要的数据结构
	 */
	public static Automatic TransUppaal(String filename) {				
		return GetAutomatic.getAutomatic(filename);
	}
	/**
	 * 输入：xml文件名
	 * 输出：Automatic实例（一个时间自动机）
	 * @param xml
	 * @return
	 */
	public static Automatic getAutomatic(String xml) {
		XML2UppaalUtil util = new XML2UppaalUtil(new File(xml));
		UppaalTemPlate temPlate = util.getTemplates().get(0);
		
		Automatic automatic=new Automatic();
		ArrayList<Transition> TransitionSet=new ArrayList<Transition>();//automatic中的转换集合
		ArrayList<State> StateSet = new ArrayList<State>();//automatic中的状态集合

		
		ArrayList<String> Clocks=temPlate.getClocks();//获取时间自动机中的时钟集合
		
		ArrayList<UppaalLocation> locations=temPlate.getLocations();//获取时间自动机中的所有状态
	
		for(UppaalLocation loc:locations){//遍历所有状态
		
			if(loc.getInvariant().size()!=0){
				ArrayList<String> invar=loc.getInvariant();
				ArrayList<String> invariant=new ArrayList<String>();
				for(String i:invar){
					String[] s=i.split("s");
					invariant.add(s[0]);
				}
			
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, invariant));//将状态中的不变式转成DBM矩阵
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				state.setFinalState(loc.isFinalState());
				StateSet.add(state);
			}
			else{
				ArrayList<String> invariant=new ArrayList<String>();
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式为空，则DBM矩阵为全集
				
				State state=new State();
				state.setName(loc.getName());
				state.setInvariantDBM(DBM);
				//state.setFinalState(loc.isFinalState());
				state.setFinalState(loc.isFinalState());
				state.setPosition(loc.getName());
				StateSet.add(state);
			}
			
		}
		automatic.setStateSet(StateSet);//设定automatic中的状态集合
		
		ArrayList<UppaalTransition> transitions=temPlate.getTransitions();//获取template中的所有转换
		for(UppaalTransition tran:transitions){//遍历转换集合
			if(tran.getConstraint().size()!=0){
				ArrayList<String> cons=tran.getConstraint();//获取转换中的约束
				ArrayList<String> constraint=new ArrayList<String>();
				for(String c:cons){
					String[] s=c.split("s");
					constraint.add(s[0]);
				}
				
				DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element__1.stringToDBM_element(Clocks, constraint));//将转换中的约束转成DBM矩阵
				
				Transition transition=new Transition();
				transition.setConstraintDBM(DBM);
						
				ArrayList<String> events=new ArrayList<String>();
				if(tran.getEvents().size()!=0){
					String event=new String();
					
					for(String e:tran.getEvents())
					{
						event+=e+";";
					}
					events.add(event);
				}
				transition.setEventSet(events);
				
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				transition.setTypeIds(tran.getTypeIds());
				transition.setTypes(tran.getTypes());
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
				
				ArrayList<String> events=new ArrayList<String>();
				if(tran.getEvents().size()!=0){
					String event=new String();
					
					for(String e:tran.getEvents())
					{
						event+=e+";";
					}
					events.add(event);
				}
				transition.setEventSet(events);
				
				transition.setResetClockSet(tran.getResetClocks());
				transition.setSource(tran.getSource());
				transition.setTarget(tran.getTarget());
				TransitionSet.add(transition);
				transition.setTypeIds(tran.getTypeIds());
				transition.setTypes(tran.getTypes());
			}
			
		}
		automatic.setTransitionSet(TransitionSet);//设定automatic中的转换集合
		
		
		
		ArrayList<String> ClockSet=temPlate.getClocks();
		automatic.setClockSet(ClockSet);//设定autotimatic中的时钟集合
		
		//设定automatic中的初始状态
		State initstate=new State();
		initstate.setFinalState(temPlate.getInitState().isFinalState());
		initstate.setName(temPlate.getInitState().getName());
		if(temPlate.getInitState().getInvariant()!=null){
			initstate.setInvariantDBM(DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, temPlate.getInitState().getInvariant())));
			initstate.setPosition(temPlate.getInitState().getName());
		}
		else{
			ArrayList<String> invariant=new ArrayList<String>();
			DBM_element[][] DBM=DBM_elementToDBM.buildDBM(Clocks,StringToDBM_element.stringToDBM_element(Clocks, invariant));//将状态中的不变式转成DBM矩阵
			initstate.setInvariantDBM(DBM);
			initstate.setPosition(temPlate.getInitState().getName());
		}
		automatic.setInitState(initstate);
		//设定automatic的name
		String name=temPlate.getName();
		automatic.setName(name);
		
		/*System.out.println("时间自动机名字:"+automatic.getName());
		System.out.println("时间自动机时钟集合：");
		for(String c:automatic.getClockSet()){
			System.out.println(c);
		}
		State iniState=automatic.getInitState();
		System.out.println("初始状态名字："+iniState.getName());
		System.out.println(iniState.getPosition());
		System.out.println(iniState.isFinalState());
		DBM_element[][] DBM=iniState.getInvariantDBM();
		for(int i=0;i<automatic.getClockSet().size()+1;i++){
			for(int j=0;j<automatic.getClockSet().size()+1;j++){
				DBM_element cons=DBM[i][j];
				//System.out.println("DBM_i:"+cons.getDBM_i());
				//System.out.println("DBM_j:"+cons.getDBM_j());
				System.out.println("value:"+cons.getValue());
				System.out.println("Strictness:"+cons.isStrictness());					
			}
		}
		
		System.out.println("状态个数："+automatic.getStateSet().size());
		int k=0;
		for(State state:automatic.getStateSet()){
			System.out.println("第"+k+"个状态");
			k++;
			DBM_element[][] dbm=state.getInvariantDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			System.out.println(state.getName());
			System.out.println(state.getPosition());
			System.out.println(state.isFinalState());
			System.out.println("--------------------");
		}
		System.out.println("迁移个数"+automatic.getTransitionSet().size());
		int p=0;
		for(Transition tran:automatic.getTransitionSet()){
			System.out.println("第"+p+"条迁移");
			p++;
			DBM_element[][] dbm=tran.getConstraintDBM();
			for(int i=0;i<automatic.getClockSet().size()+1;i++){
				for(int j=0;j<automatic.getClockSet().size()+1;j++){
					DBM_element cons=dbm[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());					
				}
			}
			
			System.out.println("源:"+tran.getSource());
			System.out.println("目的："+tran.getTarget());
			
			ArrayList<String> events=tran.getEventSet();
			//System.out.println(events.size());
			for(String e:events){
				System.out.println("事件："+e);
			}
			
			ArrayList<String> reset=tran.getResetClockSet();
			for(String r:reset){
				System.out.println("重置的时钟："+r);
			}
			
			ArrayList<String> typeid=tran.getTypeIds();
			for(String i:typeid){
				System.out.println("typeid:"+i);
			}
			
			ArrayList<String> type=tran.getTypes();
			for(String t:type){
				System.out.println("type:"+t);
			}
			
			
			System.out.println("********************");
		}*/
		
		return automatic;
		
		
	}
}
