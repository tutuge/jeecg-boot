package org.jeecg.modules.cable.mapper.dao.userDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.userDelivery.EcbudDelivery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbudDeliveryMapper extends BaseMapper<EcbudDelivery> {

    EcbudDelivery getObject(EcbudDelivery record);
}
