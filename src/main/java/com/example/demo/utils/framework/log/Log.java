package com.example.demo.utils.framework.log;

import com.example.demo.utils.framework.core.FrameWorkConfig;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
//import cn.adwan.framework.queue.Queue;

/**
 * 
 * @Title:logForCore.java
 * @tag:error log只记录error信息，log.log记录处debug外的所有信息，debug只在console内显示
 */

public class Log {
	public static final String PROFILE = "log4j.properties";
	private static Log logInstance;

	static {
		try {
			logInstance = new Log();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	private Logger log4j;
	private LogConfig config;
//	private Queue<Loginfo> queue;

	private Log() throws Throwable {
		try {
			log4j = LogManager.getLogger(Log.class);
			URL url = ClassLoader.getSystemResource("log4j.properties");
			config = FrameWorkConfig.getLogConfig();
//			if (config.getRemote())
//				queue = new Queue<Loginfo>(Loginfo.class);
			String path = "";
			if (null != url) {
				path = url.getPath();
			}
			PropertyConfigurator.configure(path);
			BasicConfigurator.configure();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	private void log(String level, Object msg, Throwable e) {
		if (null != log4j) {
			log4j.log((Priority) Level.toLevel(level), msg, e);
		}
	}

	private void log(String level, Object msg) {
		log(level, msg, null);
	}

	private void log(String level, Throwable e) {
		log(level, null, e);
	}

	public static void logError(String msg) {
		logInstance.log("ERROR", msg);
	}

	public static void logError(Throwable e) {
		logInstance.log("ERROR", e);
	}

	public static void logError(String msg, Throwable e) {
		logInstance.log("ERROR", msg, e);
	}

	public static void logWarn(String msg) {
		logInstance.log("WARN", msg);
	}

	public static void logWarn(Throwable e) {
		logInstance.log("WARN", e);
	}

	public static void logWarn(String msg, Throwable e) {
		logInstance.log("WARN", msg, e);
	}

	public static void logInfo(String msg) {
		logInstance.log("INFO", msg);
	}

	public static void logInfo(Throwable e) {
		logInstance.log("INFO", e);
	}

	public static void logInfo(String msg, Throwable e) {
		logInstance.log("INFO", msg, e);
	}

	public static void logDebug(String msg) {
		logInstance.log("DEBUG", msg);
	}

	public static void logDebug(Throwable e) {
		logInstance.log("DEBUG", e);
	}

	public static void logDebug(String msg, Throwable e) {
		logInstance.log("DEBUG", msg, e);
	}

	private static Loginfo initLoginfo(String title, String Body,
                                       String category, LogInfoLevel lever) {
		Loginfo infoLoginfo = new Loginfo();
		infoLoginfo.setBody(Body);
		infoLoginfo.setMessage(title);
		infoLoginfo.setCategory(category);
		infoLoginfo.setCreateTime(new Date());
		infoLoginfo.setLevel(lever);
		infoLoginfo.setFixed(false);
		infoLoginfo.setFixedInfo("");// 初始设置默认值为空
		infoLoginfo.setFixedDate(new Date());

		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			infoLoginfo.setIp(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		return infoLoginfo;
	}

//	private static void logRemote(Loginfo loginfo) throws Exception {
//		if (logInstance.queue != null)
//			logInstance.queue.send(loginfo);
//		else
//			throw new Exception("远程消息服务器出错！无法连接");
//	}

	public static void logInfo(String title, String Body, String category,
			LogInfoLevel lever) {
		try {
			Loginfo infoLoginfo = initLoginfo(title, Body, category, lever);
			if (logInstance.config.getRemote()) {
//				logRemote(infoLoginfo);
			} else if (logInstance.config.getLocal()) {
				switch (lever) {
				case Info:
					logInfo(Body);
					break;
				case Debug:
					logDebug(Body);
					break;
				case Error:
					logError(Body);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
