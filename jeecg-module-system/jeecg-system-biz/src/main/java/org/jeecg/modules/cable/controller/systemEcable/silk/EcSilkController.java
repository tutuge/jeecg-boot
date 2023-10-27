package org.jeecg.modules.cable.controller.systemEcable.silk;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkBo;
import org.jeecg.modules.cable.controller.systemEcable.silk.bo.EcbSilkStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.model.systemEcable.EcSilkModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order = 310)
@Tag(name = "丝型号")
@RestController
public class EcSilkController {
    @Resource
    EcSilkModel ecSilkModel;


    @Operation(summary = "获取丝型号")
    // 根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecSilk/getList"})
    public Result<List<EcSilk>> getList(@RequestBody EcbSilkBo bo) {
        return Result.ok(ecSilkModel.getList(bo));
    }


    @Operation(summary = "根据silkName获取丝列号列表")
    // 根据silkName获取数据列表列表
    @PostMapping({"/ecableErpPc/ecSilk/getListPassSilkName"})
    public Result<List<EcSilk>> getListPassSilkName(@RequestBody EcbSilkStartBo bo) {
        return Result.ok(ecSilkModel.getListPassSilkName(bo));
    }

    @Operation(summary = "获取所有丝型号列表")
    // 获取数据列表列表
    @PostMapping({"/ecableErpPc/ecSilk/getListSilkName"})
    public Result<List<EcSilk>> getListSilkName(@RequestBody EcbSilkBo bo) {
        return Result.ok(ecSilkModel.getListSilkName(bo));
    }
}
