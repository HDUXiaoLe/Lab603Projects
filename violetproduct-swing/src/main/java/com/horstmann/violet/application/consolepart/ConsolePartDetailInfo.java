package com.horstmann.violet.application.consolepart;

public class ConsolePartDetailInfo {

	private String index;//序列号
	private String message;//消息
	private String date;//日期
	private String messagedetail;//消息详细描述
	private String note;//备注
	public ConsolePartDetailInfo(String index,String message,String messagedetail,String date,String note)
	{
		this.index=index;
		this.message=message;
		this.date=date;
		this.messagedetail=messagedetail;
		this.note=note;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMessagedetail() {
		return messagedetail;
	}

	public void setMessagedetail(String messagedetail) {
		this.messagedetail = messagedetail;
	}

	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
