package org.jeecg.modules.cable.controller.userCommon.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.company.bo.CompanyBo;
import org.jeecg.modules.cable.controller.userCommon.company.vo.CompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;

import org.jeecg.modules.cable.model.userCommon.EcbuPcompanyModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "平台公司")
@RestController
public class EcbuPcompanyController {
    @Resource
    EcbuPcompanyModel ecbuPcompanyModel;


    @Operation(summary = "获取平台公司列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuPcompany/getList"})
    public Result<CompanyVo> getList(@RequestBody CompanyBo bo) {
        return Result.ok(ecbuPcompanyModel.getListAndCount(bo));
    }


    @Operation(summary = "获取平台公司")
    //getObject
    @PostMapping({"/ecableErpPc/ecbuPcompany/getObject"})
    public Result<EcbuPcompany> getObject(HttpServletRequest request) {
        return Result.ok(ecbuPcompanyModel.getObject(request));
    }


    @Operation(summary = "编辑平台公司")
    //deal
    @PostMapping({"/ecableErpPc/ecbuPcompany/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecbuPcompanyModel.deal(request));
    }


    @Operation(summary = "平台公司排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbuPcompany/sort"})
    public Result<?> sort(HttpServletRequest request) {
        ecbuPcompanyModel.sort(request);
        return Result.ok();
    }


    @Operation(summary = "平台公司删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbuPcompany/delete"})
    public Result<?> delete(HttpServletRequest request) {
        ecbuPcompanyModel.delete(request);
        return Result.ok();
    }


    @Operation(summary = "平台公司开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuPcompany/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(ecbuPcompanyModel.start(request));
    }
}
