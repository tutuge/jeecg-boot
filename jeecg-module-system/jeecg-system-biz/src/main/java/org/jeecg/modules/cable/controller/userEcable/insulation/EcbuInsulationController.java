package org.jeecg.modules.cable.controller.userEcable.insulation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationListBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationStartBo;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.model.userEcable.EcbuInsulationModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "绝缘")
@RestController
public class EcbuInsulationController {
    @Resource
    EcbuInsulationModel ecbuInsulationModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "提交编辑绝缘")
    //deal
    @PostMapping({"/ecableErpPc/ecbuInsulation/deal"})
    public Result<String> deal(@RequestBody EcbuInsulationBo bo) {
        String msg = ecbuInsulationModel.deal(bo);
        return Result.ok(msg);
    }


    @Operation(summary = "开启禁用绝缘")
    //start
    @PostMapping({"/ecableErpPc/ecbuInsulation/start"})
    public Result<String> start(@RequestBody EcbuInsulationStartBo bo) {
        String msg = ecbuInsulationModel.start(bo);
        return Result.OK(msg);
    }


    @Operation(summary = "绝缘列表")
    //getList
    @PostMapping({"/ecableErpPc/ecbuInsulation/getList"})
    public Result<List<EcbuInsulation>> getList(@RequestBody EcbuInsulationListBo bo) {
        List<EcbuInsulation> list = ecbuInsulationModel.getList(bo);
        return Result.OK(list);
    }
}
