package org.jeecg.modules.cable.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.EcUser;
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
    public Result<EcUser> getObject() {
        return Result.ok(ecUserModel.getObject());
    }


    @Operation(summary = "获取用户列表")
    //获取用户列表
    @PostMapping({"/ecableErpPc/ecUser/getList"})
    public Result<Map<String, Object>> getList(HttpServletRequest request) {
        return Result.ok(ecUserModel.getList(request));
    }


    @Operation(summary = "提交")
    //deal
    @PostMapping({"/ecableErpPc/ecUser/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecUserModel.deal(request));
    }


    @Operation(summary = "修改个人信息")
    //dealMine 修改个人信息
    @PostMapping({"/ecableErpPc/ecUser/dealMine"})
    public Result<?> dealMine(HttpServletRequest request) {
        ecUserModel.dealMine(request);
        return Result.ok();
    }


    @Operation(summary = "注册")
    //dealRegister 注册
    @PostMapping({"/ecableErpPc/ecUser/dealRegister"})
    public Result<String> dealRegister(HttpServletRequest request) {
        return Result.ok(ecUserModel.dealRegister(request));
    }


    @Operation(summary = "登录")
    //dealLoginCode 登录
    @PostMapping({"/ecableErpPc/ecUser/dealLoginCode"})
    public Result<Map<String, Object>> dealLogin(HttpServletRequest request) {
        return Result.ok(ecUserModel.dealLoginCode(request));
    }


    @Operation(summary = "修改利润点")
    //dealProfit 修改利润点
    @PostMapping({"/ecableErpPc/ecUser/dealProfit"})
    public Result<?> dealProfit(HttpServletRequest request) {
        ecUserModel.dealProfit(request);
        return Result.ok();
    }
}
