package org.jeecg.modules.cable.controller.systemEcable.micatape;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeStartBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.modules.cable.model.systemEcable.EcbMicatapeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "云母带")
@RestController
public class EcbMicatapeController {
    @Resource
    EcbMicatapeModel ecbMicatapeModel;

    @Operation(summary = "云母带列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbMicatape/getList"})
    public Result<MicatapeVo> getList(@RequestBody EcbMicatapeBo bo) {
        return Result.ok(ecbMicatapeModel.getListAndCount(bo));
    }

    @Operation(summary = "根据id获取")
    //根据EcbMicatape获取EcbMicatape
    @PostMapping({"/ecableErpPc/ecbMicatape/getObject"})
    public Result<EcbMicatape> getObjectPassId(@RequestBody EcbMicatapeStartBo bo) {
        return Result.ok(ecbMicatapeModel.getObject(bo));
    }
}
