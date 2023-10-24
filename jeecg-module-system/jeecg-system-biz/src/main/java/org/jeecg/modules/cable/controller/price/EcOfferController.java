package org.jeecg.modules.cable.controller.price;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.model.systemOffer.EcOfferModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "报价导入")
@RestController
@Slf4j
public class EcOfferController {
    @Resource
    EcOfferModel ecOfferModel;

    //importData
    @PostMapping({"/ecableAdminPc/ecOffer/importData"})
    public Map<String, Object> importData(HttpServletRequest request) throws Exception {
        return ecOfferModel.importDeal(request);
    }

    //loadSteelbandThickness 加载钢带厚度
    @PostMapping({"/ecableAdminPc/ecOffer/loadSteelbandThicknessAndSheathThickness"})
    public void loadSteelBandThicknessAndSheathThickness() {
        ecOfferModel.loadSteelbandThicknessAndSheathThickness();
    }
}
