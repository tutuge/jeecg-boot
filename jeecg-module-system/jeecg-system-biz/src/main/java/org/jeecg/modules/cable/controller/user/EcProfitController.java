package org.jeecg.modules.cable.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcProfitModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "利润")
@RestController
public class EcProfitController {
    @Resource
    EcProfitModel ecProfitModel;

    @Operation(summary = "获取利润")
    @PostMapping({"/ecableErpPc/ecProfit/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecProfitModel.getObject(request);
    }

    @Operation(summary = "获取利润列表")
    @PostMapping({"/ecableErpPc/ecProfit/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecProfitModel.getList(request);
    }

    @Operation(summary = "编辑提交")
    @PostMapping({"/ecableErpPc/ecProfit/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecProfitModel.deal(request);
    }

    @Operation(summary = "启用禁用")
    @PostMapping({"/ecableErpPc/ecProfit/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecProfitModel.start(request);
    }

    @Operation(summary = "排序")
    @PostMapping({"/ecableErpPc/ecProfit/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return ecProfitModel.sort(request);
    }

    @Operation(summary = "删除")
    @PostMapping({"/ecableErpPc/ecProfit/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecProfitModel.delete(request);
    }
}
