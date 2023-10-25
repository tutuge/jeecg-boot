package org.jeecg.modules.cable.mapper.dao.certs;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.certs.EcuqCerts;

import java.util.List;

@Mapper
public interface EcuqCertsDao {
    EcuqCerts getObject(EcuqCerts record);//根据ID查找

    List<EcuqCerts> getList(EcuqCerts record);//获取用户列表

    long getCount(EcuqCerts record);

    int insert(EcuqCerts record);

    int update(EcuqCerts record);

    int delete(EcuqCerts record);

}
