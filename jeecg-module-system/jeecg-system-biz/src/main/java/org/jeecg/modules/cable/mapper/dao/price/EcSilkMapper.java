package org.jeecg.modules.cable.mapper.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcSilk;

import java.util.List;

@Mapper
public interface EcSilkMapper extends BaseMapper<EcSilk> {
    List<EcSilk> getList(EcSilk record);// 获取数据列表

    // getObject
    EcSilk getObject(EcSilk record);// 通过EcSilk获取EcSilk
}
