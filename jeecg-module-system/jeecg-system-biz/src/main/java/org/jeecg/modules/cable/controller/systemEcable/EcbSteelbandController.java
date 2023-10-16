package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.systemEcable.EcbSteelbandModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "钢带")
@RestController
public class EcbSteelbandController {
    @Resource
    EcbSteelbandModel ecbSteelbandModel;
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息

    @Operation(summary = "获取刚带列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbSteelband/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbSteelbandModel.getListAndCount(request);
        }
        return map;
    }

    @Operation(summary = "获取刚带")
    //根据EcbSteelband获取EcbSteelband
    @PostMapping({"/ecableErpPc/ecbSteelband/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecbSteelbandModel.getObject(request);
        }
        return map;
    }
}
