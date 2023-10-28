package org.jeecg.modules.cable.controller.price;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.model.systemOffer.EcOfferModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "报价导入--系统接口", description = "报价导入--系统接口",
        extensions = {@Extension(properties = {@ExtensionProperty(name = "x-order", value = "411", parseValue = true)})})
@RestController
@Slf4j
@RequestMapping("/ecableAdminPc/ecOffer")
public class EcOfferController {
    @Resource
    EcOfferModel ecOfferModel;

    @Operation(summary = "导入")
    // importData
    @PostMapping({"/importData"})
    public Map<String, Object> importData(HttpServletRequest request) throws Exception {
        return ecOfferModel.importDeal(request);
    }

    @Operation(summary = "加载钢带厚度")
    // loadSteelbandThickness 加载钢带厚度
    @PostMapping({"/loadSteelbandThicknessAndSheathThickness"})
    public void loadSteelBandThicknessAndSheathThickness() {
        ecOfferModel.loadSteelbandThicknessAndSheathThickness();
    }
}
