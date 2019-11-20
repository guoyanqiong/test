package com.example.demo.utils.framework.log;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Title:Loginfo.java
 */
public class Loginfo implements Serializable {

	/**
	 * 向远程传消息时会用到.
	 */
	private static final long serialVersionUID = 1L;

  
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LogInfoLevel getLevel() {
		return level;
	}

	public void setLevel(LogInfoLevel level) {
		this.level = level;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Boolean getFixed() {
		return fixed;
	}

	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

	public String getFixedInfo() {
		return fixedInfo;
	}

	public void setFixedInfo(String fixedInfo) {
		this.fixedInfo = fixedInfo;
	}

	public Date getFixedDate() {
		return fixedDate;
	}

	public void setFixedDate(Date fixedDate) {
		this.fixedDate = fixedDate;
	}

	private String id;
	private LogInfoLevel level;//
	private String appName;// xml
	private String category;//
	private String ip;//
	private String message;// 当标题用
	private String body;// /
	private Date createTime;// /
	private String source;
	private int elapsedTime;
	private Boolean fixed; // 是否修复
	private String fixedInfo;// 修复说明
	private Date fixedDate;// 修复时间

	@Override
	public String toString() {

		return String
				.format(
				"LogLevel:{%s}, AppName:{%s}, Category:{%s}, IP:{%s}, Message:{%s}, Body:{%s}, Source:{%s}, ElapsedTime:{%s}, CreateTime:{%s}, Fixed:{%s}, FixedInfo:{%s}, FixedDate:{%s}",
						this.level.toString(), this.appName, this.category,
						this.ip, this.message, this.body, this.source,
						String.valueOf(this.elapsedTime),
						this.createTime.toString(), this.fixed.toString(),
						this.fixedInfo, this.fixedDate.toString());

	}

}
