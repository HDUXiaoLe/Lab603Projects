package com.horstmann.violet.application.gui.util.wqq.other;

import java.util.ArrayList;

public class NextAdjVex {
	public static void main(String[] args) {
		Automatic a=AutomaticIBean.automic();
		int next=nextAdjVex(a,3,1);
		System.out.println(next);
	}
	/**
	 * 返回时间自动机中v中相对于w的下一个邻接点,如果w是最后一个邻接点，返回-1。
	 * @param automatic
	 * @param v
	 * @param w
	 * @return
	 */
	public static int nextAdjVex(Automatic automatic,int v,int w) {
		ArrayList<State> StateSet=automatic.getStateSet();
		State state=StateSet.get(v);
		State state_w=StateSet.get(w);
		ArrayList<Transition> TransitionSet=automatic.getTransitionSet();
		ArrayList<Transition> v_trans=new ArrayList<Transition>();
		for(Transition t:TransitionSet){//构建源为v的边集合
			if (t.getSource().equals(state.getName())) {
				v_trans.add(t);
			}
		}
		
		
		if (v_trans.size()>0) {
			Transition tran=new Transition();
			for(int i=0;i<v_trans.size();i++){
				if(v_trans.get(i).getTarget().equals(state_w.getName())){
					int j=i+1;
					if(j>=v_trans.size()){return -1;}//如果w是v的最后一个邻接点，则返回-1
					else tran=v_trans.get(j);//下一条边的目的状态就是v的下一个邻接点
				}
			}
			for(int k=0;k<StateSet.size();k++){
				if(tran.getTarget().equals(StateSet.get(k).getName())){
					return k;
				}
			}
		}
		return -1;
	}
}
