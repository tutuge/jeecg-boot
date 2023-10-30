package org.jeecg.modules.cable.model.userDelivery;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.userDelivery.model.bo.EcbudModelBo;
import org.jeecg.modules.cable.controller.userDelivery.model.bo.EcbudModelInsertBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.jeecg.modules.cable.service.userDelivery.EcbudModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EcbudModelModel {
    @Resource
    EcbudModelService ecbudModelService;

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbudModelInsertBo bo) {

        Integer ecbudId = bo.getEcbudId();
        Integer startWeight1 = ObjectUtil.isNotNull(bo.getStartWeight1()) ? bo.getStartWeight1() : 0;
        Integer endWeight1 = ObjectUtil.isNotNull(bo.getEndWeight1()) ? bo.getEndWeight1() : 0;
        Integer startWeight2 = ObjectUtil.isNotNull(bo.getStartWeight2()) ? bo.getStartWeight2() : 0;
        Integer endWeight2 = ObjectUtil.isNotNull(bo.getEndWeight2()) ? bo.getEndWeight2() : 0;
        Integer startWeight3 = ObjectUtil.isNotNull(bo.getStartWeight3()) ? bo.getStartWeight3() : 0;
        Integer endWeight3 = ObjectUtil.isNotNull(bo.getEndWeight3()) ? bo.getEndWeight3() : 0;
        Integer startWeight4 = ObjectUtil.isNotNull(bo.getStartWeight4()) ? bo.getStartWeight4() : 0;
        Integer endWeight4 = ObjectUtil.isNotNull(bo.getEndWeight4()) ? bo.getEndWeight4() : 0;
        Integer startWeight5 = ObjectUtil.isNotNull(bo.getStartWeight5()) ? bo.getStartWeight5() : 0;
        Integer endWeight5 = ObjectUtil.isNotNull(bo.getEndWeight5()) ? bo.getEndWeight5() : 0;
        String msg;
        EcbudModel record = new EcbudModel();
        record.setEcbudId(ecbudId);
        EcbudModel ecbudModel = ecbudModelService.getObject(record);
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
        if (ecbudModel == null) {
            ecbudModelService.insert(record);
            msg = "正常插入数据";
        } else {
            ecbudModelService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    // getObject
    public EcbudModel getObject(EcbudModelBo bo) {
        EcbudModel record = new EcbudModel();
        Integer ecbudId = bo.getEcbudId();
        record.setEcbudId(ecbudId);
        return ecbudModelService.getObject(record);
    }

    /***===物流模型===***/
    // deal
    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbudModel record) {
        EcbudModel recordEcbudModel = new EcbudModel();
        recordEcbudModel.setEcbudId(record.getEcbudId());
        EcbudModel ecbudModel = ecbudModelService.getObject(record);
        if (ecbudModel != null) {
            ecbudModelService.update(record);
        } else {
            ecbudModelService.insert(record);
        }
    }

    // delete
    public void deletePassEcbudId(Integer ecbudId) {
        EcbudModel record = new EcbudModel();
        record.setEcbudId(ecbudId);
        ecbudModelService.delete(record);
    }
}
