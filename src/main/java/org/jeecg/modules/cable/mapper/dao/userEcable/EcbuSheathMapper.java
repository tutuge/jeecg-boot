package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuSheath;

import java.util.List;

@Mapper
public interface EcbuSheathMapper extends BaseMapper<EcbuSheath> {
    EcbuSheath getObject(EcbuSheath record);

    List<EcbuSheath> getList(EcbuSheath record);

    Integer deleteByCompanyId(EcbuSheath record);
}
