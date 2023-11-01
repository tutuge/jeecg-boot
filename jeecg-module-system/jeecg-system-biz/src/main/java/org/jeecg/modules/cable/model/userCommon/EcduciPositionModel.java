package org.jeecg.modules.cable.model.userCommon;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.userCommon.position.bo.EcduciPositionBo;
import org.jeecg.modules.cable.controller.userCommon.position.bo.PositionBo;
import org.jeecg.modules.cable.entity.userCommon.EcduciPosition;
import org.jeecg.modules.cable.service.userCommon.EcduciPositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class EcduciPositionModel {
    @Resource
    EcduciPositionService ecduciPositionService;

    // deal
    @Transactional(rollbackFor = Exception.class)
    public String deal(EcduciPositionBo bo) {
        Integer ecduciId = bo.getEcduciId();
        String pX = bo.getPX();
        String pY = bo.getPY();
        BigDecimal imagePercent1 = bo.getImagePercent();

        EcduciPosition ecduciPosition = getObjectPassEcduciId(ecduciId);
        EcduciPosition record = new EcduciPosition();
        record.setEcduciId(ecduciId);
        record.setPX(pX);
        record.setPY(pY);
        record.setEffectTime(System.currentTimeMillis());
        String msg = "";
        if (ecduciPosition == null) {// 新增
            BigDecimal imagePercent = BigDecimal.ONE;
            if (imagePercent1 != null) {
                imagePercent = imagePercent1;
            }
            record.setImagePercent(imagePercent);
            ecduciPositionService.insert(record);
            msg = "正常新增数据";
        } else {
            if (imagePercent1 != null) {
                BigDecimal imagePercent = imagePercent1;
                record.setImagePercent(imagePercent);
            }
            ecduciPositionService.update(record);
            msg = "正常更新数据";
        }
        return msg;
    }

    // getObject
    public EcduciPosition getObject(PositionBo bo) {
        Integer ecduciId = bo.getEcduciId();
        return getObjectPassEcduciId(ecduciId);
    }

    /***===数据模型===***/
    public EcduciPosition getObjectPassEcduciId(Integer ecduciId) {
        EcduciPosition record = new EcduciPosition();
        record.setEcduciId(ecduciId);
        return ecduciPositionService.getObject(record);
    }

    // deletePassEcduciId
    public void deletePassEcduciId(Integer ecduciId) {
        EcduciPosition record = new EcduciPosition();
        record.setEcduciId(ecduciId);
        ecduciPositionService.delete(record);
    }
}
