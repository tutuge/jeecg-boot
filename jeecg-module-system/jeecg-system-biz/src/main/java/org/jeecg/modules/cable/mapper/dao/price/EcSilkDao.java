package org.jeecg.modules.cable.mapper.dao.price;

import org.jeecg.modules.cable.entity.systemEcable.EcSilk;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcSilkDao {
    List<EcSilk> getList(EcSilk record);//获取数据列表

    //getObject
    EcSilk getObject(EcSilk record);//通过EcSilk获取EcSilk
}
