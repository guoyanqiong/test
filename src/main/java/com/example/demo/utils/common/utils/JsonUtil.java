package com.example.demo.utils.common.utils;


import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Title.json工具类 <br>
 * Description.
 * <p>
 * Copyright: Copyright (c) 2014-7-31 下午3:01:33
 * <p>
 * Company: 联嘉云贸易有限公司
 * <p>
 * Author: duyujie@uni2uni-js.com
 * <p>
 * Version: 1.0
 * <p>
 */
public class JsonUtil {
	private static Logger log = Logger.getLogger(JsonUtil.class);
	/**
	 * 从json串转换成实体对象
	 * 
	 * @param jsonObjStr
	 *            e.g. {'name':'get','dateAttr':'2009-11-12'}
	 * @param clazz
	 *            Person.class
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getDtoFromJsonObjStr(String jsonObjStr, Class clazz) {
		try{
			return JSONObject.toBean(JSONObject.fromObject(jsonObjStr), clazz);
		}catch(Exception e){
			log.error("json:"+jsonObjStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
	}

	/**
	 * 从json串转换成实体对象，并且实体集合属性存有另外实体Bean
	 * 
	 * @param jsonObjStr
	 *            e.g. {'data':[{'name':'get'},{'name':'set'}]}
	 * @param clazz
	 *            e.g. MyBean.class
	 * @param classMap
	 *            e.g. classMap.put("data", Person.class)
	 * @return Object
	 */
	@SuppressWarnings("rawtypes")
	public static Object getDtoFromJsonObjStr(String jsonObjStr, Class clazz,
			Map classMap) {
		try{
			return JSONObject.toBean(JSONObject.fromObject(jsonObjStr), clazz,
					classMap);
		}catch(Exception e){
			log.error("json:"+jsonObjStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
		
	}

	/**
	 * 把一个json数组串转换成普通数组
	 * 
	 * @param jsonArrStr
	 *            e.g. ['get',1,true,null]
	 * @return Object[]
	 */
	public static Object[] getArrFromJsonArrStr(String jsonArrStr) {
		try{
			return JSONArray.fromObject(jsonArrStr).toArray();
		}catch(Exception e){
			log.error("json:"+jsonArrStr+",exception:"+e.getMessage());
			return null;
		}
		
	}

	/**
	 * 把一个json数组串转换成实体数组
	 * 
	 * @param jsonArrStr
	 *            e.g. [{'name':'get'},{'name':'set'}]
	 * @param clazz
	 *            e.g. Person.class
	 * @return Object[]
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] getDtoArrFromJsonArrStr(String jsonArrStr,
			Class clazz) {
		try{
			JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
			Object[] objArr = new Object[jsonArr.size()];
			for (int i = 0; i < jsonArr.size(); i++) {
				objArr[i] = JSONObject.toBean(jsonArr.getJSONObject(i), clazz);
			}
			return objArr;
		}catch(Exception e){
			log.error("json:"+jsonArrStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
		
	}
	@SuppressWarnings("rawtypes")
	public static List<Object> getDtoArrFromJsonArrListStr(String jsonArrStr,
			Class clazz) {
		try{
			JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
			JSONArray jsonArray=(JSONArray)jsonArr.getJSONArray(0);
			List<Object> objArr = new ArrayList<Object>();
			for (int i = 0; i < jsonArr.size(); i++) {
				objArr.add(JSONObject.toBean(jsonArray.getJSONObject(i), clazz));
			}
			return objArr;
		}catch(Exception e){
			log.error("json:"+jsonArrStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
		
	}
	/**
	 * 把一个json数组串转换成实体数组，且数组元素的属性含有另外实例Bean
	 * 
	 * @param jsonArrStr
	 *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
	 * @param clazz
	 *            e.g. MyBean.class
	 * @param classMap
	 *            e.g. classMap.put("data", Person.class)
	 * @return Object[]
	 */
	@SuppressWarnings("rawtypes")
	public static Object[] getDtoArrFromJsonArrStr(String jsonArrStr,
			Class clazz, Map classMap) {
		try{
			JSONArray array = JSONArray.fromObject(jsonArrStr);
			Object[] obj = new Object[array.size()];
			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				obj[i] = JSONObject.toBean(jsonObject, clazz, classMap);
			}
			return obj;
		}catch(Exception e){
			log.error("json:"+jsonArrStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
		
	}

	/**
	 * 把一个json数组串转换成存放普通类型元素的集合
	 * 
	 * @param jsonArrStr
	 *            e.g. ['get',1,true,null]
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getListFromJsonArrStr(String jsonArrStr) {
		try{
			JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
			List list = new ArrayList();
			for (int i = 0; i < jsonArr.size(); i++) {
				list.add(jsonArr.get(i));
			}
			return list;
		}catch(Exception e){
			log.error("json:"+jsonArrStr+",exception:"+e.getMessage());
			return null;
		}
		
	}

	/**
	 * 把一个json数组串转换成集合，且集合里存放的为实例Bean
	 * 
	 * @param jsonArrStr
	 *            e.g. [{'name':'get'},{'name':'set'}]
	 * @param clazz
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getListFromJsonArrStr(String jsonArrStr, Class clazz) {
		
		try{
			JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
			List list = new ArrayList();
			for (int i = 0; i < jsonArr.size(); i++) {
				list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));
			}
			return list;
		}catch(Exception e){
			log.error("json:"+jsonArrStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
	}

	/**
	 * 把一个json数组串转换成集合，且集合里的对象的属性含有另外实例Bean
	 * 
	 * @param jsonArrStr
	 *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
	 * @param clazz
	 *            e.g. MyBean.class
	 * @param classMap
	 *            e.g. classMap.put("data", Person.class)
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getListFromJsonArrStr(String jsonArrStr, Class clazz,
			Map classMap) {
		
		try{
			JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
			List list = new ArrayList();
			for (int i = 0; i < jsonArr.size(); i++) {
				list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz,
						classMap));
			}
			return list;
		}catch(Exception e){
			log.error("json:"+jsonArrStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
	}

	/**
	 * 把json对象串转换成map对象
	 * 
	 * @param jsonObjStr
	 *            e.g. {'name':'get','int':1,'double',1.1,'null':null}
	 * @return Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getMapFromJsonObjStr(String jsonObjStr) {
		
		try{
			Map map = new HashMap();
			if(jsonObjStr.equals("0")){
				return map;
			}
			JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				map.put(key, jsonObject.get(key));
			}
			return map;
		}catch(Exception e){
			log.error("json:"+jsonObjStr+",exception:"+e.getMessage());
			return null;
		}
	}

	/**
	 * 把json对象串转换成map对象，且map对象里存放的为其他实体Bean
	 * 
	 * @param jsonObjStr
	 *            e.g. {'data1':{'name':'get'},'data2':{'name':'set'}}
	 * @param clazz
	 *            e.g. Person.class
	 * @return Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getMapFromJsonObjStr(String jsonObjStr, Class clazz) {
		try{
			JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);

			Map map = new HashMap();
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				map.put(key,
						JSONObject.toBean(jsonObject.getJSONObject(key), clazz));
			}
			return map;
		}catch(Exception e){
			log.error("json:"+jsonObjStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
		
	}

	/**
	 * {"0":[12,23],"1":[12,23]}
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr){
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonStr);
		for(Object k : json.keySet()){
			Object v = json.get(k);
			if(v instanceof JSONArray){
				List<String> list = new ArrayList<String>();

				for(int i=0;i<((JSONArray) v).size();i++)
				{
					String temp = ((JSONArray) v).get(i).toString();
					list.add(temp);
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}


	/**
	 * 把json对象串转换成map对象，且map对象里存放的其他实体Bean还含有另外实体Bean
	 * 
	 * @param jsonObjStr
	 *            e.g. {'mybean':{'data':[{'name':'get'}]}}
	 * @param clazz
	 *            e.g. MyBean.class
	 * @param classMap
	 *            e.g. classMap.put("data", Person.class)
	 * @return Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getMapFromJsonObjStr(String jsonObjStr, Class clazz,
			Map classMap) {
		try{
			JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);

			Map map = new HashMap();
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				map.put(key, JSONObject.toBean(jsonObject.getJSONObject(key),
						clazz, classMap));
			}
			return map;
		}catch(Exception e){
			log.error("json:"+jsonObjStr+",clazz:"+clazz+",exception:"+e.getMessage());
			return null;
		}
	}

	/**
	 * 把实体Bean、Map对象、数组、列表集合转换成Json串
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static String getJsonStr(Object obj) {
		try{
			String jsonStr = null;
			// Json配置
			JsonConfig jsonCfg = new JsonConfig();

			// 注册日期处理器
			jsonCfg.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor());
			if (obj == null) {
				return "{}";
			}

			if (obj instanceof Collection || obj instanceof Object[]) {
				jsonStr = JSONArray.fromObject(obj, jsonCfg).toString();
			} else {
				jsonStr = JSONObject.fromObject(obj, jsonCfg).toString();
			}
			return jsonStr;
		}catch(Exception e){
			log.error("exception:"+e.getMessage());
			return "";
		}

	}

	/**
	 * 把json串、数组、集合(collection map)、实体Bean转换成XML XMLSerializer API：
	 * http://json-lib
	 * .sourceforge.net/apidocs/net/sf/json/xml/XMLSerializer.html 具体实例请参考：
	 * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/
	 * TestXMLSerializer_writes.html
	 * http://json-lib.sourceforge.net/xref-test/net
	 * /sf/json/xml/TestXMLSerializer_writes.html
	 *
	 * @param obj
	 * @return
	 * @throws Exception
	 *             String
	 */
	public static String getXMLFromObj(Object obj) {
		XMLSerializer xmlSerial = new XMLSerializer();

		// Json配置
		JsonConfig jsonCfg = new JsonConfig();

		// 注册日期处理器
		jsonCfg.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());

		if ((String.class.isInstance(obj) && String.valueOf(obj)
				.startsWith("["))
				|| obj.getClass().isArray()
				|| Collection.class.isInstance(obj)) {
			JSONArray jsonArr = JSONArray.fromObject(obj, jsonCfg);
			return xmlSerial.write(jsonArr);
		} else {
			JSONObject jsonObj = JSONObject.fromObject(obj, jsonCfg);
			return xmlSerial.write(jsonObj);
		}
	}

	/**
	 * 从XML转json串
	 * 
	 * @param xml
	 * @return String
	 */
	public static String getJsonStrFromXML(String xml) {
		XMLSerializer xmlSerial = new XMLSerializer();
		return String.valueOf(xmlSerial.read(xml));
	}

	@SuppressWarnings("unused")
	private static void setDataFormat2JAVA() {
		// 设定日期转换格式
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd",
						"yyyy-MM-dd HH:mm:ss" }));
	}

	/**
	 * 将Map对象通过反射机制转换成Bean对象
	 *
	 * @param map 存放数据的map对象
	 * @param clazz 待转换的class
	 * @return 转换后的Bean对象
	 * @throws Exception 异常
	 */
	public static Object mapToBean(Map<String, Object> map, Class<?> clazz) throws Exception {
		Object obj = clazz.newInstance();
		if(map != null && map.size() > 0) {
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				String propertyName = entry.getKey();       //属性名
				Object value = entry.getValue();
				String setMethodName = "set"
						+ propertyName.substring(0, 1).toUpperCase()
						+ propertyName.substring(1);
				Field field = getClassField(clazz, propertyName);
				if(field==null)
					continue;
				Class<?> fieldTypeClass = field.getType();
				value = convertValType(value, fieldTypeClass);
				try{
					clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
				}catch(NoSuchMethodException e){
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/**
	 * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类)
	 *
	 * @param clazz 指定的class
	 * @param fieldName 字段名称
	 * @return Field对象
	 */
	private static Field getClassField(Class<?> clazz, String fieldName) {
		if( Object.class.getName().equals(clazz.getName())) {
			return null;
		}
		Field []declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		Class<?> superClass = clazz.getSuperclass();
		if(superClass != null) {// 简单的递归一下
			return getClassField(superClass, fieldName);
		}
		return null;
	}

	/**
	 * 将Object类型的值，转换成bean对象属性里对应的类型值
	 *
	 * @param value Object对象值
	 * @param fieldTypeClass 属性的类型
	 * @return 转换后的值
	 */
	private static Object convertValType(Object value, Class<?> fieldTypeClass) {
		Object retVal = null;
		if(Long.class.getName().equals(fieldTypeClass.getName())
				|| long.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Long.parseLong(value.toString());
		} else if(Integer.class.getName().equals(fieldTypeClass.getName())
				|| int.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Integer.parseInt(value.toString());
		} else if(Float.class.getName().equals(fieldTypeClass.getName())
				|| float.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Float.parseFloat(value.toString());
		} else if(Double.class.getName().equals(fieldTypeClass.getName())
				|| double.class.getName().equals(fieldTypeClass.getName())) {
			retVal = Double.parseDouble(value.toString());
		}else if(BigDecimal.class.getName().equals(fieldTypeClass.getName())
				|| BigDecimal.class.getName().equals(fieldTypeClass.getName())) {
			retVal = BigDecimal.valueOf(Double.parseDouble(value.toString()));
		} else {
			retVal = value;
		}
		return retVal;
	}
}

