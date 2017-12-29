package com.weixin.web.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.web.entity.AccessToken;
import com.weixin.web.utils.NetWorkHelper;

public class Test {
	 public static void main(String[] args) throws IOException {
		  InputStream inputStream =	NetWorkHelper.class.getResourceAsStream("../../../../menu.txt");
		  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		 String text ;
		 StringBuffer buffer = new StringBuffer();
		 while( (text = bufferedReader.readLine())!=null) {
			 buffer.append(text);
		 }
		 AccessToken accessToken = NetWorkHelper.getAccessToken("wx42b1d8b6d9df0332","44fe52b96116620dfc001b93bf41050e");
		 String menu = buffer.toString();
		 JSONObject jsonObject = JSON.parseObject(menu);
	     System.out.println(jsonObject.toJSONString());
		 String api = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken.getToken();
		 String re = NetWorkHelper.sendHttpResponse(api,jsonObject.toJSONString(),"POST");
		 //获取
		 System.out.println(re);
		 inputStream.close();
		 bufferedReader.close();
		}

}
