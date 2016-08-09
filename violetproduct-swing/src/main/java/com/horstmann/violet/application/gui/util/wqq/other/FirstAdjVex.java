package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class FirstAdjVex {
	public static void main(String[] args) {
		Automatic a=AutomaticIBean.automic();
		int first=firstAdjVex(a,0);
		System.out.println(first);
	}
	/**
	 * 返回时间自动机中顶点v的第一个邻接顶点，如果v没有邻接点，返回-1。
	 * @param automatic
	 * @param v
	 * @return
	 */
	public static int firstAdjVex(Automatic automatic,int v) {
		ArrayList<State> StateSet=automatic.getStateSet();
		State state=StateSet.get(v);
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<Transition> v_trans=new ArrayList<Transition>();
		for(Transition t:TransitionSet){//构建源为v的边集合
			if (t.getSource().equals(state.getName())) {
				v_trans.add(t);
			}
		}
		
		
		if (v_trans.size()>0) {
			Transition tran=new Transition();
			tran=v_trans.get(0);
			for(int i=0;i<StateSet.size();i++){//找到v的第一个邻接点
				if(tran.getTarget().equals(StateSet.get(i).getName())){
					return i;
				}
			}
		}
		
		return -1;
	}
}
