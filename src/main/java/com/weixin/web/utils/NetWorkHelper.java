package com.weixin.web.utils;

import javax.net.ssl.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.web.constance.MyConst;
import com.weixin.web.entity.AccessToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 访问网络用到的工具类
 */
public final class NetWorkHelper {

	
	private NetWorkHelper() {
	}
	/**
	 * http 和 https 请求
	 * @param url 请求的 url
	 * @param data 输出的数据
	 * @param requestMethod 请求方法
	 * @return
	 */
	public static String sendHttpResponse(String url, String data, String requestMethod) {
		String result = "";
		OutputStream outputStream =null;
		InputStream inputStream =null;
		BufferedReader bufferedReader =null;
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
				outputStream = connection.getOutputStream();
				outputStream.write(data.getBytes());
				outputStream.flush();
				outputStream.close();
			}
			// 获取输入流
			inputStream = connection.getInputStream();
		    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			StringBuffer buffer = new StringBuffer();
			while ((result = bufferedReader.readLine()) != null) {
				buffer.append(result);
			}
			result = buffer.toString();
			inputStream.close();
			bufferedReader.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}
	
	public static String sendHttpResponse(String url) {
		return sendHttpResponse(url, "", "");
	}

	public static String sendHttpResponse(String url, String requestMethod) {
		return sendHttpResponse(url, "", requestMethod);
	}
	
}
