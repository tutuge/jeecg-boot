package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.userCommon.EcducImagesModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name = "图片")
@RestController
public class EcducImagesController {
    @Resource
    EcducImagesModel ecducImagesModel;

    @Operation(summary = "获取图片列表")
    @PostMapping({"/ecableErpPc/ecducImages/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        return ecducImagesModel.getList(request);
    }


    @Operation(summary = "获取图片")
    @PostMapping({"/ecableErpPc/ecducImages/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        return ecducImagesModel.getObject(request);
    }


    @Operation(summary = "编辑图片")
    //deal
    @PostMapping({"/ecableErpPc/ecducImages/deal"})
    public Map<String, Object> deal(HttpServletRequest request, MultipartFile image) {
        return ecducImagesModel.deal(request, image);
    }


    @Operation(summary = "删除图片")
    //delete
    @PostMapping({"/ecableErpPc/ecducImages/delete"})
    public Map<String, Object> delete(HttpServletRequest request) {
        return ecducImagesModel.delete(request);
    }
}
