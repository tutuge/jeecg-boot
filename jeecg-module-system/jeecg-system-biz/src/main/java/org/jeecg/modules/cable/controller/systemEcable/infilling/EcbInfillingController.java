package org.jeecg.modules.cable.controller.systemEcable.infilling;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingDealBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.bo.EcbInfillingSortBo;
import org.jeecg.modules.cable.controller.systemEcable.infilling.vo.InfillingVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;
import org.jeecg.modules.cable.model.systemEcable.EcbInfillingModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "填充物--系统接口", description = "填充物--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "451", parseValue = true)})})
@RestController
public class EcbInfillingController {
    @Resource
    EcbInfillingModel ecbInfillingModel;

    @PostMapping({"/ecableAdminPc/ecbInfilling/getList"})
    public Result<InfillingVo> getList(@RequestBody EcbInfillingBo bo) {
        return Result.ok(ecbInfillingModel.getList(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbInfilling/getObject"})
    public Result<EcbInfilling> getObject(@RequestBody EcbInfillingBaseBo bo) {
        return Result.ok(ecbInfillingModel.getObject(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbInfilling/deal"})
    public Result<String> deal(@RequestBody EcbInfillingDealBo bo) {
        return Result.ok(ecbInfillingModel.deal(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbInfilling/sort"})
    public Result<?> sort(@RequestBody List<EcbInfillingSortBo> boList) {
        ecbInfillingModel.sort(boList);
        return Result.ok();
    }

    @PostMapping({"/ecableAdminPc/ecbInfilling/start"})
    public Result<String> start(@RequestBody EcbInfillingBaseBo bo) {
        String start = ecbInfillingModel.start(bo);
        return Result.ok(start);
    }

    @PostMapping({"/ecableAdminPc/ecbInfilling/delete"})
    public Result<?> delete(@RequestBody EcbInfillingBaseBo bo) {
        ecbInfillingModel.delete(bo);
        return Result.ok();
    }

}
