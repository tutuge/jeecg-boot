package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbShield;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbShieldMapper extends BaseMapper<EcbShield> {
    List<EcbShield> getList(EcbShield record);

    List<EcbShield> getListStart(EcbShield record);//获取启用列表

    long getCount();//获取总数

    EcbShield getObject(EcbShield record);
    List<EcbShield> getSysList(EcbShield record);
    long getSysCount(EcbShield record);
}
