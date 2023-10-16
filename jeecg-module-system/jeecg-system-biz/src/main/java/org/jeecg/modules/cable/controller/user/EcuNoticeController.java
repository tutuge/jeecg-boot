package org.jeecg.modules.cable.controller.user;

import org.jeecg.modules.cable.model.user.EcuNoticeModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcuNoticeController {
    @Resource
    EcuNoticeModel ecuNoticeModel;

    @PostMapping({"/ecableErpPc/ecuNotice/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecuNoticeModel.getObject(request);
    }

    @PostMapping({"/ecableErpPc/ecuNotice/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecuNoticeModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/ecuNotice/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {
        return ecuNoticeModel.deal(request);
    }

    @PostMapping({"/ecableErpPc/ecuNotice/start"})
    public Map<String, Object> start(HttpServletRequest request) {
        return ecuNoticeModel.start(request);
    }

    @PostMapping({"/ecableErpPc/ecuNotice/sort"})
    public Map<String, Object> sort(HttpServletRequest request) {
        return ecuNoticeModel.sort(request);
    }

    @PostMapping({"/ecableErpPc/ecuNotice/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecuNoticeModel.delete(request);
    }

    //设置默认
    @PostMapping({"/ecableErpPc/ecuNotice/defaultType"})
    public Map<String, Object> defaultType(HttpServletRequest request) {
        return ecuNoticeModel.defaultType(request);
    }
}
