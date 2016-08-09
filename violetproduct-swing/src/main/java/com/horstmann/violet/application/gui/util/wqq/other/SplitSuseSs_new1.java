package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class SplitSuseSs_new1 {
	public static ArrayList<State> splitSuseSs(State x,ArrayList<State> Ps,ArrayList<Transition> trans,ArrayList<String> ClockSet) {
		ArrayList<State> States=new ArrayList<State>();
		String x_position=x.getPosition();//获取x的标志位
		String x_name=x.getName();//获取状态x的名称
		DBM_element x_DBM[][] =x.getAddClockRelationDBM();//获取状态x加了时钟复位信息的时钟约束
		
		ArrayList<State> posts=PostAndPre.post(x, Ps, trans, ClockSet);//获取x的后继集合
		if(posts.size()!=0){
			updatePosts(x, posts, trans, ClockSet);//将x的后继们加入时钟复位信息
			
			ArrayList<DBM_element[][]> Zs=getZs(x, posts, trans, ClockSet);//根据后继，获取拆分x的Zs
			ArrayList<DBM_element[][]> X_DBMs=SplitZuseZs_2.splitZuseZs(x_DBM, Zs);
			
			int size=X_DBMs.size();
			if(size==1){//x没有被拆分
				States.add(x);
				return States;
			}
			else{//x被拆分为多个状态
				for(int i=0;i<size;i++){
					State s=new State();
					s.setPosition(x_position);
					s.setInvariantDBM(X_DBMs.get(i));//状态的不变式为X_DBMs.get(i)
					s.setAddClockRelationDBM(X_DBMs.get(i));//状态的添加了时钟复位信息不变式也为X_DBMs.get(i)
					s.setName(x_name+i);//状态的名称为原名称加i
					States.add(s);
				}
				return States;
			}
		}
		else{
			States.add(x);
			return States;
		}
		
	}
	
	/**
	 * 将x的后继posts加入时钟复位信息
	 * @param x
	 * @param posts
	 * @param trans
	 * @param ClockSet
	 */
	public static void updatePosts(State x,ArrayList<State> posts,ArrayList<Transition> trans,ArrayList<String> ClockSet) {
		String x_position=x.getPosition();//获取x的标志位
		String x_name=x.getName();//获取状态x的名称
		DBM_element x_DBM[][] =x.getAddClockRelationDBM();//获取状态x添加了时钟复位的时钟约束
		
		for(State p:posts){
			String p_position=p.getPosition();//获取p的标志位
			String p_name=p.getName();//获取状态p的名称
			DBM_element p_DBM[][] =p.getInvariantDBM();//获取状态p的不变式
			
			for(Transition t:trans){
				DBM_element t_DBM[][]=t.getConstraintDBM();//获取迁移t上的时间约束
				String clock=null;
				if(t.getResetClockSet()!=null){
					clock=t.getResetClockSet().get(0);//获取迁移t上的时钟复位信息
				}		
				if(t.getSource().equals(x_name)&&t.getTarget().equals(p_name)){//存在x-->p
					if(clock!=null){//迁移上有时钟复位信息
						DBM_element[][] updateclock=updateClok(x_DBM, t_DBM, p_DBM, ClockSet, clock);
						p.setAddClockRelationDBM(updateclock);
					}
					else{//迁移上没有时钟复位信息
						DBM_element[][] updateclock=updateClok_1(x_DBM, p_DBM);
						p.setAddClockRelationDBM(updateclock);
					}
					
				}
				else{//不存在x-->p
					if(t.getSource().equals(x_position)&&t.getTarget().equals(p_position)){//存在x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							DBM_element[][] updateclock=updateClok_1(x_DBM, p_DBM);
							p.setAddClockRelationDBM(updateclock);
						}
						else{//x.position!=p.position
							if(clock!=null){//迁移上有时钟复位信息
								DBM_element[][] updateclock=updateClok(x_DBM, t_DBM, p_DBM, ClockSet, clock);
								p.setAddClockRelationDBM(updateclock);
							}
							else{//迁移上没有时钟复位信息
								DBM_element[][] updateclock=updateClok_1(x_DBM, p_DBM);
								p.setAddClockRelationDBM(updateclock);
							}	
						}
					}
					else{//不存在x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							DBM_element[][] updateclock=updateClok_1(x_DBM, p_DBM);
							p.setAddClockRelationDBM(updateclock);
						}
					}
				}
			}		
		}
		
	}
	
	/**
	 * 求a(Z∩z),提取它的时钟间关系，将它的时钟间关系加入Zone
	 * @param Z
	 * @param z
	 * @param Zone
	 * @param ClockSet
	 * @param clock
	 * @return
	 */
	public static DBM_element[][] updateClok(DBM_element[][] Z,DBM_element[][] z,DBM_element[][] Zone,ArrayList<String> ClockSet,String clock) {
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(Z), Floyds.floyds(z)))==-1){//Z∩z不为空
			DBM_element[][]and=AndDBM.andDBM(Z, z);
			DBM_element[][] reset_and=Reset_1.reset(and, ClockSet, clock);
			if(reset_and!=null){//时钟复位后不为空
				DBM_element[][] exe_reset_and=ExtractReset.extract(reset_and);
				if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(exe_reset_and), Floyds.floyds(Zone)))==-1){//提取的时钟复位信息∩Zone不为空
					return AndDBM.andDBM(Zone, exe_reset_and);
				}
				else return Zone;
			}
			else return Zone;
		}
		else return Zone;	
	}
	

	/**
	 * 提取Z的时钟间关系，将它的时钟间关系加入Zone
	 * @param Z
	 * @param Zone
	 * @return
	 */
	public static DBM_element[][] updateClok_1(DBM_element[][] Z,DBM_element[][] Zone) {
		DBM_element[][] exe_Z=ExtractReset.extract(Z);
		if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(exe_Z), Floyds.floyds(Zone)))==-1){
			return AndDBM.andDBM(Zone, exe_Z);
		}
		else return Zone;
	}
	
	
	/**
	 * 求z∩a-1(Zone),包含a为空的情况
	 * @param z 边上的时钟约束
	 * @param Zone 状态中的不变式
	 * @param Clocks
	 * @param clock 边上的复位时钟
	 * @return
	 */
	public static DBM_element[][] difference(DBM_element[][] z,DBM_element[][] Zone,ArrayList<String> Clocks,String clock) {
		if(clock==null){//边上没有时钟复位
			if(IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(z), Floyds.floyds(Zone)))==-1){
				DBM_element[][] and=AndDBM.andDBM(z, Zone);
				return and;
			}
			else return null;
		}
		else{//边上有时钟复位
			DBM_element[][] befor=BeforeReset.beforeReset(Zone, Clocks, clock);
			if(befor!=null){
				if((IsEmpty.isEmpty(AndDBM.andDBM(Floyds.floyds(z), Floyds.floyds(befor))))==-1){
					DBM_element[][] and=AndDBM.andDBM(z, befor);
					return and;
				}
				return null;
			}
			else return null;
		}
	}
	
	
	public static ArrayList<DBM_element[][]> getZs(State x,ArrayList<State> posts,ArrayList<Transition> trans,ArrayList<String> ClockSet){
		ArrayList<DBM_element[][]> Zs=new ArrayList<DBM_element[][]>();
		
		String x_position=x.getPosition();//获取x的标志位
		String x_name=x.getName();//获取状态x的名称
		DBM_element x_DBM[][] =x.getAddClockRelationDBM();//获取状态x添加了时钟约束的时钟约束
		
		for(State p:posts){
			String p_position=p.getPosition();//获取p的标志位
			String p_name=p.getName();//获取状态p的名称
			DBM_element p_DBM[][] =p.getAddClockRelationDBM();//获取状态p的添加了时钟复位信息的时钟约束
			
			for(Transition t:trans){
				DBM_element t_DBM[][]=t.getConstraintDBM();//获取迁移t上的时间约束
				String clock=null;
				if(t.getResetClockSet()!=null){
					clock=t.getResetClockSet().get(0);//获取迁移t上的时钟复位信息
				}
				if(t.getSource().equals(x_name)&&t.getTarget().equals(p_name)){//存在x-->p
					DBM_element[][] zi=difference(t_DBM, p_DBM, ClockSet, clock);//求t_DBM ∩ a-1(p_DBM)
					if(IsEmpty.isEmpty(zi)==-1){
						Zs.add(zi);
					}
					
				}
				else{//不存在x-->p
					if(t.getSource().equals(x_position)&&t.getTarget().equals(p_position)){//存在x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							DBM_element[][] zi=Godirectly_2.goDirectly(x_DBM, p_DBM);//求x_DBM 向上算子 p_DBM
							if(IsEmpty.isEmpty(zi)==-1){
								Zs.add(zi);
							}
						}
						else{//x.position!=p.position
							DBM_element[][] zi=difference(t_DBM, p_DBM, ClockSet, clock);//求t_DBM ∩ a-1(p_DBM)
							if(IsEmpty.isEmpty(zi)==-1){
								Zs.add(zi);
							}
						}
					}
					else{//不存在x.position-->p.position
						if(x_position.equals(p_position)){//x.position==p.position
							DBM_element[][] zi=Godirectly_2.goDirectly(x_DBM, p_DBM);//求x_DBM 向上算子 p_DBM
							if(IsEmpty.isEmpty(zi)==-1){
								Zs.add(zi);
								break;
							}
						}
					}
				}
			}		
		}
		return Zs;
	}
		
}
 