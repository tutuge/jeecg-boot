package org.jeecg.modules.cable.mapper.dao.systemPcc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemPcc.EcProvince;

import java.util.List;

@Mapper
public interface EcProvinceMapper extends BaseMapper<EcProvince> {

    List<EcProvince> getList(EcProvince record);


    EcProvince getObjectByName(EcProvince record);
}
