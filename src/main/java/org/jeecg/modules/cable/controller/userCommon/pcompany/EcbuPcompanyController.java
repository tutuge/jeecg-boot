package org.jeecg.modules.cable.controller.userCommon.pcompany;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyBaseBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyDealBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanyListBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.bo.CompanySortBo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.CompanyListVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;
import org.jeecg.modules.cable.model.userCommon.EcbuPlatformCompanyModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "平台费率--用户接口", description = "平台费率--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "520", parseValue = true)})})
@RestController
public class EcbuPcompanyController {
    @Resource
    EcbuPlatformCompanyModel ecbuPlatformCompanyModel;


    @Operation(summary = "获取平台公司费率列表")

    @PostMapping({"/ecableErpPc/ecbuPcompany/getList"})
    public Result<CompanyListVo> getList(@RequestBody CompanyListBo bo) {
        return Result.ok(ecbuPlatformCompanyModel.getListAndCount(bo));
    }


    @Operation(summary = "获取平台公司费率")

    @PostMapping({"/ecableErpPc/ecbuPcompany/getObject"})
    public Result<EcbuPlatformCompany> getObject(@RequestBody CompanyBaseBo bo) {
        return Result.ok(ecbuPlatformCompanyModel.getObject(bo));
    }


    @Operation(summary = "编辑、新增平台公司费率")

    @PostMapping({"/ecableErpPc/ecbuPcompany/deal"})
    public Result<String> deal(@RequestBody CompanyDealBo bo) {
        return Result.ok(ecbuPlatformCompanyModel.saveOrUpdate(bo));
    }


    @Operation(summary = "平台公司费率排序")

    @PostMapping({"/ecableErpPc/ecbuPcompany/sort"})
    public Result<?> sort(@Validated @RequestBody List<CompanySortBo> bos) {
        ecbuPlatformCompanyModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "平台公司费率删除")

    @PostMapping({"/ecableErpPc/ecbuPcompany/delete"})
    public Result<?> delete(@RequestBody CompanyBaseBo bo) {
        ecbuPlatformCompanyModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "平台公司开启禁用")

    @PostMapping({"/ecableErpPc/ecbuPcompany/start"})
    public Result<String> start(@RequestBody CompanyBaseBo bo) {
        return Result.ok(ecbuPlatformCompanyModel.start(bo));
    }
}
