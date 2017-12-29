package com.weixin.web.controller;

import com.weixin.web.entity.AccessTokenInfo;
import com.weixin.web.utils.NetWorkHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 用于获取accessToken的Servlet Created by xdp on 2016/1/25.
 */
@WebServlet(name = "AccessTokenServlet", urlPatterns = { "/AccessTokenServlet" }, loadOnStartup = 1, initParams = {
		@WebInitParam(name = "appId", value = "wx42b1d8b6d9df0332"),
		@WebInitParam(name = "appSecret", value = "44fe52b96116620dfc001b93bf41050e") })
public class AccessTokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		System.out.println("启动WebServlet");
		super.init();

		final String appId = getInitParameter("appId");
		final String appSecret = getInitParameter("appSecret");
		// 开启一个新的线程
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						// 获取accessToken
						AccessTokenInfo.accessToken = NetWorkHelper.getAccessToken(appId, appSecret);
						// 获取成功
						if (AccessTokenInfo.accessToken != null) {
							// 获取到access_token 休眠7000秒,大约2个小时左右
							Thread.sleep(7000 * 1000);
							// Thread.sleep(10 * 1000);//10秒钟获取一次
						} else {
							// 获取失败
							Thread.sleep(1000 * 3); // 获取的access_token为空 休眠3秒
						}
					} catch (Exception e) {
						System.out.println("发生异常：" + e.getMessage());
						e.printStackTrace();
						try {
							Thread.sleep(1000 * 10); // 发生异常休眠1秒
						} catch (Exception e1) {

						}
					}
				}

			}
		}).start();
	}

}