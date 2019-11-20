package com.example.demo.utils.common.utils;


import com.example.demo.utils.framework.http.ResultData;
import com.example.demo.utils.framework.http.ResultDataCode;

/**
 * @ProjectName：
 * 
 * @FileName:ResultBaseUtil.java
 */
public class ResultDataUtil {
	/*
	 * public static ResultData getErrorResultData(String msg) { ResultData
	 * resultData = new ResultData();
	 * resultData.setCode(ResultDataCode.FAIL.getValue());
	 * resultData.setStatus("fail"); resultData.setMessage(msg);
	 * resultData.setData(null); return resultData; }
	 * 
	 * public static ResultData getErrorResultData(String msg,ResultDataCode
	 * code) { ResultData resultData = new ResultData();
	 * resultData.setCode(code.getValue()); resultData.setStatus("fail");
	 * resultData.setMessage(msg); resultData.setData(null); return resultData;
	 * }
	 * 
	 * public static ResultData getSuccessResultData(String msg, Object data) {
	 * ResultData resultData = new ResultData();
	 * resultData.setCode(ResultDataCode.SUCCESS.getValue());
	 * resultData.setStatus("success"); if (msg != null) {
	 * resultData.setMessage(msg); } resultData.setData(data); return
	 * resultData; }
	 */
	public static <TResult> ResultData<TResult> getErrorResult(String msg, TResult data) {
		ResultData<TResult> resultData = new ResultData<TResult>();
		resultData.setCode(ResultDataCode.FAIL.getValue());
		resultData.setStatus("fail");
		resultData.setMessage(msg);
		resultData.setData(data);
		return resultData;
	}

	public static <TResult> ResultData<TResult> getErrorResult(String msg) {
		ResultData<TResult> resultData = new ResultData<TResult>();
		resultData.setCode(ResultDataCode.FAIL.getValue());
		resultData.setStatus("fail");
		resultData.setMessage(msg);
		resultData.setData(null);
		return resultData;
	}

	public static <TResult> ResultData<TResult> getErrorResult(String msg, ResultDataCode resultDataCode) {
		ResultData<TResult> resultData = new ResultData<TResult>();
		resultData.setCode(resultDataCode.getValue());
		resultData.setStatus("fail");
		resultData.setMessage(msg);
		resultData.setData(null);
		return resultData;
	}

	public static <TResult> ResultData<TResult> getSuccessResult(TResult data) {
		return getSuccessResult(null, data);
	}

	public static <TResult> ResultData<TResult> getSuccessResult(String msg, TResult data) {
		ResultData<TResult> resultData = new ResultData<TResult>();
		resultData.setCode(ResultDataCode.SUCCESS.getValue());
		resultData.setStatus("success");
		if (msg != null) {
			resultData.setMessage(msg);
		}
		resultData.setData(data);
		return resultData;
	}

}
