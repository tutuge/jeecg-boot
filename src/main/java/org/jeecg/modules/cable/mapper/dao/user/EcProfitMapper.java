package org.jeecg.modules.cable.mapper.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.user.EcProfit;

import java.util.List;

@Mapper
public interface EcProfitMapper extends BaseMapper<EcProfit> {
    EcProfit getObject(EcProfit record);

    List<EcProfit> getList(EcProfit record);

    long getCount(EcProfit record);

}
