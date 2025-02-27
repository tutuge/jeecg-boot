package org.jeecg.modules.cable.controller.systemCommon.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyDealBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanyListBo;
import org.jeecg.modules.cable.controller.systemCommon.company.bo.EcdCompanySortBo;
import org.jeecg.modules.cable.controller.systemCommon.company.vo.EcdCompanyListVo;
import org.jeecg.modules.cable.controller.userCommon.company.bo.UserCompanyBaseBo;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.jeecg.modules.cable.model.systemCommon.EcdCompanyModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "公司信息--系统接口", description = "公司信息",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "411", parseValue = true)})})
@Validated
@RequestMapping("/ecableAdminPc/ecdCompany")
public class EcdCompanyController {
    @Resource
    EcdCompanyModel ecdCompanyModel;

    @Operation(summary = "新增编辑")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcdCompanyDealBo bo) {
        return Result.ok(ecdCompanyModel.deal(bo));
    }

    @Operation(summary = "获取列表")
    @PostMapping({"/getList"})
    public Result<EcdCompanyListVo> getList(@RequestBody EcdCompanyListBo bo) {
        return Result.ok(ecdCompanyModel.getList(bo));
    }

    @Operation(summary = "获取对象")
    @PostMapping({"/getObject"})
    public Result<EcdCompany> getObject(@Validated @RequestBody EcdCompanyBaseBo bo) {
        return Result.ok(ecdCompanyModel.getObject(bo));
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcdCompanySortBo> bos) {
        ecdCompanyModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "启用、禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcdCompanyBaseBo bo) {
        return Result.ok(ecdCompanyModel.start(bo));
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcdCompanyBaseBo bo) {
        ecdCompanyModel.delete(bo);
        return Result.ok();
    }

    @Operation(summary = "设置默认公司")
    @PostMapping({"/dealDefault"})
    public Result<?> dealDefault(@Validated @RequestBody EcdCompanyBaseBo bo) {
        ecdCompanyModel.dealDefault(bo);
        return Result.ok();
    }
}
