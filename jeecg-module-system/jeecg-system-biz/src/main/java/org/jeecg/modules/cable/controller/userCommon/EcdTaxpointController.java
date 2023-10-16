package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userCommon.EcdTaxpointModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "税点")
@RestController
public class EcdTaxpointController {
    @Resource
    EcdTaxpointModel ecdTaxpointModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取税点列表")
    //getList
    @PostMapping({"/ecableErpPc/ecdTaxpoint/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecdTaxpointModel.getListAndCount(request);
        }
        return map;
    }

    @Operation(summary = "获取税点")
    //getObject
    @PostMapping({"/ecableErpPc/ecdTaxpoint/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecdTaxpointModel.getObject(request);
        }
        return map;
    }
}
