//package org.jeecg.modules.cable.controller.user;
//
//import org.jeecg.common.system.vo.EcUser;
//import org.jeecg.modules.cable.entity.user.EcuLogin;
//import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
//import org.jeecg.modules.cable.model.userEcable.EcbuConductorModel;
//import org.jeecg.modules.cable.service.user.EcUserService;
//import org.jeecg.modules.cable.service.user.EcuLoginService;
//import org.jeecg.modules.cable.tools.CommonFunction;
//import jakarta.annotation.Resource;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class LoginController {
//    @Resource
//    EcUserService ecUserService;
//    @Resource
//    EcuLoginService ecuLoginService;//用户登录信息
//    @Resource
//    EcbuConductorModel ecbuConductorModel;//用户导体
//
//    //登录提交
//    @PostMapping({"/ecableErpPc/login_deal"})
//    public Map<String, Object> login_deal(HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        int status;
//        String code;
//        String msg;
//        String ecPhone = request.getParameter("ecPhone");
//        String ecPwd = CommonFunction.getMd5Str(CommonFunction.getMd5Str(request.getParameter("ecPwd")));
//        EcUser recordEcUser = new EcUser();
//        recordEcUser.setEcPhone(ecPhone);
//        EcUser ecUser = ecUserService.getObject(recordEcUser);
//        if (ecUser == null) {
//            status = 1;//手机号不存在
//            code = "101";
//            msg = "手机号不存在";
//        } else {
//            EcUser record = new EcUser();
//            record.setEcPhone(ecPhone);
//            record.setEcPwd(ecPwd);
//            ecUser = ecUserService.getObject(record);
//            if (ecUser == null) {
//                status = 2;//账号或密码错误
//                code = "102";
//                msg = "账号或密码错误";
//            } else {
//                EcbuConductor ecbuConductor = ecbuConductorModel.getObjectPassEcCompanyId(ecUser.getEcCompanyId());
//                if (ecbuConductor == null) {
//                    status = 3;//没有初始化数据
//                    code = "103";
//                    msg = "没有初始化数据";
//                } else {
//                    //更新user token
//                    EcuLogin recordEcuLogin = new EcuLogin();
//                    recordEcuLogin.setEcuId(ecUser.getEcuId());
//                    EcuLogin ecuLogin = ecuLoginService.getObject(recordEcuLogin);
//                    System.out.println("ecuLogin + " + ecuLogin);
//                    if (ecuLogin == null) {
//                        String tokenStr = CommonFunction.getMd5Str(String.valueOf(CommonFunction.getRandom(1, 999999)));
//                        ecuLogin = new EcuLogin();
//                        ecuLogin.setEcuId(ecUser.getEcuId());//用户ID
//                        ecuLogin.setClientType(1);//PC端
//                        ecuLogin.setToken(tokenStr);
//                        ecuLogin.setPhoneStr("");//手机信息为空
//                        ecuLogin.setEffectTime(System.currentTimeMillis());
//                        ecuLoginService.insert(ecuLogin);
//                    } else {
//                        String tokenStr = CommonFunction.getMd5Str(String.valueOf(CommonFunction.getRandom(1, 999999)));
//                        ecuLogin = new EcuLogin();
//                        ecuLogin.setEcuId(ecUser.getEcuId());//用户ID
//                        ecuLogin.setToken(tokenStr);
//                        ecuLogin.setEffectTime(System.currentTimeMillis());
//                        ecuLoginService.updateTokenPassEcuId(ecuLogin);
//                    }
//                    recordEcuLogin = new EcuLogin();
//                    recordEcuLogin.setEcuId(ecUser.getEcuId());
//                    ecuLogin = ecuLoginService.getObject(recordEcuLogin);
//                    status = 4;//登录成功
//                    code = "200";
//                    msg = "登录成功";
//                    map.put("ecUser", ecUser);
//                    map.put("ecuLogin", ecuLogin);
//                }
//            }
//        }
//        map.put("status", status);
//        map.put("code", code);
//        map.put("msg", msg);
//        return map;
//    }
//}
