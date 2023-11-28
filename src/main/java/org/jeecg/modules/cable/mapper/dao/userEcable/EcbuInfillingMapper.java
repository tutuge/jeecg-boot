package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuInfilling;

import java.util.List;

@Mapper
public interface EcbuInfillingMapper extends BaseMapper<EcbuInfilling> {
    EcbuInfilling getObject(EcbuInfilling record);

    List<EcbuInfilling> getList(EcbuInfilling record);

    Integer deleteByEcCompanyId(EcbuInfilling record);
}
