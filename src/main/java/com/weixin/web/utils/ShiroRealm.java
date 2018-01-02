package com.weixin.web.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.security.auth.login.AccountNotFoundException;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.weixin.web.constance.MyConst;
import com.weixin.web.entity.AdminPermission;
import com.weixin.web.entity.AdminUser;
import com.weixin.web.service.imp.AdminUserService;

public class ShiroRealm extends AuthorizingRealm {

	private static Logger log = Logger.getLogger(ShiroRealm.class);
	@Autowired
	private AdminUserService adminUserService;
	
	public ShiroRealm() {
		super();
		//密码认证器
		HashedCredentialsMatcher  matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName(MyConst.ALGORITHMNAME);
		matcher.setHashIterations(MyConst.ITERATIONS);
		setCredentialsMatcher(matcher);
	}

	//权限认证
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Set<String> roleUrls = new HashSet<String>();
		String  username = (String) principals.getPrimaryPrincipal();
		//根据用户名查询其含有的所有权限
	List<AdminPermission> permissionList=adminUserService.findPermissionAllByUsername(username);
	  for(AdminPermission parent :permissionList  ){
		  //父级菜单没有url
		  for(AdminPermission child : parent.getChildPermission()){
			  roleUrls.add(child.getUrl());
		  }
	  }
		SimpleAuthorizationInfo authorizationInfo = new  SimpleAuthorizationInfo(roleUrls);
		return authorizationInfo;
	}
	
	//身份认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("shiro认证开始");
		String username = (String) token.getPrincipal();//获取用户名
		//根据用户名用户
		AdminUser user = adminUserService.findAdminUserByUsername(username);
		if(null != user){
			//对盐加密
			ByteSource salt = Crypto.cryptoSalt(username);
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), salt, getName());
			return authenticationInfo;
		}
		
		return null;
	}

}
