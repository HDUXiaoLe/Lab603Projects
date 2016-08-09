package com.horstmann.violet.application.gui.util.wqq.other;
import java.util.ArrayList;
public class Minimization {
	public static void main(String[] args) {
		Automatic automatic=Test_split_01_new.getAutomatic();
		ArrayList<State> new_stateSet=minimization(automatic);
		System.out.println("拆分后的状态总数： "+new_stateSet.size());
		for(State s:new_stateSet){
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
		}
		
	}
	/**
	 * 最小化算法，返回一个稳定的状态集
	 * @param automatic
	 * @return
	 */
	public static ArrayList<State> minimization(Automatic automatic) {
		State Init_State=automatic.getInitState();//获得时间自动机初始状态
		ArrayList<State> StateSet=automatic.getStateSet();//获得时间自动机状态集合
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();//获得时间自动机迁移集合
		ArrayList<String> ClockSet=automatic.getClockSet();//获得时间自动机时钟集合
		
		ArrayList<State> P=new ArrayList<State>();//P复制时间自动机的状态集合，防止时间自动机的状态集合被改变
		for(State s:StateSet){
			State ss=new State();
			ss.setName(s.getName());
			ss.setPosition(s.getPosition());
			ss.setInvariantDBM(s.getInvariantDBM());
			ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
			P.add(ss);
		}
		
		State s0=new State();//s0为第一个被拆分的状态，复制时间自动机的初始状态，防止时间自动机的初始状态被改变
		s0.setName(Init_State.getName());
		s0.setPosition(Init_State.getPosition());
		s0.setInvariantDBM(Init_State.getInvariantDBM());
		s0.setAddClockRelationDBM(Init_State.getAddClockRelationDBM());
		
		ArrayList<State> accessible=new ArrayList<State>();//可达的状态集合
		accessible.add(s0);
		
		ArrayList<State> stable=new ArrayList<State>();//稳定的状态集合
		
		while(accessible.size()!=stable.size()){//当可达集合和稳定集合不相同时
			State x=accessibleNostable(accessible, stable);//选取可达集合中第一个不在稳定集合的状态进行拆分
			ArrayList<State> new_X=SplitSuseSs_new1.splitSuseSs(x, P, TransitionSet, ClockSet);//new_x为x被拆分后的状态集合
			//x拆分后的状态集合
			/*System.out.println("new_X size: "+new_X.size());
			for(State s:new_X){
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
			if(new_X.size()==1){//如果x没有被拆分
				
				State x_copy=new State();
				x_copy.setName(x.getName());
				x_copy.setPosition(x.getPosition());
				x_copy.setInvariantDBM(x.getInvariantDBM());
				x_copy.setAddClockRelationDBM(x.getAddClockRelationDBM());
				stable.add(x_copy);//将x加入稳定集(稳定集中肯定没有x)
				
				ArrayList<State> posts=PostAndPre.post(x, P, TransitionSet, ClockSet);//获取x的后继
				//输出x的后继
				/*System.out.println("posts size: "+posts.size());
				for(State s:posts){
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
				ArrayList<State> diff=posts_differ_access(accessible, posts);//获得后继集中与可达集不同的状态集合
				for(State s:diff){//将后继加入可达集
					State ss=new State();
					ss.setName(s.getName());
					ss.setPosition(s.getPosition());
					ss.setInvariantDBM(s.getInvariantDBM());
					ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
					accessible.add(ss);
				}
			}
			else{//x被拆分
				for(int i=0;i<accessible.size();i++){//可达集中删除x
					if(accessible.get(i).getName().equals(x.getName())){
						accessible.remove(i);
					}
				}
				for(State s:new_X){//如果拆分得到的状态中包含零点，则加入可达集
					DBM_element[][] zone=s.getInvariantDBM();
					if(includeZero(zone)==1){
						State ss=new State();
						ss.setName(s.getName());
						ss.setPosition(s.getPosition());
						ss.setInvariantDBM(s.getInvariantDBM());
						ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
						accessible.add(ss);
					}
				}
				ArrayList<State> pres=PostAndPre.pre(x, P, TransitionSet, ClockSet);//获得x的前驱集合
				//输出x的前驱
			/*	System.out.println("pres size: "+pres.size());
				for(State s:pres){
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
				for(int i=0;i<stable.size();i++){//稳定集中删除x的前驱集合（稳定集一定包含前驱状态）
					for(int j=0;j<pres.size();j++){
						if(stable.get(i).getName().equals(pres.get(j).getName())){
							stable.remove(i);
						}
					}
				}
				for(int i=0;i<P.size();i++){//状态集中删除x
					if(P.get(i).getName().equals(x.getName())){
						P.remove(i);
					}
				}
				for(State s:new_X){//状态集中加入拆分得到的新状态
					State ss=new State();
					ss.setName(s.getName());
					ss.setPosition(s.getPosition());
					ss.setInvariantDBM(s.getInvariantDBM());
					ss.setAddClockRelationDBM(s.getAddClockRelationDBM());
					P.add(s);
				}
			}
			//输出x
			//System.out.println("x name: "+x.getName());
			/*System.out.println("x position: "+x.getName());
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					DBM_element cons=x.getInvariantDBM()[i][j];
					//System.out.println("DBM_i:"+cons.getDBM_i());
					//System.out.println("DBM_j:"+cons.getDBM_j());
					System.out.println("value:"+cons.getValue());
					System.out.println("Strictness:"+cons.isStrictness());									
				}
			}*/
		
			
			//输出可达集
			/*System.out.println("accessible size: "+accessible.size());
			for(State s:accessible){
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
			//输出稳定集
			//System.out.println("stable size: "+stable.size());
			/*for(State s:stable){
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
			//输出P
			/*System.out.println("P size: "+P.size());
			for(State s:P){
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
		}
		
		return stable;
	}
	
	/**
	 * 返回在可达集中而不在稳定集中的第一个状态 ，返回的是一个新的状态
	 * @param accessible
	 * @param stable
	 * @return
	 */
	public static State accessibleNostable(ArrayList<State> accessible,ArrayList<State> stable) {
		int accessible_len=accessible.size();
		int Stable_len=stable.size();
		State s=new State();
		for(int i=0;i<accessible_len;i++){
			int flag=1;
			for(int j=0;j<Stable_len;j++){
				if(accessible.get(i).getName().equals(stable.get(j).getName())){
					flag=0;
					break;}	
			}
			if(flag==1){
				s=accessible.get(i);
				break;
			}
		}
		
		State accNostable=new State();
		accNostable.setName(s.getName());
		accNostable.setPosition(s.getPosition());
		accNostable.setInvariantDBM(s.getInvariantDBM());
		accNostable.setAddClockRelationDBM(s.getAddClockRelationDBM());
		
		return accNostable;
	}
	
	/**
	 * zone包含零点返回1，不包含零点返回0
	 * @param zone
	 * @return
	 */
	public static int includeZero(DBM_element[][] zone){
		int len=zone.length;
		DBM_element[][] zero=new DBM_element[len][len];
		for(int i=0;i<len;i++){
			for(int j=0;j<len;j++){
				DBM_element ele=new DBM_element();
				ele.setValue(0);
				ele.setStrictness(true);
				ele.setDBM_i(i);
				ele.setDBM_j(j);
				zero[i][j]=ele;	
			}
		}
		
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(zone), Floyds.floyds(zero)))==1){//如果与零点相交为空，则该DBM不包含零点，返回0
			return 0;
		}
		else return 1;//如果与零点相交不为空，则该DBM包含零点，返回1
	}
	
	/**
	 * 返回后继集中与可达集不同的状态
	 * @param accessible
	 * @param posts
	 * @return
	 */
	public static ArrayList<State> posts_differ_access(ArrayList<State> accessible,ArrayList<State> posts) {
		ArrayList<State> diff=new ArrayList<State>();//保存后继集中与可达集不同的状态
		
		for(int i=0;i<posts.size();i++){
			int flag=1;
			for(int j=0;j<accessible.size();j++){
				if(posts.get(i).getName().equals(accessible.get(j).getName())){
					flag=0;
					break;
				}
			}
			if(flag==1){
				State state=new State();
				state.setName(posts.get(i).getName());
				state.setPosition(posts.get(i).getPosition());
				state.setInvariantDBM(posts.get(i).getInvariantDBM());
				state.setAddClockRelationDBM(posts.get(i).getAddClockRelationDBM());
				diff.add(state);
			}
		}
		return diff;
	}
}
