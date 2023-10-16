package org.jeecg.modules.cable.controller.userEcable.sheath;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathBo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathListBo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.model.userEcable.EcbuSheathModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "护套")
@RestController
public class EcbuSheathController {
    @Resource
    EcbuSheathModel ecbuSheathModel;

    @Operation(summary = "编辑护套")
    //deal
    @PostMapping({"/ecableErpPc/ecbuSheath/deal"})
    public Result<?> login_deal(@RequestBody EcbuSheathBo bo) {
        ecbuSheathModel.deal(bo);
        return Result.ok();
    }


    @Operation(summary = "开启护套")
    //start
    @PostMapping({"/ecableErpPc/ecbuSheath/start"})
    public Result<?> start(@RequestBody EcbuSheathStartBo bo) {
        return Result.OK(ecbuSheathModel.start(bo));
    }


    @Operation(summary = "护套列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuSheath/getList"})
    public Result<List<EcbuSheath>> getList(@RequestBody EcbuSheathListBo bo) {
        return Result.OK(ecbuSheathModel.getList(bo));
    }
}
