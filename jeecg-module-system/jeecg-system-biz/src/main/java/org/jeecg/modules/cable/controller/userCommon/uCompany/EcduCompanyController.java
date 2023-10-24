package org.jeecg.modules.cable.controller.userCommon.uCompany;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.uCompany.bo.CompanyBo;
import org.jeecg.modules.cable.controller.userCommon.uCompany.vo.CompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.jeecg.modules.cable.model.userCommon.EcduCompanyModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ApiSupport(order =560)
@Tag(name = "公司信息")
@RestController
public class EcduCompanyController {
    @Resource
    EcduCompanyModel ecduCompanyModel;

    @Operation(summary = "获取公司列表")
    //getList
    @PostMapping({"/ecableErpPc/ecduCompany/getList"})
    public Result<CompanyVo> getList(@RequestBody CompanyBo bo) {
        return Result.ok(ecduCompanyModel.getListAndCount(bo));
    }


    @Operation(summary = "获取公司")
    //getObject
    @PostMapping({"/ecableErpPc/ecduCompany/getObject"})
    public Result<EcduCompany> getObject(HttpServletRequest request) {
        return Result.ok(ecduCompanyModel.getObject(request));
    }


    @Operation(summary = "获取默认公司")
    //getObjectDefault
    @PostMapping({"/ecableErpPc/ecduCompany/getObjectDefault"})
    public Result<EcduCompany> getObjectDefault(HttpServletRequest request) {
        return Result.ok(ecduCompanyModel.getObjectDefault(request));
    }


    @Operation(summary = "编辑公司")
    //deal
    @PostMapping({"/ecableErpPc/ecduCompany/deal"})
    public Result<String> deal(HttpServletRequest request) {
        return Result.ok(ecduCompanyModel.deal(request));
    }


    @Operation(summary = "公司排序")
    //sort
    @PostMapping({"/ecableErpPc/ecduCompany/sort"})
    public Result<?> sort(HttpServletRequest request) {
        ecduCompanyModel.sort(request);
        return Result.ok();
    }


    @Operation(summary = "公司删除")
    //delete
    @PostMapping({"/ecableErpPc/ecduCompany/delete"})
    public Result<?> delete(HttpServletRequest request) {
        ecduCompanyModel.delete(request);
        return Result.ok();
    }


    @Operation(summary = "公司开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecduCompany/start"})
    public Result<String> start(HttpServletRequest request) {
        return Result.ok(ecduCompanyModel.start(request));
    }


    @Operation(summary = "设置默认公司")
    //dealDefault 更改为默认
    @PostMapping({"/ecableErpPc/ecduCompany/dealDefault"})
    public Result<?> dealDefault(HttpServletRequest request) {
        ecduCompanyModel.dealDefault(request);
        return Result.ok();
    }
}
