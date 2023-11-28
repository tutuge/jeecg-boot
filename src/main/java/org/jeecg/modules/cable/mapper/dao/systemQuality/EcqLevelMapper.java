package org.jeecg.modules.cable.mapper.dao.systemQuality;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemQuality.EcqLevel;

import java.util.List;

@Mapper
public interface EcqLevelMapper extends BaseMapper<EcqLevel> {


    List<EcqLevel> getList(EcqLevel record);

    long getCount(EcqLevel record);

    EcqLevel getObject(EcqLevel record);

    Integer updateRecord(EcqLevel record);
}
