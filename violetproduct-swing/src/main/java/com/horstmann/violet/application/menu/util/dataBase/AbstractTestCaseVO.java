package com.horstmann.violet.application.menu.util.dataBase;

public class AbstractTestCaseVO {
	private String id;
	private String name;
	private String textRouter;
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

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTextRouter() {
		return textRouter;
	}
	public void setTextRouter(String textRouter) {
		this.textRouter = textRouter;
	}
	@Override
	public String toString() {
		return "AbstractTestCaseVO [id=" + id + ", name=" + name + ", textRouter=" + textRouter + ", remark=" + remark
				+ "]";
	}

	

	
}
