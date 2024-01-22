package org.jeecg.modules.cable.controller.systemCommon.platform;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ConvertUtils;
import org.jeecg.modules.cable.entity.systemCommon.EcPlatform;
import org.jeecg.modules.cable.service.systemCommon.EcPlatformService;
import org.jeecg.poi.excel.def.NormalExcelConstants;
import org.jeecg.poi.excel.entity.ExportParams;
import org.jeecg.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Slf4j
@Tag(name = "平台类型--系统接口", description = "平台类型--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "23", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/platform")
public class PlatformController {

    @Resource
    private EcPlatformService platformService;


    @Operation(summary = "平台类型-分页列表查询", description = "平台类型-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EcPlatform>> queryPageList(EcPlatform ecPlatform,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        Result<IPage<EcPlatform>> result = new Result<>();

        //------------------------------------------------------------------------------------------------
        QueryWrapper<EcPlatform> queryWrapper = QueryGenerator.initQueryWrapper(ecPlatform, req.getParameterMap());
        Page<EcPlatform> page = new Page<>(pageNo, pageSize);
        IPage<EcPlatform> pageList = platformService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "平台类型-添加", description = "平台类型-添加")
    @PostMapping(value = "/add")
    public Result<EcPlatform> add(@RequestBody EcPlatform platform) {
        Result<EcPlatform> result = new Result<>();
        try {
            platform.setAddTime(new Date());
            platformService.save(platform);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @Operation(summary = "平台类型-编辑", description = "平台类型-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcPlatform> edit(@RequestBody EcPlatform platform) {
        Result<EcPlatform> result = new Result<>();
        EcPlatform ecPlatform = platformService.getById(platform.getPlatformId());
        if (ecPlatform == null) {
            result.error500("未找到对应实体");
        } else {
            platform.setUpdateTime(new Date());
            boolean ok = platformService.updateById(platform);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }
        return result;
    }


    @Operation(summary = "平台类型-通过id删除", description = "平台类型-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id") Integer id) {
        try {
            platformService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    @Operation(summary = "平台类型-批量删除", description = "平台类型-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EcPlatform> deleteBatch(@RequestParam(name = "ids") String ids) {
        Result<EcPlatform> result = new Result<EcPlatform>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.platformService.removeByIds(Arrays.asList(ids.split(",")));
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
    @Operation(summary = "平台类型-通过id查询", description = "平台类型-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EcPlatform> queryById(@RequestParam(name = "id") Integer id) {
        Result<EcPlatform> result = new Result<>();
        EcPlatform ecPlatform = platformService.getById(id);
        if (ecPlatform == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(ecPlatform);
            result.setSuccess(true);
        }
        return result;
    }


    @Operation(summary = "平台类型-导出", description = "平台类型-导出")
    @PostMapping(value = "/exportPlatformXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<EcPlatform> queryWrapper = null;
        String paramsStr = request.getParameter("paramsStr");
        if (ConvertUtils.isNotEmpty(paramsStr)) {
            String deString = URLDecoder.decode(paramsStr, StandardCharsets.UTF_8);
            EcPlatform ecPlatform = JSON.parseObject(deString, EcPlatform.class);
            //------------------------------------------------------------------------------------------------
            queryWrapper = QueryGenerator.initQueryWrapper(ecPlatform, request.getParameterMap());
        }

        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<EcPlatform> pageList = platformService.list(queryWrapper);
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "平台类型列表");
        mv.addObject(NormalExcelConstants.CLASS, EcPlatform.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("平台类型列表数据", "导出人:" + user.getRealname(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }


    @Operation(summary = "平台类型-通过名称查询", description = "平台类型-通过名称查询")
    @GetMapping(value = "/queryByName")
    public Result<EcPlatform> queryByArea(@RequestParam(name = "name") String name) {
        Result<EcPlatform> result = new Result<>();
        LambdaQueryWrapper<EcPlatform> eq = Wrappers.lambdaQuery(EcPlatform.class).like(EcPlatform::getPlatformName, name);
        EcPlatform ecPlatform = platformService.getOne(eq);
        if (ecPlatform == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(ecPlatform);
            result.setSuccess(true);
        }
        return result;
    }
}
