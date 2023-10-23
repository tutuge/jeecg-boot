package org.jeecg.modules.cable.controller.userCommon;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
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

@ApiSort(10151)
@Tag(name = "上传图片")
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
