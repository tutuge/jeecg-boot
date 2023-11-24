package org.jeecg.modules.cable.mapper.dao.systemOffer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;

import java.util.List;

@Mapper
public interface EcOfferMapper extends BaseMapper<EcOffer> {

    EcOffer getObject(EcOffer record);

    void update(EcOffer record);

    List<EcOffer> getList(EcOffer record);
}
