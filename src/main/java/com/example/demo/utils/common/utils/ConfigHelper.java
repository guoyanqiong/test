package com.example.demo.utils.common.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 配置文件读取类
 * 
 * @author Administrator
 *
 */
public enum ConfigHelper {

	// Default("properties/default.properties"),AppSetting("properties/appSetting.properties"),Key("properties/key.properties");
	Key("key.properties"), Config("config.properties") ;

	Properties properties = null;

	private ConfigHelper(String fileName) {
        InputStreamReader fis = null;
        try {
            fis = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(fileName), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        properties = new Properties();
		try {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取配置文件项值
	 * 
	 * @param propertyName
	 * @return
	 */
	public String getPropertie(String propertyName) {
		return properties.getProperty(propertyName);
	}
}
