package com.example.demo.utils.framework.serialization;

import com.alibaba.fastjson.JSON;

public class JsonUtil
{
	public static Object toObject(String json)
	{
		Object obj = null;
		obj = JSON.parse(json);
		return obj;
	}
	
	public static <T> T toObject(String json, Class<T> clazz)
	{
		T t = null;
		t = JSON.parseObject(json, clazz);
		return t;
	}
	
	public static String toString(Object object)
	{
		String value = null;
		value = JSON.toJSONString(object);
		return value;
	}
	
	public static String toStringWithDateFormat(Object object, String format)
	{
		//SerializerFeature.WriteDateUseDateFormat
		String value = null;
		value = JSON.toJSONStringWithDateFormat(object, format);
		return value;
	}
}