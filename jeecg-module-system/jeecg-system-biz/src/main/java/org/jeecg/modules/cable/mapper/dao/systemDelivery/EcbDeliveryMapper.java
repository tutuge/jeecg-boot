package org.jeecg.modules.cable.mapper.dao.systemDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.systemDelivery.EcbDelivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbDeliveryMapper extends BaseMapper<EcbDelivery> {

    List<EcbDelivery> getList(EcbDelivery record);

    //getCount
    long getCount(EcbDelivery record);

    //getObject
    EcbDelivery getObject(EcbDelivery record);

}
