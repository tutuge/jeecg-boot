package org.jeecg.modules.cable.mapper.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.user.EcuData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuDataMapper extends BaseMapper<EcuData> {
    EcuData getObject(EcuData record);

    List<EcuData> getList(EcuData record);

    long getCount(EcuData record);

    Integer update(EcuData record);

}
