package org.jeecg.modules.cable.mapper.dao.systemCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.controller.systemCommon.platformCompany.vo.EcbPlatformCompanyVo;
import org.jeecg.modules.cable.entity.systemCommon.EcbPlatformCompany;

import java.util.List;

@Mapper
public interface EcbPlatformCompanyMapper extends BaseMapper<EcbPlatformCompany> {

    List<EcbPlatformCompanyVo> getList(EcbPlatformCompany record);


    long getCount(EcbPlatformCompany record);


    EcbPlatformCompanyVo getObject(EcbPlatformCompany record);

}
