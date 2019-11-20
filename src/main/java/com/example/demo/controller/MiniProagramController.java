package com.example.demo.controller;

import com.example.demo.utils.AESOfMiniproagram;
import com.example.demo.utils.ResultData;
import com.example.demo.utils.common.utils.FunctionUtil;
import com.example.demo.utils.common.utils.HttpTookit;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by gyq on 2019/7/17.
 */
@RestController
@RequestMapping("/testMiniproagram")
public class MiniProagramController {

    private String appId = "wxc22ab8d016d1ab9c";
    private String secret = "2f27b5d8f8e47c865795688ec6ddc916";

    //引用redis模板
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 小程序连接测试
     * 从小程序传过来一个临时登录凭证code，
     * 到微信服务器获取session_key 和 openid
     * @param request
     * @return
     */
    @RequestMapping(value = "testMini",method = RequestMethod.POST)
    public ResultData testMini(HttpServletRequest request) {
        ResultData resultData = new ResultData();
        String code = request.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        String jsonStr = HttpTookit.doGet(url);
        System.out.println("======= " + jsonStr);
        if (FunctionUtil.IsNullOrEmpty(jsonStr)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        String session_key = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");
        /**
         *  将数据存入redis中
         * 第一个参数：存放的数据的名称
         * 第二个参数：存放的内容
         * 第三个参数：存放的时间
         * 第四个参数：存放的格式：TimeUnit.SECONDS
         */
        redisTemplate.opsForValue().set("session_key: " + openid, session_key, 1000*60*30, TimeUnit.SECONDS);

        Map map = new HashMap();
        map.put("session_key",session_key);
        map.put("openid",openid);//o6XCf4ibO7tcEz2XKtr_Mv_icpL0

        resultData.setCode(0);
        resultData.setMessage("success");
        resultData.setData("");
        return resultData;
    }

    /**
     * 获取微信步数
     */
    @RequestMapping(value = "getWeRunData",method = RequestMethod.POST)
    public ResultData getWeRunData(HttpServletRequest request) throws UnsupportedEncodingException {
        ResultData resultData = new ResultData();
        String encryptedData = request.getParameter("encryptedData");
        String iv = request.getParameter("iv");
        String session_key = redisTemplate.opsForValue().get("session_key: o6XCf4ibO7tcEz2XKtr_Mv_icpL0");

        //通过AES解密，获取微信步数的数据s
        AESOfMiniproagram aes = new AESOfMiniproagram();
        byte[] bytess = aes.decrypt(encryptedData,session_key,iv);
        String counts = new String(bytess);
        JSONObject jsonObject = JSONObject.fromObject(counts);
        System.out.println(jsonObject.getJSONArray("stepInfoList").size() + " ---");

        resultData.setCode(0);
        resultData.setMessage("success");
        resultData.setData("");
        return resultData;
    }
}
