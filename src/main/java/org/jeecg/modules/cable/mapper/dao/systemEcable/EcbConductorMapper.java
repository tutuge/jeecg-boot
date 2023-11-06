package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbConductor;

import java.util.List;

@Mapper
public interface EcbConductorMapper extends BaseMapper<EcbConductor> {
    List<EcbConductor> getList(EcbConductor record);// 获取数据列表

    List<EcbConductor> getListStart(EcbConductor record);// 获取启用列表

    long getCount();// 获取总数

    EcbConductor getObject(EcbConductor record);

    EcbConductor getSysObject(EcbConductor record);

    List<EcbConductor> getSysList(EcbConductor record);

    long getSyCount(EcbConductor record);

}
