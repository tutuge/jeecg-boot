package org.jeecg.modules.cable.controller.userQuality.uarea;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userQuality.uarea.bo.AreaBo;
import org.jeecg.modules.cable.controller.userQuality.uarea.bo.AreaSortBo;
import org.jeecg.modules.cable.controller.userQuality.uarea.bo.EcuAreaBo;
import org.jeecg.modules.cable.controller.userQuality.uarea.bo.UAreaBo;
import org.jeecg.modules.cable.entity.userQuality.EcuArea;
import org.jeecg.modules.cable.model.userQuality.EcuAreaModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@Tag(name = "获取截面--用户接口", description = "获取截面--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "103", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecuArea")
public class EcuAreaController {
    @Resource
    EcuAreaModel ecuAreaModel;

    @Operation(summary = "获取截面列表")
    @PostMapping({"/getList"})
    public Result<List<EcuArea>> getList(@RequestBody UAreaBo bo) {
        return Result.ok(ecuAreaModel.getList(bo));
    }

    @Operation(summary = "获取编辑截面信息")
    @PostMapping({"/getObject"})
    public Result<EcuArea> getObject(@RequestBody AreaBo bo) {
        return Result.ok(ecuAreaModel.getObject(bo));
    }

    @Operation(summary = "提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcuAreaBo bo) throws IOException {
        return Result.ok(ecuAreaModel.deal(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<AreaSortBo> bos) {
        ecuAreaModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "开启")
    @PostMapping({"/start"})
    public Result<String> start(@RequestBody AreaBo bo) {
        return Result.ok(ecuAreaModel.start(bo));
    }
}
