package com.weixin.web.service.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.web.dao.AdminPermissionDao;
import com.weixin.web.dao.AdminUserDao;
import com.weixin.web.entity.AdminPermission;
import com.weixin.web.entity.AdminRole;
import com.weixin.web.entity.AdminUser;
import com.weixin.web.enums.AccountStatusEnum;
import com.weixin.web.form.AdminUserForm;



@Service("adminUserService")
public class AdminUserService {

	@Autowired
	private AdminUserDao adminUserDao;
	@Autowired
	private AdminPermissionDao adminPermissionDao;

	/**
	 * 通过用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	public AdminUser findAdminUserByUsername(String username) {
		AdminUser entity = new AdminUser();
		entity.setUsername(username);
		entity.setDelFlg(0);
		entity = adminUserDao.selectOne(entity);
		return entity;
	}

	/**
	 * 用户名查询所拥有的所有权限
	 * 
	 * @param username
	 * @return
	 */
	public List<AdminPermission> findPermissionAllByUsername(String username) {
		List<AdminPermission> permissionList = new ArrayList<AdminPermission>();
		// 查询客户所用有子菜单
		List<AdminPermission> permissionChildList = adminUserDao.findPermissionAllChildByUsername(username);
		// 根据子菜单查询父菜单
		permissionList = adminUserDao.findPermissionAllParentByUsername(username);
		if (null != permissionChildList && permissionChildList.size() > 0) {
			
			for (AdminPermission parent : permissionList) {
				List<AdminPermission> childPermissions = new ArrayList<AdminPermission>();
				for (AdminPermission child : permissionChildList) {
					if (child.getParentId().equals(parent.getId())) {
						childPermissions.add(child);
					}
				}
				parent.setChildPermission(childPermissions);
			}
		}
		return permissionList;
	}

	/**
	 * 添加注册用户
	 * 
	 * @param adminUser
	 */
	public void saveRegister(AdminUser adminUser) {
		adminUserDao.insert(adminUser);
	}

	/**
	 * 根据用户名查询角色
	 * 
	 * @param adminUser
	 * @return
	 */
	public AdminRole findRoleByUsername(AdminUser adminUser) {
		AdminRole role = adminUserDao.findfindRoleByUsername(adminUser.getUsername());
		return role;
	}

	/**
	 * 分页查询
	 * 
	 * @param vo
	 * @return
	 */
	public AdminUserForm searchePage(AdminUserForm vo) {
		int total = adminUserDao.searchePageTotal(vo);
		if (total > 0) {
			List<AdminUserForm> rows = adminUserDao.searchePage(vo);
			for (AdminUserForm row : rows) {
				if (AccountStatusEnum.NORMAL.getValue().equals(row.getLocked())) {
					row.setLocked(AccountStatusEnum.NORMAL.getText());
				} else {
					row.setLocked(AccountStatusEnum.CANCEL.getText());
				}
			}
			vo.setRows(rows);
		}
		vo.setTotal(total);
		return vo;
	}

	public AdminUser findAdminUserById(String userId) {
		return adminUserDao.selectById(userId);
	}

	public void saveEdit(AdminUserForm vo) {
		AdminUser adminUser = new AdminUser();
		adminUser.setUserId(vo.getUserId());
		adminUser.setUsername(vo.getUsername());
		adminUserDao.updateById(adminUser);
		
	}

	public void delete(String userId) {
		AdminUser adminUser = new AdminUser();
		adminUser.setUserId(userId);
		adminUser.setDelFlg(1);
		adminUserDao.updateById(adminUser);
		
	}

}
