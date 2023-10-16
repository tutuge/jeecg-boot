package org.jeecg.modules.cable.controller.load;

import org.jeecg.modules.cable.model.load.LoadRegister;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class loadRegisterController {
    @Resource
    LoadRegister loadRegister;

    //loadRegister
    @PostMapping({"/ecableErpPc/load/loadRegister"})
    public void loadRegister(HttpServletRequest request) {
        loadRegister.load(request);
    }

    //cleanRegisterData 清空注册时的公司数据
    @PostMapping({"/ecableErpPc/load/cleanRegisterData"})
    public void cleanRegisterData(HttpServletRequest request) {
        int ecCompanyId = Integer.parseInt(request.getParameter("ecCompanyId"));
        loadRegister.clean(ecCompanyId);
    }

    //getObject
    @PostMapping({"/ecableErpPc/load/loadZeyang"})
    public void loadZeyang(HttpServletRequest request) {
        loadRegister.loadZeyang();
    }
}
