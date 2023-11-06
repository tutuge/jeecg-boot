package org.jeecg.modules.cable.mapper.dao.userEcable;

import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbuSheathMapper {
    EcbuSheath getObject(EcbuSheath record);

    Integer insert(EcbuSheath ecbuSheath);

    Integer update(EcbuSheath record);

    List<EcbuSheath> getList(EcbuSheath record);

    Integer delete(EcbuSheath record);
}
