package org.jeecg.modules.cable.controller.user;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuCodeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcuCodeController {
    @Resource
    EcuCodeModel ecuCodeModel;

    //注册时发送验证码
    @PostMapping({"/ecableErpPc/ecuCode/dealRegister"})
    public Map<String, Object> dealRegister(HttpServletRequest request) {
        return ecuCodeModel.dealRegister(request);
    }

    //登录时发送验证码
    @PostMapping({"/ecableErpPc/ecuCode/dealLogin"})
    public Map<String, Object> dealLogin(HttpServletRequest request) {
        return ecuCodeModel.dealLogin(request);
    }
}
