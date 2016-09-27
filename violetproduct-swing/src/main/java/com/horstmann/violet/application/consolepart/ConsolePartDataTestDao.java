package com.horstmann.violet.application.consolepart;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTestCase;
import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTestCaseVO;
import com.horstmann.violet.application.menu.util.zhangjian.Database.DataBaseUtil;
import com.horstmann.violet.application.menu.util.zhangjian.Database.RealProcess;
import com.horstmann.violet.application.menu.util.zhangjian.Database.RealTestCase;
import com.horstmann.violet.application.menu.util.zhangjian.Database.RealTestCaseVO;

public class ConsolePartDataTestDao {
	/**
	 * 获取实例化测试用例
	 * @return
	 */
	public static List<RealTestCaseVO> getRealTestCaseList() {
		List<RealTestCaseVO> list=new ArrayList<RealTestCaseVO>();
		//这里只查询四条记录，后期要修改
    	List<RealTestCase>rl =DataBaseUtil.getAllRealTestCase("select * from real_testcase ");
    	for(RealTestCase r :rl){
    		RealTestCaseVO rv =new RealTestCaseVO();
    		rv.setId(r.getId()+"");
    		rv.setName(r.getName());
    		String routerid=r.getRealTestRouter();
    		String[] sids=routerid.split(",");
    		int[] ids =new int[sids.length] ;
    		for (int i = 0; i < sids.length; i++) {
				int id= Integer.parseInt(sids[i]);
				ids[i]=id;
			}
    		List<RealProcess> rp=DataBaseUtil.getProcessByID(ids, "select * from real_process where pid =?");
    		StringBuffer sb =new StringBuffer();
    		for(RealProcess p :rp){
    			if(!(p.equals(rp.get(rp.size()-1)))){
    				sb.append(p.getOperation()+"("+p.getInputInfo()+")---->");
    			}else{
    				sb.append(p.getOperation()+"("+p.getInputInfo()+")");
    			}
    			
    		}
    		rv.setProcessList(sb.toString());
    		list.add(rv);	
    	}
    	return list;
		
	}
	public static List<AbstractTestCaseVO> getAbsTestCaseList() {
	
		//这里只查询四条记录，后期要修改
    	List<AbstractTestCaseVO> rl =DataBaseUtil.ShowAllAbstractTestCase();
  
    	return rl;
		
	}
	
	
	

}
