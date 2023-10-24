package org.jeecg.modules.cable.controller.systemEcable.micatape;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeDealBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeListBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.bo.EcbMicatapeSortBo;
import org.jeecg.modules.cable.controller.systemEcable.micatape.vo.MicatapeVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.jeecg.modules.cable.model.systemEcable.EcbMicatapeModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order =441)
@Tag(name = "云母带--系统接口")
@RestController
public class EcbMicatapeController {
    @Resource
    EcbMicatapeModel ecbMicatapeModel;

    @PostMapping({"/ecableAdminPc/ecbMicatape/getList"})
    public Result<MicatapeVo> getList(@RequestBody EcbMicatapeListBo bo) {
        return Result.ok(ecbMicatapeModel.getList(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbMicatape/getObject"})
    public Result<EcbMicatape> getObject(@RequestBody EcbMicatapeBaseBo bo) {
        return Result.ok(ecbMicatapeModel.getObject(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbMicatape/deal"})
    public Result<String> deal(@RequestBody EcbMicatapeDealBo bo) {
        return Result.ok(ecbMicatapeModel.deal(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbMicatape/sort"})
    public Result<?> sort(@RequestBody List<EcbMicatapeSortBo> bos) {
        ecbMicatapeModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/ecableAdminPc/ecbMicatape/start"})
    public Result<String> start(@RequestBody EcbMicatapeBaseBo bo) {
        return Result.ok(ecbMicatapeModel.start(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbMicatape/delete"})
    public Result<?> delete(@RequestBody EcbMicatapeBaseBo bo) {
        ecbMicatapeModel.delete(bo);
        return Result.ok();
    }
}
