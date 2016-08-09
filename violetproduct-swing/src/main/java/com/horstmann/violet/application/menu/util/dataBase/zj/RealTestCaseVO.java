package com.horstmann.violet.application.menu.util.dataBase.zj;

public class RealTestCaseVO {
	private String id;
	private String name;
	private String processList;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProcessList() {
		return processList;
	}
	public void setProcessList(String processList) {
		this.processList = processList;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "RealTestCaseVO [id=" + id + ", name=" + name + ", processList=" + processList + ", remark=" + remark
				+ "]";
	}
	
	
}
