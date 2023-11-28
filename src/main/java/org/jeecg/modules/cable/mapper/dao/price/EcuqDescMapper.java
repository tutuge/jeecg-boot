package org.jeecg.modules.cable.mapper.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.cable.entity.price.EcuqDesc;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface EcuqDescMapper extends BaseMapper<EcuqDesc> {

    List<EcuqDesc> getList(EcuqDesc record);


    EcuqDesc getObject(EcuqDesc record);

    //deletePassEcuqiId
    void deletePassEcuqiId(Integer ecuqiId);

    Integer updateRecord(EcuqDesc record);

    @Update("update ecuq_desc set cunit_price = #{cunitPrice} where ecuq_id = #{ecuqId} and ecbuc_id = #{ecbucId}")
    void updateConductorPriceById(@Param("ecuqId") Integer ecuqId,
                                  @Param("ecbucId") Integer ecbucId,
                                  @Param("cunitPrice") BigDecimal cunitPrice);
}
