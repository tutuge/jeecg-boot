package org.jeecg.modules.cable.controller.user.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.EcUser;
import org.jeecg.modules.cable.controller.user.user.bo.*;
import org.jeecg.modules.cable.controller.user.user.vo.EcuUserRegisterVo;
import org.jeecg.modules.cable.controller.user.user.vo.UserVo;
import org.jeecg.modules.cable.model.user.EcUserModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户管理", description = "用户管理",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "949", parseValue = true)})})
@RestController
public class EcUserController {
    @Resource
    EcUserModel ecUserModel;


    @Operation(summary = "根据ID获取用户信息")
    //根据ID获取用户信息
    @PostMapping({"/ecableErpPc/ecUser/getObject"})
    public Result<EcUser> getObject() {
        return Result.ok(ecUserModel.getObject());
    }


    @Operation(summary = "获取用户列表")
    //获取用户列表
    @PostMapping({"/ecableErpPc/ecUser/getList"})
    public Result<UserVo> getList(@Validated @RequestBody EcuUserListBo bo) {
        return Result.ok(ecUserModel.getList(bo));
    }


    @Operation(summary = "提交")
    //deal
    @PostMapping({"/ecableErpPc/ecUser/deal"})
    public Result<String> deal(@Validated @RequestBody EcuUserDealBo bo) {
        return Result.ok(ecUserModel.deal(bo));
    }


    @Operation(summary = "修改个人信息")
    //dealMine 修改个人信息
    @PostMapping({"/ecableErpPc/ecUser/dealMine"})
    public Result<?> dealMine(@Validated @RequestBody EcuUserDealMineBo bo) {
        ecUserModel.dealMine(bo);
        return Result.ok();
    }


    @Operation(summary = "注册")
    //dealRegister 注册
    @PostMapping({"/ecableErpPc/ecUser/dealRegister"})
    public Result<EcuUserRegisterVo> dealRegister(EcuUserRegisterBo bo) {
        return Result.ok(ecUserModel.dealRegister(bo));
    }

//登录使用jeecg自带的登录
//    @Operation(summary = "登录")
//    //dealLoginCode 登录
//    @PostMapping({"/ecableErpPc/ecUser/dealLoginCode"})
//    public Result<Map<String, Object>> dealLogin(HttpServletRequest request) {
//        return Result.ok(ecUserModel.dealLoginCode(request));
//    }


    @Operation(summary = "修改利润点")
    //dealProfit 修改利润点
    @PostMapping({"/ecableErpPc/ecUser/dealProfit"})
    public Result<?> dealProfit(@Validated @RequestBody EcuUserProfitBo bo) {
        ecUserModel.dealProfit(bo);
        return Result.ok();
    }
}
