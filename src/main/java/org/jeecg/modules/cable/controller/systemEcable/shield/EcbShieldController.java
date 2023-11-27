package org.jeecg.modules.cable.controller.systemEcable.shield;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.sheath.bo.EcbSheathDealBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldListBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldSortBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.vo.ShieldVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.model.systemEcable.EcbShieldModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "屏蔽--系统接口", description = "屏蔽--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "431", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbShield")
public class EcbShieldController {
    @Resource
    EcbShieldModel ecbShieldModel;


    @Operation(summary = "获取列表")
    @PostMapping({"/getList"})
    public Result<ShieldVo> getList(@RequestBody EcbShieldListBo bo) {
        return Result.ok(ecbShieldModel.getList(bo));
    }

    @Operation(summary = "获取对象")
    @PostMapping({"/getObject"})
    public Result<EcbShield> getObject(@RequestBody EcbShieldBaseBo bo) {
        return Result.ok(ecbShieldModel.getObject(bo));
    }

    @Operation(summary = "编辑")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbSheathDealBo bo) {
        return Result.ok(ecbShieldModel.deal(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbShieldSortBo> bos) {
        ecbShieldModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用停用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbShieldBaseBo bo) {
        return Result.ok(ecbShieldModel.start(bo));
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbShieldBaseBo bo) {
        ecbShieldModel.delete(bo);
        return Result.ok();
    }
}
