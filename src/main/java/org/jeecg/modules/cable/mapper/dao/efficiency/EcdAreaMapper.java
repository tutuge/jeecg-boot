package org.jeecg.modules.cable.mapper.dao.efficiency;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.efficiency.EcdArea;

import java.util.List;

@Mapper
public interface EcdAreaMapper extends BaseMapper<EcdArea> {
    List<EcdArea> getList(EcdArea record);

    long getCount(EcdArea record);

    Integer updateRecord(EcdArea record);

    EcdArea getObject(EcdArea record);
}
