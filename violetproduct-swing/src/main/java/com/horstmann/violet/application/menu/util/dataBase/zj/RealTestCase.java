package com.horstmann.violet.application.menu.util.dataBase.zj;

import java.io.Serializable;
/**
 *实例化测试用例的数据结构
 * @author Administrator
 *
 */
public class RealTestCase implements Serializable {
	private int id ;
	private String name;
	private String realTestRouter;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealTestRouter() {
		return realTestRouter;
	}
	public void setRealTestRouter(String realTestRouter) {
		this.realTestRouter = realTestRouter;
	}
	 
}
