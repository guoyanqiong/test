package com.example.demo.utils.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Send {
   /**
    *<p>发送点击</p> 
    *@param   click_url
    *@return
    */
   public String send_click(String click_url){
	    HttpURLConnection hc = null;
	    String rep = null;
	    try {
	        hc = (HttpURLConnection)new URL(click_url).openConnection();
	    	hc.setConnectTimeout(10*1000);
	    	hc.setRequestMethod("GET");
			if(hc.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
				rep = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (hc != null) {
	            hc.disconnect();
	          }
		}
	   return rep;
   }
   
   /**
    *<p>POST方式发送点击</p> 
    *@param   click_url
    *@param   param
    *@return
    */
   public String sendPost_click(String click_url,String param){
	   OutputStreamWriter out = null;
       BufferedReader in = null;
       String result = "";
       try {
           URL realUrl = new URL(click_url);
           // 打开和URL之间的连接
           URLConnection conn = realUrl.openConnection();
           // 设置通用的请求属性
           conn.setRequestProperty("accept", "*/*");
           conn.setRequestProperty("connection", "Keep-Alive");
           conn.setRequestProperty("user-agent",
                   "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
           // 发送POST请求必须设置如下两行
           conn.setDoOutput(true);
           conn.setDoInput(true);
           // 获取URLConnection对象对应的输出流
           out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
           // 发送请求参数
           out.write(param);
           // flush输出流的缓冲
           out.flush();
           // 定义BufferedReader输入流来读取URL的响应
           in = new BufferedReader(
                   new InputStreamReader(conn.getInputStream(),"UTF-8"));
           String line;
           while ((line = in.readLine()) != null) {
               result += line;
           }
       } catch (Exception e) {
           System.out.println("发送 POST 请求出现异常！"+e);
           e.printStackTrace();
       }
       //使用finally块来关闭输出流、输入流
       finally{
           try{
               if(out!=null){
                   out.close();
               }
               if(in!=null){
                   in.close();
               }
           }
           catch(IOException ex){
               ex.printStackTrace();
           }
       }
       return result;
   }
   
   /**
    *<p>发送激活</p> 
    *@param   callback_url
    *@return
    */
   public String send_activation(String callback_url){
	   HttpURLConnection hc = null;
	    String rep = null;
	    try {
	        hc = (HttpURLConnection)new URL(callback_url).openConnection();
	    	hc.setConnectTimeout(5*1000);
	    	hc.setRequestMethod("GET");
			if(hc.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
				rep = br.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (hc != null) {
	            hc.disconnect();
	          }
		}
	   return rep;
   }
}
