package com.horstmann.violet.application.menu.util.dataBase.zj;

import java.io.Serializable;
/**
 * 抽象测试序列的数据结构
 * @author ZhangJian
 *
 */
public class AbstractTestCase implements Serializable {
	private int tid;
	private String tname;
	private String testRouter;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getTestRouter() {
		return testRouter;
	}
	public void setTestRouter(String testRouter) {
		this.testRouter = testRouter;
	}
	
}
