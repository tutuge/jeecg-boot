package org.jeecg.modules.cable.controller.systemEcable.shield;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.bo.EcbShieldStartBo;
import org.jeecg.modules.cable.controller.systemEcable.shield.vo.ShieldVo;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.jeecg.modules.cable.model.systemEcable.EcbShieldModel;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "屏蔽")
@RestController
public class EcbShieldController {
    @Resource
    EcbShieldModel ecbShieldModel;
//核验登录信息

    @Operation(summary = "获取屏蔽列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbShield/getList"})
    public Result<ShieldVo> getList(@RequestBody EcbShieldBo bo) {
           return Result.ok(ecbShieldModel.getListAndCount(bo));
    }

    @Operation(summary = "根据id获取")
    //根据EcbShield获取EcbShield
    @PostMapping({"/ecableErpPc/ecbShield/getObject"})
    public Result<EcbShield> getObject(@RequestBody EcbShieldStartBo bo) {
          return Result.ok(ecbShieldModel.getObject(bo));
    }
}
