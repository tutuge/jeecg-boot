package org.jeecg.modules.cable.controller.quality;

import org.jeecg.modules.cable.model.quality.EcuAreaModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class EcuAreaController {
    @Resource
    EcuAreaModel ecuAreaModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //getList
    @PostMapping({"/ecableErpPc/ecuArea/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuAreaModel.getListAndCount(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecuArea/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuAreaModel.getObject(request);
        }
        return map;
    }

    //deal
    @PostMapping({"/ecableErpPc/ecuArea/deal"})
    public Map<String, Object> deal(HttpServletRequest request) throws IOException {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            ecuAreaModel.deal(request);
        }
        return map;
    }

    //sort
    @PostMapping({"/ecableErpPc/ecuArea/sort"})
    public Map<String, Object> sort(HttpServletRequest request) throws IOException {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuAreaModel.sort(request);
        }
        return map;
    }

    //start
    @PostMapping({"/ecableErpPc/ecuArea/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuAreaModel.start(request);
        }
        return map;
    }
}
