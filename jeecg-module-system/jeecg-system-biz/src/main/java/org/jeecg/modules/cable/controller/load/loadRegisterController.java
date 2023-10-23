package org.jeecg.modules.cable.controller.load;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.model.load.LoadRegister;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiSort(10041)
@Tag(name = "加载注册时数据")
@RestController
@Slf4j
public class loadRegisterController {
    @Resource
    LoadRegister loadRegister;

    @Operation(summary = "加载数据")
    //loadRegister
    @PostMapping({"/ecableErpPc/load/loadRegister"})
    public void loadRegister(HttpServletRequest request) {
        loadRegister.load(request);
    }

    @Operation(summary = "清空注册时的公司数据")
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
