package org.jeecg.modules.cable.controller.quality.uarea;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.quality.uarea.bo.AreaBo;
import org.jeecg.modules.cable.controller.quality.uarea.bo.AreaSortBo;
import org.jeecg.modules.cable.controller.quality.uarea.bo.EcuAreaBo;
import org.jeecg.modules.cable.controller.quality.uarea.bo.UAreaBo;
import org.jeecg.modules.cable.controller.quality.uarea.vo.UAreaVo;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.model.quality.EcuAreaModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@ApiSort(10101)
@Tag(name = "获取截面")
@RestController
public class EcuAreaController {
    @Resource
    EcuAreaModel ecuAreaModel;

    @Operation(summary = "获取截面列表")
    //getList
    @PostMapping({"/ecableErpPc/ecuArea/getList"})
    public Result<UAreaVo> getList(@RequestBody UAreaBo bo) {
        return Result.ok(ecuAreaModel.getListAndCount(bo));
    }

    @Operation(summary = "获取编辑截面信息")
    //getObject
    @PostMapping({"/ecableErpPc/ecuArea/getObject"})
    public Result<EcuArea> getObject(@RequestBody AreaBo bo) {
        return Result.ok(ecuAreaModel.getObject(bo));
    }

    @Operation(summary = "提交")
    //deal
    @PostMapping({"/ecableErpPc/ecuArea/deal"})
    public Result<String> deal(@RequestBody EcuAreaBo bo) throws IOException {
        return Result.ok(ecuAreaModel.deal(bo));
    }

    @Operation(summary = "排序")
    //sort
    @PostMapping({"/ecableErpPc/ecuArea/sort"})
    public Result<?> sort(@RequestBody AreaSortBo bo) throws IOException {
        ecuAreaModel.sort(bo);
        return Result.ok();
    }

    @Operation(summary = "开启")
    //start
    @PostMapping({"/ecableErpPc/ecuArea/start"})
    public Result<String> start(@RequestBody AreaBo bo) {
        return Result.ok(ecuAreaModel.start(bo));
    }
}
