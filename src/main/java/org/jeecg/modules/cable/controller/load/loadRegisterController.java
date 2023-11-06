package org.jeecg.modules.cable.controller.load;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.load.bo.CompanyRegisterBo;
import org.jeecg.modules.cable.model.load.LoadRegister;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "加载注册时数据", description = "加载注册时数据",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "1", parseValue = true)})})
@RestController
@Slf4j
@RequestMapping("/ecableErpPc/load")
public class loadRegisterController {
    @Resource
    LoadRegister loadRegister;

    @Operation(summary = "加载数据")
    // loadRegister
    @PostMapping({"/loadRegister"})
    public void loadRegister(@Validated @RequestBody CompanyRegisterBo registerBo) {
        loadRegister.load(registerBo);
    }

    @Operation(summary = "清空注册时的公司数据")
    @PostMapping({"/cleanRegisterData"})
    public void cleanRegisterData(@Validated @RequestBody CompanyRegisterBo registerBo) {
        Integer ecCompanyId = registerBo.getEcCompanyId();
        loadRegister.clean(ecCompanyId);
    }

    @PostMapping({"/loadZeyang"})
    public void loadZeyang() {
        loadRegister.loadZeyang();
    }
}
