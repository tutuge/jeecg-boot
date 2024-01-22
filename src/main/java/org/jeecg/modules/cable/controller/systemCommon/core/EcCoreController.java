package org.jeecg.modules.cable.controller.systemCommon.core;


import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ImportExcelUtil;
import org.jeecg.common.util.ConvertUtils;
import org.jeecg.modules.cable.entity.systemCommon.EcCore;
import org.jeecg.modules.cable.service.systemCommon.EcCoreService;
import org.jeecg.poi.excel.ExcelImportUtil;
import org.jeecg.poi.excel.def.NormalExcelConstants;
import org.jeecg.poi.excel.entity.ExportParams;
import org.jeecg.poi.excel.entity.ImportParams;
import org.jeecg.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Tag(name = "芯数--系统接口", description = "芯数--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "4", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/core")
public class EcCoreController {

    @Resource
    private EcCoreService ecCoreService;


    @Operation(summary = "芯数-分页列表查询", description = "芯数-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EcCore>> queryPageList(EcCore ecCore,
                                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                               HttpServletRequest req) {
        Result<IPage<EcCore>> result = new Result<>();
        //------------------------------------------------------------------------------------------------
        Page<EcCore> page = new Page<>(pageNo, pageSize);
        QueryWrapper<EcCore> queryWrapper = QueryGenerator.initQueryWrapper(ecCore, req.getParameterMap());
        IPage<EcCore> pageList = ecCoreService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "芯数-添加", description = "芯数-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody EcCore ecCore) {
        EcCore byCore = ecCoreService.getByCore(ecCore.getCoreStr());
        if (ObjUtil.isNotNull(byCore)) {
            throw new RuntimeException("当前芯数已经存在");
        }
        ecCoreService.save(ecCore);
        return Result.ok("添加成功！");
    }


    @Operation(summary = "芯数-编辑", description = "芯数-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcCore> edit(@RequestBody EcCore ecCore) {
        Integer newEcCoreId = ecCore.getEcCoreId();
        EcCore byCore = ecCoreService.getByCore(ecCore.getCoreStr());
        if (ObjUtil.isNotNull(byCore)) {
            Integer oldEcCoreId = byCore.getEcCoreId();
            if (!Objects.equals(newEcCoreId, oldEcCoreId)) {
                throw new RuntimeException("当前芯数已经存在");
            }
        }
        Result<EcCore> result = new Result<>();
        EcCore core = ecCoreService.getById(ecCore.getEcCoreId());
        if (core == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = ecCoreService.updateById(ecCore);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }
        return result;
    }


    @Operation(summary = "芯数-通过id删除", description = "芯数-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id") String id) {
        try {
            ecCoreService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    @Operation(summary = "芯数-批量删除", description = "芯数-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EcCore> deleteBatch(@RequestParam(name = "ids") String ids) {
        Result<EcCore> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.ecCoreService.removeByIds(Arrays.asList(ids.split(",")));
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
    @Operation(summary = "芯数-通过id查询", description = "芯数-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EcCore> queryById(@RequestParam(name = "id") Integer id) {
        Result<EcCore> result = new Result<>();
        EcCore ecCore = ecCoreService.getById(id);
        if (ecCore == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(ecCore);
            result.setSuccess(true);
        }
        return result;
    }


    @Operation(summary = "芯数-导出", description = "芯数-导出")
    @PostMapping(value = "/exportEcuQualifiedXls")
    public ModelAndView exportEcuQualifiedXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<EcCore> queryWrapper = null;
        String paramsStr = request.getParameter("paramsStr");
        if (ConvertUtils.isNotEmpty(paramsStr)) {
            String deString = URLDecoder.decode(paramsStr, StandardCharsets.UTF_8);
            EcCore ecCore = JSON.parseObject(deString, EcCore.class);
            //------------------------------------------------------------------------------------------------
            queryWrapper = QueryGenerator.initQueryWrapper(ecCore, request.getParameterMap());
        }

        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<EcCore> pageList = ecCoreService.list(queryWrapper);
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "芯数对照列表");
        mv.addObject(NormalExcelConstants.CLASS, EcCore.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("芯数对照列表数据", "导出人:" + user.getRealname(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }


    @Operation(summary = "芯数-导入", description = "芯数-导入")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        // 错误信息
        List<String> errorMessage = new ArrayList<>();
        int successLines = 0, errorLines = 0;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<Object> importExcel = ExcelImportUtil.importExcel(file.getInputStream(), EcCore.class, params);
                List<String> list = ImportExcelUtil.importDateSave(importExcel, EcCoreService.class, errorMessage, CommonConstant.SQL_INDEX_UNIQ_AREA_STR);
                errorLines += list.size();
                successLines += (importExcel.size() - errorLines);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ImportExcelUtil.imporReturnRes(errorLines, successLines, errorMessage);
    }
}
