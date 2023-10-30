package org.jeecg.modules.cable.mapper.dao.systemEcable;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.cable.entity.systemEcable.EcbSheath;

import java.util.List;

@Mapper
public interface EcbSheathMapper extends BaseMapper<EcbSheath> {
    List<EcbSheath> getList(EcbSheath record);// 获取数据列表

    List<EcbSheath> getListStart(EcbSheath record);

    long getCount();// 获取总数

    EcbSheath getObject(EcbSheath record);// 根据EcbSheath获取EcbSheath


    EcbSheath getSysObject(EcbSheath record);

    List<EcbSheath> getSysList(EcbSheath record);

    long getSysCount(EcbSheath record);
}
