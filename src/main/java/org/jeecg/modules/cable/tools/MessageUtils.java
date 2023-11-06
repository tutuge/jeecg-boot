package org.jeecg.modules.cable.tools;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
public class MessageUtils {
    public static void sendSms(String phone, String content) {
        String urls = "https://api.mix2.zthysms.com/v2/sendSms";
        long tKey = System.currentTimeMillis() / 1000;
        JSONObject json = new JSONObject();
        json.set("username", "greatwyh369");//账号
        json.set("password", SecureUtil.md5(SecureUtil.md5("Snynitfqm01") + tKey));//密码
        json.set("tKey", tKey); // //tKey
        json.set("mobile", phone); //手机号
        json.set("content", content); //内容
        String result = HttpRequest.post(urls)
                .timeout(60000)
                .body(json.toString(), MediaType.APPLICATION_JSON_VALUE)
                .execute()
                .body();
        log.info("result + " + result);
    }

    //registerEcUserSendCode
    public static void registerEcUserSendCode(String ecPhone, String code) {
        String content = "【缆查查】尊敬的用户您好，您正在注册缆查查电缆报价平台，您的验证码为：" + code + ",请勿向他人泄漏。";
        sendSms(ecPhone, content);
    }

    //loginEcUserSendCode
    public static void loginEcUserSendCode(String ecPhone, String code) {
        String content = "【缆查查】尊敬的用户您好，您正在登录缆查查电缆报价平台，您的验证码为：" + code + ",请勿向他人泄漏。";
        sendSms(ecPhone, content);
    }
}
