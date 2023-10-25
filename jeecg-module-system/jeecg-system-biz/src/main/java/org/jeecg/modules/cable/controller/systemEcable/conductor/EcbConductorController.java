package org.jeecg.modules.cable.controller.systemEcable.conductor;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorDealBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorListBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.bo.EcbConductorSortBo;
import org.jeecg.modules.cable.controller.systemEcable.conductor.vo.ConductorVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;
import org.jeecg.modules.cable.model.systemEcable.EcbConductorModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "导体--系统接口",description = "导体--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "411", parseValue = true)})})
@RestController
public class EcbConductorController {
    @Resource
    EcbConductorModel ecbConductorModel;

    @PostMapping({"/ecableAdminPc/ecbConductor/getList"})
    public Result<ConductorVo> getList(@RequestBody EcbConductorListBo bo) {
        return Result.ok(ecbConductorModel.getList(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbConductor/getObject"})
    public Result<EcbConductor> getObject(@RequestBody EcbConductorBaseBo bo) {
        return Result.ok(ecbConductorModel.getObject(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbConductor/deal"})
    public Result<String> deal(@RequestBody EcbConductorDealBo bo) {
        return Result.ok(ecbConductorModel.deal(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbConductor/sort"})
    public Result<?> sort(@RequestBody List<EcbConductorSortBo> bos) {
        ecbConductorModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/ecableAdminPc/ecbConductor/start"})
    public Result<String> start(@RequestBody EcbConductorBaseBo bo) {
        return Result.ok(ecbConductorModel.start(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbConductor/delete"})
    public Result<?> delete(@RequestBody EcbConductorBaseBo bo) {
        ecbConductorModel.delete(bo);
        return Result.ok();
    }
}
