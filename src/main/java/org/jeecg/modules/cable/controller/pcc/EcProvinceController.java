package org.jeecg.modules.cable.controller.pcc;

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
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.jeecg.modules.cable.service.pcc.EcProvinceService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "省地址接口--基础资料接口", description = "省地址接口--基础资料接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "22", parseValue = true)})})
@RestController
@Slf4j
@RequestMapping("/ecableAdminPc/ecProvince")
public class EcProvinceController {

    @Resource
    private EcProvinceService ecProvinceService;


    @Operation(summary = "省份-分页列表查询", description = "省份-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EcProvince>> queryPageList(EcProvince ecProvince,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        Result<IPage<EcProvince>> result = new Result<>();
        //------------------------------------------------------------------------------------------------
        Page<EcProvince> page = new Page<>(pageNo, pageSize);
        IPage<EcProvince> pageList = ecProvinceService.selectPageData(page, ecProvince);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "省份-添加", description = "省份-添加")
    @PostMapping(value = "/add")
    public Result<EcProvince> add(@RequestBody EcProvince ecProvince) {
        Result<EcProvince> result = new Result<>();
        try {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            ecProvince.setEcaId(sysUser.getUserId());
            ecProvinceService.save(ecProvince);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @Operation(summary = "省份-编辑", description = "省份-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcProvince> edit(@RequestBody EcProvince ecProvince) {
        Result<EcProvince> result = new Result<>();
        EcProvince silkModel = ecProvinceService.getObjectById(ecProvince.getEcpId());
        if (silkModel == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = ecProvinceService.updateById(ecProvince);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }
        return result;
    }


    @Operation(summary = "省份-通过id删除", description = "省份-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) Integer id) {
        try {
            ecProvinceService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @Operation(summary = "省份-通过id查询", description = "省份-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EcProvince> queryById(@RequestParam(name = "id") Integer id) {
        Result<EcProvince> result = new Result<>();
        EcProvince ecProvince = ecProvinceService.getObjectById(id);
        if (ecProvince == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(ecProvince);
            result.setSuccess(true);
        }
        return result;
    }
}
