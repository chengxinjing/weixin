package com.weixin.web.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.web.utils.MessageHandlerUtil;

/**
 * Servlet implementation class WxRequestInface
 */
public class WxRequestInface extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TOKEN = "chengxinjing";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WxRequestInface() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timeStamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String sortString = sort(TOKEN, timeStamp, nonce);
		String hashCode = cryptoSha1(sortString);
		if (null != hashCode && hashCode.equals(signature)) {
			System.out.println("签名通过");
			response.getWriter().println(echostr);
		} else {
			System.out.println("签名未通过");
		}

	}

	private String sort(String token, String timeStamp, String nonce) {
		// 先按字典排序
		StringBuilder builder = new StringBuilder();
		String[] str = { token, timeStamp, nonce };
		Arrays.sort(str);
		for (String string : str) {
			builder.append(string);
		}

		return builder.toString();
	}

	private String cryptoSha1(String str) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-1");
			digest.update(str.getBytes());
			byte[] messageDigest = digest.digest();
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html;charset=UTF-8");
		   String responseMessage = "";
		try {
			Map<String, String> xmlMap = MessageHandlerUtil.parseXml(request);
			responseMessage = MessageHandlerUtil.buildXml(xmlMap);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.getWriter().println(responseMessage);
	}

}
