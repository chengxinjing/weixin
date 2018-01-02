package com.weixin.web.utils;

public class AjaxBean {

	//ajax请求成功的标识（1:请求成功，0：请求失败）
	private Integer sucFlag ;
	// 信息
	private String msg;
	// ajax请求成功判断
	public boolean isSuccess(Integer suc) {
		if (suc == 1) {
			setSucFlag(1);
			return true;
		} else {
			return false;
		}
	}

	public Integer getSucFlag() {
		return sucFlag;
	}

	public void setSucFlag(Integer sucFlag) {
		this.sucFlag = sucFlag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
