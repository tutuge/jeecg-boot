package org.jeecg.modules.cable.mapper.dao.userOffer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.cable.entity.userOffer.EcuOffer;

import java.util.List;
import java.util.Set;

@Mapper
public interface EcuOfferMapper extends BaseMapper<EcuOffer> {
    List<EcuOffer> getList(EcuOffer record);

    Long getCount(EcuOffer record);

    EcuOffer getObject(EcuOffer record);

    Integer deleteRecord(EcuOffer record);

    Integer updateRecord(EcuOffer record);

    void reduceSort(@Param("ecqulId") Integer ecqulId, @Param("sortId") Integer sortId);

    @Select("select *  from ecu_offer where ecqul_id = #{ecqulId} and area_str = #{areaStr} order by sort_id desc limit 1")
    EcuOffer getByLevelIdAndArea(@Param("ecqulId") Integer ecqulId, @Param("areaStr") String areaStr);

    Set<Integer> getMaterialIdList();
}
