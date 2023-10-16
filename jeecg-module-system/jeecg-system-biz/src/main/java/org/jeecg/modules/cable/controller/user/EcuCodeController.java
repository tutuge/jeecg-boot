package org.jeecg.modules.cable.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuCodeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "发送验证码")
@RestController
public class EcuCodeController {
    @Resource
    EcuCodeModel ecuCodeModel;

    @Operation(summary = "注册时发送验证码")
    //注册时发送验证码
    @PostMapping({"/ecableErpPc/ecuCode/dealRegister"})
    public Map<String, Object> dealRegister(HttpServletRequest request) {
        return ecuCodeModel.dealRegister(request);
    }

    @Operation(summary = "登录时发送验证码")
    //登录时发送验证码
    @PostMapping({"/ecableErpPc/ecuCode/dealLogin"})
    public Map<String, Object> dealLogin(HttpServletRequest request) {
        return ecuCodeModel.dealLogin(request);
    }
}
