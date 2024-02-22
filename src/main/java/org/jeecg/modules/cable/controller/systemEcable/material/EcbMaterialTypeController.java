package org.jeecg.modules.cable.controller.systemEcable.material;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialDealBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialListBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialSortBo;
import org.jeecg.modules.cable.controller.systemEcable.material.vo.MaterialTypeVo;
import org.jeecg.modules.cable.controller.systemEcable.material.vo.MaterialListVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterialType;
import org.jeecg.modules.cable.model.systemEcable.EcbMaterialTypeModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "基础材料类型--系统接口", description = "基础材料类型--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "461", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbMaterialType")
public class EcbMaterialTypeController {

    @Resource
    private EcbMaterialTypeModel ecbMaterialTypeModel;

    @Operation(summary = "列表")
    @PostMapping({"/getList"})
    public Result<MaterialTypeVo> getList(@RequestBody EcbMaterialListBo bo) {
        return Result.ok(ecbMaterialTypeModel.getList(bo));
    }

    @Operation(summary = "材料类型对应的所有材料")
    @PostMapping({"/material/list"})
    public Result<List<MaterialListVo>> getMaterialList() {
        return Result.ok(ecbMaterialTypeModel.getMaterialList());
    }

    @Operation(summary = "根据id查询")
    @PostMapping({"/getObject"})
    public Result<EcbMaterialType> getObject(@Validated @RequestBody EcbMaterialBaseBo bo) {
        return Result.ok(ecbMaterialTypeModel.getObject(bo));
    }

    @Operation(summary = "新增或编辑")
    @PostMapping({"/saveOrUpdate"})
    public Result<String> saveOrUpdate(@RequestBody EcbMaterialDealBo bo) {
        return Result.ok(ecbMaterialTypeModel.saveOrUpdate(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbMaterialSortBo> bos) {
        ecbMaterialTypeModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbMaterialBaseBo bo) {
        return Result.ok(ecbMaterialTypeModel.start(bo));
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbMaterialBaseBo request) {
        ecbMaterialTypeModel.delete(request);
        return Result.ok();
    }
}
