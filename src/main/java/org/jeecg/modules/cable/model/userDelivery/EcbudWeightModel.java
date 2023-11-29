package org.jeecg.modules.cable.model.userDelivery;

import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;
import org.jeecg.modules.cable.controller.userDelivery.weight.bo.EcbudModelBo;
import org.jeecg.modules.cable.controller.userDelivery.weight.bo.EcbudWeightInsertBo;
import org.jeecg.modules.cable.entity.userDelivery.EcbudWeight;
import org.jeecg.modules.cable.service.userDelivery.EcbudWeightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EcbudWeightModel {
    @Resource
    EcbudWeightService ecbudWeightService;


    @Transactional(rollbackFor = Exception.class)
    public String deal(EcbudWeightInsertBo bo) {
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
        if (endWeight1 < startWeight1 || endWeight2 < startWeight2 || endWeight3 < startWeight3 || endWeight4 < startWeight4 || endWeight5 < startWeight5) {
            throw new RuntimeException("区间重量开始值不得大于结束值！");
        }
        String msg;
        EcbudWeight record = new EcbudWeight();
        record.setEcbudId(ecbudId);
        EcbudWeight ecbudWeight = ecbudWeightService.getObject(record);
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
        if (ecbudWeight == null) {
            ecbudWeightService.insert(record);
            msg = "正常插入数据";
        } else {
            ecbudWeightService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }


    public EcbudWeight getObject(EcbudModelBo bo) {
        EcbudWeight record = new EcbudWeight();
        Integer ecbudId = bo.getEcbudId();
        record.setEcbudId(ecbudId);
        return ecbudWeightService.getObject(record);
    }

    /***===物流模型===***/

    @Transactional(rollbackFor = Exception.class)
    public void deal(EcbudWeight record) {
        EcbudWeight recordEcbudWeight = new EcbudWeight();
        recordEcbudWeight.setEcbudId(record.getEcbudId());
        EcbudWeight ecbudWeight = ecbudWeightService.getObject(record);
        if (ecbudWeight != null) {
            ecbudWeightService.update(record);
        } else {
            ecbudWeightService.insert(record);
        }
    }


    public void deletePassEcbudId(Integer ecbudId) {
        EcbudWeight record = new EcbudWeight();
        record.setEcbudId(ecbudId);
        ecbudWeightService.delete(record);
    }
}
