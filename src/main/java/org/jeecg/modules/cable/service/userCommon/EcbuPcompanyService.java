package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.CompanyListVo;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;

import java.util.List;

public interface EcbuPcompanyService {
    List<EcbuPCompanyVo> getList(EcbuPcompany record);

    long getCount(EcbuPcompany record);

    EcbuPCompanyVo getObject(EcbuPcompany record);

    Integer insert(EcbuPcompany record);

    Integer update(EcbuPcompany record);

    Integer delete(EcbuPcompany record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuPcompany> getListGreaterThanSortId(EcbuPcompany record);

    //getObjectPassPcName
    EcbuPcompany getObjectPassPcName(EcbuPcompany record);


    EcbuPcompany getLatestObject(EcbuPcompany record);
}
