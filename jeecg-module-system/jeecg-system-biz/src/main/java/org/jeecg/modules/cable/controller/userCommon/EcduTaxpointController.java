package org.jeecg.modules.cable.controller.userCommon;

import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcduTaxpointModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcduTaxpointController {
    @Resource
    EcduTaxpointModel ecduTaxpointModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //getList
    @PostMapping({"/ecableErpPc/ecduTaxpoint/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduTaxpointModel.getListAndCount(request);
        }
        return map;
    }

    //deal
    @PostMapping({"/ecableErpPc/ecduTaxpoint/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduTaxpointModel.deal(request);
        }
        return map;
    }

    //start
    @PostMapping({"/ecableErpPc/ecduTaxpoint/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduTaxpointModel.start(request);
        }
        return map;
    }

    //delete
    @PostMapping({"/ecableErpPc/ecduTaxpoint/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduTaxpointModel.delete(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecduTaxpoint/getObject"})
    public Map<String, Object> getObjectPassSortId(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduTaxpointModel.getObject(request);
        }
        return map;
    }

    //dealPercent
    @PostMapping({"/ecableErpPc/ecduTaxpoint/dealPercent"})
    public Map<String, Object> dealPercent(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduTaxpointModel.dealPercent(request);
        }
        return map;
    }

}
