package org.jeecg.modules.cable.controller.userEcable.steelband;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandStartBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.vo.SteelbandVo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandBo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandListBo;
import org.jeecg.modules.cable.controller.userEcable.steelband.bo.EcbuSteelBandStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;
import org.jeecg.modules.cable.model.userEcable.EcbuSteelbandModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSort(470)
@Tag(name = "钢带--用户接口")
@RestController
public class EcbuSteelBandController {
    @Resource
    EcbuSteelbandModel ecbuSteelbandModel;


    @Operation(summary = "编辑钢带")
    //deal
    @PostMapping({"/ecableErpPc/ecbuSteelband/deal"})
    public Result<?> login_deal(@RequestBody EcbuSteelBandBo bo) {
        ecbuSteelbandModel.deal(bo);
        return Result.ok();
    }


    @Operation(summary = "开启钢带")
    //start
    @PostMapping({"/ecableErpPc/ecbuSteelband/start"})
    public Result<String> start(@RequestBody EcbuSteelBandStartBo bo) {
        return Result.ok(ecbuSteelbandModel.start(bo));
    }

    @Operation(summary = "钢带列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuSteelband/getList"})
    public Result<List<EcbuSteelband>> getList(@RequestBody EcbuSteelBandListBo bo) {
        return Result.ok(ecbuSteelbandModel.getList(bo));
    }

    @Operation(summary = "根据startType获取信息列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbSteelband/getList"})
    public Result<SteelbandVo> getList(@RequestBody EcbSteelbandBo bo) {
        return Result.ok(ecbuSteelbandModel.getListAndCount(bo));
    }

    @Operation(summary = "获取钢带")
    //根据EcbSteelband获取EcbSteelband
    @PostMapping({"/ecableErpPc/ecbSteelband/getObject"})
    public Result<EcbSteelBand> getObject(@RequestBody EcbSteelbandStartBo bo) {
        return Result.ok(ecbuSteelbandModel.getObject(bo));
    }
}
