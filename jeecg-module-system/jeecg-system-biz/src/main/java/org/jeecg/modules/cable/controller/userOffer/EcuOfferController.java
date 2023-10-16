package org.jeecg.modules.cable.controller.userOffer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "方案")
@RestController
public class EcuOfferController {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuOfferModel ecuOfferModel;

    @Operation(summary = "获取电缆成本库表等级参数")
    //getList
    @PostMapping({"/ecableErpPc/ecuOffer/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecuOfferModel.getListAndCount(request);
        return map;
    }

    @Operation(summary = "获取单行数据")
    //getObject
    @PostMapping({"/ecableErpPc/ecuOffer/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuOfferModel.getObject(request);
    }


    @Operation(summary = "获取丝型号")
    //getEcSilkPassEcqulId
    @PostMapping({"/ecableErpPc/ecuOffer/getEcSilkPassEcqulId"})
    public Map<String, Object> getEcSilkPassEcqulId(HttpServletRequest request) {
        return ecuOfferModel.getEcSilkPassEcqulId(request);
    }

    @Operation(summary = "开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecuOffer/start"})
    public Map<String, Object> start(HttpServletRequest request) {

            map = ecuOfferModel.start(request);
        return map;
    }

    @Operation(summary = "编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecuOffer/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {

            map = ecuOfferModel.deal(request);
        return map;
    }

    @Operation(summary = "排序")
    //sort
    @PostMapping({"/ecableErpPc/ecuOffer/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {

            map = ecuOfferModel.sort(request);
        return map;
    }

    @Operation(summary = "删除")
    //delete
    @PostMapping({"/ecableErpPc/ecuOffer/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {

            map = ecuOfferModel.delete(request);
        return map;
    }

    @Operation(summary = "导入")
    //importData
    @PostMapping({"/ecableErpPc/ecuOffer/importData"})
    public Map<String, Object> importData(HttpServletRequest request) throws Exception {

            map = ecuOfferModel.importDeal(request);
        return map;
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
    public Map<String, Object> getAddPercentList(HttpServletRequest request) {
        return ecuOfferModel.getAddPercentList(request);
    }

    @Operation(summary = "成本执行加点")
    //getAddPercentList 获取筛选的数组
    @PostMapping({"/ecableErpPc/ecuOffer/dealAddPercentProgramme"})
    public Map<String, Object> dealAddPercentProgramme(HttpServletRequest request) {
        return ecuOfferModel.dealAddPercentProgramme(request);
    }


    @Operation(summary = "重量金额显示")
    //getStructureData 获取编辑结构中的重量和金额
    @PostMapping({"/ecableErpPc/ecuOffer/getStructureData"})
    public Map<String, Object> getStructureData(HttpServletRequest request) {
        return ecuOfferModel.getStructureData(request);
    }
}
