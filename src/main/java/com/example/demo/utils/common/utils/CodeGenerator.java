package com.example.demo.utils.common.utils;

import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

/**
 * 负责生成各种编码
 */
public class CodeGenerator {

	private static char[] ITEMS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };
	/**
	 * 数英字符串
	 */
	private static String ALPHANUMERIC_STR;
	static {
		String numberStr = "0123456789";
		String aphaStr = "abcdefghijklmnopqrstuvwxyz";
		ALPHANUMERIC_STR = numberStr + aphaStr + aphaStr.toUpperCase();
	}

	/**
	 * 生成36个字符长度的UUID编码串，所有的字母转换为大写的格式。
	 * 
	 * @return 36个字符长度的UUID。
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().toUpperCase();
	}

	/**
	 * 获取srcStr的MD5编码（十六进制表示）
	 * 
	 * @param srcStr
	 *            需要获取MD5的字符串，不能为null
	 * @return srcStr的SHA代码（40个字符）
	 */
	public static String getSHADigest(String srcStr) {
		return getDigest(srcStr, "SHA-1");
	}

	/**
	 * 获取srcStr的MD5编码（十六进制表示）
	 * 
	 * @param srcStr
	 *            需要获取MD5的字符串，不能为null
	 * @return srcStr的MD5代码（32个字符）
	 */
	public static String getMD5Digest(String srcStr) {
		return getDigest(srcStr, "MD5");
	}

	/**
	 * 产生6位英数随机数,区分大小写
	 * 
	 * @return
	 */
	public static String getUpdateKey() {
		return getRandomStr(6);
	}

	/**
	 * 产生一个随机英数字符串，区分大小定
	 * 
	 * @param length
	 *            随机字符串的长度
	 * @return
	 */
	public static String getRandomStr(int length) {
		int srcStrLen = ALPHANUMERIC_STR.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int maxnum = (int) (Math.random() * 1000);
			int result = maxnum % srcStrLen;
			char temp = ALPHANUMERIC_STR.charAt(result);
			sb.append(temp);
		}
		return sb.toString();
	}

    /* 
     * 返回长度为【strLength】的随机数，在前面补0 
     */  
    public static String getFixLenthString(int strLength) {  
        Random rm = new Random();  
        // 获得随机数  
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
        // 将获得的获得随机数转化为字符串  
        String fixLenthString = String.valueOf(pross);  
        // 返回固定的长度的随机数  
        return fixLenthString.substring(1, strLength + 1);  
    }  

	private static String getDigest(String srcStr, String alg) {
		Assert.notNull(srcStr);
		Assert.notNull(alg);
		try {
			MessageDigest alga = MessageDigest.getInstance(alg);
			alga.update(srcStr.getBytes());
			byte[] digesta = alga.digest();
			return byte2hex(digesta);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 二进制转十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0");
			}
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * 产生固定长度随机数
	 * 
	 * @param num
	 *            数量
	 * @param length
	 *            长度
	 * @return
	 */
	public static String[] getKey(int num, int length) {
		String[] result = new String[num];
		StringBuilder buffer;
		for (int i = 0; i < num; i++) {
			buffer = new StringBuilder();
			for (int j = 0; j < length; j++) {
				double index = Math.random() * 53409984701702289312d;
				buffer.append(ITEMS[(int) (index % ITEMS.length)]);
			}
			result[i] = buffer.toString();
		}
		return result;
	}

	/**
	 * 产生固定组数,固定长度随机数
	 * 
	 * @param num
	 *            数量
	 * @param length
	 *            长度
	 * @return
	 */
	public static String[] getKey(int num, int group, int length) {
		String[] result = new String[num];
		StringBuilder buffer;
		for (int i = 0; i < num; i++) {
			buffer = new StringBuilder();
			for (int j = 0; j < group; j++) {
				if (j != 0) {
					buffer.append("-");
				}
				for (int k = 0; k < length; k++) {
					double index = Math.random() * 53409984701702289312d;
					buffer.append(ITEMS[(int) (index % ITEMS.length)]);
				}
			}
			result[i] = buffer.toString().toUpperCase();
		}
		return result;
	}

//	public static void main(String[] args) {
//		System.out.println(getUUID());
//		// System.out.println(getSHADigest("111111"));
//		System.out.println(getRandomStr(10));
//		System.out.println(getRandomStr(8));
//		System.out.println(getUpdateKey());
//	}
}
