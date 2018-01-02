package com.weixin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.web.entity.AccessToken;
import com.weixin.web.utils.AccessTokenUtil;

@Controller
@RequestMapping("login")
public class LoginController {

@Autowired
private AccessTokenUtil accessTokenUtil;
 @RequestMapping("show")
 public String show() {
	 System.out.println("dd");
	 accessTokenUtil.getAccessTokenFromCache("access_token");
	AccessToken  accessToken=  accessTokenUtil.getAccessTokenFromCache("access_token");
	System.out.println(accessToken.getToken());
	 return"index";
 }
}
