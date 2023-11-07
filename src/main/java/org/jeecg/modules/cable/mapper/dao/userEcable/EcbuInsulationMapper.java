package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuInsulation;

import java.util.List;

@Mapper
public interface EcbuInsulationMapper extends BaseMapper<EcbuInsulation> {
    EcbuInsulation getObject(EcbuInsulation record);

    Integer update(EcbuInsulation record);

    List<EcbuInsulation> getList(EcbuInsulation record);

    Integer delete(EcbuInsulation record);
}
