package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcduCompanyMapper {
    List<EcduCompany> getList(EcduCompany record);

    long getCount(EcduCompany record);

    EcduCompany getObject(EcduCompany record);

    Integer insert(EcduCompany record);

    Integer update(EcduCompany record);

    Integer delete(EcduCompany record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcduCompany> getListGreaterThanSortId(EcduCompany record);

    //getObjectPassAbbreviationOrFullName
    EcduCompany getObjectPassAbbreviationOrFullName(EcduCompany record);

    //getLatestObject
    EcduCompany getLatestObject(EcduCompany record);
}
