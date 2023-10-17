package org.jeecg.modules.cable.controller.systemEcable.infilling;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingStartBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.vo.InfillingVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.jeecg.modules.cable.model.systemEcable.EcbInfillingModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "填充物")
@RestController
public class EcbInfillingController {
    @Resource
    EcbInfillingModel ecbInfillingModel;

    @Operation(summary = "获取列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbInfilling/getList"})
    public Result<InfillingVo> getList(@RequestBody EcbInfillingBo bo) {
        return Result.ok(ecbInfillingModel.getListAndCount(bo));
    }

    @Operation(summary = "根据id获取")
    //根据EcbInfilling获取EcbInfilling
    @PostMapping({"/ecableErpPc/ecbInfilling/getObject"})
    public Result<EcbInfilling> getObject(@RequestBody EcbInfillingStartBo bo) {
        return Result.ok(ecbInfillingModel.getObject(bo));
    }
}
