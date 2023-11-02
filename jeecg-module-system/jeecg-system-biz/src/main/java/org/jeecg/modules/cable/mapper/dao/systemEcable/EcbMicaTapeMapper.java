package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbMicaTape;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbMicaTapeMapper extends BaseMapper<EcbMicaTape> {
    List<EcbMicaTape> getList(EcbMicaTape record);//获取数据列表

    List<EcbMicaTape> getListStart(EcbMicaTape record);//获取启用列表

    long getCount();//获取总数

    EcbMicaTape getObject(EcbMicaTape record);

    List<EcbMicaTape> getSysList(EcbMicaTape record);

    long getSysCount(EcbMicaTape record);
}
