package org.jeecg.modules.cable.controller.userOffer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.model.userOffer.EcuoCoreModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "芯数")
@RestController
@Slf4j
public class EcuoCoreController {
    @Resource
    EcuoCoreModel ecuoCoreModel;

    @Operation(summary = "获取芯数列表")
    @PostMapping({"/ecableErpPc/ecuoCore/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuoCoreModel.getList(request);
    }
}
