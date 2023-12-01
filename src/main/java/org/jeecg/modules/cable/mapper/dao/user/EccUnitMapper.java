package org.jeecg.modules.cable.mapper.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.user.EccUnit;

import java.util.List;

@Mapper
public interface EccUnitMapper extends BaseMapper<EccUnit> {
    EccUnit getObject(EccUnit record);

    EccUnit selectByModelId(Integer ecusmId);

    List<EccUnit> getList(EccUnit record);

    long getCount(EccUnit record);
}
