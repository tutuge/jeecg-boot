package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userCommon.EctImages;

@Mapper
public interface EctImagesMapper extends BaseMapper<EctImages> {

    EctImages getObject(EctImages record);

    Integer delete(EctImages record);
}
