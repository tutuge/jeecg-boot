package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.systemEcable.EcbShieldModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "屏蔽")
@RestController
public class EcbShieldController {
    @Resource
    EcbShieldModel ecbShieldModel;
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息

    @Operation(summary = "获取屏蔽列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbShield/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbShieldModel.getListAndCount(request);
        return map;
    }

    @Operation(summary = "根据id获取")
    //根据EcbShield获取EcbShield
    @PostMapping({"/ecableErpPc/ecbShield/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecbShieldModel.getObject(request);
        return map;
    }
}
