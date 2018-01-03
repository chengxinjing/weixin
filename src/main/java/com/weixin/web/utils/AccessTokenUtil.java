package com.weixin.web.utils;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.web.constance.MyConst;
import com.weixin.web.entity.AccessToken;

@Component
public class AccessTokenUtil {

	@Autowired
	private EhCacheCacheManager ehCacheManager;

	private Cache cache;
	private static final String APP_ID = ResourceBundle.getBundle(MyConst.BASE_NAME).getString("appId");
	private static final String APP_SECRET = ResourceBundle.getBundle(MyConst.BASE_NAME).getString("appSecret");
	private static final String ACCESS_TOKEN_API = ResourceBundle.getBundle(MyConst.BASE_NAME).getString("accessTokenApi").toString();
	
	/**
	 * 从缓存里面取access_token
	 * @param name
	 * @return
	 */
	public AccessToken getAccessTokenFromCache(String name) {
		cache = ehCacheManager.getCache(name);
		if (null == cache.get(name)) {
			// 产生一个AccessToken 并存储在cache缓存中
			AccessToken accessToken = getAccessToken();
			cache.put(name, accessToken);
			return accessToken;
		} else {
			AccessToken accessToken = (AccessToken) cache.get(name).get();
			return accessToken;
		}
	}

	/**
	 * 获取access_token
	 * 
	 * @author 程新井
	 * @param appId
	 * @param appSecret
	 * @return
	 * @2017年12月29日下午5:14:50
	 */
	public static  AccessToken getAccessToken(String appId, String appSecret) {
		/**
		 * 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET，其中grant_type固定写为client_credential即可。
		 */
		String Url = String.format(ACCESS_TOKEN_API, appId, appSecret);
		// 此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
		String result =NetWorkHelper.sendHttpResponse(Url);
		System.out.println("获取到的access_token=" + result);
		// 使用FastJson将Json字符串解析成Json对象
		JSONObject json = JSON.parseObject(result);
		AccessToken token = new AccessToken();
		token.setToken(json.getString("access_token"));
		token.setExpires_in(json.getInteger("expires_in"));
		return token;
	}

	/**
	 * 获取access_token
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken() {
		return getAccessToken(APP_ID, APP_SECRET);
	}

}
