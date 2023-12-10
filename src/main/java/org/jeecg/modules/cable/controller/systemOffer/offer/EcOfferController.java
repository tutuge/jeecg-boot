package org.jeecg.modules.cable.controller.systemOffer.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemOffer.offer.bo.*;
import org.jeecg.modules.cable.controller.systemOffer.offer.vo.EcOfferVo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.model.systemOffer.EcOfferModel;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "电缆质量等级对应的成本库表--系统接口",
        description = "电缆质量等级对应的成本库表--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecOffer")
@Validated
@Slf4j
public class EcOfferController {
    @Resource
    EcOfferModel ecOfferModel;

    @Operation(summary = "获取电缆成本库表")
    @PostMapping({"/getList"})
    public Result<EcOfferVo> getList(@Validated @RequestBody EcOfferListBo bo) {
        return Result.ok(ecOfferModel.getListAndCount(bo));
    }

    @Operation(summary = "获取单行数据")
    @PostMapping({"/getObject"})
    public Result<EcOffer> getObject(@RequestBody EcOfferBaseBo bo) {
        return Result.ok(ecOfferModel.getObject(bo));
    }


    //@Operation(summary = "获取丝型号")
    //@PostMapping({"/getEcSilkPassEcqlId"})
    //public Result<EcSilk> getEcSilkPassEcqlId(@RequestBody SilkBo bo) {
    //    return Result.ok(ecOfferModel.getEcSilkPassEcqlId(bo));
    //}

    @Operation(summary = "开启禁用")
    @PostMapping({"/start"})
    public Result<?> start(@RequestBody List<EcOfferStartBo> bos) {
        ecOfferModel.start(bos);
        return Result.ok();
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcOfferInsertBo bo) {
        return Result.ok(ecOfferModel.saveOrUpdate(bo));
    }

    @Operation(summary = "批量编辑提交")
    @PostMapping({"/batch/saveOrUpdate"})
    public Result<String> batchSaveOrUpdate(@RequestBody EcOfferBatchBo ecOfferBatchBo) {
        StringBuilder msg = new StringBuilder();
        String ecoId = ecOfferBatchBo.getEcoId();
        String[] split = ecoId.split(",");
        for (String s : split) {
            try {
                EcOfferInsertBo bo = new EcOfferInsertBo();
                BeanUtils.copyProperties(ecOfferBatchBo, bo);
                bo.setEcoId(Integer.valueOf(s));
                ecOfferModel.saveOrUpdate(bo);
                msg = new StringBuilder("批量修改成功");
            } catch (Exception e) {
                msg.append(";").append("序号 ").append(s).append("  ").append("修改失败");
            }
        }
        return Result.ok(msg.toString());
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<String> sort(@RequestBody List<EcOfferSortBo> bos) {
        ecOfferModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "成本库表删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcOfferBaseBo bo) {
        ecOfferModel.delete(bo);
        return Result.ok();
    }

    @Operation(summary = "成本库表批量删除")
    @PostMapping({"/batch/delete"})
    public Result<?> batchDelete(@Validated @RequestBody List<EcOfferBaseBo> bos) {
        int num = 0;
        for (EcOfferBaseBo bo : bos) {
            try {
                ecOfferModel.delete(bo);
            } catch (Exception e) {
                num++;
                log.error("删除报错: ", e.getCause());
            }
        }
        if (num > 0) {
            return Result.error("有" + num + "行数据删除失败");
        }
        return Result.ok();
    }

    @Operation(summary = "成本库表-导出模板", description = "成本库表-导出模板")
    @PostMapping(value = "/exportTemplate")
    public void exportTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 创建工作簿
            Workbook workbook = new XSSFWorkbook();
            // 创建工作表
            Sheet sheet = workbook.createSheet("Sheet1");
            // 创建表头行
            Row headerRow0 = sheet.createRow(0);
            // 设置单元格样式
            CellStyle mergedCellStyle = workbook.createCellStyle();
            mergedCellStyle.setAlignment(HorizontalAlignment.CENTER);
            mergedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            String[] str = {"截面积", "成本加点", "粗芯丝号", "粗芯丝数", "粗芯丝绞合系数", "细芯丝号", "细芯丝数", "细芯丝绞合系数", "绝缘类型",
                    "粗芯绝缘厚度", "细芯绝缘厚度", "非铠装绕包带类型", "非铠装绕包带厚度", "铠装绕包带类型", "铠装绕包带厚度", "屏蔽类型", "屏蔽厚度",
                    "屏蔽系数(%)", "钢带类型", "钢带厚度", "钢带层数", "护套类型", "护套厚度", "铠装护套厚度", "云母带类型", "云母带厚度", "填充物类型", "成缆系数"};
            for (int i = 0; i < str.length; i++) {
                Cell cell = headerRow0.createCell(i);
                cell.setCellValue(str[i]);
                cell.setCellStyle(mergedCellStyle);
                sheet.setColumnWidth(i, 25 * 256); // 256是POI中列宽的基本单位，乘以字符宽度
            }
            // 设置响应头
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" +
                    new String(("成本库表").getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ".xlsx");
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


    @Operation(summary = "导入成本库表")
    @PostMapping({"/importData"})
    public Result<?> importData(@NotNull(message = "传递的文件不得为空") MultipartFile file,
                                @NotNull(message = "质量等级ID不得为空") Integer ecqlId) throws Exception {
        return ecOfferModel.importDeal(file, ecqlId);
    }

    @Operation(summary = "导出")
    @GetMapping({"/exportData"})
    public void exportData(HttpServletResponse response, Integer ecqlId) throws Exception {
        ecOfferModel.exportData(response, ecqlId);
    }


    //@Operation(summary = "方案筛选")
    //@PostMapping({"/getAddPercentList"})
    //public Result<List<String>> getAddPercentList(@RequestBody ProgrammeBo bo) {
    //    return Result.ok(ecOfferModel.getAddPercentList(bo));
    //}


    @Operation(summary = "获取编辑结构中的重量和金额")
    @PostMapping({"/getStructureData"})
    public Result<ProgrammeVo> getStructureData(@Validated @RequestBody EcOfferStructBo bo) {
        return Result.ok(ecOfferModel.getStructureData(bo));
    }

    @Operation(summary = "加载钢带和护套的厚度")
    @PostMapping({"/loadSteelbandThicknessAndSheathThickness"})
    public void loadSteelBandThicknessAndSheathThickness() {
        ecOfferModel.loadSteelBandThicknessAndSheathThickness();
    }
}
