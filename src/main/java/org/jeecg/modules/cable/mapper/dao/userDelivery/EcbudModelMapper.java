package org.jeecg.modules.cable.mapper.dao.userDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcbudModelMapper extends BaseMapper<EcbudModel> {

    Integer update(EcbudModel record);

    EcbudModel getObject(EcbudModel record);

    Integer delete(EcbudModel record);

}
