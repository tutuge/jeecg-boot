package org.jeecg.modules.cable.controller.systemEcable.insulation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationStartBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.vo.InsulationVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.model.systemEcable.EcbInsulationModel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "绝缘")
@RestController
public class EcbInsulationController {
    @Resource
    EcbInsulationModel ecbInsulationModel;
//核验登录信息

    @Operation(summary = "获取绝缘列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbInsulation/getList"})
    public Result<InsulationVo> getList(@RequestBody EcbInsulationBo bo) {
        return Result.ok(ecbInsulationModel.getListAndCount(bo));
    }

    @Operation(summary = "根据id获取")
    //根据EcbInsulation获取EcbInsulation
    @PostMapping({"/ecableErpPc/ecbInsulation/getObject"})
    public Result<EcbInsulation> getObject(@RequestBody EcbInsulationStartBo bo) {
        return Result.ok(ecbInsulationModel.getObject(bo));
    }
}
