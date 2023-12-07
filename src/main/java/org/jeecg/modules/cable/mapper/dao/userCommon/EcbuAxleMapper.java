package org.jeecg.modules.cable.mapper.dao.userCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.userCommon.EcbuAxle;

import java.util.List;

@Mapper
public interface EcbuAxleMapper extends BaseMapper<EcbuAxle> {

    List<EcbuAxle> getList(EcbuAxle record);

    long getCount(EcbuAxle record);

    EcbuAxle getObject(EcbuAxle record);

    //获取大于指定序号的数据列表
    List<EcbuAxle> getListGreaterThanSortId(EcbuAxle record);

    // getObjectPassAxleName
    EcbuAxle getObjectPassAxleName(EcbuAxle record);

    EcbuAxle getLatestObject(EcbuAxle record);
}
