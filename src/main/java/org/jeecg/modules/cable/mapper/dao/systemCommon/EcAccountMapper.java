package org.jeecg.modules.cable.mapper.dao.systemCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.cable.entity.systemCommon.EcAccount;

/**
 * 账户价格
 */
@Mapper
public interface EcAccountMapper extends BaseMapper<EcAccount> {

    @Select("select * from ec_account where start_type=1 order by sort_id desc limit 1")
    EcAccount selectLastObject();
}
