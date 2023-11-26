package org.jeecg.modules.cable.mapper.dao.efficiency;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.efficiency.EcdCollect;

import java.util.List;

@Mapper
public interface EcdCollectMapper extends BaseMapper<EcdCollect> {
    List<EcdCollect> getList(EcdCollect record);

    long getCount(EcdCollect record);

    Integer update(EcdCollect record);

    EcdCollect getObject(EcdCollect record);
}
