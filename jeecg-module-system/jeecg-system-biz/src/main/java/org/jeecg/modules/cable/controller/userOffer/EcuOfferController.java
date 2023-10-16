package org.jeecg.modules.cable.controller.userOffer;

import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userOffer.EcuOfferModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcuOfferController {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuOfferModel ecuOfferModel;

    //getList
    @PostMapping({"/ecableErpPc/ecuOffer/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuOfferModel.getListAndCount(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecuOffer/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuOfferModel.getObject(request);
    }

    //getEcSilkPassEcqulId
    @PostMapping({"/ecableErpPc/ecuOffer/getEcSilkPassEcqulId"})
    public Map<String, Object> getEcSilkPassEcqulId(HttpServletRequest request) {
        return ecuOfferModel.getEcSilkPassEcqulId(request);
    }

    //start
    @PostMapping({"/ecableErpPc/ecuOffer/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuOfferModel.start(request);
        }
        return map;
    }

    //deal
    @PostMapping({"/ecableErpPc/ecuOffer/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuOfferModel.deal(request);
        }
        return map;
    }

    //sort
    @PostMapping({"/ecableErpPc/ecuOffer/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuOfferModel.sort(request);
        }
        return map;
    }

    //delete
    @PostMapping({"/ecableErpPc/ecuOffer/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuOfferModel.delete(request);
        }
        return map;
    }

    //importData
    @PostMapping({"/ecableErpPc/ecuOffer/importData"})
    public Map<String, Object> importData(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuOfferModel.importDeal(request);
        }
        return map;
    }

    //exportData
    @GetMapping({"/ecableErpPc/ecuOffer/exportData"})
    public void exportData(HttpServletResponse response, HttpServletRequest request) throws Exception {
        ecuOfferModel.exportData(response, request);
    }

    //getAddPercentList 获取筛选的数组
    @PostMapping({"/ecableErpPc/ecuOffer/getAddPercentList"})
    public Map<String, Object> getAddPercentList(HttpServletRequest request) {
        return ecuOfferModel.getAddPercentList(request);
    }

    //getAddPercentList 获取筛选的数组
    @PostMapping({"/ecableErpPc/ecuOffer/dealAddPercentProgramme"})
    public Map<String, Object> dealAddPercentProgramme(HttpServletRequest request) {
        return ecuOfferModel.dealAddPercentProgramme(request);
    }

    //getStructureData 获取编辑结构中的重量和金额
    @PostMapping({"/ecableErpPc/ecuOffer/getStructureData"})
    public Map<String, Object> getStructureData(HttpServletRequest request) {
        return ecuOfferModel.getStructureData(request);
    }
}
