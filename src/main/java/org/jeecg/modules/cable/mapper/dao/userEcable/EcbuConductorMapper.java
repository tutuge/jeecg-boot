package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuConductor;

import java.util.List;

@Mapper
public interface EcbuConductorMapper extends BaseMapper<EcbuConductor> {
    EcbuConductor getObject(EcbuConductor record);

    Integer update(EcbuConductor record);

    List<EcbuConductor> getList(EcbuConductor record);

    Integer delete(EcbuConductor record);
}
