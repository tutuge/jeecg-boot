package org.jeecg.modules.cable.mapper.dao.userQuality;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userQuality.EcquLevel;

import java.util.List;

@Mapper
public interface EcquLevelMapper extends BaseMapper<EcquLevel> {
    List<EcquLevel> getList(EcquLevel record);

    long getCount(EcquLevel record);

    EcquLevel getObject(EcquLevel record);

    Integer update(EcquLevel record);
}
