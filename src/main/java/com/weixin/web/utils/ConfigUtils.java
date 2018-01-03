package com.weixin.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

public final class ConfigUtils {

	private static Logger logger = Logger.getLogger(ConfigUtils.class);
	private String SUFFIX = ".properties";
	private String PREFIX = "/resources/";
	private Map<String, String> map = new HashMap<>();
	private static ConfigUtils configUtilInstance = null;

	private ConfigUtils(String fileName) {
		//初始化map
		fillMap(fileName);
	}
	/**
	 * 获取实例
	 * @param fileName  //文件的名字
	 * @return
	 */
	public static ConfigUtils getInstance(String fileName) {
		if (null == configUtilInstance) {
			synchronized (ConfigUtils.class) {
				if (null == configUtilInstance) {
					configUtilInstance = new ConfigUtils(fileName);
				}
			}
		}
		return configUtilInstance;
	}

	/**
	 * 根据key获得值
	 * 
	 * @param key
	 * @return
	 */
	private  String getValue(String key) {
		// 先从map查询
		String value = map.get(key);
		if (value == null) {
			return null;
		}
		return value;
	}

	public Map<String, String> getMap() {
		return map;
	}
	// 填充map
	private  void fillMap(String fileName) {
		InputStream inputStream = ConfigUtils.class.getResourceAsStream(PREFIX + fileName + SUFFIX);
		Properties properties = new Properties();
		try {
			if (null != inputStream) {
				properties.load(inputStream);
				Set<Object> keySet = properties.keySet();
				for (Object key : keySet) {
					map.put((String) key, (String) properties.get(key));
				}
			}else {
				logger.error("未找到该文件：" + fileName);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
