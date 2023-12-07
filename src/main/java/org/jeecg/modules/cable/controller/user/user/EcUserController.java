//package org.jeecg.modules.cable.controller.user.user;
//
//import io.swagger.v3.oas.annotations.extensions.Extension;
//import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.Resource;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name = "用户管理", description = "用户管理",
//        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "949", parseValue = true)})})
//@RestController
//@RequestMapping("/ecableErpPc/ecUser")
//public class EcUserController {
//    @Resource
//    EcUserModel ecUserModel;
//
//
//    //@Operation(summary = "根据ID获取用户信息")
//    //@PostMapping({"/getObject"})
//    //public Result<EcUser> getObject() {
//    //    return Result.ok(ecUserModel.getObject());
//    //}
//    //
//    //
//    //@Operation(summary = "获取用户列表")
//    //@PostMapping({"/getList"})
//    //public Result<UserVo> getList(@Validated @RequestBody EcuUserListBo bo) {
//    //    return Result.ok(ecUserModel.getList(bo));
//    //}
//
//
//    //@Operation(summary = "提交")
//    //@PostMapping({"/deal"})
//    //public Result<String> deal(@Validated @RequestBody EcuUserDealBo bo) {
//    //    return Result.ok(ecUserModel.deal(bo));
//    //}
//
//
//    //@Operation(summary = "修改个人信息")
//    ////dealMine 修改个人信息
//    //@PostMapping({"/dealMine"})
//    //public Result<?> dealMine(@Validated @RequestBody EcuUserDealMineBo bo) {
//    //    ecUserModel.dealMine(bo);
//    //    return Result.ok();
//    //}
//
//
//
//
////登录使用jeecg自带的登录
////    @Operation(summary = "登录")
////    //dealLoginCode 登录
////    @PostMapping({"/dealLoginCode"})
////    public Result<Map<String, Object>> dealLogin(HttpServletRequest request) {
////        return Result.ok(ecUserModel.dealLoginCode(request));
////    }
//
//
//    //@Operation(summary = "修改利润点")
//    ////dealProfit 修改利润点
//    //@PostMapping({"/dealProfit"})
//    //public Result<?> dealProfit(@Validated @RequestBody EcuUserProfitBo bo) {
//    //    ecUserModel.dealProfit(bo);
//    //    return Result.ok();
//    //}
//}
