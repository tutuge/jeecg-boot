package org.jeecg.modules.cable.controller.userEcable.micatape;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeStartBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeListBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
import org.jeecg.modules.cable.model.userEcable.EcbuMicaTapeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "云母带管理--用户接口")
@RestController
public class EcbuMicatapeController {
    @Resource
    EcbuMicaTapeModel ecbuMicatapeModel;

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
    public Result<List<EcbuMicaTape>> getList(@RequestBody EcbuMicatapeListBo bo) {
        return Result.OK(ecbuMicatapeModel.getList(bo));
    }

    @Operation(summary = "根据是否启动获取信息列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbMicatape/getList"})
    public Result<MicatapeVo> getList(@RequestBody EcbMicatapeBo bo) {
        return Result.ok(ecbuMicatapeModel.getListAndCount(bo));
    }

    @Operation(summary = "根据id获取")
    //根据EcbMicatape获取EcbMicatape
    @PostMapping({"/ecableErpPc/ecbMicatape/getObject"})
    public Result<EcbMicaTape> getObjectPassId(@RequestBody EcbMicatapeStartBo bo) {
        return Result.ok(ecbuMicatapeModel.getObject(bo));
    }
}
