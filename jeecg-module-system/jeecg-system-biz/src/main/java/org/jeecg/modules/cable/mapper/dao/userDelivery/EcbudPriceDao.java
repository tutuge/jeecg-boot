package org.jeecg.modules.cable.mapper.dao.userDelivery;

import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbudPriceDao {
    List<EcbudPrice> getList(EcbudPrice record);

    long getCount(EcbudPrice record);

    EcbudPrice getObject(EcbudPrice record);

    Integer insert(EcbudPrice record);

    Integer update(EcbudPrice record);

    Integer delete(EcbudPrice record);

    //getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbudPrice> getListGreaterThanSortId(EcbudPrice record);

    //getObjectPassProvinceName
    EcbudPrice getObjectPassProvinceName(EcbudPrice record);

    //getLatestObject
    EcbudPrice getLatestObject(EcbudPrice record);
}
