package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelband;

import java.util.List;

@Mapper
public interface EcbuSteelbandMapper extends BaseMapper<EcbuSteelband> {
    EcbuSteelband getObject(EcbuSteelband record);

    Integer update(EcbuSteelband record);

    List<EcbuSteelband> getList(EcbuSteelband record);

    Integer delete(EcbuSteelband record);
}
