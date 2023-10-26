package org.jeecg.modules.cable.controller.userCommon.utaxpoint;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.utaxpoint.bo.DealPercentBo;
import org.jeecg.modules.cable.controller.userCommon.utaxpoint.bo.UTaxPointBaseBo;
import org.jeecg.modules.cable.controller.userCommon.utaxpoint.bo.UTaxPointBo;
import org.jeecg.modules.cable.controller.userCommon.utaxpoint.bo.UTaxPointDealBo;
import org.jeecg.modules.cable.controller.userCommon.utaxpoint.vo.UTaxPointVo;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxpoint;
import org.jeecg.modules.cable.model.userCommon.EcduTaxpointModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "税点--用户接口", description = "税点--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "520", parseValue = true)})})
@RestController
public class EcduTaxpointController {
    @Resource
    EcduTaxpointModel ecduTaxpointModel;


    @Operation(summary = "税点列表")
    //getList
    @PostMapping({"/ecableErpPc/ecduTaxpoint/getList"})
    public Result<UTaxPointVo> getList(@RequestBody UTaxPointBo bo) {
        return Result.ok(ecduTaxpointModel.getListAndCount(bo));
    }

    @Operation(summary = "税点编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecduTaxpoint/deal"})
    public Result<String> deal(@Validated @RequestBody UTaxPointDealBo bo) {
        return Result.ok(ecduTaxpointModel.deal(bo));
    }


    @Operation(summary = "税点开启")
    //start
    @PostMapping({"/ecableErpPc/ecduTaxpoint/start"})
    public Result<String> start(@Validated@RequestBody UTaxPointBaseBo bo) {
        return Result.ok(ecduTaxpointModel.start(bo));
    }


    @Operation(summary = "税点删除")
    //delete
    @PostMapping({"/ecableErpPc/ecduTaxpoint/delete"})
    public Result<?> delete(@Validated@RequestBody UTaxPointBaseBo bo) {
        ecduTaxpointModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "税点详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecduTaxpoint/getObject"})
    public Result<EcduTaxpoint> getObjectPassSortId(@RequestBody UTaxPointBo bo) {
        return Result.ok(ecduTaxpointModel.getObject(bo));
    }


    @Operation(summary = "税点编辑")
    //dealPercent
    @PostMapping({"/ecableErpPc/ecduTaxpoint/dealPercent"})
    public Result<?> dealPercent(@RequestBody DealPercentBo bo) {
        ecduTaxpointModel.dealPercent(bo);
        return Result.ok();
    }

}
