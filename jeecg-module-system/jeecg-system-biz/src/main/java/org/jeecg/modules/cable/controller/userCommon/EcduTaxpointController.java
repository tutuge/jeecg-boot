package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcduTaxpointModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "税点")
@RestController
public class EcduTaxpointController {
    @Resource
    EcduTaxpointModel ecduTaxpointModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "税点列表")
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

    @Operation(summary = "税点编辑")
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


    @Operation(summary = "税点开启")
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


    @Operation(summary = "税点删除")
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


    @Operation(summary = "税点详情")
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


    @Operation(summary = "税点编辑")
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
