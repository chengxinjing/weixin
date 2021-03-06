package com.weixin.web.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weixin.web.enums.MessageTypeEnum;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 消息处理工具类 Created by xdp on 2016/1/26.
 */
@Component
public class MessageHandlerUtil {
	@Autowired
	private WeixinApiUtil weixinApiUtil;

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return map
	 * @throws Exception
	 */
	public Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		System.out.println("获取输入流");
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

	// 根据消息类型 构造返回消息
	public String buildXml(Map<String, String> map, String realPath) {
		String result;
		String msgType = map.get("MsgType").toString().toUpperCase();
		MessageTypeEnum type = MessageTypeEnum.valueOf(MessageTypeEnum.class, msgType);
		switch (type) {
		case TEXT:
			result = handleTextMessage(map, "程新井在学习和总结微信开发了,构建一条文本消息:Hello World!");
			break;
		case IMAGE:
			result = handleImageMessage(map, realPath);
			break;
		case VIDEO:
		case SHORTVIDEO:
			result = handleVideoMessage(map);
			break;
		case LINK:
			result = handleLinkMessage(map);
			break;
		case LOCATION:
			result = handleLocationMessage(map);
			break;
		case VOICE:
			result = handleVoiceMessage(map);
			break;
		default:
			String fromUserName = map.get("FromUserName");
			// 开发者微信号
			String toUserName = map.get("ToUserName");
			result = String.format(
					"<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" + "<FromUserName><![CDATA[%s]]></FromUserName>"
							+ "<CreateTime>%s</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
							+ "<Content><![CDATA[%s]]></Content>" + "</xml>",
					fromUserName, toUserName, getUtcTime(), "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文");
		}
		return result;
	}

	/*
	 * if(msgType.toUpperCase().equals("TEXT")){ result = buildTextMessage(map,
	 * "程新井在学习和总结微信开发了,构建一条文本消息:Hello World!"); }else{ String fromUserName =
	 * map.get("FromUserName"); // 开发者微信号 String toUserName = map.get("ToUserName");
	 * result = String.format( "<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" +
	 * "<FromUserName><![CDATA[%s]]></FromUserName>" + "<CreateTime>%s</CreateTime>"
	 * + "<MsgType><![CDATA[text]]></MsgType>" + "<Content><![CDATA[%s]]></Content>"
	 * + "</xml>", fromUserName, toUserName, getUtcTime(),
	 * "请回复如下关键词：\n文本\n图片\n语音\n视频\n音乐\n图文"); }
	 */

	private static String handleVoiceMessage(Map<String, String> map) {
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> entry : set) {
			System.out.println(entry.getKey() + "______" + entry.getValue());
		}
		return "";
	}

	private static String handleLocationMessage(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String handleLinkMessage(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String handleVideoMessage(Map<String, String> map) {
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> entry : set) {
			System.out.println(entry.getKey() + "______" + entry.getValue());
		}
		return "";
	}

	private String handleImageMessage(Map<String, String> map, String realPath) {
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> entry : set) {
			System.out.println(entry.getKey() + "______" + entry.getValue());
		}
		// 发送者
		String fromUserName = map.get("ToUserName");
		String toUsername = map.get("FromUserName");

		// 获取mediaId//上传wenjian
		String mediaId = map.get("MediaId"); // weixinApiUtil.uploadMedia("E:\\person\\weixin\\src\\main\\resources\\resources\\123.JPG","image").getString("media_id");
		weixinApiUtil.downloadMedia("E:\\person\\weixin\\src\\main\\webapp\\static\\" + mediaId + ".JPG", mediaId);
		String xml = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[%s]]></MediaId></Image></xml>";
		return String.format(xml, toUsername, fromUserName, getUtcTime(), mediaId);
	}

	private static String handleTextMessage(Map<String, String> map, String msg) {

		return buildTextMessage(map, msg);
	}

	/**
	 * 构造文本消息
	 *
	 * @param map
	 * @param content
	 * @return
	 */
	private static String buildTextMessage(Map<String, String> map, String content) {
		// 发送方帐号
		String fromUserName = map.get("FromUserName");
		// 开发者微信号
		String toUserName = map.get("ToUserName");
		/**
		 * 文本消息XML数据格式 <xml> <ToUserName><![CDATA[toUser]]></ToUserName>
		 * <FromUserName><![CDATA[fromUser]]></FromUserName>
		 * <CreateTime>1348831860</CreateTime> <MsgType><![CDATA[text]]></MsgType>
		 * <Content><![CDATA[this is a test]]></Content> <MsgId>1234567890123456</MsgId>
		 * </xml>
		 */
		return String.format(
				"<xml>" + "<ToUserName><![CDATA[%s]]></ToUserName>" + "<FromUserName><![CDATA[%s]]></FromUserName>"
						+ "<CreateTime>%s</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
						+ "<Content><![CDATA[%s]]></Content>" + "</xml>",
				fromUserName, toUserName, getUtcTime(), content);
	}

	/**
	 * 获取时间
	 * 
	 * @author 程新井
	 * @return
	 * @2017年12月28日下午4:56:12
	 */
	private static String getUtcTime() {
		Date dt = new Date();// 如果不需要格式,可直接用dt,dt就是当前系统时间
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmm");// 设置显示格式
		String nowTime = df.format(dt);
		long dd = (long) 0;
		try {
			dd = df.parse(nowTime).getTime();
		} catch (Exception e) {

		}
		return String.valueOf(dd);
	}
}