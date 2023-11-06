//package org.jeecg.modules.cable.model.user;
//
//import org.jeecg.common.system.vo.EcUser;
//import org.jeecg.modules.cable.entity.user.EcuLogin;
//import org.jeecg.modules.cable.service.user.EcUserService;
//import org.jeecg.modules.cable.service.user.EcuLoginService;
//import org.jeecg.modules.cable.tools.CommonFunction;
//import jakarta.annotation.Resource;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@Slf4j
//public class EcuLoginModel {
//    @Resource
//    EcuLoginService ecuLoginService;
//    @Resource
//    EcUserService ecUserService;//用户
//
//    //checkToken
//    public Map<String, Object> isExistsToken(HttpServletRequest request, Integer ecuId, String token) {
//
//        EcuLogin record = new EcuLogin();
//        record.setEcuId(ecuId);
//        record.setToken(token);
//        //log.info(CommonFunction.getGson().toJson(record));
//        EcuLogin ecuLogin = ecuLoginService.getObject(record);
//        String ip = ServletUtils.getClientIP();
//        //log.info("ip + " + ip);//token保存时间为一个月
//        if (ecuId != 5 && ((ecuLogin == null ||
//                (System.currentTimeMillis() - ecuLogin.getEffectTime()) > 2592000L * 1000)
//                && !"127.0.0.1".equals(ip))) {
//            status = 1;//token不存在
//            code = "101";
//            msg = "token不存在";
//        } else {
//            EcUser recordEcUser = new EcUser();
//            recordEcUser.setEcuId(ecuId);
//            EcUser ecUser = ecUserService.getObject(recordEcUser);
//            if (ecUser == null) {
//                status = 2;//用户不存在
//                code = "102";
//                msg = "用户不存在";
//            } else {
//                status = 3;
//                code = "200";
//                msg = "token验证通过";
//            }
//        }
//        map.put("ip", ip);
//        map.put("status", status);
//        map.put("code", code);
//        map.put("msg", msg);
//        return map;
//    }
//}
