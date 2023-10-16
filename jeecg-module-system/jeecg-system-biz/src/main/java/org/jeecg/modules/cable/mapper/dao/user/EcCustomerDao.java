package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcCustomerDao {
    List<EcCustomer> getList(EcCustomer record);

    long getCount(EcCustomer record);

    EcCustomer getObject(EcCustomer record);

    int insert(EcCustomer record);

    int update(EcCustomer record);
}
