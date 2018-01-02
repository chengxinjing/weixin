package com.weixin.web.enums;
/**
 * 大类型枚举
 * @author 程新井
 *
 */
public enum BigTypeEnum {

	ACCOUNT_STATUS("00001","账户状态");//账户状态
	private String value;
	private String text;

	private BigTypeEnum(String value, String text) {
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
