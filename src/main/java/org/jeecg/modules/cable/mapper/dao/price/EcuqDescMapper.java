package org.jeecg.modules.cable.mapper.dao.price;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.price.EcuqDesc;

import java.util.List;

@Mapper
public interface EcuqDescMapper extends BaseMapper<EcuqDesc> {

    List<EcuqDesc> getList(EcuqDesc record);


    EcuqDesc getObject(EcuqDesc record);

    //deletePassEcuqiId
    void deletePassEcuqiId(Integer ecuqiId);

    Integer update(EcuqDesc record);
}
