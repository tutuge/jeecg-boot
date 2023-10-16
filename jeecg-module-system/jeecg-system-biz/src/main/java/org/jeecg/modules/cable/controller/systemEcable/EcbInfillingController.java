package org.jeecg.modules.cable.controller.systemEcable;

import org.jeecg.modules.cable.model.systemEcable.EcbInfillingModel;
import org.jeecg.modules.cable.model.user.EcuLoginModel;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EcbInfillingController {
    @Resource
    EcbInfillingModel ecbInfillingModel;
    @Resource
    EcuLoginModel ecuLoginModel;//核验登录信息

    //根据startType获取信息列表
    @PostMapping({"/ecableErpPc/ecbInfilling/getList"})
    public Map<String, Object> getList(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbInfillingModel.getListAndCount(request);
        }
        return map;
    }

    //根据EcbInfilling获取EcbInfilling
    @PostMapping({"/ecableErpPc/ecbInfilling/getObject"})
    public Map<String, Object> getObject(HttpServletRequest request) {
        Map<String, Object> map;
        int ecuId = Integer.parseInt(request.getParameter("ecuId"));
        String token = request.getHeader("token");
        map = ecuLoginModel.isExistsToken(request, ecuId, token);
        if ("3".equals(map.get("status").toString())) {
            map = ecbInfillingModel.getObject(request);
        }
        return map;
    }
}
