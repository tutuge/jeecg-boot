package org.jeecg.modules.cable.mapper.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.common.system.vo.EcUser;

import java.util.List;

@Mapper
public interface EcUserMapper extends BaseMapper<EcUser> {

    EcUser getObject(EcUser record);// 根据ID查找

    List<EcUser> getList(EcUser record);// 获取用户列表

    long getCount(EcUser record);

    // getObjectPassEcUsername
    EcUser getObjectPassEcUsername(EcUser record);

    // getObjectPassEcPhone
    EcUser getObjectPassEcPhone(EcUser record);

    // getObjectPassCode
    EcUser getObjectPassCode(EcUser record);

}
