package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.userCommon.EcduCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcduCompanyMapper extends BaseMapper<EcduCompany> {
    List<EcduCompany> getList(EcduCompany record);

    long getCount(EcduCompany record);

    EcduCompany getObject(EcduCompany record);


    Integer updateByIdOrCompanyId(EcduCompany record);

    Integer deleteByIdOrCompanyId(EcduCompany record);

    
    List<EcduCompany> getListGreaterThanSortId(EcduCompany record);

    //getObjectPassAbbreviationOrFullName
    EcduCompany getObjectPassAbbreviationOrFullName(EcduCompany record);


    EcduCompany getLatestObject(EcduCompany record);
}
