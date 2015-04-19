package com.orangexxx.exam.beans;

public class Message {

	private int code;
	private String msg;
	
	public Message(int code, String desc) {
		this.code = code;
		this.msg = desc;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
