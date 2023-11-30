package org.jeecg.modules.cable.mapper.dao.systemDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdPrice;

import java.util.List;

@Mapper
public interface EcbdPriceMapper extends BaseMapper<EcbdPrice> {
    List<EcbdPrice> getList(EcbdPrice record);

    long getCount(EcbdPrice record);

    EcbdPrice getObject(EcbdPrice record);

    EcbdPrice getObjectPassProvinceName(EcbdPrice price);

    EcbdPrice getLatestObject(EcbdPrice record);
}
