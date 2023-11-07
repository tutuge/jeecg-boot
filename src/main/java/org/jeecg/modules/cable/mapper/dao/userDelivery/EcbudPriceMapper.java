package org.jeecg.modules.cable.mapper.dao.userDelivery;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userDelivery.EcbudPrice;

import java.util.List;

@Mapper
public interface EcbudPriceMapper extends BaseMapper<EcbudPrice> {
    List<EcbudPrice> getList(EcbudPrice record);

    long getCount(EcbudPrice record);

    EcbudPrice getObject(EcbudPrice record);

    Integer delete(EcbudPrice record);

    // getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbudPrice> getListGreaterThanSortId(EcbudPrice record);

    // getObjectPassProvinceName
    EcbudPrice getObjectPassProvinceName(EcbudPrice record);


    EcbudPrice getLatestObject(EcbudPrice record);
}
