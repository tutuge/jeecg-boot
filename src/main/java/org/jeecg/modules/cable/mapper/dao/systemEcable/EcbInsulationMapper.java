package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbInsulation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EcbInsulationMapper extends BaseMapper<EcbInsulation> {
    List<EcbInsulation> getList(EcbInsulation record);//获取数据列表

    List<EcbInsulation> getListStart(EcbInsulation record);//获取启用列表

    long getCount();//获取总数

    EcbInsulation getObject(EcbInsulation record);//根据ecbiId获取EcbInsulation


    EcbInsulation getSysObject(EcbInsulation record);

    List<EcbInsulation> getSysList(EcbInsulation record);

    long getSysCount(EcbInsulation record);
}
