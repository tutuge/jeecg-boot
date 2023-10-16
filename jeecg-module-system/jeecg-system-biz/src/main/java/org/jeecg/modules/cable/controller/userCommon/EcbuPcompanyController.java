package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcbuPcompanyModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "平台公司")
@RestController
public class EcbuPcompanyController {
    @Resource
    EcbuPcompanyModel ecbuPcompanyModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取平台公司列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuPcompany/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuPcompanyModel.getListAndCount(request);
        }
        return map;
    }


    @Operation(summary = "获取平台公司")
    //getObject
    @PostMapping({"/ecableErpPc/ecbuPcompany/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuPcompanyModel.getObject(request);
        }
        return map;
    }


    @Operation(summary = "编辑平台公司")
    //deal
    @PostMapping({"/ecableErpPc/ecbuPcompany/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuPcompanyModel.deal(request);
        }
        return map;
    }


    @Operation(summary = "平台公司排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbuPcompany/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuPcompanyModel.sort(request);
        }
        return map;
    }


    @Operation(summary = "平台公司删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbuPcompany/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuPcompanyModel.delete(request);
        }
        return map;
    }


    @Operation(summary = "平台公司开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuPcompany/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuPcompanyModel.start(request);
        }
        return map;
    }
}
