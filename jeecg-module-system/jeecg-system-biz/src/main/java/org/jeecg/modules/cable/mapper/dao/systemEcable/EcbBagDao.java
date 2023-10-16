package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbBag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbBagDao {
    List<EcbBag> getList(EcbBag record);//获取数据列表

    List<EcbBag> getListStart(EcbBag record);

    long getCount();//获取总数

    EcbBag getObject(EcbBag record);//根据ecbbId获取EcbBag
}
