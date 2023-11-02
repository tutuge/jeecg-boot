package org.jeecg.modules.cable.controller.load;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.load.bo.CompanyRegisterBo;
import org.jeecg.modules.cable.model.load.LoadRegister;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "加载注册时数据", description = "加载注册时数据",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "1", parseValue = true)})})
@RestController
@Slf4j
public class loadRegisterController {
    @Resource
    LoadRegister loadRegister;

    @Operation(summary = "加载数据")
    // loadRegister
    @PostMapping({"/ecableErpPc/load/loadRegister"})
    public void loadRegister(@RequestBody CompanyRegisterBo registerBo) {
        loadRegister.load(registerBo);
    }

    @Operation(summary = "清空注册时的公司数据")
    // cleanRegisterData 清空注册时的公司数据
    @PostMapping({"/ecableErpPc/load/cleanRegisterData"})
    public void cleanRegisterData(@RequestBody CompanyRegisterBo registerBo) {
        Integer ecCompanyId = registerBo.getEcCompanyId();
        loadRegister.clean(ecCompanyId);
    }

    // getObject
    @PostMapping({"/ecableErpPc/load/loadZeyang"})
    public void loadZeyang() {
        loadRegister.loadZeyang();
    }
}
