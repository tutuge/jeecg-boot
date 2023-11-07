package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuMicaTape;

import java.util.List;

@Mapper
public interface EcbuMicaTapeMapper extends BaseMapper<EcbuMicaTape> {
    EcbuMicaTape getObject(EcbuMicaTape record);
    Integer update(EcbuMicaTape record);

    List<EcbuMicaTape> getList(EcbuMicaTape record);

    Integer delete(EcbuMicaTape record);
}
