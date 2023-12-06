package org.jeecg.modules.cable.controller.userCommon.taxpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.EcduDealPercentBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.EcduTaxPointBaseBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.EcduTaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.bo.EcduTaxPointDealBo;
import org.jeecg.modules.cable.controller.userCommon.taxpoint.vo.EcduTaxPointVo;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;
import org.jeecg.modules.cable.model.userCommon.EcduTaxPointModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "税点--用户接口", description = "税点--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "551", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecduTaxpoint")
public class EcduTaxPointController {
    @Resource
    EcduTaxPointModel ecduTaxpointModel;


    @Operation(summary = "税点列表")
    @PostMapping({"/getList"})
    public Result<EcduTaxPointVo> getList(@RequestBody EcduTaxPointBo bo) {
        return Result.ok(ecduTaxpointModel.getListAndCount(bo));
    }

    @Operation(summary = "税点编辑")

    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcduTaxPointDealBo bo) {
        return Result.ok(ecduTaxpointModel.deal(bo));
    }


    @Operation(summary = "税点开启")

    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcduTaxPointBaseBo bo) {
        return Result.ok(ecduTaxpointModel.start(bo));
    }


    @Operation(summary = "税点删除")

    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcduTaxPointBaseBo bo) {
        ecduTaxpointModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "税点详情")

    @PostMapping({"/getObject"})
    public Result<EcduTaxPoint> getObjectPassSortId(@RequestBody EcduTaxPointBo bo) {
        return Result.ok(ecduTaxpointModel.getObject(bo));
    }


    @Operation(summary = "税点编辑")
    // dealPercent
    @PostMapping({"/dealPercent"})
    public Result<?> dealPercent(@RequestBody EcduDealPercentBo bo) {
        ecduTaxpointModel.dealPercent(bo);
        return Result.ok();
    }

}
