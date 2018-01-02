package com.weixin.web.form;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.weixin.web.entity.AdminPermission;
import com.weixin.web.utils.BasicPage;

/**
 * 菜单列表
 * 
 * @author 程新井
 *
 */
public class MenuForm extends BasicPage<MenuForm> {

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
	//是否分页（否：0）
	private String isPage;
	//是否是添加
	private String isAdd;
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

	public List<AdminPermission> getChildPermission() {
		return childPermission;
	}

	public void setChildPermission(List<AdminPermission> childPermission) {
		this.childPermission = childPermission;
	}

	public String getIsPage() {
		return isPage;
	}

	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}

	public String getIsAdd() {
		return isAdd;
	}

	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}

}
