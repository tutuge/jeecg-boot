package org.jeecg.modules.cable.controller.systemCommon.pcompany;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyDealBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanyListBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.bo.EcbPcompanySortBo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPcompanyListVo;
import org.jeecg.modules.cable.controller.systemCommon.pcompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.model.systemCommon.EcbPcompanyModel;
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
public class EcbPcompanyController {
    @Resource
    EcbPcompanyModel ecbPcompanyModel;

    @Operation(summary = "编辑/新增")
    @PostMapping({"/deal"})
    public Result<String> deal(@Validated @RequestBody EcbPcompanyDealBo bo) {
        return Result.ok(ecbPcompanyModel.deal(bo));
    }


    @Operation(summary = "获取列表")
    @PostMapping({"/getList"})
    public Result<EcbPcompanyListVo> getList(@RequestBody EcbPcompanyListBo bo) {
        return Result.ok(ecbPcompanyModel.getList(bo));
    }


    @Operation(summary = "获取详情")
    @PostMapping({"/getObject"})
    public Result<EcbPlatformCompanyVo> getObject(@Validated @RequestBody EcbPcompanyBaseBo bo) {
        return Result.ok(ecbPcompanyModel.getObject(bo));
    }


    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<?> sort(@Validated @RequestBody List<EcbPcompanySortBo> bos) {
        ecbPcompanyModel.sort(bos);
        return Result.ok();
    }


    @Operation(summary = "启用、禁用")
    @PostMapping({"/start"})
    public Result<String> start(@Validated @RequestBody EcbPcompanyBaseBo bo) {
        return Result.ok(ecbPcompanyModel.start(bo));
    }


    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcbPcompanyBaseBo bo) {
        ecbPcompanyModel.delete(bo);
        return Result.ok();
    }
}
