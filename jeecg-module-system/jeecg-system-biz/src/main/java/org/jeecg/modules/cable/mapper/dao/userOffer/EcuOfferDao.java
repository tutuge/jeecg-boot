package org.jeecg.modules.cable.mapper.dao.userOffer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;

import java.util.List;

@Mapper
public interface EcuOfferDao extends BaseMapper<EcuOffer> {
    List<EcuOffer> getList(EcuOffer record);

    long getCount(EcuOffer record);

    EcuOffer getObject(EcuOffer record);

    Integer delete(EcuOffer record);

    Integer update(EcuOffer record);
}
