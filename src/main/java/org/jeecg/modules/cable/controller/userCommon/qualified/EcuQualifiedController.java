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
import jakarta.servlet.ServletException;
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
import org.jeecg.modules.cable.entity.userCommon.EcbulUnit;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;
import org.jeecg.modules.cable.entity.userEcable.EcuSilkModel;
import org.jeecg.modules.cable.service.price.EcuqInputService;
import org.jeecg.modules.cable.service.userCommon.EcbulUnitService;
import org.jeecg.modules.cable.service.userCommon.EcuQualifiedService;
import org.jeecg.modules.cable.service.userEcable.EcuSilkModelService;
import org.jeecg.poi.excel.def.NormalExcelConstants;
import org.jeecg.poi.excel.entity.ExportParams;
import org.jeecg.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
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
    @Resource
    private EcuSilkModelService ecuSilkModelService;
    @Resource
    private EcbulUnitService ecbulUnitService;


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

        //组装要插入的数据
        List<Map<String,String>> list1 = new ArrayList<>();
        for (EcuqInput ecuqInput : list) {
            Map<String, String> inputData = new HashMap<>();
            Integer ecusmId = ecuqInput.getEcusmId();//型号id
            if (ObjectUtil.isNotNull(ecusmId)) {
                EcuSilkModel ecuSilkModel = ecuSilkModelService.getObjectById(ecusmId);
                if (ObjectUtil.isNotNull(ecuSilkModel)) {
                    String modelFullName = ecuSilkModel.getFullName();
                    inputData.put("型号", modelFullName);
                    inputData.put("产品名称", ecuSilkModel.getProductName());
                    inputData.put("额定电压", ecuSilkModel.getRatedVoltage());
                    String areaStr = ecuqInput.getAreaStr();
                    inputData.put("规格", areaStr);
                    Integer ecbuluId = ecuqInput.getEcbuluId();
                    if (ObjectUtil.isNotNull(ecbuluId)) {
                        EcbulUnit ecbulUnit = ecbulUnitService.getObjectById(ecbuluId);
                        inputData.put("长度", ecbulUnit.getLengthName());
                    } else {
                        inputData.put("长度", "");
                    }
                    inputData.put("执行标准", ecuSilkModel.getStandard());
                    Integer saleNumber = ecuqInput.getSaleNumber();
                    if (ObjectUtil.isNotNull(saleNumber)) {
                        inputData.put("数量", String.valueOf(saleNumber));
                    } else {
                        inputData.put("数量", "");
                    }
                    String dtdz = "";//导体电阻
                    String jydz = "";//绝缘电阻
                    if (StrUtil.isNotBlank(areaStr) && StrUtil.isNotBlank(modelFullName)) {
                        // 软线  YJRV  RVV  VVR  KVVR  YC  YZ
                        // 硬线  YJV
                        // 硬铝  YJLV
                        String lowerCase = modelFullName.toLowerCase();
                        String[] split = areaStr.split("\\+");
                        if (split.length > 0) {
                            String s = split[0];
                            String[] split1 = s.split("\\*");
                            if (split1.length > 0) {
                                String s1 = split1[split1.length - 1];
                                if (StrUtil.isNotBlank(s1)) {
                                    //软线
                                    if (lowerCase.contains("YJRV".toLowerCase()) || lowerCase.contains("RVV".toLowerCase())
                                            || lowerCase.contains("VVR".toLowerCase()) || lowerCase.contains("KVVR".toLowerCase())
                                            || lowerCase.contains("YC".toLowerCase()) || lowerCase.contains("YZ".toLowerCase())) {
                                        dtdz = RVV(s1);
                                    }
                                    if (lowerCase.contains("YJV".toLowerCase())) {
                                        dtdz = YJV(s1);
                                    }
                                    if (lowerCase.contains("YJLV".toLowerCase())) {
                                        dtdz = YJLV(s1);
                                    }
                                    jydz = JY(s1);
                                }
                            }
                        }
                    }
                    inputData.put("导体电阻", dtdz);
                    inputData.put("绝缘电阻", jydz);

                    inputData.put("耐压试验", ecuSilkModel.getPressurization());
                    Integer pressurizationTime = ecuSilkModel.getPressurizationTime();
                    if (ObjectUtil.isNotNull(pressurizationTime)) {
                        inputData.put("耐压试验时间", String.valueOf(pressurizationTime));
                    } else {
                        inputData.put("耐压试验时间", "");
                    }
                } else {
                    inputData.put("型号", "");
                    inputData.put("产品名称", "");
                    inputData.put("额定电压", "");
                    inputData.put("规格", "");
                    inputData.put("长度", "");
                    inputData.put("执行标准", "");
                    inputData.put("数量", "");
                    inputData.put("导体电阻", "");
                    inputData.put("绝缘电阻", "");
                    inputData.put("耐压试验", "");
                    inputData.put("耐压试验时间", "");
                }
            }
            list1.add(inputData);
        }
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                .withTemplate(byteArrayInputStream)    // 利用模板的输出流
                .build()) {
            for (int i = 0; i < size; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i).build();
                Map<String, String> data = list1.get(i);
                // 将数据写入到模板文件的对应sheet中
                excelWriter.fill(data, writeSheet);
            }
            excelWriter.finish();
        }
        //return ResultBean.success("数据导出成功!");
    }

    //硬线
    private String YJV(String square) {
        return switch (square) {
            case "0.5" -> "36";
            case "0.75" -> "24.5";
            case "1" -> "18.1";
            case "1.5" -> "12.1";
            case "2.5" -> "7.41";
            case "4" -> "4.61";
            case "6" -> "3.08";
            case "10" -> "1.83";
            case "16" -> "1.15";
            case "25" -> "0.727";
            case "35" -> "0.524";
            case "50" -> "0.387";
            case "70" -> "0.268";
            case "95" -> "0.193";
            case "120" -> "0.153";
            case "150" -> "0.124";
            case "185" -> "0.0991";
            case "240" -> "0.0754";
            case "300" -> "0.0601";
            case "400" -> "0.0470";
            case "500" -> "0.0366";
            case "630" -> "0.0283";
            case "800" -> "0.0221";
            default -> "";
        };
    }

    //软线
    private String RVV(String square) {
        return switch (square) {
            case "0.2" -> "92.3";
            case "0.3" -> "69.2";
            case "0.4" -> "48.2";
            case "0.5" -> "39";
            case "0.75" -> "26";
            case "1" -> "19.5";
            case "1.5" -> "13.3";
            case "2.5" -> "7.98";
            case "4" -> "4.95";
            case "6" -> "3.3";
            case "10" -> "1.91";
            case "16" -> "1.21";
            case "25" -> "0.78";
            case "35" -> "0.554";
            case "50" -> "0.386";
            case "70" -> "0.272";
            case "95" -> "0.206";
            case "120" -> "0.161";
            case "150" -> "0.129";
            case "185" -> "0.106";
            case "240" -> "0.0801";
            case "300" -> "0.0641";
            case "400" -> "0.0486";
            case "500" -> "0.0391";
            case "630" -> "0.0287";
            default -> "";
        };
    }

    //硬铝电阻
    private String YJLV(String square) {
        return switch (square) {
            case "2.5" -> "12.1";
            case "4" -> "7.41";
            case "6" -> "4.61";
            case "10" -> "3.02";
            case "16" -> "1.91";
            case "25" -> "1.2";
            case "35" -> "0.868";
            case "50" -> "0.641";
            case "70" -> "0.443";
            case "95" -> "0.3200";
            case "120" -> "0.2530";
            case "150" -> "0.2060";
            case "185" -> "0.1640";
            case "240" -> "0.1250";
            case "300" -> "0.1000";
            case "400" -> "0.0778";
            case "500" -> "0.0605";
            case "630" -> "0.0469";
            case "800" -> "0.0367";
            default -> "";
        };
    }

    //绝缘电阻
    private String JY(String square) {
        return switch (square) {
            case "0.5", "0.75", "1", "1.5" -> "20";
            case "2.5" -> "18";
            case "4" -> "16";
            case "6", "10" -> "13";
            case "16" -> "11";
            case "25", "35", "50", "70", "95", "120", "150", "185", "240", "300", "400", "500", "630" -> "10";
            default -> "";
        };
    }

    @SneakyThrows
    @Operation(summary = "下载合格证模板")
    @PostMapping({"/download"})
    public void downloadTemplate(HttpServletResponse response) {
        // 设置响应的内容类型为文件类型
        response.setContentType("application/octet-stream");
        // 获取要导出的文件路径
        String profile = UploadConfig.getProfile();
        String downloadPath = profile + StringUtils.substringAfter("/profile/合格证.xlsx", Constants.RESOURCE_PREFIX);
        File file = new File(downloadPath);
        String fileName = URLEncoder.encode("合格证", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        // 设置响应头，指定文件名
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".xlsx" + "\"");

        // 读取文件并输出到响应流
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException("Unable to export file.", e);
        }

    }
}
