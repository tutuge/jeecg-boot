package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPcompany;

import java.util.List;

@Mapper
public interface EcbuPcompanyDao {
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

    //getLatestObject
    EcbuPcompany getLatestObject(EcbuPcompany record);
}
