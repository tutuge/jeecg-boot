package org.jeecg.modules.cable.mapper.dao.user;

import org.jeecg.modules.cable.entity.user.EcuNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuNoticeDao {
    EcuNotice getObject(EcuNotice record);

    List<EcuNotice> getList(EcuNotice record);

    long getCount(EcuNotice record);

    Integer insert(EcuNotice record);

    Integer update(EcuNotice record);

    Integer delete(EcuNotice record);

}
