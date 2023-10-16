package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuInfillingModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "填充物")
@RestController
public class EcbuInfillingController {
    @Resource
    EcbuInfillingModel ecbuInfillingModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "提交编辑填充物列表")
    //deal
    @PostMapping({"/ecableErpPc/ecbuInfilling/deal"})
    public Map<String, Object> login_deal(HttpServletRequest request) {

            map = ecbuInfillingModel.deal(request);
        return map;
    }


    @Operation(summary = "是否启用")
    //start
    @PostMapping({"/ecableErpPc/ecbuInfilling/start"})
    public Map<String, Object> start(HttpServletRequest request) {

            map = ecbuInfillingModel.start(request);
        return map;
    }


    @Operation(summary = "填充物列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuInfilling/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbuInfillingModel.getList(request);
        return map;
    }
}
