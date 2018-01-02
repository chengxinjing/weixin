package com.weixin.web.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 程新井
 * @since 2017-06-05
 */
@TableName("admin_user")
public class AdminUser extends Model<AdminUser> {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
	private String userId;
    /**
     * 角色id
     */
	@TableField("role_id")
	private String roleId;
    /**
     * 用户登录的用户名
     */
	private String username;
    /**
     * 登录密码
     */
	private String password;
    /**
     * 盐
     */
	private String salt;
    /**
     * 账户的状态 0表示正常 1表示锁住
     */
	private Integer locked;
    /**
     * 删除标识 0表示未删除 1表示删除
     */
	@TableField("del_flg")
	private Integer delFlg;


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Integer getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Integer delFlg) {
		this.delFlg = delFlg;
	}

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

}
