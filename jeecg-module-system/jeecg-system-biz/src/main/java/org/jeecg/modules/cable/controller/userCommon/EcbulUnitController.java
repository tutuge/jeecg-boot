package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcbulUnitModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "单位长度")
@RestController
public class EcbulUnitController {
    @Resource
    EcbulUnitModel ecbulUnitModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取单位长度列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbulUnit/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbulUnitModel.getListAndCount(request);
        }
        return map;
    }

    @Operation(summary = "获取单位长度")
    //getObject
    @PostMapping({"/ecableErpPc/ecbulUnit/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbulUnitModel.getObject(request);
        }
        return map;
    }


    @Operation(summary = "编辑单位长度")
    //deal
    @PostMapping({"/ecableErpPc/ecbulUnit/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbulUnitModel.deal(request);
        }
        return map;
    }


    @Operation(summary = "单位长度排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbulUnit/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbulUnitModel.sort(request);
        }
        return map;
    }


    @Operation(summary = "删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbulUnit/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbulUnitModel.delete(request);
        }
        return map;
    }


    @Operation(summary = "开启或禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbulUnit/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbulUnitModel.start(request);
        }
        return map;
    }
}
