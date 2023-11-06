package org.jeecg.modules.cable.mapper.dao.systemCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemCommon.EcblUnit;

import java.util.List;

@Mapper
public interface EcblUnitMapper extends BaseMapper<EcblUnit> {
    List<EcblUnit> getList(EcblUnit record);

    long getCount(EcblUnit record);

    EcblUnit getObject(EcblUnit record);

}
