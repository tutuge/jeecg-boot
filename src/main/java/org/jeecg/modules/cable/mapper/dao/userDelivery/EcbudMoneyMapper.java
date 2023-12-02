package org.jeecg.modules.cable.mapper.dao.userDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.cable.entity.userDelivery.EcbudMoney;

import java.util.List;

@Mapper
public interface EcbudMoneyMapper extends BaseMapper<EcbudMoney> {
    List<EcbudMoney> getList(EcbudMoney record);

    long getCount(EcbudMoney record);

    EcbudMoney getObject(EcbudMoney record);

    Integer updateRecord(EcbudMoney record);

    Integer deleteRecord(EcbudMoney record);


    List<EcbudMoney> getListGreaterThanSortId(EcbudMoney record);

    //getObjectPassProvinceName
    EcbudMoney getObjectPassProvinceName(EcbudMoney record);


    EcbudMoney getLatestObject(EcbudMoney record);

    void reduceSort(@Param("ecbudId") Integer ecbudId, @Param("sortId") Integer sortId);
}
