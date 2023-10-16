package org.jeecg.modules.cable.controller.userDelivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userDelivery.EcbudMoneyModel;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "快递")
@RestController
public class EcbudMoneyController {
    @Resource
    EcbudMoneyModel ecbudMoneyModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "快递信息默认加载")
    //load 加载默认省信息
    @PostMapping({"/ecableErpPc/ecbudMoney/load"})
    public Map<String, Object> load(HttpServletRequest request) {
        Map<String, Object> map;
        int status;
        String code;
        String msg;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            ecbudMoneyModel.load(request);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常加载数据";
            CommonFunction.getCommonMap(map, status, code, msg);
        }
        return map;
    }


    @Operation(summary = "快递信息列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbudMoney/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudMoneyModel.getListAndCount(request);
        }
        return map;
    }


    @Operation(summary = "快递信息详情")
    //getObject
    @PostMapping({"/ecableErpPc/ecbudMoney/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudMoneyModel.getObject(request);
        }
        return map;
    }


    @Operation(summary = "快递信息编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecbudMoney/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudMoneyModel.deal(request);
        }
        return map;
    }


    @Operation(summary = "快递信息排序")
    //sort
    @PostMapping({"/ecableErpPc/ecbudMoney/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudMoneyModel.sort(request);
        }
        return map;
    }


    @Operation(summary = "快递信息删除")
    //delete
    @PostMapping({"/ecableErpPc/ecbudMoney/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudMoneyModel.delete(request);
        }
        return map;
    }


    @Operation(summary = "快递信息开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbudMoney/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbudMoneyModel.start(request);
        }
        return map;
    }
}
