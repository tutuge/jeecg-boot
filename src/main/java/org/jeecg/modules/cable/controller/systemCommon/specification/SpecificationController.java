package org.jeecg.modules.cable.controller.systemCommon.specification;

import cn.hutool.core.util.StrUtil;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ConvertUtils;
import org.jeecg.modules.cable.entity.systemCommon.EcSpecifications;
import org.jeecg.modules.cable.service.systemCommon.EcSpecificationsService;
import org.jeecg.poi.excel.def.NormalExcelConstants;
import org.jeecg.poi.excel.entity.ExportParams;
import org.jeecg.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Slf4j
@Tag(name = "规格对照--系统接口", description = "规格对照--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "21", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/specification")
public class SpecificationController {

    @Resource
    private EcSpecificationsService specificationsService;
    private final static String excel2003L = ".xls";    // 2003- 版本的excel
    private final static String excel2007U = ".xlsx";   // 2007+ 版本的excel


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
        Result<EcSpecifications> result = new Result<>();
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
    public Result<?> delete(@RequestParam(name = "id") String id) {
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
    public Result<EcSpecifications> deleteBatch(@RequestParam(name = "ids") String ids) {
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
    public Result<EcSpecifications> queryById(@RequestParam(name = "id") Integer id) {
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


    @Operation(summary = "规格对照-导出", description = "规格对照-导出")
    @PostMapping(value = "/exportSpecificationsXls")
    public ModelAndView exportSpecificationsXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<EcSpecifications> queryWrapper = null;
        String paramsStr = request.getParameter("paramsStr");
        if (ConvertUtils.isNotEmpty(paramsStr)) {
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

    @Operation(summary = "规格对照-导出模板", description = "规格对照-导出模板")
    @PostMapping(value = "/exportSpecificationsTemplate")
    public void exportSpecificationsTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 创建工作簿
            Workbook workbook = new XSSFWorkbook();
            // 创建工作表
            Sheet sheet = workbook.createSheet("Sheet1");
            // 创建表头行
            Row headerRow = sheet.createRow(0);
            Cell cell1 = headerRow.createCell(0);
            cell1.setCellValue("基本型号");
            Cell cell2 = headerRow.createCell(3);
            cell2.setCellValue("型号里面带YC  YZ  JHS字样的");
            // 合并单元格
            CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 1);
            CellRangeAddress mergedRegion1 = new CellRangeAddress(0, 0, 3, 4);
            sheet.addMergedRegion(mergedRegion);
            sheet.addMergedRegion(mergedRegion1);

            Row headerRow0 = sheet.createRow(1);
            Cell cell3 = headerRow0.createCell(0);
            cell3.setCellValue("规格简称");
            Cell cell4 = headerRow0.createCell(1);
            cell4.setCellValue("规格全称");
            Cell cell5 = headerRow0.createCell(3);
            cell5.setCellValue("规格简称");
            Cell cell6 = headerRow0.createCell(4);
            cell6.setCellValue("规格全称");
            // 设置合并后的单元格样式
            CellStyle mergedCellStyle = workbook.createCellStyle();
            mergedCellStyle.setAlignment(HorizontalAlignment.CENTER);
            mergedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            mergedCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            mergedCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell1.setCellStyle(mergedCellStyle);
            cell2.setCellStyle(mergedCellStyle);
            cell3.setCellStyle(mergedCellStyle);
            cell4.setCellStyle(mergedCellStyle);
            cell5.setCellStyle(mergedCellStyle);
            cell6.setCellStyle(mergedCellStyle);

            sheet.setColumnWidth(0, 25 * 256); // 256是POI中列宽的基本单位，乘以字符宽度
            sheet.setColumnWidth(1, 25 * 256);
            sheet.setColumnWidth(2, 5 * 256);
            sheet.setColumnWidth(3, 25 * 256);
            sheet.setColumnWidth(4, 25 * 256);


            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=file.xlsx");
            // 获取输出流
            OutputStream outputStream = response.getOutputStream();
            // 将工作簿写入输出流
            workbook.write(outputStream);

            // 刷新并关闭输出流
            outputStream.flush();
            outputStream.close();
            // 关闭工作簿
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("生成导入模板失败");
        }
    }


    @Operation(summary = "规格对照-导入", description = "规格对照-导入")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(@RequestPart("file") MultipartFile file) throws IOException {
        // 错误信息
        InputStream in = file.getInputStream();
        // 错误信息
        int successNum = 0, failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        try {
            List<List<String>> listObj = getListByExcel(in, file.getOriginalFilename());
            System.out.println(listObj);
            for (int j = 2; j < listObj.size(); j++) {
                List<String> list = listObj.get(j);
                String s0 = list.get(0);
                String s1 = list.get(1);
                String s2 = list.get(2);
                String s3 = list.get(3);
                String s4 = list.get(4);
                if (StrUtil.isNotBlank(s0) && StrUtil.isNotBlank(s1)) {
                    List<EcSpecifications> specifications = specificationsService.selectListByName(false, s0);
                    if (specifications.isEmpty()) {
                        specificationsService.insert(false, s0, s1);
                        successMsg.append("<br/>规格 " + "第" + j + "行" + s0 + "新增成功");
                        successNum++;
                    } else {
                        specificationsService.updateByName(false, s0, s1);
                        successMsg.append("<br/>规格 " + "第" + j + "行" + s0 + "修改成功");
                        successNum++;
                        //}
                    }
                }
                if ((StrUtil.isNotBlank(s0) && StrUtil.isBlank(s1)) || (StrUtil.isBlank(s0) && StrUtil.isNotBlank(s1))) {
                    failureMsg.append("<br/>规格 " + "第" + j + "行" + s0 + "对应的规格填写不完整");
                    failureNum++;
                }
                //------------------特殊规格-----------------------
                if (StrUtil.isNotBlank(s3) && StrUtil.isNotBlank(s4)) {
                    List<EcSpecifications> specifications = specificationsService.selectListByName(true, s3);
                    if (specifications.isEmpty()) {
                        specificationsService.insert(true, s3, s4);
                        successMsg.append("<br/>特殊规格 " + "第" + j + "行" + s3 + "新增成功");
                        successNum++;
                    } else {
                        specificationsService.updateByName(true, s3, s4);
                        successMsg.append("<br/>特殊规格 " + "第" + j + "行" + s3 + "修改成功");
                        successNum++;
                    }
                }
                if ((StrUtil.isNotBlank(s3) && StrUtil.isBlank(s4)) || (StrUtil.isBlank(s3) && StrUtil.isNotBlank(s4))) {
                    failureMsg.append("<br/>特殊规格 " + "第" + j + "行" + s3 + "对应的规格填写不完整");
                    failureNum++;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("文件导入失败:" + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return Result.ok(successMsg.toString());
    }


    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     */
    public List<List<String>> getListByExcel(InputStream in, String fileName) throws Exception {
        List<List<String>> list;
        // 创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet;  // 页数
        Row row;  // 行数
        Cell cell;  // 列数
        list = new ArrayList<>();
        //for (int i = 0; i < work.getNumberOfSheets(); i++) {
        sheet = work.getSheetAt(0);
        if (sheet == null) {
            throw new Exception("创建Excel工作薄为空！");
        }
        // 遍历当前sheet中的所有行
        for (int j = 0; j <= sheet.getLastRowNum(); j++) {
            row = sheet.getRow(j);
            if (row == null) {
                continue;
            }
            // 遍历所有的列
            List<String> li = new ArrayList<>();
            for (int y = 0; y < 5; y++) {
                cell = row.getCell(y);
                li.add(this.getValue(cell));
            }
            list.add(li);
        }
        //}
        return list;
    }

    /*描述：根据文件后缀，自适应上传文件的版本*/
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr);  // 2003-
        } else if (excel2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr);  // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /*描述：对表格中数值进行格式化*/
    // 解决excel类型问题，获得数值
    public String getValue(Cell cell) {
        String value = "";
        if (null == cell) {
            return value;
        }
        // 字符串类型
        if (Objects.requireNonNull(cell.getCellType()) == CellType.STRING) {
            value = cell.getStringCellValue();
        } else {
            value = cell.getStringCellValue();
        }
        assert value != null;
        if ("null".endsWith(value.trim())) {
            value = "";
        }
        return value;
    }
}
