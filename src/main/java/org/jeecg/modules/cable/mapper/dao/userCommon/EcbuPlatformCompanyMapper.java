package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.controller.userCommon.pcompany.vo.EcbuPCompanyVo;
import org.jeecg.modules.cable.entity.userCommon.EcbuPlatformCompany;

import java.util.List;

@Mapper
public interface EcbuPlatformCompanyMapper extends BaseMapper<EcbuPlatformCompany> {
    List<EcbuPCompanyVo> getList(EcbuPlatformCompany record);

    long getCount(EcbuPlatformCompany record);

    EcbuPCompanyVo getObject(EcbuPlatformCompany record);

    Integer deleteRecord(EcbuPlatformCompany record);

    
    List<EcbuPlatformCompany> getListGreaterThanSortId(EcbuPlatformCompany record);

    EcbuPlatformCompany getObjectPassPcName(EcbuPlatformCompany record);


    EcbuPlatformCompany getLatestObject(EcbuPlatformCompany record);
}
