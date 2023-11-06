package org.jeecg.modules.cable.mapper.dao.quality;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.quality.EcquLevel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcquLevelMapper extends BaseMapper<EcquLevel> {
    List<EcquLevel> getList(EcquLevel record);

    long getCount(EcquLevel record);

    EcquLevel getObject(EcquLevel record);

    Integer update(EcquLevel record);
}
