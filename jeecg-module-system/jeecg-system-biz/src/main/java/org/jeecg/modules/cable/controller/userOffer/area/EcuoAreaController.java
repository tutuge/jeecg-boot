package org.jeecg.modules.cable.controller.userOffer.area;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userOffer.area.bo.AreaListBo;
import org.jeecg.modules.cable.entity.userOffer.EcuoArea;
import org.jeecg.modules.cable.model.userOffer.EcuoAreaModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@ApiSupport(order = 10301)
@Tag(name = "平方数")
@RestController
@Slf4j
public class EcuoAreaController {
    @Resource
    EcuoAreaModel ecuoAreaModel;

    @Operation(summary = "获取平方数列表")
    @PostMapping({"/ecableErpPc/ecuoArea/getList"})
    public Result<List<EcuoArea>> getList(@RequestBody AreaListBo bo) {
        return Result.ok(ecuoAreaModel.getList(bo));
    }
}
