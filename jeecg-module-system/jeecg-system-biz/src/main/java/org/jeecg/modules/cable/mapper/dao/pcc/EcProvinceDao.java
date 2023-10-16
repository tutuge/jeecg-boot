package org.jeecg.modules.cable.mapper.dao.pcc;

import org.jeecg.modules.cable.entity.pcc.EcProvince;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcProvinceDao {
    //getList 获取启用中的列表
    List<EcProvince> getList(EcProvince record);
    //getObject
    EcProvince getObject(EcProvince record);
}
