package com.weixin.web.test;

import java.util.Set;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.weixin.web.entity.AccessToken;
import com.weixin.web.enums.MessageTypeEnum;
import com.weixin.web.utils.AccessTokenUtil;
import com.weixin.web.utils.ConfigUtils;
import com.weixin.web.utils.WeixinApiUtil;

public class RequestTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-all.xml");
		WeixinApiUtil apiUtil = applicationContext.getBean(WeixinApiUtil.class);
		/*
		 * ConfigUtils conf = ConfigUtils.getInstance("weixin");
		 * System.out.println(conf.getMap().get("appId")); Set<Entry<String, String>>
		 * set = conf.getMap().entrySet(); for (Entry<String, String> entry : set) {
		 * System.out.println(entry.getKey()+"_______"+entry.getValue()); }
		 */
		// System.out.println(ResourceBundle.getBundle("resources.weixin").getString("jiangxi"));
		// apiUtil.createMenu();
		JSONObject jsonObject = apiUtil.uploadMedia("E:\\workspace\\weixin\\src\\main\\resources\\resources\\123.png",
				"image");
		apiUtil.downloadMedia("D:/" + jsonObject.getString("media_id") + ".png", jsonObject.getString("media_id"));
		/*
		 * MessageTypeEnum type= MessageTypeEnum.valueOf(MessageTypeEnum.class,
		 * "VIDEO"); System.out.println(type.toString());
		 */
	}
}
