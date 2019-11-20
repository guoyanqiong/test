/**
 * 
 */
package com.example.demo.utils.framework.redis;

import java.util.List;

/**
 *
 */
public class RedisConfig {

	private String pwd;
	private String name;

	private int maxactive;
	private int maxIde;
	private int maxwait;
	private int timeout;
	

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	private List<RedisElement> redislist;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxactive() {
		return maxactive;
	}

	public void setMaxactive(int maxactive) {
		this.maxactive = maxactive;
	}

	public int getMaxIde() {
		return maxIde;
	}

	public void setMaxIde(int maxIde) {
		this.maxIde = maxIde;
	}

	public int getMaxwait() {
		return maxwait;
	}

	public void setMaxwait(int maxwait) {
		this.maxwait = maxwait;
	}

	public List<RedisElement> getRedislist() {
		return redislist;
	}

	public void setRedislist(List<RedisElement> redislist) {
		this.redislist = redislist;
	}

}
