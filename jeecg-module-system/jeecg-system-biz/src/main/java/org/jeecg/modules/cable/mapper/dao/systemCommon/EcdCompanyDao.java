package org.jeecg.modules.cable.mapper.dao.systemCommon;

import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcdCompanyDao {
    List<EcdCompany> getList(EcdCompany record);

    long getCount(EcdCompany record);

    EcdCompany getObject(EcdCompany record);

    int insert(EcdCompany record);

    int update(EcdCompany record);

    int delete(EcdCompany record);
}
