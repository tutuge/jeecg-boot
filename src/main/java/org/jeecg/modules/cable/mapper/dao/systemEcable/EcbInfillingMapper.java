package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbInfilling;

import java.util.List;

@Mapper
public interface EcbInfillingMapper extends BaseMapper<EcbInfilling> {
    List<EcbInfilling> getList(EcbInfilling record);// 获取数据列表

    List<EcbInfilling> getListStart(EcbInfilling record);

    long getCount();// 获取总数

    EcbInfilling getObject(EcbInfilling record);// 根据EcbInfilling获取EcbInfilling

    EcbInfilling getSysObject(EcbInfilling record);

    List<EcbInfilling> getSysList(EcbInfilling record);

    long getSysCount(EcbInfilling record);

}
