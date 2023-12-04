package org.jeecg.modules.cable.controller.systemCommon.account;


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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.cable.entity.systemCommon.EcAccount;
import org.jeecg.modules.cable.service.systemCommon.EcAccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "账户价格--系统接口", description = "账户价格--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "21", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/account")
public class AccountController {

    @Resource
    private EcAccountService ecAccountService;


    @Operation(summary = "账户价格-分页列表查询", description = "账户价格-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EcAccount>> queryPageList(EcAccount ecAccount,
                                                  @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  HttpServletRequest req) {
        Result<IPage<EcAccount>> result = new Result<>();
        //------------------------------------------------------------------------------------------------
        QueryWrapper<EcAccount> queryWrapper = QueryGenerator.initQueryWrapper(ecAccount, req.getParameterMap());
        Page<EcAccount> page = new Page<>(pageNo, pageSize);
        IPage<EcAccount> pageList = ecAccountService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "账户价格-添加", description = "账户价格-添加")
    @PostMapping(value = "/add")
    public Result<EcAccount> add(@Validated @RequestBody EcAccount ecAccount) {
        Result<EcAccount> result = new Result<>();
        try {
            ecAccountService.save(ecAccount);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @Operation(summary = "账户价格-编辑", description = "账户价格-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcAccount> edit(@Validated @RequestBody EcAccount ecAccount) {
        Result<EcAccount> result = new Result<>();
        EcAccount byId = ecAccountService.getById(ecAccount.getId());
        if (byId == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = ecAccountService.updateById(ecAccount);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }


    @Operation(summary = "账户价格-通过id删除", description = "账户价格-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            ecAccountService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    @Operation(summary = "账户价格-批量删除", description = "账户价格-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EcAccount> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<EcAccount> result = new Result<>();
        if (ids == null || ids.trim().isEmpty()) {
            result.error500("参数不识别！");
        } else {
            this.ecAccountService.removeByIds(List.of(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Operation(summary = "账户价格-通过id查询", description = "账户价格-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EcAccount> queryById(@RequestParam(name = "id", required = true) Integer id) {
        Result<EcAccount> result = new Result<>();
        EcAccount ecAccount = ecAccountService.getById(id);
        if (ecAccount == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(ecAccount);
            result.setSuccess(true);
        }
        return result;
    }
}
