package org.jeecg.modules.cable.controller.userEcable.shield;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldBo;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldListBo;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuShieldModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "屏蔽")
@RestController
public class EcbuShieldController {
    @Resource
    EcbuShieldModel ecbuShieldModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "编辑屏蔽")
    //deal
    @PostMapping({"/ecableErpPc/ecbuShield/deal"})
    public Result<?> login_deal(@RequestBody EcbuShieldBo bo) {
        ecbuShieldModel.deal(bo);
        return Result.ok();
    }

    @Operation(summary = "开启屏蔽")
    //start
    @PostMapping({"/ecableErpPc/ecbuShield/start"})
    public Result<String> start(@RequestBody EcbuShieldStartBo bo) {
        return Result.ok(ecbuShieldModel.start(bo));
    }


    @Operation(summary = "屏蔽列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuShield/getList"})
    public Result<List<EcbuShield>> getList(@RequestBody EcbuShieldListBo bo) {
        return Result.ok(ecbuShieldModel.getList(bo));
    }
}
