package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbMicatape;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbMicatapeDao {
    List<EcbMicatape> getList(EcbMicatape record);//获取数据列表

    List<EcbMicatape> getListStart(EcbMicatape record);//获取启用列表

    long getCount();//获取总数

    EcbMicatape getObject(EcbMicatape record);//根据EcbMicatape获取EcbMicatape
}
