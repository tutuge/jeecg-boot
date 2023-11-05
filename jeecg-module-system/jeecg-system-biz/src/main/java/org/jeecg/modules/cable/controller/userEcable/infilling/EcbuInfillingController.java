package org.jeecg.modules.cable.controller.userEcable.infilling;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingBo;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingListBo;
import org.jeecg.modules.cable.controller.userEcable.infilling.bo.EcbuInfillingStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;
import org.jeecg.modules.cable.model.userEcable.EcbuInfillingModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "填充物管理--用户接口", description = "填充物--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "450", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbuInfilling")
public class EcbuInfillingController {
    @Resource
    EcbuInfillingModel ecbuInfillingModel;

    @Operation(summary = "提交编辑填充物列表")
    //deal
    @PostMapping({"/deal"})
    public Result<?> deal(@Validated @RequestBody EcbuInfillingBo bo) {
        ecbuInfillingModel.deal(bo);
        return Result.ok();
    }


    @Operation(summary = "是否启用")
    //start
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbuInfillingStartBo bo) {
        String msg = ecbuInfillingModel.start(bo);
        return Result.OK(msg);
    }


    @Operation(summary = "填充物列表")
    //getList
    @PostMapping({"/getList"})
    public Result<List<EcbuInfilling>> getList(@RequestBody EcbuInfillingListBo bo) {
        List<EcbuInfilling> list = ecbuInfillingModel.getList(bo);
        return Result.OK(list);
    }
}
