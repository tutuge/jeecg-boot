package org.jeecg.modules.cable.controller.systemEcable.micatape;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeDealBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeListBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeSortBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;
import org.jeecg.modules.cable.model.systemEcable.EcbMicaTapeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "云母带--系统接口", description = "云母带--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "441", parseValue = true)})})
@RestController
@RequestMapping("/ecableAdminPc/ecbMicatape")
public class EcbMicaTapeController {
    @Resource
    EcbMicaTapeModel ecbMicatapeModel;

    @PostMapping({"/getList"})
    public Result<MicatapeVo> getList(@RequestBody EcbMicatapeListBo bo) {
        return Result.ok(ecbMicatapeModel.getList(bo));
    }

    @PostMapping({"/getObject"})
    public Result<EcbMicaTape> getObject(@RequestBody EcbMicatapeBaseBo bo) {
        return Result.ok(ecbMicatapeModel.getObject(bo));
    }

    @PostMapping({"/deal"})
    public Result<String> deal(@RequestBody EcbMicatapeDealBo bo) {
        return Result.ok(ecbMicatapeModel.deal(bo));
    }

    @PostMapping({"/sort"})
    public Result<?> sort(@RequestBody List<EcbMicatapeSortBo> bos) {
        ecbMicatapeModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/start"})
    public Result<String> start(@RequestBody EcbMicatapeBaseBo bo) {
        return Result.ok(ecbMicatapeModel.start(bo));
    }

    @PostMapping({"/delete"})
    public Result<?> delete(@RequestBody EcbMicatapeBaseBo bo) {
        ecbMicatapeModel.delete(bo);
        return Result.ok();
    }
}
