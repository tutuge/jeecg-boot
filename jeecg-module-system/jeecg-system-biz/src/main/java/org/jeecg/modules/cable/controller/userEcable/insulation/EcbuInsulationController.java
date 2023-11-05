package org.jeecg.modules.cable.controller.userEcable.insulation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationListBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.model.userEcable.EcbuInsulationModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "绝缘管理--用户接口", description = "绝缘--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "420", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbuInsulation")
public class EcbuInsulationController {
    @Resource
    EcbuInsulationModel ecbuInsulationModel;

    @Operation(summary = "提交编辑绝缘")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcbuInsulationBo bo) {
        String msg = ecbuInsulationModel.deal(bo);
        return Result.ok(msg);
    }


    @Operation(summary = "开启禁用绝缘")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbuInsulationStartBo bo) {
        String msg = ecbuInsulationModel.start(bo);
        return Result.OK(msg);
    }


    @Operation(summary = "绝缘列表")
    @PostMapping({"/getList"})
    public Result<List<EcbuInsulation>> getList(@RequestBody EcbuInsulationListBo bo) {
        List<EcbuInsulation> list = ecbuInsulationModel.getList(bo);
        return Result.OK(list);
    }
}
