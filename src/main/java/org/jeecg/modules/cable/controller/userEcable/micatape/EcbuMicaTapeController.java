package org.jeecg.modules.cable.controller.userEcable.micatape;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicaTapeBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeListBo;
import org.jeecg.modules.cable.controller.userEcable.micatape.bo.EcbuMicatapeStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;
import org.jeecg.modules.cable.model.userEcable.EcbuMicaTapeModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "云母带管理--用户接口")
@RestController
@RequestMapping("/ecableErpPc/ecbuMicatape")
public class EcbuMicaTapeController {
    @Resource
    EcbuMicaTapeModel ecbuMicatapeModel;

    @Operation(summary = "新增或编辑云母带")
    @PostMapping({"/deal"})
    public Result<?> deal(@Validated @RequestBody EcbuMicaTapeBo bo) {
        ecbuMicatapeModel.deal(bo);
        return Result.OK();
    }


    @Operation(summary = "是否启用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbuMicatapeStartBo bo) {
        return Result.OK(ecbuMicatapeModel.start(bo));
    }

    @Operation(summary = "云母带列表")
    @PostMapping({"/getList"})
    public Result<List<EcbuMicaTape>> getList(@RequestBody EcbuMicatapeListBo bo) {
        return Result.OK(ecbuMicatapeModel.getList(bo));
    }
}
