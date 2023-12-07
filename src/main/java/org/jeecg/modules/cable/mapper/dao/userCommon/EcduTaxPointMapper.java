package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userCommon.EcduTaxPoint;

import java.util.List;

@Mapper
public interface EcduTaxPointMapper extends BaseMapper<EcduTaxPoint> {

    List<EcduTaxPoint> getList(EcduTaxPoint record);

    long getCount(EcduTaxPoint record);


    EcduTaxPoint getObject(EcduTaxPoint record);

    Integer deletePassEcCompanyIdAndEcdtId(EcduTaxPoint record);
}
