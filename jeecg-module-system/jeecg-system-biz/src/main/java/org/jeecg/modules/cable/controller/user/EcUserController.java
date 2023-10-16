package org.jeecg.modules.cable.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "用户")
@RestController
public class EcUserController {
    @Resource
    EcUserModel ecUserModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "根据ID获取用户信息")
    //根据ID获取用户信息
    @PostMapping({"/ecableErpPc/ecUser/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecUserModel.getObject(request);
    }


    @Operation(summary = "获取用户列表")
    //获取用户列表
    @PostMapping({"/ecableErpPc/ecUser/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecUserModel.getList(request);
    }


    @Operation(summary = "提交")
    //deal
    @PostMapping({"/ecableErpPc/ecUser/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecUserModel.deal(request);
    }


    @Operation(summary = "修改个人信息")
    //dealMine 修改个人信息
    @PostMapping({"/ecableErpPc/ecUser/dealMine"})
    public Map<String, Object> dealMine(HttpServletRequest request) {
        return ecUserModel.dealMine(request);
    }


    @Operation(summary = "注册")
    //dealRegister 注册
    @PostMapping({"/ecableErpPc/ecUser/dealRegister"})
    public Map<String, Object> dealRegister(HttpServletRequest request) {
        return ecUserModel.dealRegister(request);
    }


    @Operation(summary = "登录")
    //dealLoginCode 登录
    @PostMapping({"/ecableErpPc/ecUser/dealLoginCode"})
    public Map<String, Object> dealLogin(HttpServletRequest request) {
        return ecUserModel.dealLoginCode(request);
    }


    @Operation(summary = "修改利润点")
    //dealProfit 修改利润点
    @PostMapping({"/ecableErpPc/ecUser/dealProfit"})
    public Map<String, Object> dealProfit(HttpServletRequest request) {
        return ecUserModel.dealProfit(request);
    }
}
