package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathStartBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.vo.SheathVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.model.systemEcable.EcbSheathModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "护套")
@RestController
public class EcbSheathController {
    @Resource
    EcbSheathModel ecbSheathModel;

    @Operation(summary = "获取护套列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbSheath/getList"})
    public Result<SheathVo> getList(@RequestBody EcbSheathBo bo) {
        return Result.ok(ecbSheathModel.getListAndCount(bo));
    }


    @Operation(summary = "获取护套")
    //根据ecbcId获取EcbSheath
    @PostMapping({"/ecableErpPc/ecbSheath/getObject"})
    public Result<EcbSheath> getObject(@RequestBody EcbSheathStartBo bo) {
        return Result.ok(ecbSheathModel.getObject(bo));
    }
}
