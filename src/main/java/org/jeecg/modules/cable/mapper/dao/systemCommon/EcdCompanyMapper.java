package org.jeecg.modules.cable.mapper.dao.systemCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.systemCommon.EcdCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcdCompanyMapper extends BaseMapper<EcdCompany> {
    List<EcdCompany> getList(EcdCompany record);

    Long getCount(EcdCompany record);

    EcdCompany getObject(EcdCompany record);

}
