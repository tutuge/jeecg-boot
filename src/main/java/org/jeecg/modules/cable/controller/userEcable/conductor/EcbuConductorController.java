//package org.jeecg.modules.cable.controller.userEcable.conductor;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.extensions.Extension;
//import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.Resource;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorBo;
//import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorListBo;
//import org.jeecg.modules.cable.controller.userEcable.conductor.bo.EcbuConductorStartBo;
//import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
//import org.jeecg.modules.cable.model.userEcable.EcbuConductorModel;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//
//@Tag(name = "导体--用户接口", description = "导体--用户接口",
//        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "410", parseValue = true)})})
//@RestController
//@RequestMapping("/ecableErpPc/ecbuConductor")
//public class EcbuConductorController {
//    @Resource
//    EcbuConductorModel ecbuConductorModel;
//
//    @Operation(summary = "修改导体数据")
//    @PostMapping({"/deal"})
//    public Result<?> deal(@Validated @RequestBody EcbuConductorBo ecbuConductorBo) {
//        ecbuConductorModel.deal(ecbuConductorBo);
//        return Result.OK();
//    }
//
//
//    @Operation(summary = "禁用启用导体数据")
//    @PostMapping({"/start"})
//    public Result<?> start(@RequestBody EcbuConductorStartBo bo) {
//        String start = ecbuConductorModel.start(bo);
//        return Result.OK(start);
//    }
//
//    @Operation(summary = "导体数据列表")
//    @PostMapping({"/getList"})
//    public Result<List<EcbuConductor>> getList(@RequestBody EcbuConductorListBo bo) {
//        List<EcbuConductor> list = ecbuConductorModel.getList(bo);
//        return Result.OK(list);
//    }
//}
