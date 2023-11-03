package org.jeecg.modules.cable.controller.userCommon.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanyBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UCompanyBaseBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UCompanyDealBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UCompanySortBo;
import org.jeecg.modules.cable.controller.userCommon.company.vo.CompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.model.userCommon.EcduCompanyModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "公司信息--用户接口", description = "公司信息--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "560", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecduCompany")
public class EcduCompanyController {
    @Resource
    EcduCompanyModel ecduCompanyModel;

    @Operation(summary = "获取公司列表")
    // getList
    @PostMapping({"/getList"})
    public Result<CompanyVo> getList(@RequestBody CompanyBo bo) {
        return Result.ok(ecduCompanyModel.getListAndCount(bo));
    }


    @Operation(summary = "获取公司")
    // getObject
    @PostMapping({"/getObject"})
    public Result<EcduCompany> getObject(@Validated @RequestBody UCompanyBaseBo bo) {
        return Result.ok(ecduCompanyModel.getObject(bo));
    }


    @Operation(summary = "获取默认公司")
    // getObjectDefault
    @PostMapping({"/getObjectDefault"})
    public Result<EcduCompany> getObjectDefault() {
        return Result.ok(ecduCompanyModel.getObjectDefault());
    }


    @Operation(summary = "编辑公司")
    // deal
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody UCompanyDealBo bo) {
        return Result.ok(ecduCompanyModel.deal(bo));
    }


    @Operation(summary = "公司排序")
    // sort
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<UCompanySortBo> boList) {
        ecduCompanyModel.sort(boList);
        return Result.ok();
    }


    @Operation(summary = "公司删除")
    // delete
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody UCompanyBaseBo bo) {
        ecduCompanyModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "公司开启禁用")
    // start
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody UCompanyBaseBo bo) {
        return Result.ok(ecduCompanyModel.start(bo));
    }


    @Operation(summary = "设置默认公司")
    // dealDefault 更改为默认
    @PostMapping({"/dealDefault"})
    public Result<?> dealDefault(@Validated @RequestBody UCompanyBaseBo bo) {
        ecduCompanyModel.dealDefault(bo);
        return Result.ok();
    }
}
