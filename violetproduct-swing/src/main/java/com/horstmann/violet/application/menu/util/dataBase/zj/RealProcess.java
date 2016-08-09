package com.horstmann.violet.application.menu.util.dataBase.zj;

import java.io.Serializable;
/**
 * 实例化测试用例中Process的数据结构
 * @author ZhangJian
 *
 */
public class RealProcess implements Serializable {
	private int pid ;
	private String operation;
	private String inputInfo;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getInputInfo() {
		return inputInfo;
	}
	public void setInputInfo(String inputInfo) {
		this.inputInfo = inputInfo;
	}
	
	
}
