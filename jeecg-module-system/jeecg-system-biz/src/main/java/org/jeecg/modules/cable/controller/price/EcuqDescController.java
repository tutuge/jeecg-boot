package org.jeecg.modules.cable.controller.price;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.price.EcuqDescModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "结构信息")
@RestController
public class EcuqDescController {
    @Resource
    EcuqDescModel ecuqDescModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "编辑提交")
    //dealStructure
    @PostMapping({"/ecableErpPc/ecuqDesc/dealStructure"})
    public Map<String, Object> dealStructure(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqDescModel.dealStructure(request);
        }
        return map;
    }

    @Operation(summary = "修改金额")
    //dealMoney
    @PostMapping({"/ecableErpPc/ecuqDesc/dealMoney"})
    public Map<String, Object> dealMoney(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqDescModel.dealMoney(request);
        }
        return map;
    }

    @Operation(summary = "更改为手输或自动")
    //dealInputStart 更改为手输或是自动计算价格 false 是自动 true 是手输
    @PostMapping({"/ecableErpPc/ecuqDesc/dealInputStart"})
    public Map<String, Object> dealInputStart(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqDescModel.dealInputStart(request);
        }
        return map;
    }

    @Operation(summary = "修改为手动更改税前单价")
    //dealUnitPrice 修改为手动更改税前单价
    @PostMapping({"/ecableErpPc/ecuqDesc/dealUnitPrice"})
    public Map<String, Object> dealUnitPrice(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqDescModel.dealUnitPrice(request);
        }
        return map;
    }

    @Operation(summary = "修改木轴")
    //dealAxle 修改木轴
    @PostMapping({"/ecableErpPc/ecuqDesc/dealAxle"})
    public Map<String, Object> dealAxle(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecuqDescModel.dealAxle(request);
        }
        return map;
    }

    @Operation(summary = "将税前单价由手动改为自动")
    //dealUnitPriceInput 将税前单价由手动改为自动
    @PostMapping({"/ecableErpPc/ecuqDesc/dealUnitPriceInput"})
    public Map<String, Object> dealUnitPriceInput(HttpServletRequest request) {
        return ecuqDescModel.dealUnitPriceInput(request);
    }
}
