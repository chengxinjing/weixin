package com.weixin.web.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.weixin.web.entity.AdminPermission;
import com.weixin.web.form.MenuForm;
import com.weixin.web.utils.NodeTree;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 程新井
 * @since 2017-06-05
 */
public interface AdminPermissionDao extends BaseMapper<AdminPermission> {

	List<AdminPermission> findChildPermission(@Param("parentId")String id);

	int searchParentPageTotal(MenuForm meuForm);

	List<MenuForm> searchParentPage(MenuForm meuForm);

	AdminPermission findParentPermission(@Param("id")String parentId);

	List<NodeTree> findAllPermissiom();

	List<NodeTree> findPermissionByRoleId(@Param("roleId")String roleId);

	void deleteByRoleId(@Param("roleId")String roleId);

	void saveAdminPermission(@Param("id")String id, @Param("roleId")String roleId);

	AdminPermission findAdminPermissionById(@Param("id")String id);



}