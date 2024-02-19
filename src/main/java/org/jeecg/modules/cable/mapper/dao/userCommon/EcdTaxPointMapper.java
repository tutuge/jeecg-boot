package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemCommon.EcdTaxPoint;

@Mapper
public interface EcdTaxPointMapper extends BaseMapper<EcdTaxPoint> {

    long getCount(EcdTaxPoint record);


    EcdTaxPoint getObject(EcdTaxPoint record);
}
