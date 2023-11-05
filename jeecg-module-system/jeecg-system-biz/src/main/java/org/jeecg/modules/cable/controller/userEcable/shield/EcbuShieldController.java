package org.jeecg.modules.cable.controller.userEcable.shield;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldBo;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldListBo;
import org.jeecg.modules.cable.controller.userEcable.shield.bo.EcbuShieldStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;
import org.jeecg.modules.cable.model.userEcable.EcbuShieldModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "屏蔽--用户接口", description = "屏蔽--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "430", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbuShield")
public class EcbuShieldController {
    @Resource
    EcbuShieldModel ecbuShieldModel;

    @Operation(summary = "编辑屏蔽")
    @PostMapping({"/deal"})
    public Result<?> deal(@RequestBody EcbuShieldBo bo) {
        ecbuShieldModel.deal(bo);
        return Result.ok();
    }

    @Operation(summary = "开启屏蔽")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbuShieldStartBo bo) {
        return Result.ok(ecbuShieldModel.start(bo));
    }


    @Operation(summary = "屏蔽列表")
    @PostMapping({"/getList"})
    public Result<List<EcbuShield>> getList(@RequestBody EcbuShieldListBo bo) {
        return Result.ok(ecbuShieldModel.getList(bo));
    }
}
