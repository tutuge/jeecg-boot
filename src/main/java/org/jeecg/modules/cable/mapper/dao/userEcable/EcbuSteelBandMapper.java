package org.jeecg.modules.cable.mapper.dao.userEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userEcable.EcbuSteelBand;

import java.util.List;

@Mapper
public interface EcbuSteelBandMapper extends BaseMapper<EcbuSteelBand> {
    EcbuSteelBand getObject(EcbuSteelBand record);

    List<EcbuSteelBand> getList(EcbuSteelBand record);

    Integer deleteByEcCompanyId(EcbuSteelBand record);
}
