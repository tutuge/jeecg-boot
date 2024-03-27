package org.jeecg.modules.cable.controller.userCommon.qualified;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.Constants;
import org.jeecg.common.util.ConvertUtils;
import org.jeecg.common.util.StringUtils;
import org.jeecg.common.util.file.FileUploadUtils;
import org.jeecg.config.UploadConfig;
import org.jeecg.modules.cable.entity.price.EcuqInput;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.userCommon.EcuQualifiedService;
import org.jeecg.poi.excel.def.NormalExcelConstants;
import org.jeecg.poi.excel.entity.ExportParams;
import org.jeecg.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Tag(name = "合格证模板--用户接口", description = "合格证模板用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "3", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/qualified")
public class EcuQualifiedController {

    @Resource
    private EcuQualifiedService ecuQualifiedService;
    @Resource
    private EcuqInputService ecuqInputService;


    @Operation(summary = "合格证-分页列表查询", description = "合格证-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EcuQualified>> queryPageList(EcuQualified ecuQualified,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        Result<IPage<EcuQualified>> result = new Result<>();
        //------------------------------------------------------------------------------------------------
        Page<EcuQualified> page = new Page<>(pageNo, pageSize);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        ecuQualified.setEcCompanyId(sysUser.getEcCompanyId());
        IPage<EcuQualified> pageList = ecuQualifiedService.selectPage(page, ecuQualified);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }


    @Operation(summary = "合格证-添加", description = "合格证-添加")
    @PostMapping(value = "/add")
    public Result<EcuQualified> add(@RequestBody EcuQualified ecuQualified) {
        Result<EcuQualified> result = new Result<>();
        try {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            ecuQualified.setEcuId(sysUser.getUserId());
            ecuQualified.setEcCompanyId(sysUser.getEcCompanyId());
            ecuQualifiedService.save(ecuQualified);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @Operation(summary = "合格证-编辑", description = "合格证-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<EcuQualified> edit(@RequestBody EcuQualified ecuQualified) {
        Result<EcuQualified> result = new Result<>();
        EcuQualified silkModel = ecuQualifiedService.getById(ecuQualified.getEcuqId());
        if (silkModel == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = ecuQualifiedService.updateById(ecuQualified);
            // TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }
        return result;
    }


    @Operation(summary = "合格证-通过id删除", description = "合格证-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id") String id) {
        try {
            ecuQualifiedService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }


    @Operation(summary = "合格证-批量删除", description = "合格证-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<EcuQualified> deleteBatch(@RequestParam(name = "ids") String ids) {
        Result<EcuQualified> result = new Result<>();
        if (ids == null || ids.trim().isEmpty()) {
            result.error500("参数不识别！");
        } else {
            this.ecuQualifiedService.removeByIds(Arrays.asList(ids.split(",")));
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
    @Operation(summary = "合格证-通过id查询", description = "合格证-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EcuQualified> queryById(@RequestParam(name = "id") Integer id) {
        Result<EcuQualified> result = new Result<>();
        EcuQualified ecuQualified = ecuQualifiedService.getVoById(id);
        if (ecuQualified == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(ecuQualified);
            result.setSuccess(true);
        }
        return result;
    }


    @Operation(summary = "合格证-导出", description = "合格证-导出")
    @PostMapping(value = "/exportEcuQualifiedXls")
    public ModelAndView exportEcuQualifiedXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<EcuQualified> queryWrapper = null;
        String paramsStr = request.getParameter("paramsStr");
        if (ConvertUtils.isNotEmpty(paramsStr)) {
            String deString = URLDecoder.decode(paramsStr, StandardCharsets.UTF_8);
            EcuQualified ecuQualified = JSON.parseObject(deString, EcuQualified.class);
            //------------------------------------------------------------------------------------------------
            queryWrapper = QueryGenerator.initQueryWrapper(ecuQualified, request.getParameterMap());
        }

        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<EcuQualified> pageList = ecuQualifiedService.list(queryWrapper);
        LoginUser user = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        // 导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "合格证对照列表");
        mv.addObject(NormalExcelConstants.CLASS, EcuQualified.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("合格证对照列表数据", "导出人:" + user.getRealname(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }


    @Operation(summary = "合格证模板-导入", description = "合格证-导入")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(@RequestParam(name = "file") MultipartFile file,
                                 @RequestParam(name = "fileName") String fileName) throws IOException {
        try {
            // 上传文件路径
            String filePath = UploadConfig.getUploadPath();
            // 上传并返回新文件名称
            String excelFileName = FileUploadUtils.upload(filePath, file);
            EcuQualified qualified = new EcuQualified();
            qualified.setPath(excelFileName);
            qualified.setFileName(fileName);
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            qualified.setEcCompanyId(sysUser.getEcCompanyId());
            qualified.setEcuId(sysUser.getUserId());
            qualified.setAddTime(new Date());
            ecuQualifiedService.save(qualified);
            return Result.ok();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @SneakyThrows
    @Operation(summary = "填充合格证模板")
    @PostMapping({"/hgz"})
    public void hgz(HttpServletResponse response,
                    @RequestParam(name = "ecuqId") Integer ecuqId,
                    @RequestParam(name = "id") Integer id) {
        EcuQualified byId = ecuQualifiedService.getById(id);
        if (ObjectUtil.isNull(byId)) {
            throw new RuntimeException("合格证模板不存在！");
        }
        String path = byId.getPath();
        if (StrUtil.isBlank(path)) {
            throw new RuntimeException("合格证模板文件路径不存在！");
        }
        //查询报价单的每行数据
        EcuqInput input = new EcuqInput();
        input.setEcuqId(ecuqId);
        List<EcuqInput> list = ecuqInputService.getList(input);
        if (CollUtil.isEmpty(list)) {
            throw new RuntimeException("报价单明细不存在！");
        }
        int size = list.size();
        // 重要! 设置返回格式是excel形式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        // 设置编码格式
        response.setCharacterEncoding("utf-8");
        // 设置URLEncoder.encode 防止中文乱码
        String fileName = URLEncoder.encode("合格证导出", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        // 设置响应头
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        String profile = UploadConfig.getProfile();
        String downloadPath = profile + StringUtils.substringAfter(path, Constants.RESOURCE_PREFIX);
        FileInputStream inputStream = new FileInputStream(downloadPath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        // 设置模板sheet数量, 需要复制工作表
        Sheet newSheet = workbook.cloneSheet(0);
        for (int i = 1; i < size; i++) {
            workbook.setSheetName(workbook.getSheetIndex(newSheet), "sheet" + (i + 1));
        }
        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        workbook.write(ops);
        byte[] byteArray = ops.toByteArray();
        // 原文件流后续已不使用，此处关闭
        inputStream.close();
        ops.close();
        //创建新的流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);

        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .withTemplate(byteArrayInputStream)    // 利用模板的输出流
                .build()) {
            for (int i = 0; i < size; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i).build();
                Map<String, Object> data = new HashMap<>();
                data.put("产品名称", "铜芯交联聚乙烯绝缘聚氯乙烯护套电力电缆"); // 假设模板中A1是需要填充的单元格
                // 将数据写入到模板文件的对应sheet中
                excelWriter.fill(data, writeSheet);

            }
            excelWriter.finish();
        }
        //return ResultBean.success("数据导出成功!");
    }
}
