package com.example.demo.utils.framework.http;


import com.example.demo.utils.common.entity.BaseEntity;

//返回json中去除null字段
public class ResultData<T> extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code = 0;

	private String message = "成功";

	private String status = "success";

	private T data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
