package org.jeecg.modules.cable.mapper.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.user.EcCustomer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcCustomerMapper extends BaseMapper<EcCustomer> {
    List<EcCustomer> getList(EcCustomer record);

    long getCount(EcCustomer record);

    EcCustomer getObject(EcCustomer record);
}
