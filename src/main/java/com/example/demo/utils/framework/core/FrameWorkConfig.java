/**
 * 
 */
package com.example.demo.utils.framework.core;

import com.example.demo.utils.framework.log.LogConfig;
import com.example.demo.utils.framework.redis.RedisConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class FrameWorkConfig {

	private final static ApplicationContext context = new ClassPathXmlApplicationContext(
			"frameWorkConfig.xml");

	public static RedisConfig getRedisConfig() {
		return (RedisConfig) context.getBean("redis");
	}

	public static LogConfig getLogConfig() {
		LogConfig config = null;
		try {
			config = (LogConfig) context.getBean("logging");
		} catch (Exception oe) {
			oe.printStackTrace();
		}
		return config;
	}
}
