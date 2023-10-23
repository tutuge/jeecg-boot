package org.jeecg.modules.cable.controller.userEcable.micatape;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeListBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicatape;
import org.jeecg.modules.cable.model.userEcable.EcbuMicatapeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "云母带管理")
@RestController
public class EcbuMicatapeController {
    @Resource
    EcbuMicatapeModel ecbuMicatapeModel;

    @Operation(summary = "提交编辑云母带")
    //deal
    @PostMapping({"/ecableErpPc/ecbuMicatape/deal"})
    public Result<?> login_deal(@RequestBody EcbuMicatapeBo bo) {
        ecbuMicatapeModel.deal(bo);
        return Result.OK();
    }


    @Operation(summary = "是否启用")
    //start
    @PostMapping({"/ecableErpPc/ecbuMicatape/start"})
    public Result<String> start(@RequestBody EcbuMicatapeStartBo bo) {
        return Result.OK(ecbuMicatapeModel.start(bo));
    }

    @Operation(summary = "云母带列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuMicatape/getList"})
    public Result<List<EcbuMicatape>> getList(@RequestBody EcbuMicatapeListBo bo) {
        return Result.OK(ecbuMicatapeModel.getList(bo));
    }
}
