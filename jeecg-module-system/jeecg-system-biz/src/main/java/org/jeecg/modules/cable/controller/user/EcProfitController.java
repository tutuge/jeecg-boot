package org.jeecg.modules.cable.controller.user;

import org.jeecg.modules.cable.model.user.EcProfitModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcProfitController {
    @Resource
    EcProfitModel ecProfitModel;

    @PostMapping({"/ecableErpPc/ecProfit/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecProfitModel.getObject(request);
    }

    @PostMapping({"/ecableErpPc/ecProfit/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecProfitModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/ecProfit/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecProfitModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/ecProfit/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecProfitModel.start(request);
    }

    @PostMapping({"/ecableErpPc/ecProfit/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return ecProfitModel.sort(request);
    }

    @PostMapping({"/ecableErpPc/ecProfit/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecProfitModel.delete(request);
    }
}
