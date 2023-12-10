package org.jeecg.modules.cable.tools;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
public class SmsUtils {
    public static boolean sendSms(String phone, String content) {
        try {
            String urls = "https://api.mix2.zthysms.com/v2/sendSms";
            long tKey = System.currentTimeMillis() / 1000;
            JSONObject json = new JSONObject();
            json.put("username", "greatwyh369");//账号
            json.put("password", SecureUtil.md5(SecureUtil.md5("Snynitfqm01") + tKey));//密码
            json.put("tKey", tKey);  //tKey
            json.put("mobile", phone); //手机号
            json.put("content", content); //内容
            String result = HttpRequest.post(urls)
                    .timeout(60000)
                    .body(json.toString(), MediaType.APPLICATION_JSON_VALUE)
                    .execute()
                    .body();
            //{"code":200,"msg":"success","msgId":"170219959069751530241","contNum":1}
            log.info("result : {} ", result);
            JSONObject res = JSON.parseObject(result);
            Integer code = res.getInteger("code");
            return code == 200;
        } catch (Exception e) {
            log.error("发送验证码失败 :  ", e.getCause());
            return false;
        }
    }

    //registerEcUserSendCode
    public static boolean registerEcUserSendCode(String ecPhone, String code) {
        String content = "【缆查查】尊敬的用户您好，您正在注册缆查查电缆报价平台，您的验证码为：" + code + ",请勿向他人泄漏。";
        return sendSms(ecPhone, content);
    }

    //loginEcUserSendCode
    public static boolean loginEcUserSendCode(String ecPhone, String code) {
        String content = "【缆查查】尊敬的用户您好，您正在登录缆查查电缆报价平台，您的验证码为：" + code + ",请勿向他人泄漏。";
        return sendSms(ecPhone, content);
    }

    //public static void main(String[] args) {
    //    SmsUtils.loginEcUserSendCode("15830299247", "254215");
    //}
}
