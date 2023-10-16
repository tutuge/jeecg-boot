package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuBagModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Tag(name = "包带")
@RestController
public class EcbuBagController {
    @Resource
    EcbuBagModel ecbuBagModel;
    @Resource
    EcuLoginModel ecuLoginModel;


    @Operation(summary = "包带编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecbuBag/deal"})
    public Map<String, Object> login_deal(HttpServletRequest request) {

            ecbuBagModel.deal(request);
        }
        return map;
    }

    @Operation(summary = "包带开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuBag/start"})
    public Map<String, Object> start(HttpServletRequest request) {

            map = ecbuBagModel.start(request);
        }
        return map;
    }


    @Operation(summary = "包带列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuBag/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbuBagModel.getList(request);
        }
        return map;
    }
}
