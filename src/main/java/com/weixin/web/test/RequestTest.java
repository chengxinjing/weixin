package com.weixin.web.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.weixin.web.utils.AccessTokenUtil;

public class RequestTest {
 
	public static void main(String[] args) {
		//NetWorkHelper.sendHttpResponse("http://2ryd39.natappfree.cc/weixin/login/show");
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-all.xml");
		AccessTokenUtil accessTokenUtil = 	applicationContext.getBean(AccessTokenUtil.class);
		
		for(int i = 0 ;i<= 1 ;i++) {
		//	AccessTokenUtil.getAccessTokenFromCache("access_");
		}
		
	}
}
