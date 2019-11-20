package com.example.demo.utils.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Title.公共函数
 */
public class FunctionUtil {
    /**
     * 计算采用utf-8编码方式时字符串所占字节数
     *
     * @param content
     * @return
     */
    public static int getByteSize(String content) {
        int size = 0;
        if (null != content) {
            try {
                // 汉字采用utf-8编码时占3个字节
                size = content.getBytes("utf-8").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    /**
     * 判断是否为空
     *
     * @param obj
     * @return
     */
    public static Boolean IsNullOrEmpty(Object obj) {
        try {
            if (null == obj || "".equals(obj))
                return true;
            else
                return false;
        } catch (Exception ex) {
            return true;
        }
    }

    /**
     * 将对象转换成字符串
     *
     * @param obj
     * @return
     */
    public static String ParseString(Object obj) {
        try {
            if (null == obj || "".equals(obj))
                return "";
            else {
                return obj.toString();
            }
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 将对象转换成数值
     *
     * @param obj
     * @return
     */
    public static int ParseInt(Object obj) {
        try {
            if (null == obj || "".equals(obj))
                return 0;
            else {
                return Integer.parseInt(obj.toString());
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    public static long ParseLong(Object obj) {
        try {
            if (null == obj || "".equals(obj))
                return 0;
            else {
                return Long.parseLong(obj.toString());
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 将对象转换成Double
     *
     * @param obj
     * @return
     */
    public static double ParseDouble(Object obj) {
        try {
            if (null == obj || "".equals(obj))
                return 0;
            else {
                return Double.parseDouble(obj.toString());
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 将对象转换成浮点
     *
     * @param obj
     * @return
     */
    public static float ParseFloat(Object obj) {
        try {
            if (null == obj || "".equals(obj))
                return 0;
            else {
                return Float.parseFloat(obj.toString());
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 将对象转换成BigDecimal
     *
     * @param obj
     * @return
     */
    public static BigDecimal ParseBigDecimal(Object obj) {
        try {
            if (null == obj || "".equals(obj) || "NaN".equals(obj))
                return new BigDecimal("0");
            else {
                String result = "0";
                if (obj.toString().length() >= 15) {
                    result = obj.toString().substring(0, 15);
                }
                return new BigDecimal(result);
            }
        } catch (Exception ex) {
            return new BigDecimal("0");
        }
    }

    /**
     * 将对象转换成布尔值
     *
     * @param obj
     * @return
     */
    public static Boolean ParseBoolean(Object obj) {
        try {
            if (null == obj || "".equals(obj))
                return false;
            else {
                return Boolean.parseBoolean(obj.toString());
            }
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 判断对象,集合,字符串是否为空或为null
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            String str = (String) obj;
            if (str.equals("") || str.equalsIgnoreCase("null")) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof List) {
            if (((List) obj).size() == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            String str = obj.toString();
            if (str.equals("") || str.equalsIgnoreCase("null")) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 返回当前时间，格式：yyyyMMddhhmmss
     *
     * @return
     */
    public static String getCurDate() {
        //return new Date().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        //Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());   //设置当前日期
        c.add(Calendar.MINUTE, 10); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
        Date date = c.getTime(); //结果
        return df.format(date);
    }

    /**
     * 返回当前年 格式：yyyy
     *
     * @return
     */
    public static String getCurYear() {
        return getCurDate().substring(0, 4);
    }

    /**
     * 返回当前月 格式：MM
     *
     * @return
     */
    public static String getCurMonth() {
        return getCurDate().substring(4, 6);
    }

    /**
     * 返回当前日 格式：dd
     *
     * @return
     */
    public static String getCurDay() {
        return getCurDate().substring(6, 8);
    }

    /**
     * String[]转int[]
     */
    public static Integer[] strsToInts(String[] strs) {
        Integer[] ints = new Integer[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = Integer.parseInt(strs[i]);
        }
        return ints;
    }

    /**
     * String[]转Long[]
     */
    public static Long[] strsToLongs(String[] strs) {
        Long[] ints = new Long[strs.length];
        for (int i = 0; i < strs.length; i++) {
            try {
                ints[i] = Long.parseLong(strs[i]);
            } catch (Exception e) {
                ints[i] = null;
            }
        }
        return ints;
    }
}

