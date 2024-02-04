package org.jeecg.modules.cable.controller.systemEcable.material;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialDealBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialListBo;
import org.jeecg.modules.cable.controller.systemEcable.material.bo.EcbMaterialSortBo;
import org.jeecg.modules.cable.controller.systemEcable.material.vo.MaterialVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMaterial;
import org.jeecg.modules.cable.model.systemEcable.EcbMaterialModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "基础材料--系统接口", description = "基础材料--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "461", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbMaterial")
public class EcbMaterialController {

    @Resource
    EcbMaterialModel materialModel;

    @PostMapping({"/getList"})
    public Result<MaterialVo> getList(@RequestBody EcbMaterialListBo bo) {
        return Result.ok(materialModel.getList(bo));
    }

    @PostMapping({"/getObject"})
    public Result<EcbMaterial> getObject(@Validated @RequestBody EcbMaterialBaseBo bo) {
        return Result.ok(materialModel.getObject(bo));
    }

    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbMaterialDealBo bo) {
        return Result.ok(materialModel.deal(bo));
    }

    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbMaterialSortBo> bos) {
        materialModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbMaterialBaseBo bo) {
        return Result.ok(materialModel.start(bo));
    }

    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbMaterialBaseBo request) {
        materialModel.delete(request);
        return Result.ok();
    }
}
