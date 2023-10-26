package org.jeecg.modules.cable.controller.userCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.model.userCommon.EctImagesModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Tag(name = "上传图片", description = "上传图片",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "151", parseValue = true)})})
@RestController
public class EctImagesController {
    @Resource
    EctImagesModel ectImagesModel;


    @Operation(summary = "上传图片")
    //deal
    @PostMapping({"/ecableErpPc/ectImages/deal"})
    public Result<Map<String, Object>> getList(HttpServletRequest request, MultipartFile image) throws IOException {
        return Result.ok(ectImagesModel.deal(request, image));
    }
}
