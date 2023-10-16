package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;

import java.util.List;

public interface EcbuPcompanyService {
    List<EcbuPcompany> getList(EcbuPcompany record);

    long getCount(EcbuPcompany record);

    EcbuPcompany getObject(EcbuPcompany record);

    int insert(EcbuPcompany record);

    int update(EcbuPcompany record);

    int delete(EcbuPcompany record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbuPcompany> getListGreaterThanSortId(EcbuPcompany record);

    //getObjectPassPcName
    EcbuPcompany getObjectPassPcName(EcbuPcompany record);

    //getLatestObject
    EcbuPcompany getLatestObject(EcbuPcompany record);
}
