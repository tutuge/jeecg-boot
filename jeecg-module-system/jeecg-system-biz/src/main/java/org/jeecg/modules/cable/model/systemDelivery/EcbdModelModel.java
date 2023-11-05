package org.jeecg.modules.cable.model.systemDelivery;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemDelivery.model.bo.EcbdModelDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.model.bo.ModelBaseBo;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.jeecg.modules.cable.service.systemDelivery.EcbdModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EcbdModelModel {
    @Resource
    EcbdModelService ecbdModelService;


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbdModelDealBo bo) {

        Integer ecbdId = bo.getEcbdId();
        Integer startWeight1 = bo.getStartWeight1();
        Integer endWeight1 = bo.getEndWeight1();
        Integer startWeight2 = bo.getStartWeight2();
        Integer endWeight2 = bo.getEndWeight2();
        Integer startWeight3 = bo.getStartWeight3();
        Integer endWeight3 = bo.getEndWeight3();
        Integer startWeight4 = bo.getStartWeight4();
        Integer endWeight4 = bo.getEndWeight4();
        Integer startWeight5 = bo.getStartWeight5();
        Integer endWeight5 = bo.getEndWeight5();
        EcbdModel record = new EcbdModel();
        record.setEcbdId(ecbdId);
        record.setStartWeight1(startWeight1);
        record.setEndWeight1(endWeight1);
        record.setStartWeight2(startWeight2);
        record.setEndWeight2(endWeight2);
        record.setStartWeight3(startWeight3);
        record.setEndWeight3(endWeight3);
        record.setStartWeight4(startWeight4);
        record.setEndWeight4(endWeight4);
        record.setStartWeight5(startWeight5);
        record.setEndWeight5(endWeight5);

        EcbdModel ecbdModel = ecbdModelService.getObject(ecbdId);

        String msg;
        if (ecbdModel == null) {
            ecbdModelService.insert(record);
            msg = "正常插入数据";
        } else {
            ecbdModelService.update(record);
            msg = "正常更新数据";
        }

        return msg;
    }


    public EcbdModel getObject(ModelBaseBo bo) {
        Integer ecbdId = bo.getEcbdId();
        return ecbdModelService.getObject(ecbdId);
    }

    /***===数据模型===***/
    // getObjectPassEcbdId
    public EcbdModel getObjectPassEcbdId(Integer ecbdId) {
        return ecbdModelService.getObject(ecbdId);
    }
}
