package org.jeecg.modules.cable.controller.systemEcable.insulation;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationDealBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationListBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationSortBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.vo.InsulationVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.model.systemEcable.EcbInsulationModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSort(421)
@Tag(name = "绝缘--系统接口")
@RestController
public class EcbInsulationController {
    @Resource
    EcbInsulationModel ecbInsulationModel;

    @PostMapping({"/ecableAdminPc/ecbInsulation/getList"})
    public Result<InsulationVo> getList(@RequestBody EcbInsulationListBo bo) {
        return Result.ok(ecbInsulationModel.getList(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbInsulation/getObject"})
    public Result<EcbInsulation> getObject(@RequestBody EcbInsulationBaseBo bo) {
        return Result.ok(ecbInsulationModel.getObject(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbInsulation/deal"})
    public Result<String> deal(@RequestBody EcbInsulationDealBo bo) {
        return Result.ok(ecbInsulationModel.deal(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbInsulation/sort"})
    public Result<?> sort(@RequestBody List<EcbInsulationSortBo> bos) {
        ecbInsulationModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/ecableAdminPc/ecbInsulation/start"})
    public Result<String> start(@RequestBody EcbInsulationBaseBo bo) {
        return Result.ok(ecbInsulationModel.start(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbInsulation/delete"})
    public Result<?> delete(@RequestBody EcbInsulationBaseBo bo) {
        ecbInsulationModel.delete(bo);
        return Result.ok();
    }

}
