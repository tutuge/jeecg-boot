package org.jeecg.modules.cable.controller.systemCommon.unit;


import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitDealBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitListBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitSortBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.vo.EcblUnitListVo;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.jeecg.modules.cable.model.systemCommon.EcblUnitModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order =2010)
@Tag(name = "获取长度单位")
@RestController
@Slf4j
public class EcblUnitController {
    @Resource
    EcblUnitModel ecblUnitModel;

    //deal
    @PostMapping({"/ecableAdminPc/ecblUnit/deal"})
    public Result<String> deal(@RequestBody EcblUnitDealBo bo) {
        return Result.ok(ecblUnitModel.deal(bo));
    }

    //getList
    @PostMapping({"/ecableAdminPc/ecblUnit/getList"})
    public Result<EcblUnitListVo> getList(@RequestBody EcblUnitListBo bo) {
        return Result.ok(ecblUnitModel.getList(bo));
    }

    //getObject
    @PostMapping({"/ecableAdminPc/ecblUnit/getObject"})
    public Result<EcblUnit> getObject(@RequestBody EcblUnitBaseBo bo) {
        return Result.ok(ecblUnitModel.getObject(bo));
    }

    //sort 排序
    @PostMapping({"/ecableAdminPc/ecblUnit/sort"})
    public Result<?> sort(@RequestBody List<EcblUnitSortBo> bos) {
        ecblUnitModel.sort(bos);
        return Result.ok();
    }

    //start 启用、禁用
    @PostMapping({"/ecableAdminPc/ecblUnit/start"})
    public Result<String> start(@RequestBody EcblUnitBaseBo bo) {
        return Result.ok(ecblUnitModel.start(bo));
    }

    //delete 删除
    @PostMapping({"/ecableAdminPc/ecblUnit/delete"})
    public Result<?> delete(@RequestBody EcblUnitBaseBo bo) {
        ecblUnitModel.delete(bo);
        return Result.ok();
    }
}
