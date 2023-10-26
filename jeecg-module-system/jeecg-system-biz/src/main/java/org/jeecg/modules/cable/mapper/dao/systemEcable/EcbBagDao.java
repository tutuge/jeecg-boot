package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbBag;

import java.util.List;

@Mapper
public interface EcbBagDao {


    EcbBag getSysObject(EcbBag record);//根据ecbbId获取EcbBag

    List<EcbBag> getSysList(EcbBag record);

    long getSysCount(EcbBag record);

    Integer insert(EcbBag record);

    Integer update(EcbBag record);

    Integer delete(EcbBag record);


//    -----------------下面是用户相关------------------------
    List<EcbBag> getList(EcbBag record);//获取数据列表

    List<EcbBag> getListStart(EcbBag record);

    long getCount();//获取总数

    EcbBag getObject(EcbBag record);//根据ecbbId获取EcbBag
}
