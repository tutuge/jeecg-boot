package org.jeecg.modules.cable.mapper.dao.pcc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.pcc.EcProvince;

import java.util.List;

@Mapper
public interface EcProvinceMapper extends BaseMapper<EcProvince> {

    List<EcProvince> getList(EcProvince record);


    EcProvince getObject(EcProvince record);
}
