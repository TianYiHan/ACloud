package com.user.utils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
/**
 * Author:HanTianYi
 * Date:2020/5/26 11:35
 * Project:ACloud
 * package:com.user.utils
 * 发送短信工具类
 */
@Component
public class MessageUtil {

    @Autowired
    private  StringRedisTemplate stringRedisTemplate;
    //发送短信
    //注册验证码  SMS_193140343
    //登录验证码  SMS_193140345
    //修改密码验证码  SMS_193145317
    //修改手机号码验证码  SMS_193140347
    /**
     * @mobile 手机号码
     * @yzm  6位数短信验证码
     * @TemplateCode 短信模板id
     * */
    public String sendMsg(String mobile,String TemplateCode){

        System.out.println("sendMsg");
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "ALTAI4G9quTnkG4X2UGJW2vVrZ", "AFeAFgAyctAU2vChZQe6OTpOcqp5Ld4Z");
        IAcsClient client = new DefaultAcsClient(profile);
        String Randomyzm=String.valueOf(new Random().nextInt(999999 - 100000) + 100000);//生成6位随机数
        ValueOperations ops = stringRedisTemplate.opsForValue();//StringRedisTemplate 或者 RedisTemplate 工具类
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "tianyi");
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\""+Randomyzm+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject jsonObject = JSONObject.parseObject(response.getData());
            System.out.println(Randomyzm);
            System.out.println(response.getData());
            if (jsonObject.getString("Message").equals("OK")){//发送成功
                ops.set(mobile+TemplateCode,Randomyzm,180L,TimeUnit.SECONDS);//60秒只能获取一次验证码
            }
            return jsonObject.getString("Message");
            //{
            //	"Message": "OK",
            //	"RequestId": "873043ac-bcda-44db-9052-2e204c6ed20f",
            //	"BizId": "607300000000000000^0",
            //	"Code": "OK"
            //}
        } catch (ServerException e) {
            e.printStackTrace();
            return  e.getMessage();
        } catch (ClientException e) {
            e.printStackTrace();
            return  e.getMessage();
        }

    }





    /**
     * 根据request获取IP地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


}
