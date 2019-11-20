package com.example.demo.utils.common.utils;

import com.example.demo.utils.common.entity.Page;
import com.example.demo.utils.framework.http.HttpModel;
import com.example.demo.utils.framework.http.HttpRequestExecutor;
import com.example.demo.utils.framework.http.PostRequestBuilder;
import com.example.demo.utils.framework.http.RequestBuilder;
import com.example.demo.utils.framework.http.ResultData;
import com.example.demo.utils.framework.http.ResultDataCode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;

/**
 * 封装对json请求数据的工具类
 * 
 * @author Administrator
 *
 */
public class ResultDataHelper {

	/**
	 * 记录失败日志
	 * 
	 * @param log
	 */
	private static void log(String log) {
		System.out.println(log);
	}

	/**
	 * 记录失败日志
	 * 
	 * @param resultData
	 */
	private static <TParam, TResult> void log(String url, TParam param,
			ResultData<TResult> resultData) {
		// Result是不能为空的
		if (resultData == null) {
			log("客户端调用返回值异常:" + url + " 参数:" + JsonUtil.getJsonStr(param)
					+ " 结果为空");
		} else if (resultData.getCode() != ResultDataCode.SUCCESS.getValue()) {
			log(resultData.getCode() + "客户端调用返回不成功:" + url + " 参数:"
					+ JsonUtil.getJsonStr(param) + " 信息:"
					+ resultData.getMessage() + " 结果:"
					+ JsonUtil.getJsonStr(resultData));
		}
	}

