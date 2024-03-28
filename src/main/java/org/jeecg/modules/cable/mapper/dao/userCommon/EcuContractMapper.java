package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userCommon.EcuContract;
import org.jeecg.modules.cable.entity.userCommon.EcuQualified;

@Mapper
public interface EcuContractMapper extends BaseMapper<EcuContract> {
}
