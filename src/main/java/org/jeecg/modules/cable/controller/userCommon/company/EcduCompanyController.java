package org.jeecg.modules.cable.controller.userCommon.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanyBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UserCompanyBaseBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UserCompanyDealBo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UserCompanySortBo;
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
    @PostMapping({"/getList"})
    public Result<CompanyVo> getList(@RequestBody CompanyBo bo) {
        return Result.ok(ecduCompanyModel.getListAndCount(bo));
    }


    @Operation(summary = "获取公司")
    @PostMapping({"/getObject"})
    public Result<EcduCompany> getObject(@Validated @RequestBody UserCompanyBaseBo bo) {
        return Result.ok(ecduCompanyModel.getObject(bo));
    }


    @Operation(summary = "获取默认公司")
    @PostMapping({"/getObjectDefault"})
    public Result<EcduCompany> getObjectDefault() {
        return Result.ok(ecduCompanyModel.getObjectDefault());
    }


    @Operation(summary = "编辑公司")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody UserCompanyDealBo bo) {
        return Result.ok(ecduCompanyModel.deal(bo));
    }


    @Operation(summary = "公司排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<UserCompanySortBo> boList) {
        ecduCompanyModel.sort(boList);
        return Result.ok();
    }


    @Operation(summary = "公司删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody UserCompanyBaseBo bo) {
        ecduCompanyModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "公司开启禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody UserCompanyBaseBo bo) {
        return Result.ok(ecduCompanyModel.start(bo));
    }


    @Operation(summary = "设置默认公司")
    @PostMapping({"/dealDefault"})
    public Result<?> dealDefault(@Validated @RequestBody UserCompanyBaseBo bo) {
        ecduCompanyModel.dealDefault(bo);
        return Result.ok();
    }
}
