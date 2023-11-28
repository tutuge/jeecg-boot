package org.jeecg.modules.cable.mapper.dao.userDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userDelivery.EcbudModel;

@Mapper
public interface EcbudModelMapper extends BaseMapper<EcbudModel> {

    /**
     * 根据快递id修改对应重量区间信息
     *
     * @param record
     * @return
     */
    Integer updateByEcbudId(EcbudModel record);

    EcbudModel getObject(EcbudModel record);

    /**
     * 根据快递id删除对应重量区间信息
     *
     * @param record
     * @return
     */
    Integer deleteByEcbudId(EcbudModel record);

}
