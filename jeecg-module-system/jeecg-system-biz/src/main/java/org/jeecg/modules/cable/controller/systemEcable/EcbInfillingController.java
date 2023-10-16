package org.jeecg.modules.cable.controller.systemEcable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeecg.modules.cable.model.systemEcable.EcbInfillingModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "填充物")
@RestController
public class EcbInfillingController {
    @Resource
    EcbInfillingModel ecbInfillingModel;
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息

    @Operation(summary = "获取列表")
    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbInfilling/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {

            map = ecbInfillingModel.getListAndCount(request);
        }
        return map;
    }

    @Operation(summary = "根据id获取")
    //根据EcbInfilling获取EcbInfilling
    @PostMapping({"/ecableErpPc/ecbInfilling/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {

            map = ecbInfillingModel.getObject(request);
        }
        return map;
    }
}
