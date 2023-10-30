package org.jeecg.modules.cable.controller.userEcable.sheath;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathStartBo;
import org.jeecg.modules.cable.controller.systemEcable.sheath.vo.SheathVo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathBo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathListBo;
import org.jeecg.modules.cable.controller.userEcable.sheath.bo.EcbuSheathStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.jeecg.modules.cable.model.userEcable.EcbuSheathModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order = 480)
@Tag(name = "护套--用户接口", description = "护套--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "480", parseValue = true)})})
@RestController
public class EcbuSheathController {
    @Resource
    EcbuSheathModel ecbuSheathModel;

    @Operation(summary = "编辑护套")
    // deal
    @PostMapping({"/ecableErpPc/ecbuSheath/deal"})
    public Result<?> login_deal(@RequestBody EcbuSheathBo bo) {
        ecbuSheathModel.deal(bo);
        return Result.ok();
    }


    @Operation(summary = "开启护套")
    // start
    @PostMapping({"/ecableErpPc/ecbuSheath/start"})
    public Result<?> start(@Validated @RequestBody EcbuSheathStartBo bo) {
        return Result.OK(ecbuSheathModel.start(bo));
    }


    @Operation(summary = "护套列表")
    // getList
    @PostMapping({"/ecableErpPc/ecbuSheath/getList"})
    public Result<List<EcbuSheath>> getList(@RequestBody EcbuSheathListBo bo) {
        return Result.OK(ecbuSheathModel.getList(bo));
    }

    @Operation(summary = "根据startType获取信息列表")
    // 根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbSheath/getList"})
    public Result<SheathVo> getList(@RequestBody EcbSheathBo bo) {
        return Result.ok(ecbuSheathModel.getListAndCount(bo));
    }


    @Operation(summary = "获取护套")
    // 根据ecbcId获取EcbSheath
    @PostMapping({"/ecableErpPc/ecbSheath/getObject"})
    public Result<EcbSheath> getObject(@RequestBody EcbSheathStartBo bo) {
        return Result.ok(ecbuSheathModel.getObject(bo));
    }
}