	/**
	 * get方式获取请求的数据,若有参数需要在url中体现
	 * 
	 * @return
	 * 
	 **/
	public static <TParam, TResult> ResultData<TResult> getGetResultData(
			String url, Class<TResult> resultClass){
		HttpRequestExecutor executor = HttpRequestExecutor.getClient();
		HttpModel model = null;
		ObjectMapper mapping = new ObjectMapper();
		ResultData<TResult> resultData = new ResultData<TResult>();
		try {
			model = executor.execute(RequestBuilder.get(url).build());
			if (model != null && model.getCode() == 200) {
				resultData = mapping.readValue(
						model.getBody(),
						mapping.getTypeFactory().constructParametricType(
								resultData.getClass(), resultClass));
				log(url, null, resultData);
			} else {
				log("客户端调用出错:" + url + " 出错,结果:" + JsonUtil.getJsonStr(model));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}

		return resultData;
	}

	/**
	 * get方式获取请求的数据,若有参数需要在url中体现
	 * 
	 * @return
	 * 
	 **/
	public static <TParam, TResult> TResult getGetResult(String url,
			Class<TResult> resultClass){

		ResultData<TResult> resultData = new ResultData<TResult>();
		TResult result = null;
		resultData = getGetResultData(url, resultClass);
		if (resultData != null) {
			result = resultData.getData();
		}
		return result;
	}

	/**
	 * post方式获得请求数据
	 * 
	 * @return
	 * 
	 **/
	public static <TParam, TResult> ResultData<TResult> getPostResultData(
			String url, TParam param, Class<TResult> resultClass) {
		HttpRequestExecutor executor = HttpRequestExecutor.getClient();
		HttpModel model = null;
		ObjectMapper mapping = new ObjectMapper();
		ResultData<TResult> resultData = new ResultData<TResult>();
		String jsonParam = null;
		try {
			jsonParam = mapping.writerWithType(param.getClass())
					.writeValueAsString(param);
			log("客户端请求:" + url);
			model = executor.execute(PostRequestBuilder.postRequest(url)
					.withData(jsonParam)
					.withHeader("Content-Type", "application/json").build());
			if (model != null && model.getCode() == 200) {
				resultData = mapping.readValue(
						model.getBody(),
						mapping.getTypeFactory().constructParametricType(
								resultData.getClass(), resultClass));
				log(url, param, resultData);
			} else {
				log("客户端调用出错:" + url + " 参数:" + JsonUtil.getJsonStr(param)
						+ " 结果:" + JsonUtil.getJsonStr(model));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}

		return resultData;
	}

	/**
	 * post方式获得请求数据
	 * 
	 * @return
	 * 
	 **/
	public static <TParam, TResult> TResult getPostResult(String url,
			TParam param, Class<TResult> resultClass) {
		ResultData<TResult> resultData = new ResultData<TResult>();
		TResult result = null;
		resultData = getPostResultData(url, param, resultClass);
		if (resultData != null) {
			result = resultData.getData();
		}
		return result;
	}

	/**
	 * post方式获得请求数据
	 * 
	 * @param url
	 * @param params
	 *            集合的类型的参数(需至少一个成员)
	 * @param tClass
	 * @return
	 */
	public static <TParam, TResult> ResultData<TResult> getPostResultData(
			String url, ArrayList<TParam> params, Class<TResult> tClass) {
		HttpRequestExecutor executor = HttpRequestExecutor.getClient();
		HttpModel model = null;
		ObjectMapper mapping = new ObjectMapper();
		ResultData<TResult> resultData = new ResultData<TResult>();
		String jsonParam = null;
		try {
			jsonParam = mapping.writerWithType(
					mapping.getTypeFactory().constructCollectionType(
							ArrayList.class, params.get(0).getClass()))
					.writeValueAsString(params);
			model = executor.execute(PostRequestBuilder.postRequest(url)
					.withData(jsonParam)
					.withHeader("Content-Type", "application/json").build());
			if (model != null && model.getCode() == 200) {
				resultData = mapping.readValue(
						model.getBody(),
						mapping.getTypeFactory().constructParametricType(
								resultData.getClass(), tClass));
				log(url, null, resultData);
			} else {
				log("客户端调用出错:" + url + " 参数:" + JsonUtil.getJsonStr(params)
						+ " 结果:" + JsonUtil.getJsonStr(model));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}

		return resultData;
	}

	/**
	 * post方式获得请求数据
	 * 
	 * @param url
	 * @param params
	 *            集合的类型的参数(需至少一个成员)
	 * @param tClass
	 * @return
	 */
	public static <TParam, TResult> TResult getPostResult(String url,
			ArrayList<TParam> params, Class<TResult> tClass){
		ResultData<TResult> resultData = new ResultData<TResult>();
		TResult result = null;
		resultData = getPostResultData(url, params, tClass);
		if (resultData != null) {
			result = resultData.getData();
		}
		return result;
	}

	/**
	 * get方式获取请求的数据,若有参数需要在url中体现
	 * 
	 * @return
	 * 
	 **/
	public static <TParam, TResult> ResultData<ArrayList<TResult>> getGetListResultData(
			String url, Class<TResult> resultClass) {
		HttpRequestExecutor executor = HttpRequestExecutor.getClient();
		HttpModel model = null;
		ObjectMapper mapping = new ObjectMapper();
		ResultData<ArrayList<TResult>> resultData = new ResultData<ArrayList<TResult>>();
		try {
			model = executor.execute(RequestBuilder.get(url).build());
			if (model != null && model.getCode() == 200) {
				resultData = mapping.readValue(
						model.getBody(),
						mapping.getTypeFactory().constructParametricType(
								resultData.getClass(),
								mapping.getTypeFactory().constructCollectionType(
										ArrayList.class, resultClass)));
				log(url, null, resultData);
			} else {
				log("客户端调用出错:" + url + " 结果:" + JsonUtil.getJsonStr(model));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
		
		return resultData;
	}

	/**
	 * get方式获取请求的数据,若有参数需要在url中体现
	 * 
	 * @return
	 * 
	 **/
	public static <TParam, TResult> ArrayList<TResult> getGetListResult(
			String url, Class<TResult> resultClass){
		ResultData<ArrayList<TResult>> resultData = new ResultData<ArrayList<TResult>>();
		ArrayList<TResult> result = null;
		resultData = getGetListResultData(url, resultClass);
		if (resultData != null) {
			result = resultData.getData();
		}
		return result;
	}

	/**
	 * post方式获得请求数据
	 * 
	 * @return
	 * 
	 **/
	public static <TParam, TResult> ResultData<ArrayList<TResult>> getPostListResultData(
			String url, TParam param, Class<TResult> resultClass){
		HttpRequestExecutor executor = HttpRequestExecutor.getClient();
		HttpModel model = null;
		ObjectMapper mapping = new ObjectMapper();
		ResultData<ArrayList<TResult>> resultData = new ResultData<ArrayList<TResult>>();
		String jsonParam = null;
		try {
			jsonParam = mapping.writerWithType(param.getClass())
					.writeValueAsString(param);
			model = executor.execute(PostRequestBuilder.postRequest(url)
					.withData(jsonParam)
					.withHeader("Content-Type", "application/json").build());
			if (model != null && model.getCode() == 200) {
				resultData = mapping.readValue(
						model.getBody(),
						mapping.getTypeFactory().constructParametricType(
								resultData.getClass(),
								mapping.getTypeFactory().constructCollectionType(
										ArrayList.class, resultClass)));
				log(url, param, resultData);
			} else {
				log("客户端调用出错:" + url + " 参数:" + JsonUtil.getJsonStr(param) + " 结果:"
						+ JsonUtil.getJsonStr(model));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
		
		return resultData;
	}

	/**
	 * post方式获得请求数据 分页用
	 * 
	 * @param url
	 *            :请求地址
	 * @param param
	 *            :请求参数
	 * @param resultClass
	 *            : Page中ArrayList 泛型
	 * */
	public static <TParam, TResult> ResultData<Page<TResult>> getPostPageResultData(
			String url, TParam param, Class<TResult> resultClass){
		HttpRequestExecutor executor = HttpRequestExecutor.getClient();
		HttpModel model = null;
		ObjectMapper mapping = new ObjectMapper();
		ResultData<Page<TResult>> resultData = new ResultData<Page<TResult>>();
		Page<TResult> page = new Page<TResult>();
		String jsonParam = null;
		try {
			jsonParam = mapping.writerWithType(param.getClass())
					.writeValueAsString(param);
			model = executor.execute(PostRequestBuilder.postRequest(url)
					.withData(jsonParam)
					.withHeader("Content-Type", "application/json").build());
			if (model != null && model.getCode() == 200) {
				resultData = mapping.readValue(
						model.getBody(),
						mapping.getTypeFactory().constructParametricType(
								resultData.getClass(),
								mapping.getTypeFactory().constructParametricType(
										page.getClass(), resultClass)));
				log(url, param, resultData);
			} else {
				log("客户端调用出错:" + url + " 参数:" + JsonUtil.getJsonStr(param) + " 结果:"
						+ JsonUtil.getJsonStr(model));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
		
		return resultData;
	}

	/**
	 * post方式获得请求数据
	 * 
	 * @return
	 * 
	 **/
	public static <TParam, TResult> ArrayList<TResult> getPostListResult(
			String url, TParam param, Class<TResult> resultClass){
		ResultData<ArrayList<TResult>> resultData = new ResultData<ArrayList<TResult>>();
		ArrayList<TResult> result = null;
		resultData = getPostListResultData(url, param, resultClass);
		if (resultData != null) {
			result = resultData.getData();
		}
		return result;
	}

	/**
	 * post方式获得请求数据
	 * 
	 * @param url
	 * @param params
	 *            集合的类型的参数(需至少一个成员)
	 * @param tClass
	 * @return
	 */
	public static <TParam, TResult> ResultData<ArrayList<TResult>> getPostListResultData(
			String url, ArrayList<TParam> params, Class<TResult> resultClass){
		HttpRequestExecutor executor = HttpRequestExecutor.getClient();
		HttpModel model = null;
		ObjectMapper mapping = new ObjectMapper();
		ResultData<ArrayList<TResult>> resultData = new ResultData<ArrayList<TResult>>();
		String jsonParam = null;
		try {
			jsonParam = mapping.writerWithType(
					mapping.getTypeFactory().constructCollectionType(
							ArrayList.class, params.get(0).getClass()))
					.writeValueAsString(params);
			model = executor.execute(PostRequestBuilder.postRequest(url)
					.withData(jsonParam)
					.withHeader("Content-Type", "application/json").build());
			if (model != null && model.getCode() == 200) {
				resultData = mapping.readValue(
						model.getBody(),
						mapping.getTypeFactory().constructParametricType(
								resultData.getClass(),
								mapping.getTypeFactory().constructCollectionType(
										ArrayList.class, resultClass)));
				log(url, null, resultData);
			} else {
				log("客户端调用出错:" + url + " 参数:" + JsonUtil.getJsonStr(params)
						+ " 结果:" + JsonUtil.getJsonStr(model));
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
		}
		
		return resultData;
	}

	/**
	 * post方式获得请求数据
	 * 
	 * @param url
	 * @param params
	 *            集合的类型的参数(需至少一个成员)
	 * @param tClass
	 * @return
	 */
	public static <TParam, TResult> ArrayList<TResult> getPostListResult(
			String url, ArrayList<TParam> params, Class<TResult> resultClass){
		ResultData<ArrayList<TResult>> resultData = new ResultData<ArrayList<TResult>>();
		ArrayList<TResult> result = null;
		resultData = getPostListResultData(url, params, resultClass);
		if (resultData != null) {
			result = resultData.getData();
		}
		return result;
	}
}
