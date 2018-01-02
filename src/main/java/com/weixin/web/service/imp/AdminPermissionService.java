package com.weixin.web.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.weixin.web.dao.AdminPermissionDao;
import com.weixin.web.dao.AdminRoleDao;
import com.weixin.web.entity.AdminPermission;
import com.weixin.web.entity.AdminRole;
import com.weixin.web.form.MenuForm;
import com.weixin.web.utils.NodeTree;


/**
 * 菜单服务类
 * 
 * @author 程新井
 *
 */
@Service("adminPermissionService")
public class AdminPermissionService {
	@Autowired
	private AdminPermissionDao adminPermissionDao;
	@Autowired
	private AdminRoleDao adminRoleDao;
	public MenuForm searchPage(MenuForm meuForm) {
		//只查询父菜单
	    int total =	adminPermissionDao.searchParentPageTotal(meuForm);
		if (total > 0) {
			List<MenuForm> rows = adminPermissionDao.searchParentPage(meuForm);
			meuForm.setRows(rows);
		}
		meuForm.setTotal(total);
		return meuForm;
	}
	
	/**
	 * 根据父级菜单id查询子菜单
	 * @param id
	 * @return
	 */
	public MenuForm findChildMenu(String id) {
		MenuForm menuForm = new MenuForm();
		menuForm.setParentId(id);
		menuForm = searchPage(menuForm);
		return menuForm;
	}
	
	/**
	 * 保存菜单
	 * @param vo
	 */
	public void saveMenu(MenuForm vo) {
		AdminPermission entity = new AdminPermission();
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setParentId(vo.getParentId());
		entity.setSort(vo.getSort());
		entity.setUrl(vo.getUrl());
		adminPermissionDao.insert(entity);
	}
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<AdminRole> getRole(AdminRole role) {
		List<AdminRole> roleList = adminRoleDao.findAllRole(role);
		return roleList;
	}
	
	/**
	 * 根据roleId获取其权限
	 * @param roleId
	 * @return
	 */
	public List<NodeTree> getNodeList(String roleId) {
		//查出所有权限
	    List<NodeTree> nodeTreeList = adminPermissionDao.findAllPermissiom();
	    //根据 roleId查出其所拥有的权限
	    List<NodeTree> nodeTrees = adminPermissionDao.findPermissionByRoleId(roleId);
	   for (NodeTree nodeTree : nodeTreeList) {
		   for (NodeTree node : nodeTrees) {
				if(node.getId().equals(nodeTree.getId())){
					nodeTree.setChecked(true);
				}
			}
		   if(StringUtils.isEmpty(nodeTree.getParentId())){
			   nodeTree.setOpen(true);
			   nodeTree.setParentId("0");
		   }
	}
	    
		return nodeTreeList;
	}

	
	/**
	 * 根据权限 id 和roleId 更新角色和权限的关系
	 * @param trees
	 * @param roleId
	 * @return
	 */
	public boolean editPermission(List<NodeTree> trees, String roleId) {
		//删除角色所有的权限
		adminPermissionDao.deleteByRoleId(roleId);
		//添加权限
		for(NodeTree node:trees){
			adminPermissionDao.saveAdminPermission(node.getId(),roleId);
		}
		return false;
	}
	/**
	 * 根据id查询该权限
	 * @param id
	 * @return
	 */
	public AdminPermission findMenuById(String id) {
		AdminPermission entity = adminPermissionDao.findAdminPermissionById(id);
		return entity;
	}

	public void update(MenuForm vo) {
		AdminPermission entity = new AdminPermission();
		entity.setId(vo.getId());
		entity.setName(vo.getName());
		entity.setSort(vo.getSort());
		entity.setUrl(vo.getUrl());
		adminPermissionDao.updateById(entity);
	}

}
