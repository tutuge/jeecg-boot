package org.jeecg.modules.cable.controller.userEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuSheathModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "护套")
@RestController
public class EcbuSheathController {
    @Resource
    EcbuSheathModel ecbuSheathModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "编辑护套")
    //deal
    @PostMapping({"/ecableErpPc/ecbuSheath/deal"})
    public Map<String, Object> login_deal(HttpServletRequest request) {

            map = ecbuSheathModel.deal(request);
        return map;
    }


    @Operation(summary = "开启护套")
    //start
    @PostMapping({"/ecableErpPc/ecbuSheath/start"})
    public Map<String, Object> start(HttpServletRequest request) {

            map = ecbuSheathModel.start(request);
        return map;
    }


    @Operation(summary = "护套列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuSheath/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbuSheathModel.getList(request);
        return map;
    }
}
