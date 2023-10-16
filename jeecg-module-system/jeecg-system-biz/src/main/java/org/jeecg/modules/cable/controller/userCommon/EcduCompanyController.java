package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcduCompanyModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "公司信息")
@RestController
public class EcduCompanyController {
    @Resource
    EcduCompanyModel ecduCompanyModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取公司列表")
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


    @Operation(summary = "获取公司")
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


    @Operation(summary = "获取默认公司")
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


    @Operation(summary = "编辑公司")
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


    @Operation(summary = "公司排序")
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


    @Operation(summary = "公司删除")
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


    @Operation(summary = "公司开启禁用")
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


    @Operation(summary = "设置默认公司")
    //dealDefault 更改为默认
    @PostMapping({"/ecableErpPc/ecduCompany/dealDefault"})
    public Map<String, Object> dealDefault(HttpServletRequest request) {
        return ecduCompanyModel.dealDefault(request);
    }
}
