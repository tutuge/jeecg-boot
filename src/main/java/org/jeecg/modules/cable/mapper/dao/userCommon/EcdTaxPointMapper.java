package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcdTaxPoint;

import java.util.List;

@Mapper
public interface EcdTaxPointMapper extends BaseMapper<EcdTaxPoint> {

    List<EcdTaxPoint> getList(EcdTaxPoint record);

    long getCount(EcdTaxPoint record);


    EcdTaxPoint getObject(EcdTaxPoint record);
}
