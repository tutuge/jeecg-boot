package org.jeecg.modules.cable.controller.userCommon.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.image.bo.ImageBaseBo;
import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.jeecg.modules.cable.model.userCommon.EcducImagesModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "图片", description = "图片",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "131", parseValue = true)})})
@RestController
public class EcducImagesController {
    @Resource
    EcducImagesModel ecducImagesModel;

    @Operation(summary = "获取图片列表")
    @PostMapping({"/ecableErpPc/ecducImages/getList"})
    public Result<List<EcducImages>> getList(@Validated @RequestBody ImageBaseBo bo) {
        return Result.ok(ecducImagesModel.getList(bo));
    }


    @Operation(summary = "获取图片")
    @PostMapping({"/ecableErpPc/ecducImages/getObject"})
    public Result<EcducImages> getObject(@RequestBody ImageBaseBo bo) {
        return Result.ok(ecducImagesModel.getObject(bo));
    }


    @Operation(summary = "编辑图片")
    //deal
    @PostMapping({"/ecableErpPc/ecducImages/deal"})
    public Result<?> deal(@RequestBody ImageBaseBo bo, HttpServletRequest request, MultipartFile image) {
        ecducImagesModel.deal(bo, request, image);
        return Result.ok();
    }


    @Operation(summary = "删除图片")
    //delete
    @PostMapping({"/ecableErpPc/ecducImages/delete"})
    public Result<?> delete(@RequestBody ImageBaseBo bo) {
        ecducImagesModel.delete(bo);
        return Result.ok();
    }
}
