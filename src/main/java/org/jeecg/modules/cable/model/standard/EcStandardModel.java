//package org.jeecg.modules.cable.model.standard;
//
//import jakarta.annotation.Resource;
//import lombok.extern.slf4j.Slf4j;
//import org.jeecg.modules.cable.entity.standard.EcStandard;
//import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;
//import org.jeecg.modules.cable.service.standard.EcStandardService;
//import org.jeecg.modules.cable.service.userEcable.EcbuConductorService;
//import org.jeecg.modules.cable.tools.CommonFunction;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
//@Service
//@Slf4j
//public class EcStandardModel {
//    @Resource
//    EcStandardService ecStandardService;
//    @Resource
//    EcbuConductorService ecbuConductorService;// 用户导体
//
//    // getStandardPassEcbucIdAndArea 通过ecbucId和area求EcStandard
//    public EcStandard getStandardPassEcbucIdAndArea(Integer ecbucId, BigDecimal area) {
//        EcStandard ecStandard;
//        EcbuConductor recordEcbuConductor = new EcbuConductor();
//        recordEcbuConductor.setEcbucId(ecbucId);
//        EcbuConductor ecbuConductor = ecbuConductorService.getObject(recordEcbuConductor);
//        Integer ecbcId = ecbuConductor.getEcbcId();
//        int ecssId;
//        if (ecbcId == 3 || ecbcId == 8 || ecbcId == 9 || ecbcId == 6 || ecbcId == 7) {
//            ecssId = 2;
//        } else {
//            ecssId = 3;
//        }
//        EcStandard record = new EcStandard();
//        record.setArea(area);
//        record.setEcssId(ecssId);
//        log.info(CommonFunction.getGson().toJson(record));
//        ecStandard = ecStandardService.getObject(record);
//        log.info(CommonFunction.getGson().toJson(ecStandard));
//        return ecStandard;
//    }
//}
