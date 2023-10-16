package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuPcompanyDao {
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
