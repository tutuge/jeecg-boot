package org.jeecg.modules.cable.controller.systemCommon.specification;

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
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ImportExcelUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.cable.entity.systemCommon.EcSpecifications;
import org.jeecg.modules.cable.service.systemCommon.EcSpecificationsService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
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
@Tag(name = "规格对照--系统接口", description = "规格对照--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "21", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/specification")
public class SpecificationController {

    @Resource
    private EcSpecificationsService specificationsService;


    @Operation(summary = "规格对照-分页列表查询", description = "规格对照-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EcSpecifications>> queryPageList(EcSpecifications specifications,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest req) {
        Result<IPage<EcSpecifications>> result = new Result<>();

        //------------------------------------------------------------------------------------------------
        QueryWrapper<EcSpecifications> queryWrapper = QueryGenerator.initQueryWrapper(specifications, req.getParameterMap());
        Page<EcSpecifications> page = new Page<>(pageNo, pageSize);
        IPage<EcSpecifications> pageList = specificationsService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "规格对照-添加", description = "规格对照-添加")
    @PostMapping(value = "/add")
    public Result<EcSpecifications> add(@RequestBody EcSpecifications specifications) {
        Result<EcSpecifications> result = new Result<EcSpecifications>();
        try {
            specificationsService.save(specifications);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @Operation(summary = "规格对照-编辑", description = "规格对照-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcSpecifications> edit(@RequestBody EcSpecifications specifications) {
        Result<EcSpecifications> result = new Result<>();
        EcSpecifications sysPositionEntity = specificationsService.getById(specifications.getSpecificationsId());
        if (sysPositionEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = specificationsService.updateById(specifications);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }


    @Operation(summary = "规格对照-通过id删除", description = "规格对照-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            specificationsService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    @Operation(summary = "规格对照-批量删除", description = "规格对照-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EcSpecifications> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<EcSpecifications> result = new Result<EcSpecifications>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.specificationsService.removeByIds(Arrays.asList(ids.split(",")));
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
    @Operation(summary = "规格对照-通过id查询", description = "规格对照-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EcSpecifications> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<EcSpecifications> result = new Result<>();
        EcSpecifications specifications = specificationsService.getById(id);
        if (specifications == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(specifications);
            result.setSuccess(true);
        }
        return result;
    }


    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<EcSpecifications> queryWrapper = null;
        String paramsStr = request.getParameter("paramsStr");
        if (oConvertUtils.isNotEmpty(paramsStr)) {
            String deString = URLDecoder.decode(paramsStr, StandardCharsets.UTF_8);
            EcSpecifications specifications = JSON.parseObject(deString, EcSpecifications.class);
            //------------------------------------------------------------------------------------------------
            queryWrapper = QueryGenerator.initQueryWrapper(specifications, request.getParameterMap());
        }

        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<EcSpecifications> pageList = specificationsService.list(queryWrapper);
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "规格对照列表");
        mv.addObject(NormalExcelConstants.CLASS, EcSpecifications.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("规格对照列表数据", "导出人:" + user.getRealname(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }


    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                List<Object> importExcel = ExcelImportUtil.importExcel(file.getInputStream(), EcSpecifications.class, params);
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

    /**
     * 通过code查询
     *
     * @param code
     * @return
     */
    @Operation(summary = "规格对照-通过area查询", description = "规格对照-通过area查询")
    @GetMapping(value = "/queryByArea")
    public Result<EcSpecifications> queryByArea(@RequestParam(name = "area", required = true) String area) {
        Result<EcSpecifications> result = new Result<>();
        LambdaQueryWrapper<EcSpecifications> eq = Wrappers.lambdaQuery(EcSpecifications.class).like(EcSpecifications::getAreaStr, area);
        EcSpecifications specifications = specificationsService.getOne(eq);
        if (specifications == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(specifications);
            result.setSuccess(true);
        }
        return result;
    }
}
