package org.jeecg.modules.cable.controller.efficiency;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.controller.efficiency.bo.EcdAreaBo;
import org.jeecg.modules.cable.entity.quality.EcuArea;
import org.jeecg.modules.cable.model.efficiency.EcdAreaModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiSupport(order = 101)
@Tag(name = "获取质量等级对应截面")
@RestController
public class EcdAreaController {
    @Resource
    EcdAreaModel ecdAreaModel;

    @Operation(summary = "获取质量等级对应截面")
    //getObject
    @PostMapping({"/ecableErpPc/ecdArea/getObject"})
    public Result<List<EcuArea>> getObject(@RequestBody EcdAreaBo bo, HttpServletRequest request) {
        return Result.ok(ecdAreaModel.getObject(bo, request));
    }
}
