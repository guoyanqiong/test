package com.example.demo.utils.framework.http;

public enum ResultDataCode {
	/** 失败 */
	FAIL(-1, "失败"),
	/** 成功 */
	SUCCESS(0, "成功"),
	/** 提交数据异常 */
	CODE100(100, "提交数据异常"),
	/** 无权限 */
	CODE403(403, "无权限"),
	/** 错误 */
	CODE500(500, "错误");

	public int getValue() {
		return this.status;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param status
	 * @return
	 */
	public static ResultDataCode parse(int status) {
		ResultDataCode s = null;
		for (ResultDataCode o : ResultDataCode.values()) {
			if (o.getValue() == status) {
				s = o;
				break;
			}
		}
		return s;
	}

	private ResultDataCode(int status, String name) {
		this.status = status;
		this.name = name;
	}

	private int status;
	private String name;

}
