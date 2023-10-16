package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EcducImages;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcducImagesDao {
    List<EcducImages> getList(EcducImages record);

    EcducImages getObject(EcducImages record);

    int insert(EcducImages record);

    int delete(EcducImages record);
}
