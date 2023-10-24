package org.jeecg.modules.cable.controller.efficiency;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.cable.model.efficiency.EcdCollectModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@ApiSupport(order =111)
@Tag(name = "获取除丝号以外的txt内容")
@RestController
public class EcdCollectController {
    @Resource
    EcdCollectModel ecdCollectModel;

    @Operation(summary = "获取除丝号以外的txt内容")
    //getObject
    @PostMapping({"/ecableErpPc/ecdCollect/getObject"})
    public Result<Map<String, Object>> getObject(HttpServletRequest request) {
        return Result.ok(ecdCollectModel.getObject(request));
    }
}
