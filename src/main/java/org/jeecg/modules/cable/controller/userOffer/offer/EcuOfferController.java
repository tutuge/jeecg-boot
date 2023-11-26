package org.jeecg.modules.cable.controller.userOffer.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userOffer.offer.bo.*;
import org.jeecg.modules.cable.controller.userOffer.offer.vo.EcuOfferVo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeBo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "电缆质量等级对应的成本库表--用户接口", description = "电缆质量等级对应的成本库表--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "2", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecuOffer")
public class EcuOfferController {
    @Resource
    EcuOfferModel ecuOfferModel;

    @Operation(summary = "获取电缆成本库表等参数")
    @PostMapping({"/getList"})
    public Result<EcuOfferVo> getList(@Validated @RequestBody EcuOfferListBo bo) {
        return Result.ok(ecuOfferModel.getListAndCount(bo));
    }

    @Operation(summary = "获取单行数据")
    @PostMapping({"/getObject"})
    public Result<EcuOffer> getObject(@RequestBody EcuOfferBaseBo bo) {
        return Result.ok(ecuOfferModel.getObject(bo));
    }


    @Operation(summary = "获取丝型号")
    @PostMapping({"/getEcSilkPassEcqulId"})
    public Result<EcSilk> getEcSilkPassEcqulId(@RequestBody EcuSilkBo bo) {
        return Result.ok(ecuOfferModel.getEcSilkPassEcqulId(bo));
    }

    @Operation(summary = "开启禁用")
    @PostMapping({"/start"})
    public Result<?> start(@RequestBody List<EcuOfferStartBo> bos) {
        ecuOfferModel.start(bos);
        return Result.ok();
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcuOfferInsertBo bo) {
        return Result.ok(ecuOfferModel.saveOrUpdate(bo));
    }

    @Operation(summary = "批量编辑提交")
    @PostMapping({"/batch/saveOrUpdate"})
    public Result<String> batchSaveOrUpdate(@RequestBody EcuOfferBatchBo ecuOfferBatchBo) {
        StringBuilder msg = new StringBuilder();
        String ecuoId = ecuOfferBatchBo.getEcuoId();
        String[] split = ecuoId.split(",");
        for (String s : split) {
            try {
                EcuOfferInsertBo bo = new EcuOfferInsertBo();
                BeanUtils.copyProperties(ecuOfferBatchBo, bo);
                bo.setEcuoId(Integer.valueOf(s));
                ecuOfferModel.saveOrUpdate(bo);
                msg = new StringBuilder("批量修改成功");
            } catch (Exception e) {
                msg.append(";").append("序号 ").append(s).append("  ").append("修改失败");
            }
        }
        return Result.ok(msg.toString());
    }

    @Operation(summary = "排序")
    @PostMapping({"/sort"})
    public Result<String> sort(@RequestBody List<EcuOfferSortBo> bos) {
        ecuOfferModel.sort(bos);
        return Result.ok();
    }

    @Operation(summary = "删除")
    @PostMapping({"/delete"})
    public Result<?> delete(@Validated @RequestBody EcuOfferBaseBo bo) {
        ecuOfferModel.delete(bo);
        return Result.ok();
    }


    @Operation(summary = "导入")
    @PostMapping({"/importData"})
    public Result<?> importData(MultipartFile file, Integer ecqulId) throws Exception {
        ecuOfferModel.importDeal(file, ecqulId);
        return Result.ok();
    }

    @Operation(summary = "导出")
    @GetMapping({"/exportData"})
    public void exportData(HttpServletResponse response, Integer ecqulId) throws Exception {
        ecuOfferModel.exportData(response, ecqulId);
    }


    @Operation(summary = "方案筛选")
    @PostMapping({"/getAddPercentList"})
    public Result<List<String>> getAddPercentList(@RequestBody ProgrammeBo bo) {
        return Result.ok(ecuOfferModel.getAddPercentList(bo));
    }

    @Operation(summary = "成本执行加点")
    @PostMapping({"/dealAddPercentProgramme"})
    public Result<List<Integer>> dealAddPercentProgramme(@Validated @RequestBody ProgrammeBo bo) {
        return Result.ok(ecuOfferModel.dealAddPercentProgramme(bo));
    }


    @Operation(summary = "获取编辑结构中的重量和金额")
    @PostMapping({"/getStructureData"})
    public Result<ProgrammeVo> getStructureData(@Validated @RequestBody EcuOfferStructBo bo) {
        return Result.ok(ecuOfferModel.getStructureData(bo));
    }
}
