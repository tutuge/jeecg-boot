package org.jeecg.modules.cable.model.userOffer;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.userOffer.core.bo.CoreBo;
import org.jeecg.modules.cable.entity.userOffer.EcuoCore;
import org.jeecg.modules.cable.service.userOffer.EcuoCoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcuoCoreModel {
    @Resource
    EcuoCoreService ecuoCoreService;


    public List<EcuoCore> getList(CoreBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcuoCore record = new EcuoCore();
        record.setEcqulId(ecqulId);
        return ecuoCoreService.getList(record);
    }

    

    @Transactional(rollbackFor = Exception.class)
    public void deal(Integer ecqulId, String areaStr) {
        String coreStr = "";
        String[] areaArr = areaStr.split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        coreStr = fireArr[0];
        if (areaArr.length == 2) {
            String[] zeroArr = areaArr[1].split("\\*");
            coreStr += "+" + zeroArr[0];
        }
        EcuoCore ecuoCore = getObjectPassEcqulIdAndAreaStr(ecqulId, coreStr);
        EcuoCore record = new EcuoCore();
        record.setEcqulId(ecqulId);
        record.setCoreStr(coreStr);
        if (ecuoCore == null) {
            record = new EcuoCore();
            record.setEcqulId(ecqulId);
            ecuoCore = getObjectPassEcqulId(ecqulId);
            int sortId = 1;
            if (ecuoCore != null) {
                sortId = ecuoCore.getSortId() + 1;
            }
            record.setSortId(sortId);
            record.setCoreStr(coreStr);
            ecuoCoreService.insert(record);
        } else {
            record.setEcuocId(ecuoCore.getEcuocId());
            ecuoCoreService.update(record);
        }
    }

    //getObjectPassEcuoIdAndAreaStr
    public EcuoCore getObjectPassEcqulIdAndAreaStr(Integer ecqulId, String coreStr) {
        EcuoCore record = new EcuoCore();
        record.setEcqulId(ecqulId);
        record.setCoreStr(coreStr);
        return ecuoCoreService.getObject(record);
    }

    //getObjectPassEcqulId
    public EcuoCore getObjectPassEcqulId(Integer ecqulId) {
        EcuoCore record = new EcuoCore();
        record.setEcqulId(ecqulId);
        return ecuoCoreService.getObject(record);
    }
}
