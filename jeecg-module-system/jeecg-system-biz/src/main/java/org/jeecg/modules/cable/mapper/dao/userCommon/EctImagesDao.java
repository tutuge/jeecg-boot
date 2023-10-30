package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.userCommon.EctImages;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EctImagesDao extends BaseMapper<EctImages> {

    EctImages getObject(EctImages record);

    Integer delete(EctImages record);
}
