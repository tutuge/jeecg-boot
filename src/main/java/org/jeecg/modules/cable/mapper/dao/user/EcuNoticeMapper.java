package org.jeecg.modules.cable.mapper.dao.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.user.EcuNotice;

import java.util.List;

@Mapper
public interface EcuNoticeMapper extends BaseMapper<EcuNotice> {
    EcuNotice getObject(EcuNotice record);

    List<EcuNotice> getList(EcuNotice record);

    long getCount(EcuNotice record);

    Integer delete(EcuNotice record);

}
