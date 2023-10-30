package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbMicatapeMapper extends BaseMapper<EcbMicatape> {
    List<EcbMicatape> getList(EcbMicatape record);//获取数据列表

    List<EcbMicatape> getListStart(EcbMicatape record);//获取启用列表

    long getCount();//获取总数

    EcbMicatape getObject(EcbMicatape record);

    List<EcbMicatape> getSysList(EcbMicatape record);

    long getSysCount(EcbMicatape record);
}
