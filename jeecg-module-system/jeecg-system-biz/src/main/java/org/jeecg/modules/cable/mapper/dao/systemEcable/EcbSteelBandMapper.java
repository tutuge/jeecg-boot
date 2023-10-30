package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbSteelBand;

import java.util.List;

@Mapper
public interface EcbSteelBandMapper extends BaseMapper<EcbSteelBand> {
    List<EcbSteelBand> getList(EcbSteelBand record);// 获取数据列表

    List<EcbSteelBand> getListStart(EcbSteelBand record);

    long getCount();// 获取总数

    EcbSteelBand getObject(EcbSteelBand record);


    EcbSteelBand getSysObject(EcbSteelBand record);

    List<EcbSteelBand> getSysList(EcbSteelBand record);

    long getSysCount(EcbSteelBand record);
}
