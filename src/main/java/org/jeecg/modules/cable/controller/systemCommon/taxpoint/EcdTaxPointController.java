package org.jeecg.modules.cable.controller.systemCommon.taxpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.taxpoint.bo.TaxPointBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.taxpoint.bo.TaxPointBo;
import org.jeecg.modules.cable.controller.systemCommon.taxpoint.bo.TaxPointDealBo;
import org.jeecg.modules.cable.controller.systemCommon.taxpoint.bo.TaxPointSortBo;
import org.jeecg.modules.cable.controller.systemCommon.taxpoint.vo.TaxPointVo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;
import org.jeecg.modules.cable.model.systemCommon.EcdTaxPointModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "税点--系统接口", description = "税点--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "550", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecdTaxpoint")
public class EcdTaxPointController {
    @Resource
    EcdTaxPointModel ecdTaxPointModel;

    @Operation(summary = "获取税点列表")
    @PostMapping({"/getList"})
    public Result<TaxPointVo> getList(@RequestBody TaxPointBo bo) {
        return Result.ok(ecdTaxPointModel.getListAndCount(bo));
    }


    @Operation(summary = "税点编辑")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody TaxPointDealBo bo) {
        return Result.ok(ecdTaxPointModel.deal(bo));
    }


    @Operation(summary = "税点开启")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody TaxPointBaseBo bo) {
        return Result.ok(ecdTaxPointModel.start(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<TaxPointSortBo> bos) {
        ecdTaxPointModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "税点删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody TaxPointBaseBo bo) {
        ecdTaxPointModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "获取税点详情")
    @PostMapping({"/getObject"})
    public Result<EcdTaxPoint> getObject(@Validated @RequestBody TaxPointBaseBo bo) {
        return Result.ok(ecdTaxPointModel.getObject(bo));
    }
}
