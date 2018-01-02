package com.weixin.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import com.weixin.web.entity.AccessToken;
@Component
public class AccessTokenUtil  {
	
	@Autowired
	private  EhCacheCacheManager ehCacheManager;
	
	private  Cache cache;
	public  AccessToken getAccessTokenFromCache(String name) {
		cache =	ehCacheManager.getCache(name);
		if(null == cache.get(name)) {
			//产生一个AccessToken 并存储在cache缓存中
			AccessToken accessToken = NetWorkHelper.getAccessToken("wx42b1d8b6d9df0332","44fe52b96116620dfc001b93bf41050e");
			cache.put(name, accessToken);
			return accessToken;
		}else {
			AccessToken accessToken =  (AccessToken) cache.get(name).get();
			return accessToken;
		}
	}
	
}
