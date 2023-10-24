package org.jeecg.modules.cable.controller.userEcable.insulation;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.bo.EcbInsulationStartBo;
import org.jeecg.modules.cable.controller.systemEcable.insulation.vo.InsulationVo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationListBo;
import org.jeecg.modules.cable.controller.userEcable.insulation.bo.EcbuInsulationStartBo;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;
import org.jeecg.modules.cable.model.userEcable.EcbuInsulationModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSort(420)
@Tag(name = "绝缘管理--用户接口")
@RestController
public class EcbuInsulationController {
    @Resource
    EcbuInsulationModel ecbuInsulationModel;

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

    @Operation(summary = "获取绝缘列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbInsulation/getList"})
    public Result<InsulationVo> getList(@RequestBody EcbInsulationBo bo) {
        return Result.ok(ecbuInsulationModel.getListAndCount(bo));
    }

    @Operation(summary = "根据id获取")
    //根据EcbInsulation获取EcbInsulation
    @PostMapping({"/ecableErpPc/ecbInsulation/getObject"})
    public Result<EcbInsulation> getObject(@RequestBody EcbInsulationStartBo bo) {
        return Result.ok(ecbuInsulationModel.getObject(bo));
    }
}
