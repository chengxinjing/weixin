package com.weixin.web.utils;

import javax.net.ssl.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.web.entity.AccessToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

/**
 * 访问网络用到的工具类
 */
public final class NetWorkHelper {

	private NetWorkHelper() {
	}

	public static String sendHttpResponse(String url, String data, String requestMethod) {
		String result = "";
		try {
			URL Url = new URL(url);//将String url转换成 URL
			HttpsURLConnection connection = (HttpsURLConnection) Url.openConnection();//获取连接
			connection.setRequestProperty("Content-type", "application/json");//设置请求头
			connection.setRequestProperty("encoding", "utf-8");
			TrustManager[] tm = { com.weixin.web.entity.TrustManager.getInstance() };
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tm, null);
			connection.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false); // 不使用缓冲
			if (null != requestMethod && !requestMethod.equals("")) {
				connection.setRequestMethod(requestMethod); // 使用指定的方式
			} else {
				connection.setRequestMethod("GET"); // 使用get请求
			}
			// 获取输出流
			if (!data.isEmpty()) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(data.getBytes());
				outputStream.flush();
				outputStream.close();
			}
			// 获取输入流
			InputStream inputStream = connection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			StringBuffer buffer = new StringBuffer();
			while ((result = bufferedReader.readLine()) != null) {
				buffer.append(result);
			}
			result = buffer.toString();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
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
	public static AccessToken getAccessToken(String appId, String appSecret) {
		/**
		 * 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET，其中grant_type固定写为client_credential即可。
		 */
		String Url = String.format(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId,
				appSecret);
		// 此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
		String result = sendHttpResponse(Url);
		System.out.println("获取到的access_token=" + result);
		// 使用FastJson将Json字符串解析成Json对象
		JSONObject json = JSON.parseObject(result);
		AccessToken token = new AccessToken();
		token.setToken(json.getString("access_token"));
		token.setExpires_in(json.getInteger("expires_in"));
		return token;
	}

	public static String sendHttpResponse(String url) {
		return sendHttpResponse(url, "", "");
	}

	public static String sendHttpResponse(String url, String requestMethod) {
		return sendHttpResponse(url, "", requestMethod);
	}
}
