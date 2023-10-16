package org.jeecg.modules.cable.controller.efficiency;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.efficiency.EcdAreaModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "获取质量等级对应截面")
@RestController
public class EcdAreaController {
    @Resource
    EcdAreaModel ecdAreaModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取质量等级对应截面")
    //getObject
    @PostMapping({"/ecableErpPc/ecdArea/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecdAreaModel.getObject(request);
        }
        return map;
    }
}
