package org.jeecg.modules.cable.controller.systemEcable.SilkModel;


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
import org.jeecg.modules.cable.controller.systemEcable.SilkModel.vo.EcSilkModelVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilkModel;
import org.jeecg.modules.cable.service.systemCommon.EcSpecificationsService;
import org.jeecg.modules.cable.service.systemDelivery.EcSilkModelService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "型号--系统接口", description = "型号--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "304", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/silk/model")
public class EcSilkModelController {

    @Resource
    private EcSilkModelService ecSilkModelService;


    @Operation(summary = "型号-分页列表查询", description = "型号-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EcSilkModelVo>> queryPageList(EcSilkModel ecSilkModel,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest req) {
        Result<IPage<EcSilkModelVo>> result = new Result<>();
        //------------------------------------------------------------------------------------------------
        Page<EcSilkModel> page = new Page<>(pageNo, pageSize);
        IPage<EcSilkModelVo> pageList = ecSilkModelService.selectPageData(page, ecSilkModel);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "型号-添加", description = "型号-添加")
    @PostMapping(value = "/add")
    public Result<EcSilkModel> add(@RequestBody EcSilkModel ecSilkModel) {
        Result<EcSilkModel> result = new Result<>();
        try {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            ecSilkModel.setEcaId(sysUser.getUserId());
            ecSilkModelService.save(ecSilkModel);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @Operation(summary = "型号-编辑", description = "型号-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcSilkModel> edit(@RequestBody EcSilkModel ecSilkModel) {
        Result<EcSilkModel> result = new Result<>();
        EcSilkModel silkModel = ecSilkModelService.getById(ecSilkModel.getEcSilkId());
        if (silkModel == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = ecSilkModelService.updateById(ecSilkModel);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }
        return result;
    }


    @Operation(summary = "型号-通过id删除", description = "型号-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            ecSilkModelService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    @Operation(summary = "型号-批量删除", description = "型号-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EcSilkModel> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<EcSilkModel> result = new Result<>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.ecSilkModelService.removeByIds(Arrays.asList(ids.split(",")));
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
    @Operation(summary = "型号-通过id查询", description = "型号-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EcSilkModelVo> queryById(@RequestParam(name = "id", required = true) Integer id) {
        Result<EcSilkModelVo> result = new Result<>();
        EcSilkModelVo ecSilkModel = ecSilkModelService.getVoById(id);
        if (ecSilkModel == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(ecSilkModel);
            result.setSuccess(true);
        }
        return result;
    }


    @Operation(summary = "型号-导出", description = "型号-导出")
    @PostMapping(value = "/exportSpecificationsXls")
    public ModelAndView exportSpecificationsXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<EcSilkModel> queryWrapper = null;
        String paramsStr = request.getParameter("paramsStr");
        if (ConvertUtils.isNotEmpty(paramsStr)) {
            String deString = URLDecoder.decode(paramsStr, StandardCharsets.UTF_8);
            EcSilkModel ecSilkModel = JSON.parseObject(deString, EcSilkModel.class);
            //------------------------------------------------------------------------------------------------
            queryWrapper = QueryGenerator.initQueryWrapper(ecSilkModel, request.getParameterMap());
        }

        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<EcSilkModel> pageList = ecSilkModelService.list(queryWrapper);
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "规格对照列表");
        mv.addObject(NormalExcelConstants.CLASS, EcSilkModel.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("规格对照列表数据", "导出人:" + user.getRealname(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }


    @Operation(summary = "型号-导入", description = "型号-导入")
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
                List<Object> importExcel = ExcelImportUtil.importExcel(file.getInputStream(), EcSilkModel.class, params);
                List<String> list = ImportExcelUtil.importDateSave(importExcel, EcSpecificationsService.class, errorMessage, CommonConstant.SQL_INDEX_UNIQ_AREA_STR);
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
