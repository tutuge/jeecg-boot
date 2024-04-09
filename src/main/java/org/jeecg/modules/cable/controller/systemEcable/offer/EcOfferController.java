package org.jeecg.modules.cable.controller.systemEcable.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.offer.bo.*;
import org.jeecg.modules.cable.controller.systemEcable.offer.vo.EcOfferVo;
import org.jeecg.modules.cable.controller.userEcable.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;
import org.jeecg.modules.cable.model.systemOffer.EcOfferModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                ecOfferModel.batchSaveOrUpdate(Integer.valueOf(s), ecOfferBatchBo);
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
    @GetMapping(value = "/exportTemplate")
    public void exportTemplate(@NotNull(message = "质量等级ID不得为空") Integer ecqlId, HttpServletResponse response) {
        ecOfferModel.exportTemplate(ecqlId, response);
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

    //@Operation(summary = "加载钢带和护套的厚度")
    //@PostMapping({"/loadSteelbandThicknessAndSheathThickness"})
    //public void loadSteelBandThicknessAndSheathThickness() {
    //    ecOfferModel.loadSteelBandThicknessAndSheathThickness();
    //}
}
