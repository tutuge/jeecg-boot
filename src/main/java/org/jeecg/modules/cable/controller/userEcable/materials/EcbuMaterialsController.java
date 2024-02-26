package org.jeecg.modules.cable.controller.userEcable.materials;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsBaseBo;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsDealBo;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsListBo;
import org.jeecg.modules.cable.controller.userEcable.materials.bo.EcbuMaterialsSortBo;
import org.jeecg.modules.cable.controller.userEcable.materials.vo.MaterialsVo;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterials;
import org.jeecg.modules.cable.service.userEcable.Impl.EcbuMaterialsSerivce;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "材料--用户接口", description = "导体--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "411", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbuMaterials")
public class EcbuMaterialsController {
    @Resource
    private EcbuMaterialsSerivce ecbuMaterialsSerivce;

    @Operation(summary = "获取列表")
    @PostMapping({"/getList"})
    public Result<MaterialsVo> getList(@RequestBody EcbuMaterialsListBo bo) {
        return Result.ok(ecbuMaterialsSerivce.getList(bo));
    }

    @Operation(summary = "获取所有的导体")
    @PostMapping({"/get/all/conductor"})
    public Result<List<EcbuMaterials>> getConductor() {
        return Result.ok(ecbuMaterialsSerivce.getConductor());
    }

    @Operation(summary = "获取对象")
    @PostMapping({"/getObject"})
    public Result<EcbuMaterials> getObject(@RequestBody EcbuMaterialsBaseBo bo) {
        return Result.ok(ecbuMaterialsSerivce.getObject(bo));
    }

    @Operation(summary = "新增或修改")
    @PostMapping({"/saveOrUpdate"})
    public Result<String> saveOrUpdate(@RequestBody EcbuMaterialsDealBo bo) {
        return Result.ok(ecbuMaterialsSerivce.saveOrUpdate(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbuMaterialsSortBo> bos) {
        ecbuMaterialsSerivce.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用停用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbuMaterialsBaseBo bo) {
        return Result.ok(ecbuMaterialsSerivce.start(bo));
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbuMaterialsBaseBo bo) {
        ecbuMaterialsSerivce.delete(bo);
        return Result.ok();
    }
}
