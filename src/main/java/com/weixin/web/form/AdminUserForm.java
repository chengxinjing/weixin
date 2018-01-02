package com.weixin.web.form;

import com.weixin.web.utils.BasicPage;

/**
 * 用户表
 * @author 程新井
 *
 */
public class AdminUserForm extends BasicPage<AdminUserForm>  {
	
	private String roleId;
	private String username;
	private String userId;
	private String locked;
	private String password;
	
	//是否分页 （否：0）
	private String isPage;
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getIsPage() {
		return isPage;
	}

	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}
