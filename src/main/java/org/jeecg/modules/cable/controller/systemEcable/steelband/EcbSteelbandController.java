package org.jeecg.modules.cable.controller.systemEcable.steelband;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "钢带--系统接口", description = "钢带--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "471", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbSteelband")
public class EcbSteelbandController {
    @Resource
    EcbSteelbandModel ecbSteelbandModel;

    @PostMapping({"/getList"})
    public Result<SteelbandVo> getList(@RequestBody EcbSteelBandListBo bo) {
        return Result.ok(ecbSteelbandModel.getList(bo));
    }

    @PostMapping({"/getObject"})
    public Result<EcbSteelBand> getObject(@RequestBody EcbSteelBandBaseBo bo) {
        return Result.ok(ecbSteelbandModel.getObject(bo));
    }

    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbSteelBandDealBo bo) {
        return Result.ok(ecbSteelbandModel.deal(bo));
    }

    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbSteelBandSortBo> bos) {
        ecbSteelbandModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbSteelBandBaseBo bo) {
        return Result.ok(ecbSteelbandModel.start(bo));
    }

    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbSteelBandBaseBo bo) {
        ecbSteelbandModel.delete(bo);
        return Result.ok();
    }

}
