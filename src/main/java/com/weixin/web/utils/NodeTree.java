package com.weixin.web.utils;

import java.util.List;

/**
 * 节点树
 * 
 * @author 程新井
 *
 */
public class NodeTree {

	// 节点的id
	private String id;
	// 节点的父id
	private String parentId;
	// 是否被选中
	private boolean checked;
	
	//是否展开
	private boolean open;
	/*// 子节点
	private List<NodeTree> children;*/
    //节点名称
	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

/*	public List<NodeTree> getChildren() {
		return children;
	}

	public void setChildren(List<NodeTree> children) {
		this.children = children;
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return " {id=" + id + ", parentId=" + parentId + ", checked=" + checked + ", open=" + open + ", name="
				+ name + "}";
	}

}
