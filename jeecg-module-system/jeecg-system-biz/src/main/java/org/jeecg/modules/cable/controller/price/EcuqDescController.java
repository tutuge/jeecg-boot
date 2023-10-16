package org.jeecg.modules.cable.controller.price;

import org.jeecg.modules.cable.model.price.EcuqDescModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcuqDescController {
    @Resource
    EcuqDescModel ecuqDescModel;
    @Resource
    EcuLoginModel ecuLoginModel;

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

    //dealUnitPriceInput 将税前单价由手动改为自动
    @PostMapping({"/ecableErpPc/ecuqDesc/dealUnitPriceInput"})
    public Map<String, Object> dealUnitPriceInput(HttpServletRequest request) {
        return ecuqDescModel.dealUnitPriceInput(request);
    }
}
