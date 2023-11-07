package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuShield;

import java.util.List;

@Mapper
public interface EcbuShieldMapper extends BaseMapper<EcbuShield> {
    EcbuShield getObject(EcbuShield record);

    Integer update(EcbuShield record);

    List<EcbuShield> getList(EcbuShield record);

    Integer delete(EcbuShield record);
}
