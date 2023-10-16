package org.jeecg.modules.cable.controller.price;

import org.jeecg.modules.cable.model.price.EcuQuotedModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcuQuotedController {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcuQuotedModel ecuQuotedModel;

    //getList
    @PostMapping({"/ecableErpPc/ecuQuoted/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuQuotedModel.getListAndCount(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecuQuoted/getObject"})
    public Map<String, Object> getObjectPassId(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuQuotedModel.getObject(request);
        }
        return map;
    }

    //getLatestObject
    @PostMapping({"/ecableErpPc/ecuQuoted/getLatestObject"})
    public Map<String, Object> getObjectLatestPassId(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuQuotedModel.getLatestObject(request);
        }
        return map;
    }

    //deal
    @PostMapping({"/ecableErpPc/ecuQuoted/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuQuotedModel.deal(request);
        }
        return map;
    }

    //dealMoneyPassInput
    @PostMapping({"/ecableErpPc/ecuQuoted/dealMoneyPassInput"})
    public Map<String, Object> dealMoneyPassInput(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuQuotedModel.dealMoneyPassInput(request);
        }
        return map;
    }

    //dealComplete 提交已成交
    @PostMapping({"/ecableErpPc/ecuQuoted/dealComplete"})
    public Map<String, Object> dealComplete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuQuotedModel.dealComplete(request);
        }
        return map;
    }

    //dealQuoted 提交
    @PostMapping({"/ecableErpPc/ecuQuoted/dealTotalDesc"})
    public Map<String, Object> dealTotalDesc(HttpServletRequest request) {
        return ecuQuotedModel.dealTotalDesc(request);
    }
}
