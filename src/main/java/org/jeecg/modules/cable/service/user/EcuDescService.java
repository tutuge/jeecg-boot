package org.jeecg.modules.cable.service.user;

import org.jeecg.modules.cable.entity.user.EcuDesc;

import java.util.List;

public interface EcuDescService {
    EcuDesc getObject(EcuDesc record);

    List<EcuDesc> getList(EcuDesc record);

    long getCount(EcuDesc record);

    Integer insert(EcuDesc record);

    Integer update(EcuDesc record);

    Integer delete(EcuDesc record);

    /**
     * 根据规格和型号ID查询备注信息
     *
     * @param ecCompanyId 公司ID
     * @param areaStr 规格字符串 3*16+1*10
     * @param ecusmId 型号ID
     * @return
     */
    EcuDesc getObjectByModelAndAreaStr(Integer ecCompanyId, String areaStr, Integer ecusmId);
}
