package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.systemEcable.EcbInsulationModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "绝缘")
@RestController
public class EcbInsulationController {
    @Resource
    EcbInsulationModel ecbInsulationModel;
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息

    @Operation(summary = "获取绝缘列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbInsulation/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbInsulationModel.getListAndCount(request);
        return map;
    }

    @Operation(summary = "根据id获取")
    //根据EcbInsulation获取EcbInsulation
    @PostMapping({"/ecableErpPc/ecbInsulation/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecbInsulationModel.getObject(request);
        return map;
    }
}
