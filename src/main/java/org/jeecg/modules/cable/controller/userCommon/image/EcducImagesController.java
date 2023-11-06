package org.jeecg.modules.cable.controller.userCommon.image;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userCommon.image.bo.ImageBaseBo;
import org.jeecg.modules.cable.controller.userCommon.image.bo.ImageBo;
import org.jeecg.modules.cable.controller.userCommon.image.bo.ImageDealBo;
import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.jeecg.modules.cable.model.userCommon.EcducImagesModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "公司图片--用户接口", description = "公司图片--用户接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "131", parseValue = true)})})
@RestController
@RequestMapping("/ecableErpPc/ecducImages")
public class EcducImagesController {
    @Resource
    EcducImagesModel ecducImagesModel;

    @Operation(summary = "获取图片列表")
    @PostMapping({"/getList"})
    public Result<List<EcducImages>> getList(@Validated @RequestBody ImageBo bo) {
        return Result.ok(ecducImagesModel.getList(bo));
    }


    @Operation(summary = "获取图片")
    @PostMapping({"/getObject"})
    public Result<EcducImages> getObject(@RequestBody ImageBaseBo bo) {
        return Result.ok(ecducImagesModel.getObject(bo));
    }


    @Operation(summary = "编辑图片")
    @PostMapping({"/deal"})
    public Result<?> deal(@Validated @RequestBody ImageDealBo bo) {
        ecducImagesModel.deal(bo);
        return Result.ok();
    }


    @Operation(summary = "删除图片")
    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody ImageBaseBo bo) {
        ecducImagesModel.delete(bo);
        return Result.ok();
    }
}
