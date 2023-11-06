package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcCompany;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcCompanyMapper {
    EcCompany getObject(EcCompany record);//根据ID查找

    Integer insert(EcCompany record);
}
