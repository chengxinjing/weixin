package com.weixin.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class ConfigUtils {

	private static Logger logger = Logger.getLogger(ConfigUtils.class);
	private static final String SUFFIX = ".properties";
	private static final String PREFIX = "/resources/";
	private static Map<String, String> map = new HashMap<>();
	private static ConfigUtils configUtilInstance = null;

	private ConfigUtils() {
		
	}

	public static ConfigUtils getInstance() {
		if (null == configUtilInstance) {
			synchronized (ConfigUtils.class) {
				if (null == configUtilInstance) {
					configUtilInstance = new ConfigUtils();
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
	public static String getValue(String key) {
		// 先从map查询
		String value = map.get(key);
		if (value == null) {
			return null;
		}
		return value;
	}

	// 填充map
	private static void clipseMap(String fileName) {
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

	public static void main(String[] args) {
		ConfigUtils.clipseMap("weixin");
		Set<Entry<String, String>> set =  map.entrySet();
		for (Entry<String, String> entry : set) {
			System.out.println(entry.getKey()+"_______"+entry.getValue());
		}
	}
}
