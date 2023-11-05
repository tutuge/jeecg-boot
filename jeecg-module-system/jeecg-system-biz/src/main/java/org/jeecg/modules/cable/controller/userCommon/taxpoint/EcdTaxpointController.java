package org.jeecg.modules.cable.controller.userCommon.taxpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.axle.bo.EcbuAxleSortBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBaseBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointDealBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.TaxPointSortBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.vo.TaxPointVo;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;
import org.jeecg.modules.cable.model.userCommon.EcdTaxpointModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "税点--系统接口", description = "税点--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "550", parseValue = true)})})
@RestController
public class EcdTaxpointController {
    @Resource
    EcdTaxpointModel ecdTaxpointModel;

    @Operation(summary = "获取税点列表")

    @PostMapping({"/ecableErpPc/ecdTaxpoint/getList"})
    public Result<TaxPointVo> getList(@RequestBody TaxPointBo bo) {
        return Result.ok(ecdTaxpointModel.getListAndCount(bo));
    }


    @Operation(summary = "税点编辑")

    @PostMapping({"/ecableErpPc/ecdTaxpoint/deal"})
    public Result<String> deal(@Validated @RequestBody TaxPointDealBo bo) {
        return Result.ok(ecdTaxpointModel.deal(bo));
    }


    @Operation(summary = "税点开启")

    @PostMapping({"/ecableErpPc/ecdTaxpoint/start"})
    public Result<String> start(@Validated @RequestBody TaxPointBaseBo bo) {
        return Result.ok(ecdTaxpointModel.start(bo));
    }


    @Operation(summary = "排序")

    @PostMapping({"/ecableErpPc/ecdTaxpoint/sort"})
    public Result<?> sort(@Validated @RequestBody List<TaxPointSortBo> bos) {
        ecdTaxpointModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "税点删除")

    @PostMapping({"/ecableErpPc/ecdTaxpoint/delete"})
    public Result<?> delete(@Validated @RequestBody TaxPointBaseBo bo) {
        ecdTaxpointModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "获取税点详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecdTaxpoint/getObject"})
    public Result<EcdTaxPoint> getObject(@Validated @RequestBody TaxPointBaseBo bo) {
        return Result.ok(ecdTaxpointModel.getObject(bo));
    }
}
