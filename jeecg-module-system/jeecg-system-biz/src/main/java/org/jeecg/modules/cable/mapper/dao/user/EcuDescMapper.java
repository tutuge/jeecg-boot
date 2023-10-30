package org.jeecg.modules.cable.mapper.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.user.EcuDesc;

import java.util.List;

@Mapper
public interface EcuDescMapper extends BaseMapper<EcuDesc> {
    EcuDesc getObject(EcuDesc record);

    List<EcuDesc> getList(EcuDesc record);

    long getCount(EcuDesc record);

    Integer delete(EcuDesc record);

}
