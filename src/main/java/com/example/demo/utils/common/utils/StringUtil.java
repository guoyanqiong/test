package com.example.demo.utils.common.utils;

import org.apache.commons.lang.RandomStringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static Random random = new Random();
	private static Pattern mp = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0]))\\d{8}$");
	private static String SALT="zhengliang123";
	private static final String default_char_encode = "utf-8";
	
	private static Pattern ep = Pattern.compile("^([A-Za-z0-9]+)(([A-Za-z0-9]+)|(_+)|(\\-+)|(\\.+)|(\\++))*@((\\w+\\-+)|(\\w+\\.))*\\w{1,63}\\.[a-zA-Z]{2,6}$");
	
	public static boolean isEmpty(String str){
		return str==null||"".equals(str);
	}
	
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	
	public static boolean isMobile(String mobile){
		Matcher matcher=mp.matcher(mobile);
		return matcher.matches();
	}
	
	public static boolean isEmail(String email){
		Matcher matcher=ep.matcher(email);
		return matcher.matches();
	}
	
	public static String getRandomPassword(){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<6;i++){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	public static String getRandomCode(int c){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<c;i++){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
        
        /**
         * 转换字符串，防止SQL注入
         * @return SQL查询所用字符串，以在两头添加“'”符号
         */
    public static String sqlEscapeString(String rawStr){
         return "'" + rawStr.replaceAll("\\\\", "\\\\").replaceAll("'", "\\'") + "'";
    }
        
    public static String inputStream2String(InputStream is,String charset) throws IOException {
    	BufferedReader in = new BufferedReader(new InputStreamReader(is,charset));
    	return buffer2String(in);
    }
    
    public static String buffer2String(BufferedReader buf) throws IOException {
    	StringBuffer buffer = new StringBuffer();
    	String line = "";
   		while ((line = buf.readLine()) != null) {
   			buffer.append(line);
   		}
    	return buffer.toString();
    }
    
    public static String compareStrings(String str1,String str2){
    	return str1.compareTo(str2)>0?(str1+str2):(str2+str1);
    }
    
    public static String getUtf8Encode(String src){
		try {
			src=URLEncoder.encode(src, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return src;
	}

	/**
	 * <p>生成订单号</p>
	 * @return
	 */
	private static final int ORDER_DEFAULT_LENGTH = 20;  //订单号位数
	public static String order() {
		return RandomStringUtils.randomNumeric(ORDER_DEFAULT_LENGTH);
	}

	/**
	 * <p>MD5加密</p>
	 * <p>SHA-1加密</p>
	 * @param s,type
	 * @return
	 */
	public static String encrypt(String s,String type){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(type);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			md.update(s.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] result = md.digest();

		StringBuffer sb = new StringBuffer();

		for (byte b : result) {
			int i = b & 0xff;
			if (i < 0xf) {
				sb.append(0);
			}
			sb.append(Integer.toHexString(i));
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * <p>编码</p>
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		String str = null;
		if (!StringUtil.isEmpty(s)) {
			try {
				str = URLEncoder.encode(s, default_char_encode);
			} catch (UnsupportedEncodingException e) {
				System.out.print(e.toString());
			}
		}
		return str;

	}
//手机
	public static boolean isMobileNumber(String mobilenumber) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		m = p.matcher(mobilenumber);
		b = m.matches();
		return b;
	}

}
