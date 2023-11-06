package org.jeecg.modules.cable.mapper.dao.userOffer;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.userOffer.EcuoCore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcuoCoreMapper extends BaseMapper<EcuoCore> {
    List<EcuoCore> getList(EcuoCore record);

    EcuoCore getObject(EcuoCore record);
}
