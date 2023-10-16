package org.jeecg.modules.cable.mapper.dao.userCommon;

import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EctImagesDao {
    int insert(EctImages record);

    EctImages getObject(EctImages record);

    int delete(EctImages record);
}
