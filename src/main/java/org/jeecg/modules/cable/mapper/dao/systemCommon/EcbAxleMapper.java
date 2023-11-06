package org.jeecg.modules.cable.mapper.dao.systemCommon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemCommon.EcbAxle;

import java.util.List;

@Mapper
public interface EcbAxleMapper extends BaseMapper<EcbAxle> {

    List<EcbAxle> getList(EcbAxle record);

    // getCount
    long getCount(EcbAxle record);


    EcbAxle getObject(EcbAxle record);

    // getListGreaterThanSortId 获取大于指定序号的数据列表
    List<EcbAxle> getListGreaterThanSortId(EcbAxle record);

    // getObjectPassAxleName
    EcbAxle getObjectPassAxleName(EcbAxle record);

    // getLatestObject
    EcbAxle getLatestObject(EcbAxle record);
}
