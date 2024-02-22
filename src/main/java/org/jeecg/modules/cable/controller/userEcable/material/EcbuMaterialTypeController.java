package org.jeecg.modules.cable.controller.userEcable.material;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbMaterialDealBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialBaseBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialListBo;
import org.jeecg.modules.cable.controller.userEcable.material.bo.EcbuMaterialSortBo;
import org.jeecg.modules.cable.controller.userEcable.material.vo.MaterialListVo;
import org.jeecg.modules.cable.controller.userEcable.material.vo.MaterialTypeVo;
import org.jeecg.modules.cable.entity.userEcable.EcbuMaterialType;
import org.jeecg.modules.cable.model.userEcable.EcbuMaterialTypeModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "基础材料类型--用户接口", description = "基础材料类型--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "461", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecbuMaterialType")
public class EcbuMaterialTypeController {

    @Resource
    private EcbuMaterialTypeModel materialModel;

    @Operation(summary = "列表")
    @PostMapping({"/getList"})
    public Result<MaterialTypeVo> getList(@RequestBody EcbuMaterialListBo bo) {
        return Result.ok(materialModel.getList(bo));
    }


    @Operation(summary = "材料类型对应的所有材料")
    @PostMapping({"/material/list"})
    public Result<List<MaterialListVo>> getMaterialList() {
        return Result.ok(materialModel.getMaterialList());
    }

    @Operation(summary = "根据id查询")
    @PostMapping({"/getObject"})
    public Result<EcbuMaterialType> getObject(@Validated @RequestBody EcbuMaterialBaseBo bo) {
        return Result.ok(materialModel.getObject(bo));
    }

    @Operation(summary = "新增或编辑")
    @PostMapping({"/saveOrUpdate"})
    public Result<String> saveOrUpdate(@RequestBody EcbMaterialDealBo bo) {
        return Result.ok(materialModel.saveOrUpdate(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbuMaterialSortBo> bos) {
        materialModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "启用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbuMaterialBaseBo bo) {
        return Result.ok(materialModel.start(bo));
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbuMaterialBaseBo baseBo) {
        materialModel.delete(baseBo);
        return Result.ok();
    }
}
