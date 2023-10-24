package org.jeecg.modules.cable.controller.userCommon.position;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.position.bo.EcduciPositionBo;
import org.jeecg.modules.cable.controller.userCommon.position.bo.PositionBo;
import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.jeecg.modules.cable.model.userCommon.EcduciPositionModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiSupport(order =10141)
@Tag(name = "报价单图片位置")
@RestController
public class EcduciPositionController {
    @Resource
    EcduciPositionModel ecduciPositionModel;

    @Operation(summary = "获取图片位置")
    @PostMapping({"/ecableErpPc/ecduciPosition/getObject"})
    public Result<EcduciPosition> getObject(@RequestBody PositionBo bo) {
        return Result.ok(ecduciPositionModel.getObject(bo));
    }


    @Operation(summary = "图片位置编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecduciPosition/deal"})
    public Result<String> deal(@RequestBody EcduciPositionBo bo) {
        return Result.ok(ecduciPositionModel.deal(bo));
    }
}
