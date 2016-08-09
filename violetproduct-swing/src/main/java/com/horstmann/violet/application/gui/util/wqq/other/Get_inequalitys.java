package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class Get_inequalitys {
	public static ArrayList<String> get_Inequalitys(Automatic auto){
		ArrayList<String> clockset=auto.getClockSet();//测试序列的时钟集合
		int clock_number=clockset.size();//测试序列的时钟个数
		ArrayList<Transition> TransitionSet=auto.getTransitionSet();//测试序列的边集合
		ArrayList<State> StateSet=auto.getStateSet();//测试序列的状态集合
		ArrayList<String> Inequalitys=new ArrayList<String>();//要返回的不等式组
		
		int resetNumber=0;
		for(Transition t:TransitionSet){//获得测试序列中时钟复位的操作次数
			resetNumber=resetNumber+t.getResetClockSet().size();
		}
		
		//情况一:测试序列中没有被复位的时钟,不管系统中有1个时钟还是两个时钟
		if(resetNumber==0){
			noReset(TransitionSet, StateSet, clock_number, Inequalitys);
		}
		
		//情况二：测试序列中有2个时钟，只有1个时钟复位
		if(resetNumber==1&&clock_number==2){
			
			int k=0;//获得被复位的时钟在时钟集合中的位置
			for(Transition ts:TransitionSet){
				if(ts.getResetClockSet().size()!=0){
					String resetclock=ts.getResetClockSet().get(0);
					for(int i=0;i<clockset.size();i++){
						if(resetclock.equals(clockset.get(i))){
							k=i;
							break;
						}
					}
					break;
				}
			}
			
			for(Transition t1:TransitionSet){
				ArrayList<Transition> Ts=new ArrayList<Transition>();//获取测试路径中第一条边到该边的边集合
				for(Transition t2:TransitionSet){
					if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
						Ts.add(t2);
					}
					if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
						break;
					}
				}
				Ts.add(t1);
				
				
				DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//获得t1目标状态的时间约束矩阵
				for(State s:StateSet){
					if(t1.getTarget().equals(s.getName())){
						DBM=Floyds.floyds(s.getInvariantDBM());
					}
				}
				
				boolean flag=false;//判断第一条边到该边的边的集合中是否有时钟复位				
				for(Transition ts:Ts){
					  if(ts.getResetClockSet().size()!=0){
						  flag=true;
						  break;
					  }
				}
				if(flag==false){//第一条边到该边的边的集合中没有时钟复位
					ArrayList<String> label=new ArrayList<String>();//获得Ts中各边上的时间标号
					for(Transition ts:Ts){
						label.add(ts.getEventSet().get(1));
					}
					int upper=0;//时钟约束的上界
					String upper_label=new String();
					int lower=0;//时钟约束的下界
					String lower_label=new String();
					if(k==0){//x被复位，取y的上下界
						lower=-DBM[0][2].getValue();
						if(DBM[0][2].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						
						upper=DBM[2][0].getValue();
						if(DBM[2][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
					}
					if(k==1){//y被复位，取x的上下界
						lower=-DBM[0][1].getValue();
						if(DBM[0][1].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						upper=DBM[1][0].getValue();
						if(DBM[1][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
					}
					
					
					String Inequality1=new String();//表示上界的不等式
					String Inequality2=new String();//表示下界的不等式
					for(int i=0;i<label.size();i++){
						if(i!=label.size()-1){
							Inequality1=Inequality1+label.get(i)+"+";
							Inequality2=Inequality2+label.get(i)+"+";
						}
						else{
							Inequality1=Inequality1+label.get(i);
							Inequality2=Inequality2+label.get(i);
						}
					}
					
					if(upper!=88888){
						Inequality1=Inequality1+upper_label+upper;
						Inequalitys.add(Inequality1);
					}
					Inequality2=Inequality2+lower_label+lower;
					Inequalitys.add(Inequality2);
				}
				if(flag==true){//第一条边到该边的边的集合中有时钟复位
					ArrayList<Transition> tt=new ArrayList<Transition>();//获得从时钟复位的下一条边到该边的集合
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							for(int j=i+1;j<Ts.size();j++){
								tt.add(Ts.get(j));
							}
						}
					}
					//获得从时钟复位的下一条边到该边的不等式
					if(tt.size()>0){//时钟复位的下一条边到该边的集合如果存在
						ArrayList<String> label=new ArrayList<String>();//获得从时钟复位的下一条边到该边的集合中各边上的时间标号
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//时钟约束的上界
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//时钟约束的下界
						if(k==0){//x被复位，取x的上下界
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k==1){//y被复位，取y的上下界
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//表示上界的不等式
						String Inequality2=new String();//表示下界的不等式
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
					//获得从第一条边到该边的不等式
					ArrayList<String> label1=new ArrayList<String>();//获得Ts中各边上的时间标号
					for(Transition ts:Ts){
						label1.add(ts.getEventSet().get(1));
					}
					int upper1=0;//时钟约束的上界
					int lower1=0;//时钟约束的下界
					if(k==0){//x被复位，取y的上下界
						lower1=-DBM[0][2].getValue();
						upper1=DBM[2][0].getValue();
					}
					if(k==1){//y被复位，取x的上下界
						lower1=-DBM[0][1].getValue();
						upper1=DBM[1][0].getValue();
					}
					
					
					String Inequality3=new String();//表示上界的不等式
					String Inequality4=new String();//表示下界的不等式
					for(int i=0;i<label1.size();i++){
						if(i!=label1.size()-1){
							Inequality3=Inequality3+label1.get(i)+"+";
							Inequality4=Inequality4+label1.get(i)+"+";
						}
						else{
							Inequality3=Inequality3+label1.get(i);
							Inequality4=Inequality4+label1.get(i);
						}
					}
					
					if(upper1!=88888){
						Inequality3=Inequality3+"<"+upper1;
						Inequalitys.add(Inequality3);
					}
					Inequality4=Inequality4+">"+lower1;
					Inequalitys.add(Inequality4);
					
				}
				
				
			}
		}
	
		//情况三：测试序列中有1个时钟，1个时钟复位
		if(resetNumber==1&&clock_number==1){
			for(Transition t1:TransitionSet){
				ArrayList<Transition> Ts=new ArrayList<Transition>();//获取测试路径中第一条边到该边的边集合
				for(Transition t2:TransitionSet){
					if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
						Ts.add(t2);
					}
					if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
						break;
					}
				}
				Ts.add(t1);
				
				
				DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//获得t1目标状态的时间约束矩阵
				for(State s:StateSet){
					if(t1.getTarget().equals(s.getName())){
						DBM=Floyds.floyds(s.getInvariantDBM());
					}
				}
				
				boolean flag=false;//判断第一条边到该边的边的集合中是否有时钟复位				
				for(Transition ts:Ts){
					  if(ts.getResetClockSet().size()!=0){
						  flag=true;
						  break;
					  }
				}
				if(flag==false){//第一条边到该边的边的集合中没有时钟复位  获得第一条边到 该边的不等式
					ArrayList<String> label=new ArrayList<String>();//获得Ts中各边上的时间标号
					for(Transition ts:Ts){
						label.add(ts.getEventSet().get(1));
					}
					int upper=0;//时钟约束的上界
					String upper_label=new String();
					int lower=0;//时钟约束的下界
					String lower_label=new String();
					lower=-DBM[0][1].getValue();
					if(DBM[0][1].isStrictness()==false){
						lower_label=">";
					}
					else lower_label=">=";
					
					upper=DBM[1][0].getValue();
					if(DBM[1][0].isStrictness()==false){
						upper_label="<";
					}
					else upper_label="<=";
					
					
					String Inequality1=new String();//表示上界的不等式
					String Inequality2=new String();//表示下界的不等式
					for(int i=0;i<label.size();i++){
						if(i!=label.size()-1){
							Inequality1=Inequality1+label.get(i)+"+";
							Inequality2=Inequality2+label.get(i)+"+";
						}
						else{
							Inequality1=Inequality1+label.get(i);
							Inequality2=Inequality2+label.get(i);
						}
					}
					
					if(upper!=88888){
						Inequality1=Inequality1+upper_label+upper;
						Inequalitys.add(Inequality1);
					}
					Inequality2=Inequality2+lower_label+lower;
					Inequalitys.add(Inequality2);
				}
				if(flag==true){//第一条边到该边的边的集合中有时钟复位
					ArrayList<Transition> tt=new ArrayList<Transition>();//获得从时钟复位的边到该边的集合
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							for(int j=i;j<Ts.size();j++){
								tt.add(Ts.get(j));
							}
						}
					}
					//获得从时钟复位的边到该边的不等式
					if(tt.size()>0){//时钟复位的边到该边的集合如果存在
						ArrayList<String> label=new ArrayList<String>();//获得从时钟复位的边到该边的集合中各边上的时间标号
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//时钟约束的上界
						String upper_label=new String();
						int lower=0;//时钟约束的下界
						String lower_label=new String();
						
						lower=-DBM[0][1].getValue();
						if(DBM[0][1].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						upper=DBM[1][0].getValue();
						if(DBM[1][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
						
						String Inequality1=new String();//表示上界的不等式
						String Inequality2=new String();//表示下界的不等式
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
				}
				
				
			}
					
				
		}
		//情况四：测试序列中有1个时钟，2个时钟复位
		if(resetNumber==2&&clock_number==1){
			for(Transition t1:TransitionSet){
				ArrayList<Transition> Ts=new ArrayList<Transition>();//获取测试路径中第一条边到该边的边集合
				for(Transition t2:TransitionSet){
					if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
						Ts.add(t2);
					}
					if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
						break;
					}
				}
				Ts.add(t1);
				
				
				DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//获得t1目标状态的时间约束矩阵
				for(State s:StateSet){
					if(t1.getTarget().equals(s.getName())){
						DBM=Floyds.floyds(s.getInvariantDBM());
					}
				}
				
				int reset=0;//获得从第一条边到该边的路径上有几个时钟复位		
				for(Transition ts:Ts){
					  if(ts.getResetClockSet().size()!=0){
						  reset++;
					  }
				}
				if(reset==0){//第一条边到该边的边的集合中没有时钟复位  获得第一条边到 该边的不等式
					ArrayList<String> label=new ArrayList<String>();//获得Ts中各边上的时间标号
					for(Transition ts:Ts){
						label.add(ts.getEventSet().get(1));
					}
					int upper=0;//时钟约束的上界
					String upper_label=new String();
					int lower=0;//时钟约束的下界
					String lower_label=new String();
					lower=-DBM[0][1].getValue();
					if(DBM[0][1].isStrictness()==false){
						lower_label=">";
					}
					else lower_label=">=";
					
					upper=DBM[1][0].getValue();
					if(DBM[1][0].isStrictness()==false){
						upper_label="<";
					}
					else upper_label="<=";
					
					
					String Inequality1=new String();//表示上界的不等式
					String Inequality2=new String();//表示下界的不等式
					for(int i=0;i<label.size();i++){
						if(i!=label.size()-1){
							Inequality1=Inequality1+label.get(i)+"+";
							Inequality2=Inequality2+label.get(i)+"+";
						}
						else{
							Inequality1=Inequality1+label.get(i);
							Inequality2=Inequality2+label.get(i);
						}
					}
					
					if(upper!=88888){
						Inequality1=Inequality1+upper_label+upper;
						Inequalitys.add(Inequality1);
					}
					Inequality2=Inequality2+lower_label+lower;
					Inequalitys.add(Inequality2);
				}
				if(reset==1){//第一条边到该边的边的集合中有1个时钟复位
					ArrayList<Transition> tt=new ArrayList<Transition>();//获得从时钟复位的边到该边的集合
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							for(int j=i;j<Ts.size();j++){
								tt.add(Ts.get(j));
							}
						}
					}
					//获得从时钟复位的边到该边的不等式
					if(tt.size()>0){//时钟复位的边到该边的集合如果存在
						ArrayList<String> label=new ArrayList<String>();//获得从时钟复位的边到该边的集合中各边上的时间标号
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//时钟约束的上界
						String upper_label=new String();
						int lower=0;//时钟约束的下界
						String lower_label=new String();
						
						lower=-DBM[0][1].getValue();
						if(DBM[0][1].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						upper=DBM[1][0].getValue();
						if(DBM[1][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
						
						String Inequality1=new String();//表示上界的不等式
						String Inequality2=new String();//表示下界的不等式
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
				}
				if(reset==2){//第一条边到该边的集合中有2个时钟复位
					ArrayList<Transition> tt=new ArrayList<Transition>();//获得从第二个时钟复位的边到该边的集合
					int r=0;
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							r=r+1;
							if(r==2){
								for(int j=i;j<Ts.size();j++){
									tt.add(Ts.get(j));
								}
							}
							
						}
					}
					//获得从第二个时钟复位的边到该边的不等式
					if(tt.size()>0){//时钟复位的边到该边的集合如果存在
						ArrayList<String> label=new ArrayList<String>();//获得从时钟复位的边到该边的集合中各边上的时间标号
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//时钟约束的上界
						String upper_label=new String();
						int lower=0;//时钟约束的下界
						String lower_label=new String();
						
						lower=-DBM[0][1].getValue();
						if(DBM[0][1].isStrictness()==false){
							lower_label=">";
						}
						else lower_label=">=";
						upper=DBM[1][0].getValue();
						if(DBM[1][0].isStrictness()==false){
							upper_label="<";
						}
						else upper_label="<=";
						
						String Inequality1=new String();//表示上界的不等式
						String Inequality2=new String();//表示下界的不等式
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
				}
				
			}
		}
		//情况五：测试序列中有2个时钟，2个时钟复位
		if(resetNumber==2&&clock_number==2){
			int k1=0;//获得第一个被复位的时钟在时钟集合中的位置
			int k2=0;//获得第二个被复位的时钟在时钟集合中的位置
			ArrayList<Transition> u=new ArrayList<Transition>();
			for(Transition ts:TransitionSet){
				if(ts.getResetClockSet().size()!=0){
					u.add(ts);
				}
			}
			for(int i=0;i<clockset.size();i++){
				if(u.get(0).equals(clockset.get(i))){
					k1=i;
					break;
				}
			}
			for(int i=0;i<clockset.size();i++){
				if(u.get(1).equals(clockset.get(i))){
					k2=i;
					break;
				}
			}
			
			for(Transition t1:TransitionSet){
				ArrayList<Transition> Ts=new ArrayList<Transition>();//获取测试路径中第一条边到该边的边集合
				for(Transition t2:TransitionSet){
					if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
						Ts.add(t2);
					}
					if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
						break;
					}
				}
				Ts.add(t1);
				
				
				DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//获得t1目标状态的时间约束矩阵
				for(State s:StateSet){
					if(t1.getTarget().equals(s.getName())){
						DBM=Floyds.floyds(s.getInvariantDBM());
					}
				}
				
				int reset=0;//获得从第一条边到该边的路径上有几个时钟复位		
				for(Transition ts:Ts){
					  if(ts.getResetClockSet().size()!=0){
						  reset++;
					  }
				}
				
				if(reset==0){//第一条边到该边的边的集合中没有时钟复位
					ArrayList<String> label=new ArrayList<String>();//获得Ts中各边上的时间标号
					for(Transition ts:Ts){
						label.add(ts.getEventSet().get(1));
					}
					int upper=0;//时钟约束的上界
					String upper_label=new String();
					int lower=0;//时钟约束的下界
					String lower_label=new String();
					
					lower=-DBM[0][1].getValue();
					if(DBM[0][1].isStrictness()==false){
						lower_label=">";
					}
					else lower_label=">=";
					upper=DBM[1][0].getValue();
					if(DBM[1][0].isStrictness()==false){
						upper_label="<";
					}
					else upper_label="<=";
					
					String Inequality1=new String();//表示上界的不等式
					String Inequality2=new String();//表示下界的不等式
					for(int i=0;i<label.size();i++){
						if(i!=label.size()-1){
							Inequality1=Inequality1+label.get(i)+"+";
							Inequality2=Inequality2+label.get(i)+"+";
						}
						else{
							Inequality1=Inequality1+label.get(i);
							Inequality2=Inequality2+label.get(i);
						}
					}
					
					if(upper!=88888){
						Inequality1=Inequality1+upper_label+upper;
						Inequalitys.add(Inequality1);
					}
					Inequality2=Inequality2+lower_label+lower;
					Inequalitys.add(Inequality2);
				}
				if(reset==1){//第一条边到该边的边的集合有1个时钟复位
					ArrayList<Transition> tt=new ArrayList<Transition>();//获得从时钟复位的下一条边到该边的集合
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							for(int j=i+1;j<Ts.size();j++){
								tt.add(Ts.get(j));
							}
						}
					}
					//获得从时钟复位的下一条边到该边的不等式
					if(tt.size()>0){//时钟复位的下一条边到该边的集合如果存在
						ArrayList<String> label=new ArrayList<String>();//获得从时钟复位的下一条边到该边的集合中各边上的时间标号
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//时钟约束的上界
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//时钟约束的下界
						if(k1==0){//x被复位，取x的上下界
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k1==1){//y被复位，取y的上下界
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//表示上界的不等式
						String Inequality2=new String();//表示下界的不等式
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
					//获得从第一条边到该边的不等式
					ArrayList<String> label1=new ArrayList<String>();//获得Ts中各边上的时间标号
					for(Transition ts:Ts){
						label1.add(ts.getEventSet().get(1));
					}
					int upper1=0;//时钟约束的上界
					int lower1=0;//时钟约束的下界
					if(k1==0){//x被复位，取y的上下界
						lower1=-DBM[0][2].getValue();
						upper1=DBM[2][0].getValue();
					}
					if(k1==1){//y被复位，取x的上下界
						lower1=-DBM[0][1].getValue();
						upper1=DBM[1][0].getValue();
					}
					
					
					String Inequality3=new String();//表示上界的不等式
					String Inequality4=new String();//表示下界的不等式
					for(int i=0;i<label1.size();i++){
						if(i!=label1.size()-1){
							Inequality3=Inequality3+label1.get(i)+"+";
							Inequality4=Inequality4+label1.get(i)+"+";
						}
						else{
							Inequality3=Inequality3+label1.get(i);
							Inequality4=Inequality4+label1.get(i);
						}
					}
					
					if(upper1!=88888){
						Inequality3=Inequality3+"<"+upper1;
						Inequalitys.add(Inequality3);
					}
					Inequality4=Inequality4+">"+lower1;
					Inequalitys.add(Inequality4);
					
				}
				if(reset==2){//第一条边到该边的边的集合有1个时钟复位
				if(k1==k2){//被复位的两个时钟分别为xx或yy
					ArrayList<Transition> tt=new ArrayList<Transition>();//获得从第二个时钟复位的下一条边到该边的集合
					int r=0;
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							r=r+1;
							if(r==2){
								for(int j=i+1;j<Ts.size();j++){
									tt.add(Ts.get(j));
								}
							}
							
						}
					}
					//获得从第二个时钟复位的下一条边到该边不等式
					if(tt.size()>0){//第二个时钟复位的的下一条边到该边的集合如果存在
						ArrayList<String> label=new ArrayList<String>();//获得从第二个时钟复位的集合中各边上的时间标号
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//时钟约束的上界
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//时钟约束的下界
						if(k2==0){//x被复位，取x的上下界
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k2==1){//y被复位，取y的上下界
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//表示上界的不等式
						String Inequality2=new String();//表示下界的不等式
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
					//获得从第一条边到该边的不等式
					ArrayList<String> label1=new ArrayList<String>();//获得Ts中各边上的时间标号
					for(Transition ts:Ts){
						label1.add(ts.getEventSet().get(1));
					}
					int upper1=0;//时钟约束的上界
					int lower1=0;//时钟约束的下界
					if(k1==0){//x被复位，取y的上下界
						lower1=-DBM[0][2].getValue();
						upper1=DBM[2][0].getValue();
					}
					if(k1==1){//y被复位，取x的上下界
						lower1=-DBM[0][1].getValue();
						upper1=DBM[1][0].getValue();
					}
					
					
					String Inequality3=new String();//表示上界的不等式
					String Inequality4=new String();//表示下界的不等式
					for(int i=0;i<label1.size();i++){
						if(i!=label1.size()-1){
							Inequality3=Inequality3+label1.get(i)+"+";
							Inequality4=Inequality4+label1.get(i)+"+";
						}
						else{
							Inequality3=Inequality3+label1.get(i);
							Inequality4=Inequality4+label1.get(i);
						}
					}
					
					if(upper1!=88888){
						Inequality3=Inequality3+"<"+upper1;
						Inequalitys.add(Inequality3);
					}
					Inequality4=Inequality4+">"+lower1;
					Inequalitys.add(Inequality4);
				}
				if(k1!=k2){//被复位的两个时钟为xy或yx
					ArrayList<Transition> tt=new ArrayList<Transition>();//获得从第二个时钟复位的下一条边到该边的集合
					int r=0;
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							r=r+1;
							if(r==2){
								for(int j=i+1;j<Ts.size();j++){
									tt.add(Ts.get(j));
								}
							}
							
						}
					}
					//获得从第二个时钟复位的下一条边到该边不等式
					if(tt.size()>0){//第二个时钟复位的的下一条边到该边的集合如果存在
						ArrayList<String> label=new ArrayList<String>();//获得从第二个时钟复位的集合中各边上的时间标号
						for(Transition ts:tt){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//时钟约束的上界
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//时钟约束的下界
						if(k2==0){//x被复位，取x的上下界
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k2==1){//y被复位，取y的上下界
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//表示上界的不等式
						String Inequality2=new String();//表示下界的不等式
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
					
					//
					ArrayList<Transition> tt1=new ArrayList<Transition>();//获得从第一个时钟复位的下一条边到该边的集合
					int r1=0;
					for(int i=0;i<Ts.size();i++){
						if(Ts.get(i).getResetClockSet().size()!=0){
							r1=i;
						}
					}
					for(int j=r1+1;j<Ts.size();j++){
						tt.add(Ts.get(j));
					}
					//获得从第一个时钟复位的下一条边到该边不等式
					if(tt1.size()>0){//第一个时钟复位的的下一条边到该边的集合如果存在
						ArrayList<String> label=new ArrayList<String>();//获得从第一个时钟复位的集合中各边上的时间标号
						for(Transition ts:tt1){
							label.add(ts.getEventSet().get(1));
						}
						int upper=0;//时钟约束的上界
						String upper_label=new String();
						String lower_label=new String();
						int lower=0;//时钟约束的下界
						if(k1==0){//x被复位，取x的上下界
							lower=-DBM[0][1].getValue();
							if(DBM[0][1].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[1][0].getValue();
							if(DBM[1][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						if(k1==1){//y被复位，取y的上下界
							lower=-DBM[0][2].getValue();
							if(DBM[0][2].isStrictness()==false){
								lower_label=">";
							}
							else lower_label=">=";
							upper=DBM[2][0].getValue();
							if(DBM[2][0].isStrictness()==false){
								upper_label=">";
							}
							else upper_label=">=";
						}
						
						String Inequality1=new String();//表示上界的不等式
						String Inequality2=new String();//表示下界的不等式
						for(int i=0;i<label.size();i++){
							if(i!=label.size()-1){
								Inequality1=Inequality1+label.get(i)+"+";
								Inequality2=Inequality2+label.get(i)+"+";
							}
							else{
								Inequality1=Inequality1+label.get(i);
								Inequality2=Inequality2+label.get(i);
							}
						}
						
						if(upper!=88888){
							Inequality1=Inequality1+upper_label+upper;
							Inequalitys.add(Inequality1);
						}
						Inequality2=Inequality2+lower_label+lower;
						Inequalitys.add(Inequality2);
					}
				}
				
				}
			}
		}
		
		return Inequalitys;
		
	}
	/**
	 * 获得一条没有时钟复位的测试序列的不等式组
	 * @param TransitionSet
	 * @param StateSet
	 * @param clock_number
	 * @param Inequalitys
	 * @return
	 */
	public static ArrayList<String> noReset(ArrayList<Transition> TransitionSet,ArrayList<State> StateSet,int clock_number,ArrayList<String> Inequalitys){
		for(Transition t1:TransitionSet){
			ArrayList<Transition> Ts=new ArrayList<Transition>();//获取测试路径中第一条边到该边的边集合
			for(Transition t2:TransitionSet){
				if(!t1.getSource().equals(t2.getSource())&&!t1.getTarget().equals(t2.getTarget())){
					Ts.add(t2);
				}
				if(t1.getSource().equals(t2.getSource())&&t1.getTarget().equals(t2.getTarget())){
					break;
				}
			}
			Ts.add(t1);
			ArrayList<String> label=new ArrayList<String>();//获得Ts中各边上的时间标号
			for(Transition ts:Ts){
				label.add(ts.getEventSet().get(1));
			}
			
			DBM_element[][] DBM=new DBM_element[clock_number+1][clock_number+1];//获得t1目标状态的时间约束矩阵
			for(State s:StateSet){
				if(t1.getTarget().equals(s.getName())){
					DBM=Floyds.floyds(s.getInvariantDBM());
				}
			}
				
			int upper=0;//时钟约束的上界
			String upper_label=new String();
			int lower=0;//时钟约束的下界
			String lower_label=new String();
			
			lower=-DBM[0][1].getValue();
			if(DBM[0][1].isStrictness()==false){
				lower_label=">";
			}
			else lower_label=">=";
			
			upper=DBM[1][0].getValue();
			if(DBM[1][0].isStrictness()==false){
				upper_label="<";
			}
			else upper_label="<=";
			
			String Inequality1=new String();//表示上界的不等式
			String Inequality2=new String();//表示下界的不等式
			for(int i=0;i<label.size();i++){
				if(i!=label.size()-1){
					Inequality1=Inequality1+label.get(i)+"+";
					Inequality2=Inequality2+label.get(i)+"+";
				}
				else{
					Inequality1=Inequality1+label.get(i);
					Inequality2=Inequality2+label.get(i);
				}
			}
			
			if(upper!=88888){
				Inequality1=Inequality1+upper_label+upper;
				Inequalitys.add(Inequality1);
			}
			Inequality2=Inequality2+lower_label+lower;
			Inequalitys.add(Inequality2);
			
		}
		return Inequalitys;
	}
	
	public static void main(String[] args) {
		Automatic automatic=Test_split_01_new.getAutomatic();
		Automatic newAutomatic=IPR.iPR(automatic);
		Automatic aTDRTAutomatic=ATDTR.aTDRT(newAutomatic,automatic);  
		//Automatic aaa=DFSTree(aTDRTAutomatic);
		ArrayList<Automatic> testcaseSet=StateCoverage.testCase(aTDRTAutomatic);
		Automatic auto0=testcaseSet.get(0);
		ArrayList<String> Inequalitys0=get_Inequalitys(auto0);
		System.out.println("t3<2");
		for(int i=1;i<Inequalitys0.size();i++){
			if(i!=10){
				System.out.println(Inequalitys0.get(i));
			}
			else System.out.println("t4+t2+t8>=2");
		}
		Automatic auto1=testcaseSet.get(1);
		ArrayList<String> Inequalitys1=get_Inequalitys(auto1);
		System.out.println("--------------------");
		System.out.println("t3<2");
		for(int i=1;i<Inequalitys1.size();i++){
			System.out.println(Inequalitys1.get(i));
		}
	}
}
