package org.jeecg.modules.cable.mapper.dao.systemOffer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.systemOffer.EcOffer;

import java.util.List;

@Mapper
public interface EcOfferMapper extends BaseMapper<EcOffer> {

    EcOffer getObject(EcOffer record);

    List<EcOffer> getList(EcOffer record);


    void reduceSort(@Param("ecqlId") Integer ecqlId,@Param("sortId")  Integer sortId);
}
