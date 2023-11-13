package org.jeecg.modules.cable.mapper.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.price.EcuQuoted;

import java.util.List;

@Mapper
public interface EcuQuotedMapper extends BaseMapper<EcuQuoted> {

    List<EcuQuoted> getList(EcuQuoted record);


    Long getCount(EcuQuoted record);


    EcuQuoted getObject(EcuQuoted record);

    EcuQuoted getLatestObject(EcuQuoted record);


    Integer deleteByPrimaryKey(Integer ecuqId);

    //Integer update(EcuQuoted record);
}
