package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.systemEcable.EcbSheathModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "护套")
@RestController
public class EcbSheathController {
    @Resource
    EcbSheathModel ecbSheathModel;
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息

    @Operation(summary = "获取护套列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbSheath/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbSheathModel.getListAndCount(request);
        }
        return map;
    }


    @Operation(summary = "获取护套")
    //根据ecbcId获取EcbSheath
    @PostMapping({"/ecableErpPc/ecbSheath/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecbSheathModel.getObject(request);
        }
        return map;
    }
}
