package org.jeecg.modules.cable.controller.userOffer;

import org.jeecg.modules.cable.model.userOffer.EcuoAreaModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class EcuoAreaController {
    @Resource
    EcuoAreaModel ecuoAreaModel;

    @PostMapping({"/ecableErpPc/ecuoArea/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuoAreaModel.getList(request);
    }
}
