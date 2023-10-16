package org.jeecg.modules.cable.mapper.dao.systemEcable;

import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbSheathDao {
    List<EcbSheath> getList(EcbSheath record);//获取数据列表

    List<EcbSheath> getListStart(EcbSheath record);

    long getCount();//获取总数

    EcbSheath getObject(EcbSheath record);//根据EcbSheath获取EcbSheath
}
