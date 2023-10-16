package org.jeecg.modules.cable.controller.userOffer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.userOffer.EcuoAreaModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Tag(name = "平方数")
@RestController
@Slf4j
public class EcuoAreaController {
    @Resource
    EcuoAreaModel ecuoAreaModel;

    @Operation(summary = "获取平方数列表")
    @PostMapping({"/ecableErpPc/ecuoArea/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuoAreaModel.getList(request);
    }
}
