package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class StringToDBM_element {
	public static ArrayList<DBM_element> stringToDBM_element(ArrayList<String> ClockSet,ArrayList<String> constraintANDinvariant){
		ArrayList<DBM_element> constraints=new ArrayList<DBM_element>();//一个ArrayList<DBM_element>对应一个DBM
		for(String cons:constraintANDinvariant){
			int len=cons.length();
			
			if(len==3){
				String operator=cons.substring(1, 2);//获得约束中运算符
				String clock=cons.substring(0, 1);//获得约束中的时钟
				
				DBM_element constraint =new DBM_element();
				//x<2
				if(operator.equals("<")){
					int value=Integer.parseInt(cons.substring(2, 3));
					constraint.setValue(value);
					constraint.setStrictness(false);		
					for(int i=0;i<ClockSet.size();i++){
						if(clock.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
							constraint.setDBM_j(0);
						}
					}	
					constraints.add(constraint);
				}
				//x>2
				if(operator.equals(">")){
					int value=Integer.parseInt(cons.substring(2, 3));
					constraint.setValue(-value);
					constraint.setStrictness(false);		
					for(int i=0;i<ClockSet.size();i++){
						if(clock.equals(ClockSet.get(i))){
							constraint.setDBM_i(0);
							constraint.setDBM_j(i+1);
						}
					}	
					constraints.add(constraint);
				}
			}
			
			if(len==4){
				String operator=cons.substring(1, 3);//获得约束中运算符 <=,>=
				String clock=cons.substring(0, 1);//获得约束中的时钟
				
				DBM_element constraint =new DBM_element();
				//x<=2 x<=y
				if(operator.equals("<=")){
					if(isNumeric(cons.substring(3, 4))==1){
						int value=Integer.parseInt(cons.substring(3, 4));
						constraint.setValue(value);
						constraint.setStrictness(true);		
						for(int i=0;i<ClockSet.size();i++){
							if(clock.equals(ClockSet.get(i))){
								constraint.setDBM_i(i+1);
								constraint.setDBM_j(0);
							}
						}
					}
					else{
						String var=cons.substring(3, 4);
						constraint.setValue(0);
						constraint.setStrictness(true);		
						for(int i=0;i<ClockSet.size();i++){
							if(clock.equals(ClockSet.get(i))){
								constraint.setDBM_i(i+1);
							}
						}
						for(int j=0;j<ClockSet.size();j++){
							if(var.equals(ClockSet.get(j))){
								constraint.setDBM_j(j+1);
							}
						}
					}
					
					constraints.add(constraint);
				}
				//x>=2 x>=y
				if(operator.equals(">=")){
					if(isNumeric(cons.substring(3, 4))==1){
						int value=Integer.parseInt(cons.substring(3, 4));
						constraint.setValue(-value);
						constraint.setStrictness(true);		
						for(int i=0;i<ClockSet.size();i++){
							if(clock.equals(ClockSet.get(i))){
								constraint.setDBM_i(0);
								constraint.setDBM_j(i+1);
							}
						}
					}
					else{
						String var=cons.substring(3, 4);
						constraint.setValue(0);
						constraint.setStrictness(true);		
						for(int j=0;j<ClockSet.size();j++){
							if(clock.equals(ClockSet.get(j))){
								constraint.setDBM_j(j+1);
							}
						}
						for(int i=0;i<ClockSet.size();i++){
							if(var.equals(ClockSet.get(i))){
								constraint.setDBM_i(i+1);
							}
						}
					}
						
					constraints.add(constraint);
				}
			}
			
			//x-y<6
			if(len==5){
				String operator=cons.substring(3, 4);//获得约束中运算符 <,>
				
				DBM_element constraint =new DBM_element();
				//x-y<6
				if(operator.equals("<")){
					int value=Integer.parseInt(cons.substring(4, 5));
					constraint.setValue(value);
					constraint.setStrictness(false);	
					String clock1=cons.substring(0, 1);//获取第一个时钟
					//System.out.println("clock1:"+clock1);
					String clock2=cons.substring(2, 3);//获取第二个时钟
					//System.out.println("clock2:"+clock2);
					
					for(int i=0;i<ClockSet.size();i++){
						if(clock1.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
						}
						if(clock2.equals(ClockSet.get(i))){
							constraint.setDBM_j(i+1);
						}
					}	
					constraints.add(constraint);
				}
				//x-y>6
				if(operator.equals(">")){
					int value=Integer.parseInt(cons.substring(4, 5));
					constraint.setValue(-value);
					constraint.setStrictness(false);	
					String clock1=cons.substring(0, 1);//获取第一个时钟
					//System.out.println("clock1:"+clock1);
					String clock2=cons.substring(2, 3);//获取第二个时钟
					//System.out.println("clock2:"+clock2);
					
					for(int i=0;i<ClockSet.size();i++){
						if(clock2.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
						}
						if(clock1.equals(ClockSet.get(i))){
							constraint.setDBM_j(i+1);
						}
					}	
					constraints.add(constraint);
				}
			}
			
			//x-y<=1
			if(len==6){
				String operator=cons.substring(3, 5);//获得约束中运算符 <,>
				
				DBM_element constraint =new DBM_element();
				//x-y<=1
				if(operator.equals("<=")){
					int value=Integer.parseInt(cons.substring(5, 6));
					constraint.setValue(value);
					constraint.setStrictness(true);	
					String clock1=cons.substring(0, 1);//获取第一个时钟
					//System.out.println("clock1:"+clock1);
					String clock2=cons.substring(2, 3);//获取第二个时钟
					//System.out.println("clock2:"+clock2);
					
					for(int i=0;i<ClockSet.size();i++){
						if(clock1.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
						}
						if(clock2.equals(ClockSet.get(i))){
							constraint.setDBM_j(i+1);
						}
					}
					constraints.add(constraint);
				}
				//x-y>=1
				if(operator.equals(">=")){
					int value=Integer.parseInt(cons.substring(5, 6));
					constraint.setValue(-value);
					constraint.setStrictness(true);	
					String clock1=cons.substring(0, 1);//获取第一个时钟
					//System.out.println("clock1:"+clock1);
					String clock2=cons.substring(2, 3);//获取第二个时钟
					//System.out.println("clock2:"+clock2);
					
					for(int i=0;i<ClockSet.size();i++){
						if(clock2.equals(ClockSet.get(i))){
							constraint.setDBM_i(i+1);
						}
						if(clock1.equals(ClockSet.get(i))){
							constraint.setDBM_j(i+1);
						}
					}
					constraints.add(constraint);
				}
			}
		}
		return constraints;
	}
	
	/**
	 * 判断一个字符是否为整数，如果是，返回1，不是整数返回0
	 * @param str
	 * @return
	 */
	public static int isNumeric(String str){  
		   for(int i=str.length();--i>=0;){  
		      int chr=str.charAt(i);  
		      if(chr<48 || chr>57)  
		         return 0;  
		   }  
		   return 1;  
		} 
	public static void main(String[] args) {
		System.out.println(isNumeric("3"));
	}
}
