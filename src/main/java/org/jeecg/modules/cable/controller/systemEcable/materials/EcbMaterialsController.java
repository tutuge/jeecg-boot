package org.jeecg.modules.cable.controller.systemEcable.materials;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsDealBo;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsListBo;
import org.jeecg.modules.cable.controller.systemEcable.materials.bo.EcbMaterialsSortBo;
import org.jeecg.modules.cable.controller.systemEcable.materials.vo.MaterialsVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterials;
import org.jeecg.modules.cable.model.systemEcable.EcbMaterialsModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "材料--系统接口", description = "导体--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "411", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbMaterials")
public class EcbMaterialsController {
    @Resource
    EcbMaterialsModel ecbMaterialsModel;

    @Operation(summary = "获取列表")
    @PostMapping({"/getList"})
    public Result<MaterialsVo> getList(@RequestBody EcbMaterialsListBo bo) {
        return Result.ok(ecbMaterialsModel.getList(bo));
    }

    @Operation(summary = "获取对象")
    @PostMapping({"/getObject"})
    public Result<EcbMaterials> getObject(@RequestBody EcbMaterialsBaseBo bo) {
        return Result.ok(ecbMaterialsModel.getObject(bo));
    }

    @Operation(summary = "新增或修改")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbMaterialsDealBo bo) {
        return Result.ok(ecbMaterialsModel.deal(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbMaterialsSortBo> bos) {
        ecbMaterialsModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用停用")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbMaterialsBaseBo bo) {
        return Result.ok(ecbMaterialsModel.start(bo));
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbMaterialsBaseBo bo) {
        ecbMaterialsModel.delete(bo);
        return Result.ok();
    }
}
