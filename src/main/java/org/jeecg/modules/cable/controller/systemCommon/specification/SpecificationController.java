package org.jeecg.modules.cable.controller.systemCommon.specification;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.ConvertUtils;
import org.jeecg.common.util.ImportExcelUtil;
import org.jeecg.modules.cable.entity.systemCommon.EcSpecifications;
import org.jeecg.modules.cable.service.systemCommon.EcSpecificationsService;
import org.jeecg.modules.cable.tools.ExcelUtils;
import org.jeecg.poi.excel.def.NormalExcelConstants;
import org.jeecg.poi.excel.entity.ExportParams;
import org.jeecg.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


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


    @Operation(summary = "规格对照-导入", description = "规格对照-导入")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(@RequestPart("file") MultipartFile file) throws IOException {

        // 错误信息
        List<String> errorMessage = new ArrayList<>();
        int successLines = 0, errorLines = 0;
        InputStream in = file.getInputStream();
        try {
            List<List<Object>> listob = getListByExcel(in, file.getOriginalFilename());
            System.out.println(listob);

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
        return ImportExcelUtil.imporReturnRes(errorLines, successLines, errorMessage);
    }





    /**
     * 描述：获取IO流中的数据，组装成List<List<Object>>对象
     */
    public List<List<Object>> getListByExcel(InputStream in, String fileName) throws Exception {
        List<List<Object>> list;
        // 创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet;  // 页数
        Row row;  // 行数
        Cell cell;  // 列数
        list = new ArrayList<>();
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 遍历当前sheet中的所有行
            for (int j = 0; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                // 遍历所有的列
                List<Object> li = new ArrayList<>();
                for (int y = 0; y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(this.getValue(cell));
                }
                list.add(li);
            }
        }
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
        switch (cell.getCellType()) {
            // 数值型
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 如果是date类型则 ，获取该cell的date值
                    Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    value = format.format(date);
                } else {// 纯数字
                    BigDecimal big = BigDecimal.valueOf(cell.getNumericCellValue());
                    value = big.toString();
                    // 解决1234.0  去掉后面的.0
                    if (null != value && !value.trim().isEmpty()) {
                        String[] item = value.split("[.]");
                        if (1 < item.length && "0".equals(item[1])) {
                            value = item[0];
                        }
                    }
                }
            }
            // 字符串类型
            case STRING -> value = cell.getStringCellValue();

            // 公式类型
            case FORMULA -> {
                // 读公式计算值
                value = String.valueOf(cell.getNumericCellValue());
                if ("NaN".equals(value)) {// 如果获取的数据值为非法值,则转换为获取字符串
                    value = cell.getStringCellValue();
                }
            }
            // 布尔类型
            case BOOLEAN -> value = " " + cell.getBooleanCellValue();
            default -> value = cell.getStringCellValue();
        }
        assert value != null;
        if ("null".endsWith(value.trim())) {
            value = "";
        }
        return value;
    }
}
