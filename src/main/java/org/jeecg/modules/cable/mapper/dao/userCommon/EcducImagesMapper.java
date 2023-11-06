package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userCommon.EcducImages;

import java.util.List;

@Mapper
public interface EcducImagesMapper {
    List<EcducImages> getList(EcducImages record);

    EcducImages getObject(EcducImages record);

    Integer insert(EcducImages record);

    Integer delete(EcducImages record);
}
