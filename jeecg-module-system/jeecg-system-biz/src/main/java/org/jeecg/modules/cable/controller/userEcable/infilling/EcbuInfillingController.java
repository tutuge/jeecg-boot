package org.jeecg.modules.cable.controller.userEcable.infilling;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingBo;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingListBo;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.jeecg.modules.cable.model.userEcable.EcbuInfillingModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "填充物")
@RestController
public class EcbuInfillingController {
    @Resource
    EcbuInfillingModel ecbuInfillingModel;

    @Operation(summary = "提交编辑填充物列表")
    //deal
    @PostMapping({"/ecableErpPc/ecbuInfilling/deal"})
    public Result<?> login_deal(@RequestBody EcbuInfillingBo bo) {
        ecbuInfillingModel.deal(bo);
        return Result.ok();
    }


    @Operation(summary = "是否启用")
    //start
    @PostMapping({"/ecableErpPc/ecbuInfilling/start"})
    public Result<String> start(@RequestBody EcbuInfillingStartBo bo) {
        String msg = ecbuInfillingModel.start(bo);
        return Result.OK(msg);
    }


    @Operation(summary = "填充物列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuInfilling/getList"})
    public Result<List<EcbuInfilling>> getList(@RequestBody EcbuInfillingListBo bo) {
        List<EcbuInfilling> list = ecbuInfillingModel.getList(bo);
        return Result.OK(list);
    }
}
