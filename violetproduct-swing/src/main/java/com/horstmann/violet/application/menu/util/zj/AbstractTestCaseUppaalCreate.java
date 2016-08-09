package com.horstmann.violet.application.menu.util.zj;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.horstmann.violet.application.gui.util.wqq.GetAutomatic;
import com.horstmann.violet.application.gui.util.wqq.other.Automatic;
import com.horstmann.violet.application.gui.util.wqq.other.State;
import com.horstmann.violet.application.gui.util.wqq.other.Transition;
import com.horstmann.violet.application.menu.util.dataBase.zj.AbstractState;
import com.horstmann.violet.application.menu.util.dataBase.zj.AbstractTransition;
import com.horstmann.violet.application.menu.util.dataBase.zj.DataBaseUtil;


public class AbstractTestCaseUppaalCreate {
	private List<AbstractState> abStateList =new ArrayList<AbstractState>();
	private List<AbstractTransition> abTransList =new ArrayList<AbstractTransition>();
	public void createXML(String fileName,String targetPath){
		Automatic am=GetAutomatic.getAutomatic(fileName);
		ArrayList<State> stateList=am.getStateSet();
		ArrayList<Transition> tranList = am.getTransitionSet();
		int num =DataBaseUtil.getObjNum("select count(*) from abstract_state");
		for(State s :stateList){
			
			AbstractState abState =new AbstractState();
			abState.setSid(num++);//查询数据库里面状态节点的个数
			abState.setSname(s.getName());
			abState.setPosition(s.getPosition());
			abState.setInvariantDBM(s.getInvariantDBM().toString());
			abStateList.add(abState);
			
		}
		for(Transition t :tranList){
			AbstractTransition abTrans =new AbstractTransition();
			abTrans.setSourceID(this.getStateIdByName(abStateList, t.getSource())+"");
			abTrans.setSource(t.getSource());
			abTrans.setTargetID(this.getStateIdByName(abStateList, t.getTarget())+"");
			abTrans.setTarget(t.getTarget());
			abTrans.setType(t.getTypes().toString());
			StringBuilder sb =new StringBuilder();
			for(int i=0;i<t.getResetClockSet().size();i++){
				sb.append(t.getResetClockSet().get(i));
				if(i!=t.getResetClockSet().size()-1){
					sb.append(";");
				}
			}
			abTrans.setResetClockSet(sb.toString());
			abTrans.setConstraintDBM(t.getConstraintDBM().toString());
		}
		
		//调用CreateAbstractUppaalXML类，创建抽象测试用例的自动机的XML文件
		CreateAbstractUppaalXML c =new CreateAbstractUppaalXML(abStateList, abTransList);
		try {
			 c.create(targetPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public int getStateIdByName(List<AbstractState> list ,String name){
		int num=0;
		for(AbstractState s :list){
			if(name.equals(s.getSname())){
				num=s.getSid();
			}
		}
		return num;
	}
}
