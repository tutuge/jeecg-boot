package org.jeecg.modules.cable.service.user.impl;

import jakarta.annotation.Resource;
import org.jeecg.modules.cable.entity.user.EcuDesc;
import org.jeecg.modules.cable.mapper.dao.user.EcuDescMapper;
import org.jeecg.modules.cable.service.user.EcuDescService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcuDescServiceImpl implements EcuDescService {
    @Resource
    EcuDescMapper ecuDescMapper;

    @Override
    public EcuDesc getObject(EcuDesc record) {
        return ecuDescMapper.getObject(record);
    }

    @Override
    public List<EcuDesc> getList(EcuDesc record) {
        return ecuDescMapper.getList(record);
    }

    @Override
    public long getCount(EcuDesc record) {
        return ecuDescMapper.getCount(record);
    }

    @Override
    public Integer insert(EcuDesc record) {
        return ecuDescMapper.insert(record);
    }

    @Override
    public Integer update(EcuDesc record) {
        return ecuDescMapper.updateById(record);
    }

    @Override
    public Integer delete(EcuDesc record) {
        return ecuDescMapper.deleteRecord(record);
    }

    @Override
    public EcuDesc getObjectByModelAndAreaStr(Integer ecCompanyId, String areaStr, Integer ecusmId) {
        //切分规格，获得芯数与平米数 3*16+1*10
        String[] areaArr = areaStr.split("\\+");
        String[] fireArr = areaArr[0].split("\\*");
        //芯数
        String coreStr = "";
        //平方数
        String area = fireArr[1];
        if (areaArr.length == 2) {
            coreStr = fireArr[0] + "+" + areaArr[1];
        } else {
            coreStr = fireArr[0];
        }
        EcuDesc record = new EcuDesc();
        record.setEcCompanyId(ecCompanyId);
        record.setAreaStr(area);
        record.setCoreStr(coreStr);
        record.setEcusmId(String.valueOf(ecusmId));
        return getObject(record);
    }
}
