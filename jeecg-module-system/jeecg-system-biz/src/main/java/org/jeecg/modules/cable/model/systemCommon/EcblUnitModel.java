package org.jeecg.modules.cable.model.systemCommon;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitBaseBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitDealBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitListBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.bo.EcblUnitSortBo;
import org.jeecg.modules.cable.controller.systemCommon.unit.vo.EcblUnitListVo;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;
import org.jeecg.modules.cable.service.systemCommon.EcblUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class EcblUnitModel {
    @Resource
    EcblUnitService ecblUnitService;

    public String deal(EcblUnitDealBo bo) {

        Integer ecbluId = bo.getEcbluId();
        String lengthName = bo.getLengthName();
        Integer meterNumber = bo.getMeterNumber();
        String description = bo.getDescription();

        EcblUnit record = new EcblUnit();
        record.setEcbluId(ecbluId);
        record.setLengthName(lengthName);
        EcblUnit ecbulUnit = ecblUnitService.getObject(record);
        String msg;
        if (ecbulUnit != null) {
            throw new RuntimeException("名称已占用");
        } else {
            if (ecbluId == 0) {//插入
                Integer sortId = 1;
                record = new EcblUnit();
                ecbulUnit = ecblUnitService.getObject(record);
                if (ecbulUnit != null) {
                    sortId = ecbulUnit.getSortId() + 1;
                }
                record = new EcblUnit();
                record.setStartType(true);
                record.setSortId(sortId);
                record.setLengthName(lengthName);
                record.setMeterNumber(meterNumber);
                record.setDescription(description);
                ecblUnitService.insert(record);

                msg = "正常插入数据";
            } else {//更新
                record = new EcblUnit();
                record.setEcbluId(ecbluId);
                record.setLengthName(lengthName);
                record.setMeterNumber(meterNumber);
                record.setDescription(description);
                ecblUnitService.update(record);

                msg = "正常更新数据";
            }
        }
        return msg;
    }

    //getList
    public EcblUnitListVo getList(EcblUnitListBo bo) {
        EcblUnit record = new EcblUnit();
        record.setStartType(bo.getStartType());
        List<EcblUnit> list = ecblUnitService.getList(record);
        long count = ecblUnitService.getCount(record);
        return new EcblUnitListVo(list, count);
    }

    //getObject
    public EcblUnit getObject(EcblUnitBaseBo bo) {
        Integer ecbluId = bo.getEcbluId();
        EcblUnit record = new EcblUnit();
        record.setEcbluId(ecbluId);
        return ecblUnitService.getObject(record);
    }

    //sort
    public void sort(List<EcblUnitSortBo> bos) {
        for (EcblUnitSortBo bo : bos) {
            Integer ecbluId = bo.getEcbluId();
            Integer sortId = bo.getSortId();
            EcblUnit record = new EcblUnit();
            record.setEcbluId(ecbluId);
            record.setSortId(sortId);
            ecblUnitService.update(record);
        }
    }

    //start
    public String start(EcblUnitBaseBo bo) {
        Integer ecbluId = bo.getEcbluId();
        EcblUnit record = new EcblUnit();
        record.setEcbluId(ecbluId);
        EcblUnit ecblUnit = ecblUnitService.getObject(record);
        boolean startType = ecblUnit.getStartType();
        String msg;
        if (!startType) {
            startType = true;
            msg = "数据启用成功";
        } else {
            startType = false;
            msg = "数据禁用成功";
        }
        record = new EcblUnit();
        record.setEcbluId(ecblUnit.getEcbluId());
        record.setStartType(startType);
        ecblUnitService.update(record);

        return msg;
    }

    //delete
    @Transactional(rollbackFor = Exception.class)
    public void delete(EcblUnitBaseBo bo) {

        Integer ecbluId = bo.getEcbluId();
        EcblUnit record = new EcblUnit();
        record.setEcbluId(ecbluId);
        EcblUnit ecblUnit = ecblUnitService.getObject(record);
        Integer sortId = ecblUnit.getSortId();
        record = new EcblUnit();
        record.setSortId(sortId);
        List<EcblUnit> list = ecblUnitService.getList(record);
        Integer ecblu_id;
        for (EcblUnit ecbl_unit : list) {
            ecblu_id = ecbl_unit.getEcbluId();
            sortId = ecbl_unit.getSortId() - 1;
            record.setEcbluId(ecblu_id);
            record.setSortId(sortId);
            ecblUnitService.update(record);
        }
        record = new EcblUnit();
        record.setEcbluId(ecbluId);
        ecblUnitService.delete(record);
    }

    /***===数据模型===***/
    //getListStart
    public List<EcblUnit> getListStart() {
        EcblUnit record = new EcblUnit();
        record.setStartType(true);
        return ecblUnitService.getList(record);
    }
}
