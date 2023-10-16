package org.jeecg.modules.cable.controller.userCommon;

import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcbuStoreModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class EcbuStoreController {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcbuStoreModel ecbuStoreModel;

    //getList
    @PostMapping({"/ecableErpPc/ecbuStore/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuStoreModel.getListAndCount(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecbuStore/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuStoreModel.getObject(request);
        }
        return map;
    }

    //deal
    @PostMapping({"/ecableErpPc/ecbuStore/deal"})
    public Map<String, Object> deal(HttpServletRequest request) throws IOException {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuStoreModel.deal(request);
        }
        return map;
    }

    //sort
    @PostMapping({"/ecableErpPc/ecbuStore/sort"})
    public Map<String, Object> sort(HttpServletRequest request) throws IOException {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuStoreModel.sort(request);
        }
        return map;
    }

    //delete
    @PostMapping({"/ecableErpPc/ecbuStore/delete"})
    public Map<String, Object> delete(HttpServletRequest request) throws IOException {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuStoreModel.delete(request);
        }
        return map;
    }

    //dealDefault 设置默认项
    @PostMapping({"/ecableErpPc/ecbuStore/dealDefault"})
    public Map<String, Object> defaultType(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuStoreModel.dealDefault(request);
        }
        return map;
    }

    //start
    @PostMapping({"/ecableErpPc/ecbuStore/start"})
    public Map<String, Object> start(HttpServletRequest request) throws IOException {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbuStoreModel.start(request);
        }
        return map;
    }
}
