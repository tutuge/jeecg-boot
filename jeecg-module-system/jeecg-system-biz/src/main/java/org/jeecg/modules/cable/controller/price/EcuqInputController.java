package org.jeecg.modules.cable.controller.price;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.price.EcuqInputModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "页面编辑")
@RestController
public class EcuqInputController {
    @Resource
    EcuqInputModel ecuqInputModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "页面显示列表编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecuqInput/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {

            map = ecuqInputModel.deal(request);
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecuqInput/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecuqInputModel.getObject(request);
        return map;
    }

    @Operation(summary = "获取报价单列数")
    //getListQuoted
    @PostMapping({"/ecableErpPc/ecuqInput/getListQuoted"})
    public Map<String, Object> getListQuoted(HttpServletRequest request) {

            map = ecuqInputModel.getListQuoted(request);
        return map;
    }


    @Operation(summary = "删除列表信息")
    //delete
    @PostMapping({"/ecableErpPc/ecuqInput/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {

            map = ecuqInputModel.delete(request);
        return map;
    }

    @Operation(summary = "根据ecuqild获取")
    //getStructurePassId
    @PostMapping({"/ecableErpPc/ecuqInput/getStructurePassId"})
    public Map<String, Object> getStructurePassId(HttpServletRequest request) {

            map = ecuqInputModel.getStructurePassId(request);
        return map;
    }

    @Operation(summary = "获取编辑结构临时数据")
    //getStructureTemporary
    @PostMapping({"/ecableErpPc/ecuqInput/getStructureTemporary"})
    public Map<String, Object> getStructureTemporary(HttpServletRequest request) {

            map = ecuqInputModel.getStructureTemporary(request);
        return map;
    }

    @Operation(summary = "批量修改实际税率")
    //dealBatchBillPercent 批量修改实际税率
    @PostMapping({"/ecableErpPc/ecuqInput/dealBatchBillPercent"})
    public Map<String, Object> dealBatchBillPercent(HttpServletRequest request) {

            map = ecuqInputModel.dealBatchBillPercent(request);
        return map;
    }

    @Operation(summary = "排序")
    //dealSort 排序
    @PostMapping({"/ecableErpPc/ecuqInput/dealSort"})
    public Map<String, Object> dealSort(HttpServletRequest request) {

            map = ecuqInputModel.dealSort(request);
        return map;
    }

    @Operation(summary = "备注添加")
    //dealItemDesc
    @PostMapping({"/ecableErpPc/ecuqInput/dealItemDesc"})
    public Map<String, Object> dealItemDesc(HttpServletRequest request) {
        return ecuqInputModel.dealItemDesc(request);
    }


    @Operation(summary = "利润手输更改")
    //dealInput
    @PostMapping({"/ecableErpPc/ecuqInput/dealProfitInput"})
    public Map<String, Object> dealProfitInput(HttpServletRequest request) {
        return ecuqInputModel.dealProfitInput(request);
    }

    @Operation(summary = "根据型号获取质量等级")
    //getObjectPassSilkName 根据型号获取默认质量等级
    @PostMapping({"/ecableErpPc/ecuqInput/getObjectPassSilkName"})
    public Map<String, Object> getObjectPassSilkName(HttpServletRequest request) {
        return ecuqInputModel.getObjectPassSilkName(request);
    }

    @Operation(summary = "导入报价单")
    //importData 导入报价单
    @PostMapping({"/ecableErpPc/ecuqInput/importData"})
    public Map<String, Object> importData(HttpServletRequest request) {
        return ecuqInputModel.importData(request);
    }

    @Operation(summary = "修改丝名称别名")
    //dealSilkNameAs 修改丝名称别名
    @PostMapping({"/ecableErpPc/ecuqInput/dealSilkNameAs"})
    public Map<String, Object> dealSilkNameAs(HttpServletRequest request) {
        return ecuqInputModel.dealSilkNameAs(request);
    }

    @Operation(summary = "修改丝名称别名")
    //dealAreaStrAs 修改丝名称别名
    @PostMapping({"/ecableErpPc/ecuqInput/dealAreaStrAs"})
    public Map<String, Object> dealAreaStrAs(HttpServletRequest request) {
        return ecuqInputModel.dealAreaStrAs(request);
    }

    @Operation(summary = "修改丝名称是否手输")
    //dealSilkNameInput 修改丝名称是否手输
    @PostMapping({"/ecableErpPc/ecuqInput/dealSilkNameInput"})
    public Map<String, Object> dealSilkNameInput(HttpServletRequest request) {
        return ecuqInputModel.dealSilkNameInput(request);
    }

    @Operation(summary = "修改截面是否手输")
    //dealAreaStrInput 修改截面是否手输
    @PostMapping({"/ecableErpPc/ecuqInput/dealAreaStrInput"})
    public Map<String, Object> dealAreaStrInput(HttpServletRequest request) {
        return ecuqInputModel.dealAreaStrInput(request);
    }

}
