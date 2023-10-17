package org.jeecg.modules.cable.controller.userOffer.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userOffer.offer.bo.OfferBo;
import org.jeecg.modules.cable.controller.userOffer.offer.bo.OfferInsertBo;
import org.jeecg.modules.cable.controller.userOffer.offer.bo.OfferStartBo;
import org.jeecg.modules.cable.controller.userOffer.offer.bo.SilkBo;
import org.jeecg.modules.cable.controller.userOffer.offer.vo.OfferVo;
import org.jeecg.modules.cable.controller.userOffer.programme.bo.ProgrammeBo;
import org.jeecg.modules.cable.controller.userOffer.programme.vo.ProgrammeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "方案")
@RestController
public class EcuOfferController {
    @Resource
    EcuOfferModel ecuOfferModel;

    @Operation(summary = "获取电缆成本库表等级参数")
    //getList
    @PostMapping({"/ecableErpPc/ecuOffer/getList"})
    public Result<OfferVo> getList(@RequestBody OfferBo bo) {
        return Result.ok(ecuOfferModel.getListAndCount(bo));
    }

    @Operation(summary = "获取单行数据")
    //getObject
    @PostMapping({"/ecableErpPc/ecuOffer/getObject"})
    public Result<EcuOffer> getObject(@RequestBody OfferBo bo) {
        return Result.ok(ecuOfferModel.getObject(bo));
    }


    @Operation(summary = "获取丝型号")
    //getEcSilkPassEcqulId
    @PostMapping({"/ecableErpPc/ecuOffer/getEcSilkPassEcqulId"})
    public Result<EcSilk> getEcSilkPassEcqulId(@RequestBody SilkBo bo) {
        return Result.ok(ecuOfferModel.getEcSilkPassEcqulId(bo));
    }

    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecuOffer/start"})
    public Result<?> start(@RequestBody OfferStartBo bo) {
        return Result.ok(ecuOfferModel.start(bo));
    }

    @Operation(summary = "编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecuOffer/deal"})
    public Result<String> deal(@RequestBody OfferInsertBo bo) {
        return Result.ok(ecuOfferModel.deal(bo));
    }

    @Operation(summary = "排序")
    //sort
    @PostMapping({"/ecableErpPc/ecuOffer/sort"})
    public Result<String> sort(@RequestBody OfferBo bo) {
        ecuOfferModel.sort(bo);
        return Result.ok();
    }

    @Operation(summary = "删除")
    //delete
    @PostMapping({"/ecableErpPc/ecuOffer/delete"})
    public Result<?> delete(@RequestBody OfferBo bo) {
        ecuOfferModel.delete(bo);
        return Result.ok();
    }

    @Operation(summary = "导入")
    //importData
    @PostMapping({"/ecableErpPc/ecuOffer/importData"})
    public Result<?> importData(HttpServletRequest request) throws Exception {
        ecuOfferModel.importDeal(request);
        return Result.ok();
    }

    @Operation(summary = "导出")
    //exportData
    @GetMapping({"/ecableErpPc/ecuOffer/exportData"})
    public void exportData(HttpServletResponse response, HttpServletRequest request) throws Exception {
        ecuOfferModel.exportData(response, request);
    }


    @Operation(summary = "方案筛选")
    //getAddPercentList 获取筛选的数组
    @PostMapping({"/ecableErpPc/ecuOffer/getAddPercentList"})
    public Result<List<String>> getAddPercentList(@RequestBody ProgrammeBo bo) {
        return Result.ok(ecuOfferModel.getAddPercentList(bo));
    }

    @Operation(summary = "成本执行加点")
    //getAddPercentList 获取筛选的数组
    @PostMapping({"/ecableErpPc/ecuOffer/dealAddPercentProgramme"})
    public Result<List<String>> dealAddPercentProgramme(@RequestBody ProgrammeBo bo) {
        return Result.ok(ecuOfferModel.dealAddPercentProgramme(bo));
    }


    @Operation(summary = "获取编辑结构中的重量和金额")
    //getStructureData 获取编辑结构中的重量和金额
    @PostMapping({"/ecableErpPc/ecuOffer/getStructureData"})
    public Result<ProgrammeVo> getStructureData(HttpServletRequest request) {
        return Result.ok(ecuOfferModel.getStructureData(request));
    }
}
