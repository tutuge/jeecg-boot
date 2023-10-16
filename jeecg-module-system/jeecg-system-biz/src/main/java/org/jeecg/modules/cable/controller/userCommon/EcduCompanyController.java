package org.jeecg.modules.cable.controller.userCommon;

import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcduCompanyModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcduCompanyController {
    @Resource
    EcduCompanyModel ecduCompanyModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //getList
    @PostMapping({"/ecableErpPc/ecduCompany/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduCompanyModel.getListAndCount(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecduCompany/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduCompanyModel.getObject(request);
        }
        return map;
    }

    //getObjectDefault
    @PostMapping({"/ecableErpPc/ecduCompany/getObjectDefault"})
    public Map<String, Object> getObjectDefault(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduCompanyModel.getObjectDefault(request);
        }
        return map;
    }

    //deal
    @PostMapping({"/ecableErpPc/ecduCompany/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduCompanyModel.deal(request);
        }
        return map;
    }

    //sort
    @PostMapping({"/ecableErpPc/ecduCompany/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduCompanyModel.sort(request);
        }
        return map;
    }

    //delete
    @PostMapping({"/ecableErpPc/ecduCompany/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecduCompanyModel.delete(request);
        }
        return map;
    }

    //start
    @PostMapping({"/ecableErpPc/ecduCompany/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            ecduCompanyModel.start(request);
        }
        return map;
    }

    //dealDefault 更改为默认
    @PostMapping({"/ecableErpPc/ecduCompany/dealDefault"})
    public Map<String, Object> dealDefault(HttpServletRequest request) {
        return ecduCompanyModel.dealDefault(request);
    }
}
