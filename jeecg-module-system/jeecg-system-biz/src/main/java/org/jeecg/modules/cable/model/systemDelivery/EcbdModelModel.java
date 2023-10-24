package org.jeecg.modules.cable.model.systemDelivery;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemDelivery.model.bo.EcbdModelDealBo;
import org.jeecg.modules.cable.controller.systemDelivery.model.bo.ModelBaseBo;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdModel;
import org.jeecg.modules.cable.service.systemDelivery.EcbdModelService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EcbdModelModel {
    @Resource
    EcbdModelService ecbdModelService;

    //deal
    public String deal(EcbdModelDealBo bo) {

        int ecbdId = bo.getEcbdId();
        int startWeight1 = bo.getStartWeight1();
        int endWeight1 = bo.getEndWeight1();
        int startWeight2 = bo.getStartWeight2();
        int endWeight2 = bo.getEndWeight2();
        int startWeight3 = bo.getStartWeight3();
        int endWeight3 = bo.getEndWeight3();
        int startWeight4 = bo.getStartWeight4();
        int endWeight4 = bo.getEndWeight4();
        int startWeight5 = bo.getStartWeight5();
        int endWeight5 = bo.getEndWeight5();
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
        EcbdModel recordEcbdModel = new EcbdModel();
        recordEcbdModel.setEcbdId(ecbdId);
        EcbdModel ecbdModel = ecbdModelService.getObject(record);

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

    //getObject
    public EcbdModel getObject(ModelBaseBo bo) {
        int ecbdId = bo.getEcbdId();
        EcbdModel record = new EcbdModel();
        record.setEcbdId(ecbdId);
        return ecbdModelService.getObject(record);
    }

    /***===数据模型===***/
    //getObjectPassEcbdId
    public EcbdModel getObjectPassEcbdId(int ecbdId) {
        EcbdModel record = new EcbdModel();
        record.setEcbdId(ecbdId);
        return ecbdModelService.getObject(record);
    }
}
