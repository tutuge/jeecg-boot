package org.jeecg.modules.cable.mapper.dao.systemDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemDelivery.EcbdMoney;

import java.util.List;

@Mapper
public interface EcbdMoneyMapper extends BaseMapper<EcbdMoney> {
    List<EcbdMoney> getList(EcbdMoney record);

    long getCount(EcbdMoney record);

    EcbdMoney getObject(EcbdMoney record);

    EcbdMoney getObjectPassProvinceName(EcbdMoney money);

    EcbdMoney getLatestObject(EcbdMoney record);
}
