package org.jeecg.modules.cable.controller.user.customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.controller.user.customer.bo.EcCustomerDealBo;
import org.jeecg.modules.cable.controller.user.customer.bo.EcuCustomerBaseBo;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.jeecg.modules.cable.model.user.EcCustomerModel;
import org.jeecg.modules.cable.service.user.EcCustomerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "客户信息", description = "客户信息",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "999", parseValue = true)})})
@RestController
@Slf4j
@RequestMapping("/ecableErpPc/ecCustomer")
public class EcCustomerController {
    @Resource
    EcCustomerModel ecCustomerModel;

    @Resource
    private EcCustomerService ecCustomerService;

    @Operation(summary = "编辑")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcCustomerDealBo bo) {
        return Result.ok(ecCustomerModel.deal(bo));
    }


    @Operation(summary = "分页列表查询", description = "分页列表查询")
    @GetMapping(value = "/getList")
    public Result<IPage<EcCustomer>> queryPageList(EcCustomer bo,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        Result<IPage<EcCustomer>> result = new Result<>();
        //------------------------------------------------------------------------------------------------
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer ecCompanyId = sysUser.getEcCompanyId();
        QueryWrapper<EcCustomer> queryWrapper = QueryGenerator.initQueryWrapper(bo, req.getParameterMap());
        Page<EcCustomer> page = new Page<>(pageNo, pageSize);
        queryWrapper.eq("ec_company_id", ecCompanyId);
        queryWrapper.eq("ecu_id", sysUser.getUserId());
        queryWrapper.orderByDesc(true, "eccu_id");

        IPage<EcCustomer> pageList = ecCustomerService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "获取对象")
    @PostMapping({"/getObject"})
    public Result<EcCustomer> getObject(@Validated @RequestBody EcuCustomerBaseBo bo) {
        return Result.ok(ecCustomerModel.getObject(bo));
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcuCustomerBaseBo bo) {
        ecCustomerModel.delete(bo);
        return Result.ok();
    }
}
