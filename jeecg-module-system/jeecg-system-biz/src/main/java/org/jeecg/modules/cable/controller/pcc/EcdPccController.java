package org.jeecg.modules.cable.controller.pcc;

import org.jeecg.modules.cable.model.efficiency.EcdPccModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import org.jeecg.modules.cable.tools.CommonFunction;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcdPccController {
    @Resource
    EcuLoginModel ecuLoginModel;
    @Resource
    EcdPccModel ecdPccModel;

    //load
    @PostMapping({"/ecableErpPc/ecdPcc/load"})
    public Map<String, Object> load(HttpServletRequest request) {

            ecdPccModel.load(request);
            status = 3;//正常获取数据
            code = "200";
            msg = "正常获取数据";
            CommonFunction.getCommonMap(map, status, code, msg);}
        return map;
    }
}
