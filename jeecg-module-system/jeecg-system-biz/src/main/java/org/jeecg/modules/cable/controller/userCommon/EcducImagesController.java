package org.jeecg.modules.cable.controller.userCommon;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.jeecg.modules.cable.model.userCommon.EcducImagesModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ApiSort(10131)
@Tag(name = "图片")
@RestController
public class EcducImagesController {
    @Resource
    EcducImagesModel ecducImagesModel;

    @Operation(summary = "获取图片列表")
    @PostMapping({"/ecableErpPc/ecducImages/getList"})
    public Result<List<EcducImages>> getList(HttpServletRequest request) {
        return Result.ok(ecducImagesModel.getList(request));
    }


    @Operation(summary = "获取图片")
    @PostMapping({"/ecableErpPc/ecducImages/getObject"})
    public Result<EcducImages> getObject(HttpServletRequest request) {
        return Result.ok(ecducImagesModel.getObject(request));
    }


    @Operation(summary = "编辑图片")
    //deal
    @PostMapping({"/ecableErpPc/ecducImages/deal"})
    public Result<?> deal(HttpServletRequest request, MultipartFile image) {
        ecducImagesModel.deal(request, image);
        return Result.ok();
    }


    @Operation(summary = "删除图片")
    //delete
    @PostMapping({"/ecableErpPc/ecducImages/delete"})
    public Result<?> delete(HttpServletRequest request) {
        ecducImagesModel.delete(request);
        return Result.ok();
    }
}
