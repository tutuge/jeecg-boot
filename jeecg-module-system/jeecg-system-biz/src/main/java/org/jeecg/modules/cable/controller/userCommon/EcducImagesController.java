package org.jeecg.modules.cable.controller.userCommon;

import org.jeecg.modules.cable.model.userCommon.EcducImagesModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class EcducImagesController {
    @Resource
    EcducImagesModel ecducImagesModel;

    @PostMapping({"/ecableErpPc/ecducImages/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecducImagesModel.getList(request);
    }

    @PostMapping({"/ecableErpPc/ecducImages/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecducImagesModel.getObject(request);
    }

    //deal
    @PostMapping({"/ecableErpPc/ecducImages/deal"})
    public Map<String, Object> deal(HttpServletRequest request, MultipartFile image) {
        return ecducImagesModel.deal(request, image);
    }

    //delete
    @PostMapping({"/ecableErpPc/ecducImages/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecducImagesModel.delete(request);
    }
}
