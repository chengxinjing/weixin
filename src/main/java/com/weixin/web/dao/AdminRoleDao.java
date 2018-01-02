package com.weixin.web.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.weixin.web.entity.AdminRole;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author 程新井
 * @since 2017-06-05
 */
public interface AdminRoleDao extends BaseMapper<AdminRole> {

	List<AdminRole> findAllRole(AdminRole adminRole);

}