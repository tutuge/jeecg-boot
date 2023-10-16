package org.jeecg.modules.cable.controller.price;

import org.jeecg.modules.cable.model.price.EcuqInputModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcuqInputController {
    @Resource
    EcuqInputModel ecuqInputModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //deal
    @PostMapping({"/ecableErpPc/ecuqInput/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqInputModel.deal(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecuqInput/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqInputModel.getObject(request);
        }
        return map;
    }

    //getListQuoted
    @PostMapping({"/ecableErpPc/ecuqInput/getListQuoted"})
    public Map<String, Object> getListQuoted(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqInputModel.getListQuoted(request);
        }
        return map;
    }

    //delete
    @PostMapping({"/ecableErpPc/ecuqInput/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqInputModel.delete(request);
        }
        return map;
    }

    //getStructurePassId
    @PostMapping({"/ecableErpPc/ecuqInput/getStructurePassId"})
    public Map<String, Object> getStructurePassId(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqInputModel.getStructurePassId(request);
        }
        return map;
    }

    //getStructureTemporary
    @PostMapping({"/ecableErpPc/ecuqInput/getStructureTemporary"})
    public Map<String, Object> getStructureTemporary(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqInputModel.getStructureTemporary(request);
        }
        return map;
    }

    //dealBatchBillPercent 批量修改实际税率
    @PostMapping({"/ecableErpPc/ecuqInput/dealBatchBillPercent"})
    public Map<String, Object> dealBatchBillPercent(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqInputModel.dealBatchBillPercent(request);
        }
        return map;
    }

    //dealSort 排序
    @PostMapping({"/ecableErpPc/ecuqInput/dealSort"})
    public Map<String, Object> dealSort(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqInputModel.dealSort(request);
        }
        return map;
    }

    //dealItemDesc
    @PostMapping({"/ecableErpPc/ecuqInput/dealItemDesc"})
    public Map<String, Object> dealItemDesc(HttpServletRequest request) {
        return ecuqInputModel.dealItemDesc(request);
    }

    //dealInput
    @PostMapping({"/ecableErpPc/ecuqInput/dealProfitInput"})
    public Map<String, Object> dealProfitInput(HttpServletRequest request) {
        return ecuqInputModel.dealProfitInput(request);
    }

    //getObjectPassSilkName 根据型号获取默认质量等级
    @PostMapping({"/ecableErpPc/ecuqInput/getObjectPassSilkName"})
    public Map<String, Object> getObjectPassSilkName(HttpServletRequest request) {
        return ecuqInputModel.getObjectPassSilkName(request);
    }

    //importData 导入报价单
    @PostMapping({"/ecableErpPc/ecuqInput/importData"})
    public Map<String, Object> importData(HttpServletRequest request) {
        return ecuqInputModel.importData(request);
    }

    //dealSilkNameAs 修改丝名称别名
    @PostMapping({"/ecableErpPc/ecuqInput/dealSilkNameAs"})
    public Map<String, Object> dealSilkNameAs(HttpServletRequest request) {
        return ecuqInputModel.dealSilkNameAs(request);
    }

    //dealAreaStrAs 修改丝名称别名
    @PostMapping({"/ecableErpPc/ecuqInput/dealAreaStrAs"})
    public Map<String, Object> dealAreaStrAs(HttpServletRequest request) {
        return ecuqInputModel.dealAreaStrAs(request);
    }
    //dealSilkNameInput 修改丝名称是否手输
    @PostMapping({"/ecableErpPc/ecuqInput/dealSilkNameInput"})
    public Map<String, Object> dealSilkNameInput(HttpServletRequest request) {
        return ecuqInputModel.dealSilkNameInput(request);
    }
    //dealAreaStrInput 修改截面是否手输
    @PostMapping({"/ecableErpPc/ecuqInput/dealAreaStrInput"})
    public Map<String, Object> dealAreaStrInput(HttpServletRequest request) {
        return ecuqInputModel.dealAreaStrInput(request);
    }

}
