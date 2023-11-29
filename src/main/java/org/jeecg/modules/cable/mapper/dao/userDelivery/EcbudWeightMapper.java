package org.jeecg.modules.cable.mapper.dao.userDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userDelivery.EcbudWeight;

@Mapper
public interface EcbudWeightMapper extends BaseMapper<EcbudWeight> {

    /**
     * 根据快递id修改对应重量区间信息
     *
     * @param record
     * @return
     */
    Integer updateByEcbudId(EcbudWeight record);

    EcbudWeight getObject(EcbudWeight record);

    /**
     * 根据快递id删除对应重量区间信息
     *
     * @param record
     * @return
     */
    Integer deleteByEcbudId(EcbudWeight record);

}
