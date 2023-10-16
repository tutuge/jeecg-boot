package org.jeecg.modules.cable.controller.userEcable.bag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagBo;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagListBo;
import org.jeecg.modules.cable.controller.userEcable.bag.bo.EcbuBagStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuBag;
import org.jeecg.modules.cable.model.userEcable.EcbuBagModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "包带")
@RestController
public class EcbuBagController {
    @Resource
    EcbuBagModel ecbuBagModel;


    @Operation(summary = "包带编辑")
    //deal
    @PostMapping({"/ecableErpPc/ecbuBag/deal"})
    public Result<?> deal(@RequestBody EcbuBagBo bo) {
        ecbuBagModel.deal(bo);
        return Result.ok();
    }

    @Operation(summary = "包带开启禁用")
    //start
    @PostMapping({"/ecableErpPc/ecbuBag/start"})
    public Result<String> start(@RequestBody EcbuBagStartBo bo) {
        return Result.OK(ecbuBagModel.start(bo));
    }


    @Operation(summary = "包带列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuBag/getList"})
    public Result<List<EcbuBag>> getList(@RequestBody EcbuBagListBo bo) {
        return Result.OK(ecbuBagModel.getList(bo));
    }
}
