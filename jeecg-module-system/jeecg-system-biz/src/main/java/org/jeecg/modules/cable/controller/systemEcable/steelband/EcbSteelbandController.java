package org.jeecg.modules.cable.controller.systemEcable.steelband;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelBandBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelBandDealBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelBandListBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelBandSortBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.vo.SteelbandVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;
import org.jeecg.modules.cable.model.systemEcable.EcbSteelbandModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order =471)
@Tag(name = "钢带--系统接口")
@RestController
public class EcbSteelbandController {
    @Resource
    EcbSteelbandModel ecbSteelbandModel;

    @PostMapping({"/ecableAdminPc/ecbSteelband/getList"})
    public Result<SteelbandVo> getList(@RequestBody EcbSteelBandListBo bo) {
        return Result.ok(ecbSteelbandModel.getList(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbSteelband/getObject"})
    public Result<EcbSteelBand> getObject(@RequestBody EcbSteelBandBaseBo bo) {
        return Result.ok(ecbSteelbandModel.getObject(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbSteelband/deal"})
    public Result<String> deal(@RequestBody EcbSteelBandDealBo bo) {
        return Result.ok(ecbSteelbandModel.deal(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbSteelband/sort"})
    public Result<?> sort(@RequestBody List<EcbSteelBandSortBo> bos) {
        ecbSteelbandModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/ecableAdminPc/ecbSteelband/start"})
    public Result<String> start(@RequestBody EcbSteelBandBaseBo bo) {
        return Result.ok(ecbSteelbandModel.start(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbSteelband/delete"})
    public Result<?> delete(@RequestBody EcbSteelBandBaseBo bo) {
        ecbSteelbandModel.delete(bo);
        return Result.ok();
    }

}
