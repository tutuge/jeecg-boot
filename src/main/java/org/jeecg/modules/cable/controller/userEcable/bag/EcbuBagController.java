//package org.jeecg.modules.cable.controller.userEcable.bag;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.extensions.Extension;
//import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.Resource;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagBo;
//import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagListBo;
//import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagStartBo;
//import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
//import org.jeecg.modules.cable.model.userEcable.EcbuBagModel;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//
//@Tag(name = "包带--用户接口", description = "包带--用户接口",
//        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "460", parseValue = true)})})
//@RestController
//@RequestMapping("/ecableErpPc/ecbuBag")
//public class EcbuBagController {
//    @Resource
//    EcbuBagModel ecbuBagModel;
//
//
//    @Operation(summary = "包带编辑")
//    @PostMapping({"/deal"})
//    public Result<?> deal(@Validated @RequestBody EcbuBagBo bo) {
//        ecbuBagModel.deal(bo);
//        return Result.ok();
//    }
//
//    @Operation(summary = "包带开启禁用")
//    @PostMapping({"/start"})
//    public Result<String> start(@RequestBody EcbuBagStartBo bo) {
//        return Result.OK(ecbuBagModel.start(bo));
//    }
//
//
//    @Operation(summary = "获取用户包带列表")
//    @PostMapping({"/getList"})
//    public Result<List<EcbuBag>> getList(@RequestBody EcbuBagListBo bo) {
//        return Result.OK(ecbuBagModel.getList(bo));
//    }
//
//}
