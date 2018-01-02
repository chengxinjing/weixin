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
@TableName("admin_role")
public class AdminRole extends Model<AdminRole> {

    private static final long serialVersionUID = 1L;

    @TableId("role_id")
	private String roleId;
	@TableField("role_name")
	private String roleName;


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
