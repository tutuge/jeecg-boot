package org.jeecg.modules.cable.controller.userOffer;

import org.jeecg.modules.cable.model.userOffer.EcuoCoreModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class EcuoCoreController {
    @Resource
    EcuoCoreModel ecuoCoreModel;

    @PostMapping({"/ecableErpPc/ecuoCore/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuoCoreModel.getList(request);
    }
}
