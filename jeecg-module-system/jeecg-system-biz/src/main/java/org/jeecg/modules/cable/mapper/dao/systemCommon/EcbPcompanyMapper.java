package org.jeecg.modules.cable.mapper.dao.systemCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.systemCommon.EcbPcompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbPcompanyMapper extends BaseMapper<EcbPcompany> {
    //getList
    List<EcbPcompany> getList(EcbPcompany record);

    //getCount
    long getCount(EcbPcompany record);

    //getObject
    EcbPcompany getObject(EcbPcompany record);

}
