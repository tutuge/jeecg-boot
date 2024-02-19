//package org.jeecg.modules.cable.controller.userEcable.sheath;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.extensions.Extension;
//import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.Resource;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathBo;
//import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathListBo;
//import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathStartBo;
//import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
//import org.jeecg.modules.cable.model.userEcable.EcbuSheathModel;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@Tag(name = "护套--用户接口", description = "护套--用户接口",
//        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "480", parseValue = true)})})
//@RestController
//@RequestMapping("/ecableErpPc/ecbuSheath")
//public class EcbuSheathController {
//    @Resource
//    EcbuSheathModel ecbuSheathModel;
//
//    @Operation(summary = "编辑护套")
//    @PostMapping({"/deal"})
//    public Result<?> deal(@Validated @RequestBody EcbuSheathBo bo) {
//        ecbuSheathModel.deal(bo);
//        return Result.ok();
//    }
//
//
//    @Operation(summary = "开启护套")
//    @PostMapping({"/start"})
//    public Result<?> start(@Validated @RequestBody EcbuSheathStartBo bo) {
//        return Result.OK(ecbuSheathModel.start(bo));
//    }
//
//
//    @Operation(summary = "护套列表")
//    @PostMapping({"/getList"})
//    public Result<List<EcbuSheath>> getList(@RequestBody EcbuSheathListBo bo) {
//        return Result.OK(ecbuSheathModel.getList(bo));
//    }
//}
