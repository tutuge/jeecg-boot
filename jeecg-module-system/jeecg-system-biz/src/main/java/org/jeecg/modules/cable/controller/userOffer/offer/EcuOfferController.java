package org.jeecg.modules.cable.controller.userOffer.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userOffer.offer.bo.*;
import org.jeecg.modules.cable.controller.userOffer.offer.vo.OfferVo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeBo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
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
    // getList
    @PostMapping({"/getList"})
    public Result<OfferVo> getList(@RequestBody OfferListBo bo) {
        return Result.ok(ecuOfferModel.getListAndCount(bo));
    }

    @Operation(summary = "获取单行数据")
    // getObject
    @PostMapping({"/getObject"})
    public Result<EcuOffer> getObject(@RequestBody OfferBo bo) {
        return Result.ok(ecuOfferModel.getObject(bo));
    }


    @Operation(summary = "获取丝型号")
    // getEcSilkPassEcqulId
    @PostMapping({"/getEcSilkPassEcqulId"})
    public Result<EcSilk> getEcSilkPassEcqulId(@RequestBody SilkBo bo) {
        return Result.ok(ecuOfferModel.getEcSilkPassEcqulId(bo));
    }

    @Operation(summary = "开启禁用")
    // start
    @PostMapping({"/start"})
    public Result<?> start(@RequestBody OfferStartBo bo) {
        return Result.ok(ecuOfferModel.start(bo));
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody OfferInsertBo bo) {
        return Result.ok(ecuOfferModel.deal(bo));
    }

    @Operation(summary = "排序")
    // sort
    @PostMapping({"/sort"})
    public Result<String> sort(@RequestBody OfferBo bo) {
        ecuOfferModel.sort(bo);
        return Result.ok();
    }

    @Operation(summary = "删除")
    // delete
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody OfferBo bo) {
        ecuOfferModel.delete(bo);
        return Result.ok();
    }


    // importData
    @Operation(summary = "导入")
    @PostMapping({"/importData"})
    public Result<?> importData(MultipartFile file, Integer ecqulId) throws Exception {
        ecuOfferModel.importDeal(file, ecqulId);
        return Result.ok();
    }

    @Operation(summary = "导出")
    // exportData
    @GetMapping({"/exportData"})
    public void exportData(HttpServletResponse response, Integer ecqulId) throws Exception {
        ecuOfferModel.exportData(response, ecqulId);
    }


    @Operation(summary = "方案筛选")
    // getAddPercentList 获取筛选的数组
    @PostMapping({"/getAddPercentList"})
    public Result<List<String>> getAddPercentList(@RequestBody ProgrammeBo bo) {
        return Result.ok(ecuOfferModel.getAddPercentList(bo));
    }

    @Operation(summary = "成本执行加点")
    // getAddPercentList 获取筛选的数组
    @PostMapping({"/dealAddPercentProgramme"})
    public Result<List<String>> dealAddPercentProgramme(@RequestBody ProgrammeBo bo) {
        return Result.ok(ecuOfferModel.dealAddPercentProgramme(bo));
    }


    @Operation(summary = "获取编辑结构中的重量和金额")
    // getStructureData 获取编辑结构中的重量和金额
    @PostMapping({"/getStructureData"})
    public Result<ProgrammeVo> getStructureData(@RequestBody OfferStructBo bo) {
        return Result.ok(ecuOfferModel.getStructureData(bo));
    }
}
