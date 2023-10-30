package org.jeecg.modules.cable.controller.systemEcable.bag;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagBaseBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagDealBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagListBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.bo.EcbBagSortBo;
import org.jeecg.modules.cable.controller.systemEcable.bag.vo.BagVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.jeecg.modules.cable.model.systemEcable.EcbBagModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "包带--系统接口", description = "包带--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "461", parseValue = true)})})
@RestController
public class EcbBagController {

    @Resource
    EcbBagModel ecbBagModel;

    @PostMapping({"/ecableAdminPc/ecbBag/getList"})
    public Result<BagVo> getList(@RequestBody EcbBagListBo bo) {
        return Result.ok(ecbBagModel.getList(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbBag/getObject"})
    public Result<EcbBag> getObject(@Validated @RequestBody EcbBagBaseBo bo) {
        return Result.ok(ecbBagModel.getObject(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbBag/deal"})
    public Result<String> deal(@RequestBody EcbBagDealBo bo) {
        return Result.ok(ecbBagModel.deal(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbBag/sort"})
    public Result<?> sort(@RequestBody List<EcbBagSortBo> bos) {
        ecbBagModel.sort(bos);
        return Result.ok();
    }

    @PostMapping({"/ecableAdminPc/ecbBag/start"})
    public Result<String> start(@Validated @RequestBody EcbBagBaseBo bo) {
        return Result.ok(ecbBagModel.start(bo));
    }

    @PostMapping({"/ecableAdminPc/ecbBag/delete"})
    public Result<?> delete(@RequestBody EcbBagBaseBo request) {
        ecbBagModel.delete(request);
        return Result.ok();
    }
}
