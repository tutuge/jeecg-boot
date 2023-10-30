package org.jeecg.modules.cable.controller.userCommon.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanyBaseBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanyDealBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanyListBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanySortBo;
import org.jeecg.modules.cable.controller.userCommon.company.vo.CompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.jeecg.modules.cable.model.userCommon.EcbuPcompanyModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "平台费率", description = "平台费率",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "520", parseValue = true)})})
@RestController
public class EcbuPcompanyController {
    @Resource
    EcbuPcompanyModel ecbuPcompanyModel;


    @Operation(summary = "获取平台公司列表")
    // getList
    @PostMapping({"/ecableErpPc/ecbuPcompany/getList"})
    public Result<CompanyVo> getList(@RequestBody CompanyListBo bo) {
        return Result.ok(ecbuPcompanyModel.getListAndCount(bo));
    }


    @Operation(summary = "获取平台公司")
    // getObject
    @PostMapping({"/ecableErpPc/ecbuPcompany/getObject"})
    public Result<EcbuPcompany> getObject(@RequestBody CompanyBaseBo bo) {
        return Result.ok(ecbuPcompanyModel.getObject(bo));
    }


    @Operation(summary = "编辑、新增平台公司")
    // deal
    @PostMapping({"/ecableErpPc/ecbuPcompany/deal"})
    public Result<String> deal(@RequestBody CompanyDealBo bo) {
        return Result.ok(ecbuPcompanyModel.deal(bo));
    }


    @Operation(summary = "平台公司排序")
    // sort
    @PostMapping({"/ecableErpPc/ecbuPcompany/sort"})
    public Result<?> sort(@Validated @RequestBody List<CompanySortBo> bos) {
        ecbuPcompanyModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "平台公司删除")
    // delete
    @PostMapping({"/ecableErpPc/ecbuPcompany/delete"})
    public Result<?> delete(@RequestBody CompanyBaseBo bo) {
        ecbuPcompanyModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "平台公司开启禁用")
    // start
    @PostMapping({"/ecableErpPc/ecbuPcompany/start"})
    public Result<String> start(@RequestBody CompanyBaseBo bo) {
        return Result.ok(ecbuPcompanyModel.start(bo));
    }
}
