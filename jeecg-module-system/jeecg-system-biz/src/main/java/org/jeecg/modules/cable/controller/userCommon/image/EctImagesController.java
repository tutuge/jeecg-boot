package org.jeecg.modules.cable.controller.userCommon.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.image.bo.EctImageDealBo;
import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.jeecg.modules.cable.model.userCommon.EctImagesModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "上传图片", description = "上传图片",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "151", parseValue = true)})})
@RestController
public class EctImagesController {
    @Resource
    EctImagesModel ectImagesModel;


    @Operation(summary = "上传图片")
    // deal
    @PostMapping({"/ecableErpPc/ectImages/deal"})
    public Result<EctImages> getList(@Validated @RequestBody EctImageDealBo bo) throws IOException {
        return Result.ok(ectImagesModel.deal(bo));
    }
}
