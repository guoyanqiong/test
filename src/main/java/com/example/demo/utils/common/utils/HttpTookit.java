package com.example.demo.utils.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.common.entity.IpInfo;
import com.example.demo.utils.framework.ip.IpHelper;
import com.example.demo.utils.framework.redis.Redis;
import com.example.demo.utils.framework.redis.RedisDB;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 网络数据提交类
 *
 * @author
 */
public class HttpTookit {

    private static final CloseableHttpClient httpClient;
    private static final String CHARSET_UTF8 = "UTF-8";
    private static final String CHARSET_GB2312 = "gb2312";
    private static final String CHARSET_GBK = "gbk";

    private static Logger logger = Logger.getLogger(HttpTookit.class);

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000)
                .setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    /**
     * 20      * 发起http get请求获取网页源代码
     * 21      * @param requestUrl     String    请求地址
     * 22      * @return                 String    该地址返回的html字符串
     * 23
     */
    public static String httpRequest(String requestUrl) {
        logger.info("httpRequest_url:" + requestUrl);
        StringBuffer buffer = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpURLConnection httpUrlConn = null;

        try {
            // 建立get请求
            URL url = new URL(requestUrl);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            // 获取输入流
            inputStream = httpUrlConn.getInputStream();
            String encoding = httpUrlConn.getContentEncoding();
            String contextType = httpUrlConn.getContentType();
            String charset = CHARSET_UTF8;
            if (contextType.toLowerCase().contains(CHARSET_UTF8.toLowerCase())) {
                charset = CHARSET_UTF8;
            } else if (contextType.toLowerCase().contains(CHARSET_GB2312.toLowerCase())) {
                charset = CHARSET_GB2312;
            } else if (contextType.toLowerCase().contains(CHARSET_GBK.toLowerCase())) {
                charset = CHARSET_GBK;
            }
            inputStreamReader = new InputStreamReader(inputStream, charset);//utf-8  gb2312
            bufferedReader = new BufferedReader(inputStreamReader);

            // 从输入流读取结果
            buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrlConn != null) {
                httpUrlConn.disconnect();
            }
        }
        logger.info("httpRequest_buffer:" + buffer);
        return buffer.toString();
    }

    public static String getPageContent(String strUrl, String strPostRequest,
                                        int maxLength) {
        // 读取结果网页
        StringBuffer buffer = new StringBuffer();
        System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
        System.setProperty("sun.net.client.defaultReadTimeout", "5000");
        try {
            URL newUrl = new URL(strUrl);
            HttpURLConnection hConnect = (HttpURLConnection) newUrl
                    .openConnection();
            // POST方式的额外数据
            if (strPostRequest.length() > 0) {
                hConnect.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(hConnect
                        .getOutputStream());
                out.write(strPostRequest);
                out.flush();
                out.close();
            }
            // 读取内容

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    hConnect.getInputStream()));
            int ch;
            for (int length = 0; (ch = rd.read()) > -1
                    && (maxLength <= 0 || length < maxLength); length++)
                buffer.append((char) ch);
            String s = buffer.toString();
            s.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
            System.out.println(s);

            rd.close();
            hConnect.disconnect();
            return buffer.toString().trim();
        } catch (Exception e) {
            // return "错误:读取网页失败！";
            //
            return null;


        }
    }


    /**
     * HTTP Get 获取内容
     *
     * @param url 请求的url地址 ?之前的地址
     * @return 页面内容
     */
    public static String doGet(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {

            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String send_click(String click_url) {
        HttpURLConnection hc = null;
        String rep = null;
        try {
            hc = (HttpURLConnection) new URL(click_url).openConnection();
            hc.setConnectTimeout(10 * 1000);
            hc.setRequestMethod("GET");
            if (hc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                rep = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (hc != null) {
                hc.disconnect();
            }
        }
        return rep;
    }

    public static String doPost(String url, Map<String, String> params) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET_UTF8));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doHttpPost(String jsonInfo, String URL) {
        System.out.println("发起的数据:" + jsonInfo);
        byte[] xmlData = jsonInfo.getBytes();
        InputStream instr = null;
        // java.io.ByteArrayOutputStream out = null;
        try {
            URL url = new URL(URL);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("Content-Type", "text/json");
            urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
            System.out.println(String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
            printout.write(xmlData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");
            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                System.out.println("返回空");
            }
            System.out.println("返回数据为:" + ResponseString);
            return ResponseString;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            try {
                // out.close();
                instr.close();

            } catch (Exception ex) {
                return "0";
            }
        }
    }

    public static String doHttpPost(String jsonInfo, String URL, String userName,
                                    String userPass, String contentType) {
        System.out.println("发起的数据:" + jsonInfo);
        byte[] xmlData = jsonInfo.getBytes();
        InputStream instr = null;
        // java.io.ByteArrayOutputStream out = null;
        try {
            URL url = new URL(URL);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            if (contentType.equals("")) {
                urlCon.setRequestProperty("Content-Type", "text/json");
            } else {
                urlCon.setRequestProperty("Content-Type", contentType);
            }
            if (!userName.equals("") && !userPass.equals("")) {
                // 使用base64进行加密
                byte[] tokenByte = Base64
                        .encodeBase64((userName + ":" + userPass).getBytes());
                // 将加密的信息转换为string
                String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0,
                        tokenByte.length);
                // Basic YFUDIBGDJHFK78HFJDHF== token的格式
                String token = tokenStr;
                // 把认证信息发到header中
                urlCon.setRequestProperty("Authorization", "Basic " + token);
            }
            // MultipartEntity mutiEntity = newMultipartEntity();
            urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
            System.out.println(String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
            printout.write(xmlData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");
            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                System.out.println("返回空");
            }
            System.out.println("返回数据为:" + ResponseString);
            return ResponseString;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            try {
                // out.close();
                instr.close();

            } catch (Exception ex) {
                return "0";
            }
        }
    }

    /**
     * 上传图片
     *
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String formUpload(String urlStr, String userName, String userPass,
                                    Map<String, String> textMap, Map<String, String> fileMap) {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            if (!userName.equals("") && !userPass.equals("")) {
                // 使用base64进行加密
                byte[] tokenByte = Base64
                        .encodeBase64((userName + ":" + userPass).getBytes());
                // 将加密的信息转换为string
                String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0,
                        tokenByte.length);
                // Basic YFUDIBGDJHFK78HFJDHF== token的格式
                String token = tokenStr;
                // 把认证信息发到header中
                conn.setRequestProperty("Authorization", "Basic " + token);
            }
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName
                            + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }

            // file
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    if (filename.endsWith(".png")) {
                        contentType = "image/png";
                    }
                    if (contentType == null || contentType.equals("")) {
                        contentType = "application/octet-stream";
                    }

                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName
                            + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    // Patch提交 用于修改offer的状态
    public static String doPatch(String url, String userName, String userPass) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {

            HttpPatch httpPost = new HttpPatch(url);
            // "multipart/form-data; boundary=" + BOUNDARY);
            if (!userName.equals("") && !userPass.equals("")) {
                // 使用base64进行加密
                byte[] tokenByte = Base64
                        .encodeBase64((userName + ":" + userPass).getBytes());
                // 将加密的信息转换为string
                String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0,
                        tokenByte.length);
                // Basic YFUDIBGDJHFK78HFJDHF== token的格式
                String token = tokenStr;
                // 把认证信息发到header中
                httpPost.setHeader("Authorization", "Basic " + token);
            }

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // put 提交 用于修改offer全部信息
    public static String doPut(String json, String url, String userName,
                               String userPass) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {

            HttpPut httpPost = new HttpPut(url);
            // "multipart/form-data; boundary=" + BOUNDARY);
            if (!userName.equals("") && !userPass.equals("")) {
                // 使用base64进行加密
                byte[] tokenByte = Base64
                        .encodeBase64((userName + ":" + userPass).getBytes());
                // 将加密的信息转换为string
                String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0,
                        tokenByte.length);
                // Basic YFUDIBGDJHFK78HFJDHF== token的格式
                String token = tokenStr;
                // 把认证信息发到header中
                httpPost.setHeader("Authorization", "Basic " + token);
            }
            // 将JSON进行UTF-8编码,以便传输中文
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Post提交
    public static String doPost(String json, String url, String userName, String userPass,
                                String contentType) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {

            HttpPost httpPost = new HttpPost(url);
            // "multipart/form-data; boundary=" + BOUNDARY);
            if (!userName.equals("") && !userPass.equals("")) {
                // 使用base64进行加密
                byte[] tokenByte = Base64
                        .encodeBase64((userName + ":" + userPass).getBytes());
                // 将加密的信息转换为string
                String tokenStr = DataTypeChange.bytesSub2String(tokenByte, 0,
                        tokenByte.length);
                // Basic YFUDIBGDJHFK78HFJDHF== token的格式
                String token = tokenStr;
                // 把认证信息发到header中
                httpPost.setHeader("Authorization", "Basic " + token);
            }
            // 将JSON进行UTF-8编码,以便传输中文
            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding("UTF-8");
            s.setContentType(contentType);
            httpPost.setEntity(s);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 文件上传接口
    public static String getPostFile(HttpServletRequest request,
                                     HttpServletResponse response, String filePath)
            throws ServletException, IOException {

        InputStream is = request.getInputStream();
        StringBuffer sb = new StringBuffer();
        int count = 0;
        while (true) {
            int a = is.read();
            sb.append((char) a);
            if (a == '\r')
                count++;
            if (count == 4) {
                is.read();
                break;
            }

        }
        String title = sb.toString();
        System.out.println(title);
        String[] titles = title.split("\r\n");
        String fileName = titles[1].split(";")[2].split("=")[1].replace("\"", "");
        System.out.println(fileName);
        FileOutputStream os = new FileOutputStream(new File(filePath + fileName));
        byte[] ob = new byte[1024];
        int length = 0;
        while ((length = is.read(ob, 0, ob.length)) > 0) {
            os.write(ob, 0, length);
            os.flush();
        }
        os.close();
        is.close();
        return fileName;
    }

    public static String getRequestIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        // ipAddress = request.getRemoteAddr();
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0
                || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
        // String address = request.getHeader("X-Real-IP");
        // if (address != null && address.length() > 0
        // && !"unknown".equalsIgnoreCase(address)) {
        // return address;
        // }
        // return request.getRemoteAddr();
    }

    /**
     * 获取IP信息
     */
    public static IpInfo getIpInfo(HttpServletRequest request) {
        return getIpInfo(getRequestIpAddress(request));
    }

    /**
     * 获取IP信息
     */
    public static IpInfo getIpInfo(String ip) {
        IpInfo ipInfo = Redis.hGetJsonObject("ip_info", ip, IpInfo.class, RedisDB.BaseData);
        if (FunctionUtil.IsNullOrEmpty(ipInfo)) {
            String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
            String jsonStr = httpRequest(url);
            JSONObject jsonObject = null;
            if (!FunctionUtil.IsNullOrEmpty(jsonStr)) {
                jsonObject = JSON.parseObject(jsonStr);
                if (jsonObject.getInteger("code") == 0) {
                    ipInfo = jsonObject.getObject("data", IpInfo.class);
                    Redis.hSetObjectJson("ip_info", ip, ipInfo, RedisDB.BaseData);
                } else {
                    ipInfo = IpHelper.findRegionByIp(ip);
                }
            }
        }
        return ipInfo;
    }

    public static List<String> saveImage(String urlImage, String userId)
            throws Exception {
        String filePathString = "F:\\filedownlaod\\Avatarold";
        // new一个URL对象
        URL url = new URL(urlImage);

        File file = new File(filePathString + "\\images\\xunai\\" + userId);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }

        String fileNameString = urlImage.split("/")[urlImage.split("/").length - 1];
        filePathString = filePathString + "\\" + fileNameString;
//		if (fileNameString.indexOf("mp4") >= 0) {
//			filePathString = filePathString + "\\video\\xunai\\" + fileNameString;
//		} else {
//			if (urlImage.indexOf("videos_pics") >= 0) {
//				filePathString = filePathString + "\\images\\xunai\\videos_pics\\"
//						+ fileNameString;
//			} else {
//				filePathString = filePathString + "\\images\\xunai\\" + userId + "\\"
//						+ fileNameString;
//			}
//
//		}

        // 打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置请求方式为"GET"
        conn.setRequestMethod("GET");
        // 超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        // 通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        // new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File(filePathString);
        // 创建输出流
        FileOutputStream outStream = new FileOutputStream(imageFile);
        // 写入数据
        outStream.write(data);
        // 关闭输出流
        outStream.close();
        List<String> fileList = new ArrayList<String>();
        fileList.add(fileNameString);
        fileList.add(filePathString + fileNameString);
        return fileList;
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * application/x-www-form-urlencoded
     */
    public static String postForm(String url, Map<String, String> params) {

        int responseCode = 0;
        String callBackString = null;
        try {
            HttpClient client = new HttpClient();
            PostMethod postMethod = new PostMethod(url);

            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        postMethod.setParameter(entry.getKey(), value);
                    }
                }
            }
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            responseCode = client.executeMethod(postMethod);
            if (responseCode == 200) {
                callBackString = postMethod.getResponseBodyAsString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return callBackString;
    }

    // 正确的IP拿法，即优先拿site-local地址
    public static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
