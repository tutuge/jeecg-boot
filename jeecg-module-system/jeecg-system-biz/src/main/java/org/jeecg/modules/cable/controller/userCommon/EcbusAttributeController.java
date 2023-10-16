package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.model.userCommon.EcbusAttributeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "仓库信息")
@RestController
@Slf4j
public class EcbusAttributeController {
    @Resource
    EcbusAttributeModel ecbusAttributeModel;

    @Operation(summary = "控制仓库信息显示与隐藏")
    @PostMapping({"/ecableErpPc/ecbusAttribute/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecbusAttributeModel.deal(request);
    }


    @Operation(summary = "仓库信息")
    @PostMapping({"/ecableErpPc/ecbusAttribute/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecbusAttributeModel.getObject(request);
    }
}
