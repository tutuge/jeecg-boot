package org.jeecg.modules.cable.controller.userOffer;

import org.jeecg.modules.cable.model.userOffer.EcuoProgrammeModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class EcuoProgrammeController {
    @Resource
    EcuoProgrammeModel ecuoProgrammeModel;

    @PostMapping({"/ecableErpPc/ecuoProgramme/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuoProgrammeModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/ecuoProgramme/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuoProgrammeModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/ecuoProgramme/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuoProgrammeModel.getObject(request);
    }

    @PostMapping({"/ecableErpPc/ecuoProgramme/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return ecuoProgrammeModel.sort(request);
    }

    @PostMapping({"/ecableErpPc/ecuoProgramme/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecuoProgrammeModel.delete(request);
    }
}
