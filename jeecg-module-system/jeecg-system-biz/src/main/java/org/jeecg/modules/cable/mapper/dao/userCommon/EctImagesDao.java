package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EctImagesDao {
    Integer insert(EctImages record);

    EctImages getObject(EctImages record);

    Integer delete(EctImages record);
}
