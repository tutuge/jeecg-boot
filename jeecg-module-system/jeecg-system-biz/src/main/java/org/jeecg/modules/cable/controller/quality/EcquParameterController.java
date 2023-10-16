package org.jeecg.modules.cable.controller.quality;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeecg.modules.cable.model.quality.EcquParameterModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "获取电缆质量")
@RestController
public class EcquParameterController {
    @Resource
    EcquParameterModel ecquParameterModel;
    @Resource
    EcuLoginModel ecuLoginModel;

    @Operation(summary = "获取电缆质量等级参数列表")
    //getList
    @PostMapping({"/ecableErpPc/ecquParameter/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecquParameterModel.getListAndCount(request);
        }
        return map;
    }

    //getObject
    @PostMapping({"/ecableErpPc/ecquParameter/getObject"})
    public Map<String, Object> getObjectPassId(HttpServletRequest request) {

            map = ecquParameterModel.getObject(request);
        }
        return map;
    }

    @Operation(summary = "编辑提交")
    //deal
    @PostMapping({"/ecableErpPc/ecquParameter/deal"})
    public Map<String, Object> deal(HttpServletRequest request) {

            map = ecquParameterModel.deal(request);
        }
        return map;
    }
}
