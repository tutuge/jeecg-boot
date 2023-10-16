package org.jeecg.modules.cable.service.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcduCompany;

import java.util.List;

public interface EcduCompanyService {
    List<EcduCompany> getList(EcduCompany record);

    long getCount(EcduCompany record);

    EcduCompany getObject(EcduCompany record);

    int insert(EcduCompany record);

    int update(EcduCompany record);

    int delete(EcduCompany record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcduCompany> getListGreaterThanSortId(EcduCompany record);

    //getObjectPassAbbreviationAndFullName
    EcduCompany getObjectPassAbbreviationAndFullName(EcduCompany record);

    //getLatestObject
    EcduCompany getLatestObject(EcduCompany record);
}
