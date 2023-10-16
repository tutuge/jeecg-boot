package org.jeecg.modules.cable.controller.userDelivery;

import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudPriceModel;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcbudPriceController {
    @Resource
    EcbudPriceModel ecbudPriceModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    //load 加载默认省信息
    @PostMapping({"/ecableErpPc/ecbudPrice/load"})
    public Map<String, Object> load(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            ecbudPriceModel.load(request);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常加载数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }

    //getList
    @PostMapping({"/ecableErpPc/ecbudPrice/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudPriceModel.getListAndCount(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecbudPrice/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudPriceModel.getObject(request);
        }
        return map;
    }

    //deal
    @PostMapping({"/ecableErpPc/ecbudPrice/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudPriceModel.deal(request);
        }
        return map;
    }

    //sort
    @PostMapping({"/ecableErpPc/ecbudPrice/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudPriceModel.sort(request);
        }
        return map;
    }

    //delete
    @PostMapping({"/ecableErpPc/ecbudPrice/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudPriceModel.delete(request);
        }
        return map;
    }

    //start
    @PostMapping({"/ecableErpPc/ecbudPrice/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudPriceModel.start(request);
        }
        return map;
    }
}
