package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuSheathDao {
    EcbuSheath getObject(EcbuSheath record);

    int insert(EcbuSheath ecbuSheath);

    int update(EcbuSheath record);

    List<EcbuSheath> getList(EcbuSheath record);

    int delete(EcbuSheath record);
}
