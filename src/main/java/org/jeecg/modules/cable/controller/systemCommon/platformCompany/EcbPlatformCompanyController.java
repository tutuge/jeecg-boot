package org.jeecg.modules.cable.controller.systemCommon.platformCompany;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo.EcbPlatformCompanyBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo.EcbPlatformCompanyDealBo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo.EcbPlatformCompanyListBo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.bo.EcbPlatformCompanySortBo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo.EcbPlatformCompanyListVo;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.model.systemCommon.EcbPlatformCompanyModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@Tag(name = "平台费率--系统接口", description = "平台费率--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "521", parseValue = true)})})
@RequestMapping("/ecableAdminPc/ecbPcompany")
public class EcbPlatformCompanyController {
    @Resource
    EcbPlatformCompanyModel ecbPlatformcompanyModel;

    @Operation(summary = "编辑/新增")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcbPlatformCompanyDealBo bo) {
        return Result.ok(ecbPlatformcompanyModel.deal(bo));
    }


    @Operation(summary = "获取列表")
    @PostMapping({"/getList"})
    public Result<EcbPlatformCompanyListVo> getList(@RequestBody EcbPlatformCompanyListBo bo) {
        return Result.ok(ecbPlatformcompanyModel.getList(bo));
    }


    @Operation(summary = "获取详情")
    @PostMapping({"/getObject"})
    public Result<EcbPlatformCompanyVo> getObject(@Validated @RequestBody EcbPlatformCompanyBaseBo bo) {
        return Result.ok(ecbPlatformcompanyModel.getObject(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcbPlatformCompanySortBo> bos) {
        ecbPlatformcompanyModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "启用、禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbPlatformCompanyBaseBo bo) {
        return Result.ok(ecbPlatformcompanyModel.start(bo));
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbPlatformCompanyBaseBo bo) {
        ecbPlatformcompanyModel.delete(bo);
        return Result.ok();
    }
}
