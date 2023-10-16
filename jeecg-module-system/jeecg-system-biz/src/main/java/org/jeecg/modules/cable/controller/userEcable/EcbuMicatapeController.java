package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuMicatapeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "云母带")
@RestController
public class EcbuMicatapeController {
    @Resource
    EcbuMicatapeModel ecbuMicatapeModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "提交编辑云母带")
    //deal
    @PostMapping({"/ecableErpPc/ecbuMicatape/deal"})
    public Map<String, Object> login_deal(HttpServletRequest request) {

            map = ecbuMicatapeModel.deal(request);
        }
        return map;
    }


    @Operation(summary = "是否启用")
    //start
    @PostMapping({"/ecableErpPc/ecbuMicatape/start"})
    public Map<String, Object> start(HttpServletRequest request) {

            map = ecbuMicatapeModel.start(request);
        }
        return map;
    }

    @Operation(summary = "云母带列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuMicatape/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbuMicatapeModel.getList(request);
        }
        return map;
    }
}
