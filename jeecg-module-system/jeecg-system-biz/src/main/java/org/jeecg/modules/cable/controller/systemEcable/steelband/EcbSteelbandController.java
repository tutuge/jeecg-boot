package org.jeecg.modules.cable.controller.systemEcable.steelband;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.bo.EcbSteelbandStartBo;
import org.jeecg.modules.cable.controller.systemEcable.steelband.vo.SteelbandVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelband;
import org.jeecg.modules.cable.model.systemEcable.EcbSteelbandModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "钢带")
@RestController
public class EcbSteelbandController {
    @Resource
    EcbSteelbandModel ecbSteelbandModel;

    @Operation(summary = "获取钢带列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbSteelband/getList"})
    public Result<SteelbandVo> getList(@RequestBody EcbSteelbandBo bo) {
           return Result.ok(ecbSteelbandModel.getListAndCount(bo));
    }

    @Operation(summary = "获取钢带")
    //根据EcbSteelband获取EcbSteelband
    @PostMapping({"/ecableErpPc/ecbSteelband/getObject"})
    public Result<EcbSteelband> getObject(@RequestBody EcbSteelbandStartBo bo) {
            return Result.ok(ecbSteelbandModel.getObject(bo));
    }
}
