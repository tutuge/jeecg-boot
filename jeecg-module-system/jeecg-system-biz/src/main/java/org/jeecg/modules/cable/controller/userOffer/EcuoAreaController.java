package org.jeecg.modules.cable.controller.userOffer;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.entity.userOffer.EcuoArea;
import org.jeecg.modules.cable.model.userOffer.EcuoAreaModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@ApiSort(10301)
@Tag(name = "平方数")
@RestController
@Slf4j
public class EcuoAreaController {
    @Resource
    EcuoAreaModel ecuoAreaModel;

    @Operation(summary = "获取平方数列表")
    @PostMapping({"/ecableErpPc/ecuoArea/getList"})
    public Result<List<EcuoArea>> getList(HttpServletRequest request) {
        return Result.ok(ecuoAreaModel.getList(request));
    }
}
