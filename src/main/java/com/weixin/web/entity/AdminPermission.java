package com.weixin.web.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author 程新井
 * @since 2017-06-05
 */
@TableName("admin_permission")
public class AdminPermission extends Model<AdminPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
	private String id;
    /**
     * 权限名
     */
	private String name;
    /**
     * 排序
     */
	private Integer sort;
    /**
     * 菜单的连接地址
     */
	private String url;
    /**
     * 父级菜单id
     */
	@TableField("parent_id")
	private String parentId;
	List<AdminPermission> childPermission;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public List<AdminPermission> getChildPermission() {
		return childPermission;
	}

	public void setChildPermission(List<AdminPermission> childPermission) {
		this.childPermission = childPermission;
	}

}
