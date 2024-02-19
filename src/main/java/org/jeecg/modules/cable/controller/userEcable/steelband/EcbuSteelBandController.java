//package org.jeecg.modules.cable.controller.userEcable.steelband;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.extensions.Extension;
//import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.annotation.Resource;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandBo;
//import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandListBo;
//import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandStartBo;
//import org.jeecg.modules.cable.entity.userEcable.EcbuSteelBand;
//import org.jeecg.modules.cable.model.userEcable.EcbuSteelBandModel;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@Tag(name = "钢带--用户接口", description = "钢带--用户接口",
//        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "470", parseValue = true)})})
//@RestController
//@RequestMapping("/ecableErpPc/ecbuSteelband")
//public class EcbuSteelBandController {
//    @Resource
//    EcbuSteelBandModel ecbuSteelbandModel;
//
//
//    @Operation(summary = "编辑钢带")
//    @PostMapping({"/deal"})
//    public Result<?> deal(@Validated @RequestBody EcbuSteelBandBo bo) {
//        ecbuSteelbandModel.deal(bo);
//        return Result.ok();
//    }
//
//
//    @Operation(summary = "开启钢带")
//    @PostMapping({"/start"})
//    public Result<String> start(@RequestBody EcbuSteelBandStartBo bo) {
//        return Result.ok(ecbuSteelbandModel.start(bo));
//    }
//
//    @Operation(summary = "钢带列表")
//    @PostMapping({"/getList"})
//    public Result<List<EcbuSteelBand>> getList(@RequestBody EcbuSteelBandListBo bo) {
//        return Result.ok(ecbuSteelbandModel.getList(bo));
//    }
//}
