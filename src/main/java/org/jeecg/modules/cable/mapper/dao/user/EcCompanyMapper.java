package org.jeecg.modules.cable.mapper.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.user.EcCompany;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcCompanyMapper extends BaseMapper<EcCompany> {
    EcCompany getObject(EcCompany record);//根据ID查找

}
