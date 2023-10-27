package org.jeecg.modules.cable.mapper.dao.userOffer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userOffer.EcuoProgramme;

@Mapper
public interface EcuoProgrammeMapper extends BaseMapper<EcuoProgramme> {

    EcuoProgramme getObject(EcuoProgramme record);
}
