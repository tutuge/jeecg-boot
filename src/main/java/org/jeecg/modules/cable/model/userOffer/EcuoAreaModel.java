package org.jeecg.modules.cable.model.userOffer;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.userOffer.area.bo.AreaListBo;
import org.jeecg.modules.cable.entity.userOffer.EcuoArea;
import org.jeecg.modules.cable.service.userOffer.EcuoAreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcuoAreaModel {

    @Resource
    EcuoAreaService ecuoAreaService;


    public List<EcuoArea> getList(AreaListBo bo) {
        Integer ecqulId = bo.getEcqulId();
        EcuoArea record = new EcuoArea();
        record.setEcqulId(ecqulId);
        return ecuoAreaService.getList(record);
    }

    /***===数据模型===***/
    // load
    public void load(Integer ecqulId, String areaStr) {
        String[] areaArr = areaStr.split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        areaStr = fireArr[1];
        deal(ecqulId, areaStr);
        if (areaArr.length == 2) {
            String[] zeroArr = areaArr[1].split("\\*");
            areaStr = zeroArr[1];
            deal(ecqulId, areaStr);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void deal(Integer ecqulId, String areaStr) {
        EcuoArea record = new EcuoArea();
        record.setEcqulId(ecqulId);
        record.setAreaStr(areaStr);
        //先查看此平方数是否存在
        EcuoArea ecuoArea = getObjectPassEcqulIdAndAreaStr(ecqulId, areaStr);
        if (ecuoArea == null) {
            record = new EcuoArea();
            record.setEcqulId(ecqulId);
            ecuoArea = getObjectPassEcqulId(ecqulId);
            int sortId = 1;
            if (ecuoArea != null) {
                sortId = ecuoArea.getSortId() + 1;
            }
            record.setSortId(sortId);
            record.setAreaStr(areaStr);
            ecuoAreaService.insert(record);
        }
    }

    // getObjectPassEcqulIdAndAreaStr
    public EcuoArea getObjectPassEcqulIdAndAreaStr(Integer ecqulId, String coreStr) {
        EcuoArea record = new EcuoArea();
        record.setEcqulId(ecqulId);
        record.setAreaStr(coreStr);
        return ecuoAreaService.getObject(record);
    }

    // getObjectPassEcqulId
    public EcuoArea getObjectPassEcqulId(Integer ecqulId) {
        EcuoArea record = new EcuoArea();
        record.setEcqulId(ecqulId);
        return ecuoAreaService.getObject(record);
    }
}
