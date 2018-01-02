package com.weixin.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.weixin.web.entity.AdminPermission;
import com.weixin.web.entity.AdminRole;
import com.weixin.web.entity.AdminUser;
import com.weixin.web.form.AdminUserForm;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 程新井
 * @since 2017-06-05
 */
public interface AdminUserDao extends BaseMapper<AdminUser> {

	List<AdminPermission> findPermissionAllParentByUsername(@Param("username") String username);

	AdminRole findfindRoleByUsername(@Param("username") String username);

	int searchePageTotal(AdminUserForm vo);

	List<AdminUserForm> findEntityByEntity(AdminUserForm vo);

	List<AdminUserForm> searchePage(AdminUserForm vo);

	List<AdminPermission> findPermissionAllChildByUsername(String username);

}