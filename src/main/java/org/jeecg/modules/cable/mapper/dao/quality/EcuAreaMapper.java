package org.jeecg.modules.cable.mapper.dao.quality;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.quality.EcuArea;

import java.util.List;

@Mapper
public interface EcuAreaMapper extends BaseMapper<EcuArea> {

    List<EcuArea> getList(EcuArea record);

    long getCount(EcuArea record);

    EcuArea getObject(EcuArea record);

    
    List<EcuArea> getListGreaterThanSortId(EcuArea record);

    //getObjectPassAreaStr
    EcuArea getObjectPassAreaStr(EcuArea record);

    EcuArea getLatestObject(EcuArea record);

    //deletePassEcqulId
    Integer deletePassEcqulId(Integer ecqulId);

    void batchInsert(List<EcuArea> areas);
}
