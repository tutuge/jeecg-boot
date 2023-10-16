package org.jeecg.modules.cable.controller.systemEcable.bag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.vo.BagVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.modules.cable.model.systemEcable.EcbBagModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "包带")
@RestController
public class EcbBagController {
    @Resource
    EcbBagModel ecbBagModel;

    @Operation(summary = "获取包带列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbBag/getList"})
    public Result<BagVo> getList(@RequestBody EcbBagBo bo) {
        return Result.ok(ecbBagModel.getListAndCount(bo));
    }

    @Operation(summary = "根据ID获取")
    //根据EcbBag获取EcbBag
    @PostMapping({"/ecableErpPc/ecbBag/getObject"})
    public Result<EcbBag> getObjectPassId(@RequestBody EcbBagBo bo) {
        return Result.ok(ecbBagModel.getObject(bo));
    }
}
