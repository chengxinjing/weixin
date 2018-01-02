package com.weixin.web.enums;

/**
 * 账户状态
 * @author 程新井
 *
 */
public enum AccountStatusEnum {

	NORMAL("0","正常"),
	CANCEL("1","注销");
	
	private String value;
	
	private String text;

	private AccountStatusEnum(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
